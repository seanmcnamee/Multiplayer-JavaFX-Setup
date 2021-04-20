package communications;

import java.io.*;
import java.net.*;

import shared.LocationUpdate;
 
/**
 * This thread is responsible for reading user's input and send it to the server.
 * Each write call is waiting on the program. That's why it does NOT need a Thread.
 *
 * @author www.codejava.net
 * @author Altered by Sean McNamee
 */
public class Writer {
    private ObjectOutputStream writer;
    private Socket socket;
    private int count;
 
    /**
     * Instantiate the writer for communication
     * @param socket
     */
    public Writer(Socket socket) {
        this.socket = socket;
        count = 1;
        try {
            OutputStream output = socket.getOutputStream();
            writer = new ObjectOutputStream(output);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Close the socket. Should be called at program termination
     */
    public void done() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Message to be sent through the socket to the server
     * @param message
     */
    public void write(LocationUpdate update) {
        System.out.println("Writing message: " + update);
        try {
            writer.writeObject(update);
        } catch (IOException e) {
            System.out.println("Problem writing Object to socket");
            e.printStackTrace();
        }
    }
 
}