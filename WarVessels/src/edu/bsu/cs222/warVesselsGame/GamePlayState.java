package edu.bsu.cs222.warVesselsGame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.bsu.cs222.warVessels.AI;
import edu.bsu.cs222.warVessels.AbstractAI;
import edu.bsu.cs222.warVessels.Grid;
import edu.bsu.cs222.warVessels.Guess;
import edu.bsu.cs222.warVessels.ShipType;
import edu.bsu.cs222.warVessels.SmartAI;
import edu.bsu.cs222.warVessels.Statistics;

/**
 * Main state that runs during the game. Contains all the processes to reset the
 * game upon reentering the state, rendering the game, and handling user input.
 * 
 * @author Dan Buis
 * @author Steffan Byrne
 * @author Tom Foreman
 * @author Brandon Wolfe
 * 
 */
public class GamePlayState extends BasicGameState {

	private int stateID = -1;

	private Grid playerOceanGrid;
	private Grid playerRadarGrid;

	// maps used to tie Grid points to graphical rectangles
	private Map<Rectangle, Point> oceanRectangles;
	private Map<Rectangle, Point> radarRectangles;
	private Logger logger = LoggerFactory.getLogger(GamePlayState.class);
	// constants to tie pixel locations to grid cells.
	private final int OCEAN_X = 35, OCEAN_Y = 35, RADAR_X = 572, RADAR_Y = 35,
			GRID_INCREMENT = 42, BEGIN_SHIP_PLACE_TRAY_Y = 482;

	private final int MENU_X = 447, MENU_Y = 628;

	private int runningDeltaTotal;
	private int tempYCoord;

	private Statistics gameStats;
	private STATES currentState = null;

	// Sprite and image fields
	private SpriteStore sprites;
	private Image background = null;
	private Image mainMenuButton = null;
	private Image startHighlight = null;
	private Image startSmartAIlight = null;
	private Image redFlash = null;
	private Image win = null;
	private Image lose = null;
	private Image stats = null;

	// Rectangles used for button locations
	private Rectangle menuRect;
	private Rectangle regStartRect;
	private Rectangle smartStartRect;

	private boolean insideMenuButton;
	private boolean insideStartGame;
	private boolean insideStartSmartGame;
	private boolean readyToStart;
	private boolean won;
	private boolean renderRedFlash;
	private boolean validGuess;

	private EnumMap<ShipType, ShipEntity> shipEntities;
	private ShipType shipSelected;

	private Input input;

	private int mouseX;
	private int mouseY;

	private List<Point> radarHits;
	private List<Point> radarMisses;
	private List<Point> oceanHits;
	private List<Point> oceanMisses;

	private AbstractAI arty;

	public GamePlayState(int stateID) {
		this.stateID = stateID;

	}

