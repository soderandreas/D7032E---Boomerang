package IO;

import Exception.BoomerangIOException;
import Message.*;

/**
 * An interface for handeling input and output from local and online players.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public interface HandleIO {
    /**
     * Gets the input from a Real player.
     * 
     * @param choice The Choice Message which contains the choices that the player have.
     * @return The Response Message from the player.
     * @throws BoomerangIOException Thrown if the something goes wrong with the IO.
     * @throws ClassNotFoundException Thrown if the class being sent (i.e. Message) can not be found.
     */
    public Response getInput(Choice choice) throws BoomerangIOException, ClassNotFoundException;

    /**
     * Outputs a message to a Real player.
     * 
     * @param message The Message being shown to the player.
     * @throws BoomerangIOException Thrown if the something goes wrong with the IO.
     */
    public void outputMessage(Message message) throws BoomerangIOException;

    /**
     * Shows the player an exception that has been thrown.
     * 
     * @param e The exception.
     */
    public void outputError(Exception e);
}
