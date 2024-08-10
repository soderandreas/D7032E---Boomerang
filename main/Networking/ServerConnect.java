package Networking;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Exception.BoomerangIOException;

/**
 * Creates a server that it is possible to connect to. Used by players
 * wishing to host a game.
 * 
 * @author Andreas Söderman
 * @version 1.0
 * @since 1.0
 */
public class ServerConnect {
    /**
     * The socket being opened on the host computer
     */
    private ServerSocket aSocket;

    /**
     * Opens a socket on the host computer for other players to connect to.
     * 
     * @throws BoomerangIOException Thrown if something goes wrong with the IO between the players.
     */
    public ServerConnect() throws BoomerangIOException{
        try{
            this.aSocket = new ServerSocket(2048);
        } catch (IOException e){
            throw new BoomerangIOException(e);
        }
    }

    /**
     * Opens a socket chosen by the host for other players to connect to.
     * 
     * @param port The port that will be opened on the host computer.
     * @throws BoomerangIOException Thrown if something goes wrong with the IO between the players.
     */
    public ServerConnect(int port) throws BoomerangIOException{
        try{
            this.aSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new BoomerangIOException(e);
        }
    }

    /**
     * Creates a connection to a between the host and a joining online player.
     * 
     * @return The connection between the host and the new player.
     * @throws BoomerangIOException Thrown if something goes wrong with the IO between the players.
     */
    public OnlinePlayerConnect createConnection() throws BoomerangIOException{
        Socket connectionSocket = null;
        ObjectInputStream inFromClient = null;
        ObjectOutputStream outToClient = null;

        try{
            connectionSocket = aSocket.accept();
            inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
        } catch (IOException e){
            throw new BoomerangIOException(e);
        }

        return new OnlinePlayerConnect(inFromClient, outToClient);
    }
}