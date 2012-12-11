package edu.bsu.cs222.warVesselsGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State of the game that is the entry point to the game. Provides access to
 * both the intructions page as well as the game play state.
 * 
 * @author Dan
 * 
 */
public class MainMenuState extends BasicGameState {
	/**
	 * logger to send messages to the logfile
	 */
	private Logger logger = LoggerFactory.getLogger(MainMenuState.class);

	/**
	 * state initialized to -1, will be changed via the constructor
	 */
	int stateID = -1;

	/**
	 * constants to draw the menu
	 */
	int MENUOFFSET = 27, MENUX = 40, MENUY = 40;

	/**
	 * collection of sprites
	 */
	SpriteStore sprites;

	/**
	 * Images used
	 */
	Image background = null;
	Image newGameButton = null;
	Image instructionsButton = null;

	/**
	 * construtor, changes the stateID
	 * 
	 * @param stateID
	 */
	public MainMenuState(int stateID) {
		this.stateID = stateID;

	}

	/**
	 * initialize the state
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		try {
			sprites = new SpriteStore();
		} catch (SlickException e) {
			logger.error("Error intializing state.");
		}
		logger.info("main menu state initialized");

		background = new Image("images/WVMainScreen.jpg");
		newGameButton = sprites.newGameButton;
		instructionsButton = sprites.instructionsButton;

	}

	/**
	 * render the state to the screen
	 */
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw(0, 0);
		newGameButton.draw(MENUX, MENUY);
		instructionsButton.draw(MENUX, MENUY + MENUOFFSET);

	}

	/**
	 * what to do when updating the game state
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbgame, int delta)
			throws SlickException {
		Input input = gc.getInput();

		// get mouse location
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		boolean insideNewGameButton = false;

		// if mouse is within bounds of mainMenu button
		if ((mouseX >= MENUX && mouseX <= MENUX + newGameButton.getWidth())
				&& (mouseY >= MENUY && mouseY <= MENUY
						+ newGameButton.getHeight())) {
			insideNewGameButton = true;
		}
		// enter game play state
		if (insideNewGameButton) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				sbgame.enterState(WarVesselsGame.GAMEPLAYSTATE);
				logger.info("entering game play state");
			}
		}

		boolean insideInstructionsButton = false;

		// if mouse is within bounds of mainMenu button
		if ((mouseX >= MENUX && mouseX <= MENUX + instructionsButton.getWidth())
				&& (mouseY >= MENUY + MENUOFFSET && mouseY <= MENUY
						+ MENUOFFSET + instructionsButton.getHeight())) {
			insideInstructionsButton = true;
		}
		// enter game play state
		if (insideInstructionsButton) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				sbgame.enterState(WarVesselsGame.INSTRUCTSTATE);
				logger.info("entering instructions state");
			}
		}

	}

	/**
	 * returns the stateID
	 */
	@Override
	public int getID() {
		return stateID;
	}

}
