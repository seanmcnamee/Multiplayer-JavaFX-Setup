package app;

import java.util.Hashtable;

import communications.ChatClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import shared.LocationUpdate;
import shared.UserIdentifier;

public class App extends Application {
    
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private Group group;
    private Hashtable<UserIdentifier, Node> users;

    @Override
    public void start(Stage stage) throws Exception {
        this.users = new Hashtable<UserIdentifier, Node>();
        group = new Group();

        Box[] boxes = {
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
            new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10), new Box(10, 10, 10),
        };


        Point2D[] ranges = {
            new Point2D(-500, 500),
            new Point2D(-500, 500),
            new Point2D(-500, 500)
        };

        Point2D[] rangeMin = new Point2D[ranges.length];
        for (int i = 0; i < ranges.length; i++) {
            rangeMin[i] = new Point2D(ranges[i].getY()-ranges[i].getX(), ranges[i].getX());
        }

        for (Box box : boxes) {
            box.getTransforms().addAll(
                new Translate(
                    rangeMin[0].getX()*Math.random()+rangeMin[0].getY(), 
                    rangeMin[1].getX()*Math.random()+rangeMin[1].getY(),
                    rangeMin[2].getX()*Math.random()+rangeMin[2].getY()
                )
            );
            PhongMaterial boxMaterial = new PhongMaterial(new Color(Math.random(), Math.random(), Math.random(), 1));
            box.setMaterial(boxMaterial);
        }

        Box floor = new Box(1000, 2, 1000);
        floor.getTransforms().addAll(new Translate(0, 800, 0));
        Box cieling = new Box(1000, 2, 1000);
        cieling.getTransforms().addAll(new Translate(0, -800, 0));

        Box xAxis = new Box(5000, 2, 2);
        Box zAxis = new Box(2, 2, 5000);
        Box yAxis = new Box(2, 2500, 2);

        PhongMaterial floorMaterial = new PhongMaterial(Color.GREY);
        PhongMaterial xAxisMaterial = new PhongMaterial(Color.BLUE);
        PhongMaterial yAxisMaterial = new PhongMaterial(Color.YELLOW);
        PhongMaterial zAxisMaterial = new PhongMaterial(Color.BLACK);
        PhongMaterial cielingMaterial = new PhongMaterial(Color.CYAN);
        floor.setMaterial(floorMaterial);
        cieling.setMaterial(cielingMaterial);
        xAxis.setMaterial(xAxisMaterial);
        yAxis.setMaterial(yAxisMaterial);
        zAxis.setMaterial(zAxisMaterial);

        group.getChildren().addAll(boxes);
        group.getChildren().addAll(floor, cieling, xAxis, yAxis, zAxis);

        //Setup a camera to view them
        Camera camera = new PerspectiveCamera(true);
        camera.translateXProperty().set(30);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(-700);
        camera.setNearClip(.0001);
        camera.setFarClip(100000);
        connectToServer(camera, stage);
        
        
        
        Scene scene = new Scene(group, WIDTH, HEIGHT, true);//, SceneAntialiasing.BALANCED);
        scene.setFill(Color.LIGHTGRAY);
        scene.setCamera(camera);

        //Add the scene to the stage
        stage.setTitle("JavaFX 3D");
        stage.setScene(scene);
        stage.show();

        
    }

    private void connectToServer(Camera camera, Stage stage) {

        new Thread() {
            public void start() {
                System.out.println("Connecting...");
                ChatClient chat = new ChatClient("Sean", "Group 1", App.this);
                new CameraLook(camera, stage, chat);
                chat.execute();
            }
        }.start();
        
    }

    public void updateUser(LocationUpdate update) {
        Platform.runLater(new Runnable() {
            public void run() {
                if (!App.this.users.containsKey(update.getUserInfo())) {
                    addUser(update.getUserInfo());
                }
        
                //this.users.get(update.getUserInfo()).getTransforms().clear();
                Point3D location = update.getPosition();
                App.this.users.get(update.getUserInfo()).setTranslateX(location.getX());
                App.this.users.get(update.getUserInfo()).setTranslateY(location.getY());
                App.this.users.get(update.getUserInfo()).setTranslateZ(location.getZ());
            }
        });
    }

    private void addUser(UserIdentifier newUser) {
        Box newUserBox = new Box(30, 30, 30);
        newUserBox.setMaterial(new PhongMaterial(newUser.getColor()));
        this.group.getChildren().add(newUserBox);
        this.users.put(newUser, newUserBox);
    }
}
