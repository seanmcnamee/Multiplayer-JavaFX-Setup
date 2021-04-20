package communications;

import java.io.*;
import java.net.*;

import app.App;
import shared.LocationUpdate;
import shared.SimpleMessage;
 
/**
 * This thread is responsible for reading server's input and writing it to the local user
 * It runs in an infinite loop until the client disconnects from the server.
 * This is why it needs to be a Thread.
 *
 * @author www.codejava.net
 * @author Altered by Sean McNamee
 */
public class ReadThread extends Thread {
    private ObjectInputStream reader;
    private App main;
 
    /**
     * Instantiate the reader for communication from the server
     */
    public ReadThread(Socket socket, App main) {
        this.main = main;
        try {
            InputStream input = socket.getInputStream();
            reader = new ObjectInputStream(input);
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    /**
     * Infinitely wait on input from the server.
     * Note that input from the server becomes output to the user (in form of the GUI)
     * 
     * All information here is due to a 'broadcast' from the server
     * If non-string messages were sent, they would have to be recognized here (picture or other information).
     */
    public void run() {
        System.out.println("Starting the run");
        while (true) {
            try {
                
                System.out.println("Reading from server...");
                LocationUpdate serverMessage = (LocationUpdate) reader.readObject();
                this.main.updateUser((LocationUpdate) serverMessage);
                
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            } catch (Exception e) {
                System.out.println("Could not find class: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}