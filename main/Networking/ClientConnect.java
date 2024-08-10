package Networking;

import java.net.Socket;

import Exception.BoomerangIOException;

import java.lang.ClassNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Message.*;

/**
 * Used by a player to connect to a game being hosted at another computer.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class ClientConnect {
    /**
     * The socket the player is connected to.
     */
    private Socket aSocket;

    /**
     * The output-stream to the server. Used to send information to the server.
     */
    private ObjectOutputStream outToServer;

    /**
     * The input-stream from the server. Used to get information from the server.
     */
    private ObjectInputStream inFromServer;

    /**
     * Used to save the latest message from the server.
     */
    private Message message;

    /**
     * Tries to create a connection between the player and the host. Assumes that the
     * port used by the host computer is: 2048.
     * 
     * @param ipAddress The IP-address of the host that the player is connecting to.
     * @throws BoomerangIOException Gets thrown if something goes wrong with the IO.
     */
    public ClientConnect(String ipAddress) throws BoomerangIOException{
        try{
            aSocket = new Socket(ipAddress, 2048);
            outToServer = new ObjectOutputStream(aSocket.getOutputStream());
            inFromServer = new ObjectInputStream(aSocket.getInputStream());
        } catch (Exception e) {
            throw new BoomerangIOException(e);
        }
    }

    /**
     * Tries to create a connection between the player and the host. Used when the host is
     * not using port 2048.
     * 
     * @param ipAddress The IP-address of the host that the player is connecting to.
     * @param port The port that the host has opened.
     * @throws BoomerangIOException Gets thrown if something goes wrong with the IO.
     */
    public ClientConnect(String ipAddress, int port) throws BoomerangIOException{
        try{
            aSocket = new Socket(ipAddress, port);
            outToServer = new ObjectOutputStream(aSocket.getOutputStream());
            inFromServer = new ObjectInputStream(aSocket.getInputStream());
        } catch (Exception e) {
            throw new BoomerangIOException(e);
        }
    }

    /**
     * Tries to read the latest message from the server. If this fails the exception will be shown to
     * the player.
     * 
     * @return The lastest message from the server or an exception.
     * @throws BoomerangIOException Gets thrown if something goes wrong with the IO.
     * @throws ClassNotFoundException Gets thrown if the class being sent between the host
     * and the player can not be found.
     */
    public Message readMessage() throws BoomerangIOException, ClassNotFoundException{
        try{
            this.message = (Message)this.inFromServer.readObject();
        } catch (Exception e){
            this.message = new Information(e.getMessage());
            throw new BoomerangIOException(e);
        }
        return message;
    }

    /**
     * Sends a message to the host.
     * 
     * @param message The Message begin sent.
     * @throws BoomerangIOException Gets thrown if something goes wrong with the IO.
     */
    public void sendMessage(Message message) throws BoomerangIOException{
        try {
            outToServer.writeObject(message);
        } catch (Exception e) {
            throw new BoomerangIOException(e);
        }
    }

    /**
     * Closes the connection to the host.
     */
    public void disconnect(){
        try{
            outToServer.close();
            inFromServer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
