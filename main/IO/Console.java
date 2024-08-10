package IO;

import Message.*;

import java.util.Scanner;

/**
 * An implememtation of HandleIO used for local IO in a console setting. Used by 
 * both local and online players.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class Console implements HandleIO {
    /**
     * Scanner used to handle input by the user.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * String added to the beginning of every new output.
     */
    private String beginNewOutput = "*--------------------------------------------------------------------------------------------------------------*\n";


    /**
     * Gets the input from the player in the console.
     */
    public Response getInput(Choice choice){
        Response playerResponse = new Response(choice, scanner.nextLine());
        return playerResponse;
    }

    /**
     * Outputs a message to the console.
     */
    public void outputMessage(Message message){
        System.out.println(this.beginNewOutput + message.getMessage());
    }

    /**
     * Prints the stack trace for an exception
     */
    public void outputError(Exception e){
        e.printStackTrace();
    }
}
