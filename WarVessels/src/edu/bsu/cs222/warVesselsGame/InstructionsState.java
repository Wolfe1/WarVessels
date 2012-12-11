package edu.bsu.cs222.warVesselsGame;

import org.slf4j.Logger;   
import org.slf4j.LoggerFactory;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
/**
 * Simple game state that will give instructions to the player about how to play this game.
 * @author Dan
 *
 */
public class InstructionsState extends BasicGameState{
	/**
	 * logger to send messages to the logfile
	 */
	private Logger logger = LoggerFactory.getLogger(InstructionsState.class);
	
	/**
	 * state initialized to -1, will be changed via the constructor
	 */
	int stateID = -1;
	
	/**
	 * constants to draw the menu
	 */
	int MENUX = 40,
		MENUY = 40;
	
	/**
	 * collection of sprites
	 */
	SpriteStore sprites;
	
	/**
	 * Images used
	 */
	Image background = null;
	Image mainMenuButton = null;  
	
	/**
	 * construtor, changes the stateID
	 * @param stateID
	 */
	public InstructionsState(int stateID){
		this.stateID = stateID;
	}

	/**
	 * initialize the state
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		try{
			sprites = new SpriteStore();
		} catch (SlickException e){
			logger .error("Error intializing state.");
		}
		logger.info("instructions state initialized");
		background = new Image("images/WVMainScreen.jpg");
		mainMenuButton = sprites.menuButton;
	}

	/**
	 * render the state to the screen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbgame, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		g.setColor(Color.black);
		g.fillRect(45, 65, 645, 500);
		g.setColor(Color.white);
		g.drawString("War Vessels Instructions", 55, 70);
		g.drawString("On the main menu select 'New Game' to start a new game of War Vessels.", 55, 100);
		g.drawString("From this screen you, the player, must strategically place your ships", 55, 125);
		g.drawString("in order to elude your enemies attacks.", 55, 140);
		g.drawString("Once all of your ships have been placed from here you must make a", 55, 170);
		g.drawString("decision. Should you play against a regular opponent or take on the", 55, 185);
		g.drawString("challenge of a smarter one? You make this decision by selecting", 55, 200);
		g.drawString("the start button for either opponent.", 55, 215);
		g.drawString("At this point the game enters the battle phase and you may make", 55, 245);
		g.drawString("your first move by slecting a space to attack on the radar screen,", 55, 260);
		g.drawString("the grid on the right. The game will respond with a hit or a miss", 55, 275);
		g.drawString("for each space and your opponent will then take a shot at your ships.", 55, 290);
		g.drawString("As the game progresses ships will be hit, once the ship is sunk", 55, 320);
		g.drawString("it will be marked in the tray with a large red X", 55, 335);
		g.drawString("The game continues until either you or your opponent has", 55, 350);
		g.drawString("destroyed all of the opposing ships.", 55, 365);
		g.drawString("Once the game is over the opposing player's grid will be shown", 55, 395);
		g.drawString("and statistics will be displayed. You may then go back to the", 55, 410);
		g.drawString("main menu and play another game.", 55, 425);
		mainMenuButton.draw(MENUX, MENUY);
		
	}

	/**
	 * what to do when updating the game state
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbgame, int delta)
			throws SlickException {
		Input input = gc.getInput();
		
		//get mouse location
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		boolean insideMenuButton = false;
		
		//if mouse is within bounds of mainMenu button
		if ((mouseX >= MENUX && mouseX <= MENUX + mainMenuButton.getWidth()) &&
        ( mouseY >= MENUY && mouseY <= MENUY + mainMenuButton.getHeight())){
			insideMenuButton = true;
		}
		
		//enter main menu state when clicked
		if(insideMenuButton){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				sbgame.enterState(WarVesselsGame.MAINMENUSTATE);
				logger.info("entering main menu state");
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
