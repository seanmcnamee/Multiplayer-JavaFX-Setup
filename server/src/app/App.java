package app;

import app.GUI.GUI;
import app.GUI.Pages.MainPage;
import app.server.ChatServer;

public class App {
    public static int MAIN = 0;
    public static void main(String[] args) throws Exception {

        MainPage mainPage = new MainPage();
        GUI g = new GUI(mainPage);
        g.start();

        new ChatServer(mainPage); //Doing this will force this Thread into an infinite loop.
    }
}