	/**
	 * As best I can tell this method is only called when this state is
	 * Initialized.
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		try {
			sprites = new SpriteStore();
		} catch (SlickException e) {
			logger.error("Error intializing state.");
		}

		logger.info("game play state initialized");

		background = new Image("images/WVBackground.jpg");

		stats = new Image("images/Stats.jpg");
		mainMenuButton = sprites.menuButton;
		startHighlight = sprites.startGameLight;
		startSmartAIlight = sprites.startGameLight;
		redFlash = sprites.redFlash;
		win = sprites.playerWin;
		lose = sprites.playerLose;

		menuRect = new Rectangle(MENU_X, MENU_Y, mainMenuButton.getWidth(),
				mainMenuButton.getHeight());
		regStartRect = new Rectangle(37, 517, startHighlight.getWidth(),
				startHighlight.getHeight());
		smartStartRect = new Rectangle(817, 517, startSmartAIlight.getWidth(),
				startSmartAIlight.getHeight());

		shipEntities = new EnumMap<ShipType, ShipEntity>(ShipType.class);

		shipSelected = null;

		oceanRectangles = new HashMap<Rectangle, Point>();
		radarRectangles = new HashMap<Rectangle, Point>();

		// Make rectangle for every square on the ocean and radar grids
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Rectangle rect = new Rectangle(OCEAN_X + j * GRID_INCREMENT,
						OCEAN_Y + i * GRID_INCREMENT, GRID_INCREMENT,
						GRID_INCREMENT);
				oceanRectangles.put(rect, new Point(j, i));

				rect = new Rectangle(RADAR_X + j * GRID_INCREMENT, RADAR_Y + i
						* GRID_INCREMENT, GRID_INCREMENT, GRID_INCREMENT);
				radarRectangles.put(rect, new Point(j, i));
			}
		}

		input = gc.getInput();
	}

	public void enter(GameContainer gc, StateBasedGame sbg) {

		logger.info("game play state entered");
		logger.info("entering SETUP_STATE");
		logger.info("PLAYER PLACING SHIPS");

		currentState = STATES.SETUP_STATE;
		playerOceanGrid = new Grid();
		playerRadarGrid = new Grid();

		oceanHits = new ArrayList<Point>();
		oceanMisses = new ArrayList<Point>();
		radarHits = new ArrayList<Point>();
		radarMisses = new ArrayList<Point>();
		gameStats = new Statistics();
		// Creates ShipEntities and ties a sprite to them.
		tempYCoord = BEGIN_SHIP_PLACE_TRAY_Y;
		for (ShipType s : ShipType.values()) {
			ShipEntity aShip = new ShipEntity(s,
					sprites.getShipSprite().get(s), 341, tempYCoord);
			shipEntities.put(s, aShip);
			tempYCoord += 39;
		}

		readyToStart = false;
		validGuess = false;
	}

	/*
	 * this method handles drawing the stuff on the screen. Draws in the order
	 * given in the method.
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbc, Graphics g)
			throws SlickException {
		background.draw(0, 0);

		for (ShipType s : ShipType.values()) {
			shipEntities.get(s).render();
		}

		if (currentState == STATES.PLAYER_TURN_STATE
				|| currentState == STATES.AI_TURN_STATE) {
			renderOcean(playerOceanGrid);
			renderRadar(playerRadarGrid);
		}

		mainMenuButton.draw(MENU_X, MENU_Y);

		if (renderRedFlash) {
			renderFlash();
		}

		if (readyToStart && currentState == STATES.SETUP_STATE) {
			startHighlight.setAlpha((float) 0.60); // makes slightly transparant
			startHighlight.draw(regStartRect.getX(), regStartRect.getY());
			startSmartAIlight
					.draw(smartStartRect.getX(), smartStartRect.getY());
		}

		if (currentState == STATES.GAME_OVER_STATE) {
			renderStats(gc, sbc, g);
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbgame, int delta)
			throws SlickException {
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();

		switch (currentState) {
		case SETUP_STATE:
			setup(gc, sbgame, delta);
			break;
		case PLAYER_TURN_STATE:
			playerTurn(gc, sbgame, delta);
			break;
		case AI_TURN_STATE:
			aiTurn(gc, sbgame, delta);
		case GAME_OVER_STATE:
			gameOver(gc, sbgame, delta);
			break;
		}

		// if mouse is within bounds of mainMenu button
		if (menuRect.contains(mouseX, mouseY)) {
			insideMenuButton = true;
		} else {
			insideMenuButton = false;
		}

		// enter main menu state
		if (insideMenuButton) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				sbgame.enterState(WarVesselsGame.MAINMENUSTATE);
				logger.info("entering main menu state");
			}
		}

	}

	private void setup(GameContainer gc, StateBasedGame sbgame, int delta)
			throws SlickException {

		checkButtons(sbgame);
		checkShips();

		if (playerOceanGrid.shipsAfloat().size() == 5) {
			readyToStart = true;
		} else {
			readyToStart = false;
		}

	}

	private void checkButtons(StateBasedGame sbgame) {
		// This prevents the menu button from being triggered if the user
		// already has the mouse button clicked and moves over it.
		if (insideMenuButton == false && insideStartGame == false
				&& insideStartSmartGame == false
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			input.clearMousePressedRecord();
		}

		// if mouse is within bounds of startHighlight
		if (regStartRect.contains(mouseX, mouseY)) {
			insideStartGame = true;
		} else {
			insideStartGame = false;
		}

		// if clicked we start the game
		if (insideStartGame && readyToStart
				&& currentState == STATES.SETUP_STATE) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

				arty = new AI(playerRadarGrid, playerOceanGrid);
				logger.info("new regular AI created");
				logger.info("AI PLACING SHIPS");
				arty.placeAllShips();

				currentState = STATES.PLAYER_TURN_STATE;
				logger.info("entering PLAYER_TURN_STATE");
			}
		}

		// if mouse is inside button to start game with a smart AI
		if (smartStartRect.contains(mouseX, mouseY)) {
			insideStartSmartGame = true;
		} else {
			insideStartSmartGame = false;
		}

		// if clicked we start a game with a smartAI
		if (insideStartSmartGame && readyToStart
				&& currentState == STATES.SETUP_STATE) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

				arty = new SmartAI(playerRadarGrid, playerOceanGrid);
				logger.info("new smart AI created");
				logger.info("AI PLACING SHIPS");
				arty.placeAllShips();

				currentState = STATES.PLAYER_TURN_STATE;
				logger.info("entering PLAYER_TURN_STATE");
			}
		}
	}

	private void checkShips() {
		// Loop to check if the ships are being manipulated by the mouse
		for (ShipType s : ShipType.values()) {
			ShipEntity currShip = shipEntities.get(s);

			// if mouse is within bounds of ship sprite
			if ((shipSelected != null && shipSelected.equals(s))
					|| currShip.isMouseIn(mouseX, mouseY)) {

				if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
					if (!currShip.isSnapped())
						currShip.rotate();
				}

				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)
						&& (shipSelected == null || shipSelected.equals(s))) {
					if (currShip.isSnapped()) {
						currShip.unsnap();
						playerOceanGrid.removeShip(currShip.getShipObj());
					}

					shipSelected = s;
				}
			}

			// If the ship is selected i.e. the user has clicked it
			if (shipSelected != null && shipSelected.equals(s)) {
				currShip.setX(mouseX);
				currShip.setY(mouseY);

				// This is executed only when the mouse is released after
				// picking up a ship.
				if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

					if (currShip.shipOnGrid(oceanRectangles.keySet())) {

						// Gets the coordinates associated with the rectangle
						Rectangle rect = currShip.getRectangle();
						int x = (int) oceanRectangles.get(rect).getX();
						int y = (int) oceanRectangles.get(rect).getY();

						if (playerOceanGrid.validateLocation(x, y,
								currShip.getShipObj())) {
							playerOceanGrid
									.setShip(x, y, currShip.getShipObj());
							currShip.snap();
						} else {
							currShip.unsnap();
						}
					} else {
						currShip.unsnap();
					}

					shipSelected = null;
				}
			}

		} // end of for each loop for ships

	}

	private void playerTurn(GameContainer gc, StateBasedGame sbgame, int delta)
			throws SlickException {

		runningDeltaTotal += delta;

		// Find out which rectangle the mouse is in
		for (Rectangle r : radarRectangles.keySet()) {
			if (r.contains(mouseX, mouseY)) {
				Point gridCoord = radarRectangles.get(r);

				// Guess the coordinate that the mouse clicks
				if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

					logger.info("player guessed " + gridCoord.x + ","
							+ gridCoord.y);

					try {
						if (playerRadarGrid
								.checkGuess(gridCoord.x, gridCoord.y)) {
							radarHits.add(gridCoord);
							validGuess = true;
							gameStats.hit();
						} else {
							radarMisses.add(gridCoord);
							gameStats.miss();
							logger.info("player misses");
							validGuess = true;

						}
					} catch (IllegalArgumentException e) {
						logger.info("REPEAT GUESS!");
					}
					// only run this if a valid guess has been made, otherwise
					// the player can try again.
					if (validGuess) {
						if (arty.getOcean().shipsAfloat().size() == 0) {
							won = true;
							currentState = STATES.GAME_OVER_STATE;
							logger.info("GAME OVER, PLAYER WINS");
							logger.info("entering GAME_OVER_STATE");
							logStats();
						} else {
							currentState = STATES.AI_TURN_STATE;
							logger.info("entering AI_TURN_STATE");
							runningDeltaTotal = 0;
						}
					}
				}
			}
		}
	}

	private void aiTurn(GameContainer gc, StateBasedGame sbgame, int delta) {
		Point guessPoint = null;
		runningDeltaTotal += delta;

		if (runningDeltaTotal > 1000) {
			arty.guessLocation();
			guessPoint = arty.getGuess();

			logger.info("opponent guessed " + guessPoint.x + "," + guessPoint.y);

			if (playerOceanGrid.getStatus(guessPoint.x, guessPoint.y).equals(
					Guess.HIT)) {
				oceanHits.add(guessPoint);
				renderRedFlash = true;
			} else {
				oceanMisses.add(guessPoint);
				logger.info("opponent misses");
			}

			if (playerOceanGrid.shipsAfloat().size() == 0) {
				won = false;
				currentState = STATES.GAME_OVER_STATE;
				logger.info("GAME OVER, AI WINS");
				logger.info("entering GAME_OVER_STATE");
				logStats();
			} else {
				currentState = STATES.PLAYER_TURN_STATE;
				// resets boolean, this is the only place it needs to be done
				// b/c when
				// the player starts a game it is impossible to reguess spaces
				// for the first turn
				validGuess = false;
				logger.info("entering PLAYER_TURN_STATE");
			}
		}
	}

	private void gameOver(GameContainer gc, StateBasedGame sbgame, int delta) {
		runningDeltaTotal += delta;

	}

	@Override
	public int getID() {
		return stateID;
	}

	private void renderRadar(Grid g) {
		for (Point p : radarHits) {
			sprites.guessRadarHit.drawCentered(p.x * GRID_INCREMENT + RADAR_X
					+ 21, p.y * GRID_INCREMENT + RADAR_Y + 21);
		}

		for (Point p : radarMisses) {
			sprites.guessRadarMiss.drawCentered(p.x * GRID_INCREMENT + RADAR_X
					+ 21, p.y * GRID_INCREMENT + RADAR_Y + 21);
		}

		renderShipProfiles(685);
		renderShipsAfloat(g, 685);

	}

	private void renderOcean(Grid g) {
		for (Point p : oceanHits) {
			sprites.guessOceanHit.drawCentered(p.x * GRID_INCREMENT + OCEAN_X
					+ 21, p.y * GRID_INCREMENT + OCEAN_Y + 21);
		}
		for (Point p : oceanMisses) {
			sprites.guessOceanMiss.drawCentered(p.x * GRID_INCREMENT + OCEAN_X
					+ 21, p.y * GRID_INCREMENT + OCEAN_Y + 21);
		}
		renderShipProfiles(341);
		renderShipsAfloat(g, 341);

	}

	private void renderShipProfiles(int x) {
		tempYCoord = BEGIN_SHIP_PLACE_TRAY_Y;

		for (Image img : sprites.profileList) {
			img.drawCentered(x, tempYCoord);
			tempYCoord += 39;
		}
	}

	private void renderShipsAfloat(Grid g, int x) {
		tempYCoord = BEGIN_SHIP_PLACE_TRAY_Y;

		for (ShipType s : ShipType.values()) {
			if (!g.shipsAfloat().contains(s)) {
				sprites.profileX.drawCentered(x, tempYCoord);
			}
			tempYCoord += 39;
		}
	}

	private void renderStats(GameContainer gc, StateBasedGame sbc, Graphics g) {
		stats.draw(RADAR_X, RADAR_Y);
		g.setColor(Color.black);
		g.drawString("Guess Total: " + gameStats.getGuesses(), 595, 85);
		g.drawString("Hits: " + gameStats.getHits(), 595, 100);
		g.drawString("Misses: " + gameStats.getMisses(), 595, 115);
		g.drawString("Hit Rate: " + gameStats.getHitRate(), 595, 130);

		if (won == true) {
			win.drawCentered(782, 200);
		} else {
			lose.drawCentered(782, 200);
		}
	}

	private void renderFlash() {
		if (runningDeltaTotal < 1500 && runningDeltaTotal > 1000) {
			redFlash.setAlpha((float) 0.5);
			redFlash.draw(0, 0);
		} else {
			renderRedFlash = false;
		}
	}

	private void logStats() {
		logger.info("Total Guess: " + gameStats.getGuesses());
		logger.info("Total Hits: " + gameStats.getHits());
		logger.info("Total Misses: " + gameStats.getMisses());
		logger.info("Hit Rate: " + gameStats.getHitRate());
	}

}
