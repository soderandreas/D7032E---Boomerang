package State;

import java.util.ArrayList;

import Player.*;

/**
 * Handles counting each players score and deciding 
 * who wins the game.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class GameOverState extends GameState {
    /**
     * Runs the state. Goes through each player and looks at their 
     * final scores and deciding who won. If two players have the same
     * score who won is decided based on who had the highest throw-catch
     * score.
     */
    public GameState runState(){
        ArrayList<Player> allPlayers = currGameClient.getPlayers();
        Player winningPlayer = null;
        int highestScore = 0;
        int highestScoreThrowCatch = 0;

        for(Player currPlayer : allPlayers){ // Find which player won
            int playerScore = currPlayer.getTotalScore();
            int playerThrowCatchScore = currPlayer.getThrowCatchScore();
            if(playerScore > highestScore){
                highestScore = playerScore;
                highestScoreThrowCatch = playerThrowCatchScore;
                winningPlayer = currPlayer;
            } else if (playerScore == highestScore){
                if(playerThrowCatchScore > highestScoreThrowCatch){
                    highestScore = playerScore;
                    highestScoreThrowCatch = playerThrowCatchScore;
                    winningPlayer = currPlayer;
                }
            }
        }

        for(Player currPlayer : allPlayers){ // output to all real players which the final results
            if(currPlayer instanceof RealPlayer){
                if(currPlayer == winningPlayer){
                    ((RealPlayer)currPlayer).outputWinner();
                } else {
                    ((RealPlayer)currPlayer).outputLoser();
                }
                ((RealPlayer)currPlayer).outputPlayersFinalscore(allPlayers);
            }
        }

        return null;
    }
}
