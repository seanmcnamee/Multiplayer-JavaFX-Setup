package communications;

import java.io.IOException;
import java.net.Socket;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import java.net.UnknownHostException;

import app.App;
import shared.LocationUpdate;
import shared.UserIdentifier;

/**
 * The ChatClient deals with overall communication with the server through the socket.
 * It creates a reader and writer to do so.
 * 
 * Note that this class's functionality is USED by the GUI and Reader when recieving information
 * Note that this class USES the functionality of the GUI and Writer when sending information
 * 
 * @author Sean McNamee
 */
public class ChatClient {
    //To connect to the server
    private String hostname;
    private int port;

    //To identify this client
    private UserIdentifier id;
    private App main;

    //To update the GUI and server with messages
    private Writer toThread; //Sending info to the server
    
 
    public ChatClient(String userName, String groupName, App main) {
        //this.hostname = "23.96.54.163";
        this.hostname = "localhost";
        this.port = 42069;
        this.main = main;
        this.id = new UserIdentifier(userName, groupName, new Color(Math.random(), Math.random(), Math.random(), 1));
    }
 
    /**
     * Connect to the server (through the socket)
     * And create a reader (and start its thread) and writer.
     * 
     * Note that it immediately sends the username and group of this client.
     */
    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

            System.out.println("Making writer");
            toThread = new Writer(socket);
 
            System.out.println("Starting reading thread");
            new ReadThread(socket, this.main).start();

            System.out.println("Identifying client to server");
            writeToThread(this.id);
             
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    private void writeToThread(UserIdentifier identifier) {
        if (toThread != null) {
            System.out.println("Writing to socket");
            toThread.write(new LocationUpdate(new Point3D(0, 0, 0), identifier));
        }   
    }

    /**
     * Use the Writer to communicate through the socket with the server
     */
    public void writeToThread(LocationUpdate update) {
        update.setUserInfo(this.id);
        if (toThread != null) {
            System.out.println("Writing to socket");
            toThread.write(update);
        }   
    }

    /**
     * Close the socket. Should be called at program termination
     */
    public void done() {
        toThread.done();
    }

}
