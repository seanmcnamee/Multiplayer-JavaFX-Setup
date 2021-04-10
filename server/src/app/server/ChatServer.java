package app.server;

import java.io.*;
import java.net.*;
import java.util.*;

import app.GUI.Pages.MainPage;

/**
 * This is the chat server program.
 * Press Ctrl + C to terminate the program.
 * 
 *
 * @author www.codejava.net
 * @author Adapted by Sean McNamee
 */
public class ChatServer {
    private static int port = 42069; //When changing, adjust client side as well

    //TODO change this to be a Set of Sets. Doing so would allow getting all of a groups' members quickly.
    private Set<UserThread> userThreads = new HashSet<>(); //What clients are connected
    private MainPage guiPage;

 
    public ChatServer(MainPage guiPage) {
        this.guiPage = guiPage;
        execute();
    }
 
    /**
     * Handles the overall server logic. create ServerSocket, accept incoming sockets.
     */
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            guiPage.addConnection("Chat Server is listening on port " + port);
            
            //Wait for a user to connect. start a UserThread to deal with communication.
            while (true) {
                Socket socket = serverSocket.accept();

                guiPage.addConnection("Just recieved a new socket");

                UserThread newUser = new UserThread(guiPage, socket, this);
                userThreads.add(newUser);
                newUser.start();
 
                guiPage.addConnection("Re looping to wait for another");
            }
 
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    /**
     * Delivers a message from one user to others (broadcasting) in the same group
     */
    void broadcast(String message, UserThread sendingClient) {
        for (UserThread aUser : userThreads) {
            //if (aUser != sendingClient && aUser.getGroup().equals(sendingClient.getGroup())) {
                aUser.sendMessage(message);
            //}
        }
    }
 
    /**
     * When a client is disconneted, removes the associated UserThread
     */
    void removeUser(UserThread aUser) {
        userThreads.remove(aUser);
        System.out.println("The user " + aUser.getUserName() + " quitted");
    }
 
 
    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    boolean hasUsers() {
        return !this.userThreads.isEmpty();
    }
}


