import Exception.GameLaunchException;
import GameClient.*;
import State.*;
import IO.*;

/**
 * The main file for playing Boomerang. It is from here all
 * setting for the game are choosen, where the GameClient and
 * GameState are initiated. It is also from here players join
 * a hosted game.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class Boomerang {
	/**
	 * Handles starting or joining a game. If the player is hosting a game they
	 * can choose the number of real/bot players are going to be included in the
	 * game and choose the game settings (edition, rules, bot behavior). A player
	 * can also choose to join a game from here, by running the file with the 
	 * argument as the IP-address of the player they are trying to join.
	 * 
	 * @param argv The input arguments for the player trying to host or join a game.
	 */
    public static void main(String argv[]) {
		try {
			if(argv.length == 2 || argv.length == 5){
				GameClientBuilder gameBuilder = new GameClientBuilder();
				if(argv.length == 2){
					gameBuilder.setNumPlayers(Integer.valueOf(argv[0]));
					gameBuilder.setNumBots(Integer.valueOf(argv[1]));
				} else if (argv.length == 5){
					gameBuilder.setNumPlayers(Integer.valueOf(argv[0]));
					gameBuilder.setNumBots(Integer.valueOf(argv[1]));
					gameBuilder.setEdition(Integer.valueOf(argv[2]));
					gameBuilder.setRules(Integer.valueOf(argv[3]));
					gameBuilder.setBotBehavior(Integer.valueOf(argv[4]));
				}

				GameClient gameClient = gameBuilder.getClient();
				GameState gameState = new ShuffleAndDealState();
				gameState.setGameClient(gameClient);
				while (gameState != null){
					gameState = gameState.runState();
				}
			} else if (argv.length == 1){
				GameJoin gameJoin = new GameJoin(argv[0]);
				gameJoin.run();
				gameJoin.disconnect();
			} else {
				throw new GameLaunchException("The game could not be launched. \nRemember that the format for launching a standard session is:\n\"Boomerang \'Number of real players\' \'Number of bot players\'\". \n\nTo launch a session with specific settings the format is:\n\"Boomerang \'Number of real players\' \'Number of bot players\' \'The edition of Boomerang (1 = Australia)\' \'What rules are being used (1 = standard rules)\' \'Bot behavior (1 = standard behavior)\' \"\n\n The format for joining an online session is:\n\"Boomerang \'Host ip address\'\".");
			}
		} catch(Exception e) {
			new Console().outputError(e);
		}
	}
}
