package edu.bsu.cs222.warVesselsGame;
import java.util.ArrayList;
import java.util.EnumMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import edu.bsu.cs222.warVessels.ShipType;

/**
 * Class handles the creation of subimages used throughout the game.  Uses the WVSprites image and assigns only the 
 * relevant pixels to each Image.  Not concerned with OO because the class really is just a convenient repository of images
 * used at runtime.
 * 
 * @author Dan
 *
 */
public class SpriteStore {
		
	//plan sprites for ships
	public Image carrierPlan;
	public Image battleshipPlan;
	public Image submarinePlan;
	public Image destroyerPlan;
	public Image patrol_boatPlan;
	
	//profile sprites for ships
	public Image carrierProfile;
	public Image battleshipProfile;
	public Image submarineProfile;
	public Image destroyerProfile;
	public Image patrol_boatProfile;
	
	//sprites to render guesses on the grid
	public Image guessOceanMiss;
	public Image guessOceanHit;
	public Image guessRadarHit;
	public Image guessRadarMiss;
	
	//sprites associated with game navigation
	public Image menuButton;
	public Image newGameButton;
	public Image instructionsButton;
	public Image startGameLight;
	
	//red X to mark a ship as sunk
	public Image profileX;
	
	//sprite to give red overlay to screen
	public Image redFlash;
	
	//text for the end-game screen
	public Image playerWin;
	public Image playerLose;
	
	//map of plan sprites
	private EnumMap<ShipType, Image> shipSprites;
	
	//list of profile sprites
	public ArrayList<Image> profileList;
	
	/**
	 * constructor
	 * @throws SlickException
	 */
	public SpriteStore() throws SlickException{
		Image spriteSheet = new Image("images/WVSprites.gif");
		createSprites(spriteSheet);
	}

	/**takes a single image file and breaks it into smaller sub images, to be used by game states
	 * 
	 * @param spriteSheet
	 */
	private void createSprites(Image spriteSheet) {
		
		//sprites for ship plans
		carrierPlan = spriteSheet.getSubImage(275, 7, 210, 42);
		battleshipPlan = spriteSheet.getSubImage(277, 125, 168, 42);
		submarinePlan = spriteSheet.getSubImage(275, 66, 125, 42);
		destroyerPlan = spriteSheet.getSubImage(275, 198, 127, 42);
		patrol_boatPlan = spriteSheet.getSubImage(275, 272, 83, 42);
 
		//adds ships to map for object oriented usage in GamePlayState
		shipSprites = new EnumMap<ShipType, Image>(ShipType.class);
		shipSprites.put(ShipType.CARRIER, carrierPlan);
		shipSprites.put(ShipType.BATTLESHIP, battleshipPlan);
		shipSprites.put(ShipType.SUBMARINE, submarinePlan);
		shipSprites.put(ShipType.DESTROYER, destroyerPlan);
		shipSprites.put(ShipType.PATROL_BOAT, patrol_boatPlan);
		
		//sprites for ship profiles
		carrierProfile = spriteSheet.getSubImage(62, 8, 186,21);
		battleshipProfile = spriteSheet.getSubImage(71, 72, 173, 35);
		submarineProfile = spriteSheet.getSubImage(77, 49, 154, 14);
		destroyerProfile = spriteSheet.getSubImage(66, 128, 130, 21);
		patrol_boatProfile = spriteSheet.getSubImage(81, 171, 58, 14);
		
		//adds profiles to a list for iterating
		profileList = new ArrayList<Image>();
		profileList.add(carrierProfile);
		profileList.add(battleshipProfile);
		profileList.add(submarineProfile);
		profileList.add(destroyerProfile);
		profileList.add(patrol_boatProfile);
		
		//sprites for various button elements
		menuButton = spriteSheet.getSubImage(292, 517, 140, 25);
		newGameButton = spriteSheet.getSubImage(292, 400, 125, 25);
		instructionsButton = spriteSheet.getSubImage(292, 456, 170, 25);
		startGameLight = spriteSheet.getSubImage(13, 241, 162, 115);
		
		//sprites for guesses
		guessOceanHit = spriteSheet.getSubImage(17, 12, 25, 23);
		guessOceanMiss = spriteSheet.getSubImage(20, 43, 29, 29);
		guessRadarHit = spriteSheet.getSubImage(18, 76, 36, 36);
		guessRadarMiss = spriteSheet.getSubImage(24,112, 21,24);
		
		//to cross out ship when sunk
		profileX = spriteSheet.getSubImage(25, 155, 25, 25);
		
		//red flash when hit, grabs small image and scales it up
		redFlash = spriteSheet.getSubImage(21, 386, 10, 7).getScaledCopy(1000, 667);
		
		//player win/lose sprites
		playerWin = spriteSheet.getSubImage(7, 432, 183, 31);
		playerLose = spriteSheet.getSubImage(8, 517, 187, 30);
	}
	
	
	
	public EnumMap<ShipType, Image> getShipSprite(){
		return shipSprites;
	}
	
}
