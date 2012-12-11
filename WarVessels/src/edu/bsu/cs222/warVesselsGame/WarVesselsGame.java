package edu.bsu.cs222.warVesselsGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the game.  Game is built of different "states" that control what screen you 
 * see and what actions you are able to perform.  Built from the tutorial at 
 * http://slick.cokeandcode.com/wiki/doku.php?id=02_-_slickblocks#creating_the_game_states
 * @author Dan
 *
 */
public class WarVesselsGame extends StateBasedGame{
	
	private Logger logger = LoggerFactory.getLogger(WarVesselsGame.class);
	
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	public static final int INSTRUCTSTATE = 2;

	public WarVesselsGame(String name) {
		super(name);
		logger.info("WAR VESSELS GAME adding states");
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GamePlayState(GAMEPLAYSTATE));
		this.addState(new InstructionsState(INSTRUCTSTATE));
		this.enterState(MAINMENUSTATE); //this line controls entry point of game
		logger.info("WAR VESSELS GAME entering main menu state");
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new WarVesselsGame("War Vessels"));
		
		app.setDisplayMode(1000,667,false);
		app.setTargetFrameRate(60);
		app.setShowFPS(false);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		logger.info("WAR VESSELS GAME initializing states");
		
 		this.getState(MAINMENUSTATE).init(gameContainer, this);
		this.getState(GAMEPLAYSTATE).init(gameContainer, this);
		this.getState(INSTRUCTSTATE).init(gameContainer, this);
	}

}
