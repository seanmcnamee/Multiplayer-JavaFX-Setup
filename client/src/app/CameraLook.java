package app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import communications.ChatClient;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import shared.LocationUpdate;

public class CameraLook {
    private Camera camera;
    private Stage stage;

    private Point2D mousePressLocation;
    private Point2D mouseChange;

    private ObjectPropertyBase<Point3D> perpendicularToSightAxis;
    private DoubleProperty angleAboutPerpendicularSight, angleAboutYAxis;

    private double scrollDistance;

    private List<EventSet> eventSets;

    private ChatClient chatter;

    public CameraLook(Camera camera, Stage stage, ChatClient chat) {
        this(camera, stage, chat, new ArrayList<EventSet>());
    }

    public CameraLook(Camera camera, Stage stage, ChatClient chat, EventSet... eventSets) {
        this(camera, stage, chat, Arrays.asList(eventSets));
    }

    public CameraLook(Camera camera, Stage stage, ChatClient chat, List<EventSet> eventSets) {
        this.camera = camera;
        this.stage = stage;
        this.chatter = chat;
        this.eventSets = eventSets;
        this.perpendicularToSightAxis = new SimpleObjectProperty<Point3D>(Rotate.X_AXIS);
        this.angleAboutPerpendicularSight = new SimpleDoubleProperty(0);
        this.angleAboutYAxis = new SimpleDoubleProperty(0);
        this.mouseChange = this.mousePressLocation = null;
        this.scrollDistance = 25;
        setup();
    }

    private void setup() {
        //Setup Camera rotate
        setupCameraRotate();

        //Setup Camera Move
        setupCameraZoom();
    }

    private void setupCameraRotate() {
        //setup the rotate variables
        Rotate rotateAboutPerpendicularToSight = new Rotate(0, new Point3D(0, 0, 0));
        Rotate rotateAboutYAxis = new Rotate(0, Rotate.Y_AXIS);

        //bind them to our class variables
        rotateAboutPerpendicularToSight.angleProperty().bind(angleAboutPerpendicularSight);
        rotateAboutPerpendicularToSight.axisProperty().bind(perpendicularToSightAxis);
        rotateAboutYAxis.angleProperty().bind(angleAboutYAxis);

        //Add transforms to camera
        this.camera.getTransforms().addAll(rotateAboutPerpendicularToSight, rotateAboutYAxis);

        //update loop
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                CameraLook.this.angleAboutPerpendicularSight.set(angleAboutPerpendicularSight.get() - Math.signum(mouseChange.getY())*Math.pow(mouseChange.getY()/200, 2));
                CameraLook.this.perpendicularToSightAxis.set(new Point3D(Math.cos(Math.toRadians(angleAboutYAxis.get())), 0, -Math.sin(Math.toRadians(angleAboutYAxis.get()))));
                CameraLook.this.angleAboutYAxis.set(angleAboutYAxis.get() + Math.signum(mouseChange.getX())*Math.pow(mouseChange.getX()/300, 2));
            }

        };

        

        //handle key and mouse events
        this.stage.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            if (event.isControlDown()) {
                if (this.mousePressLocation == null || this.mouseChange == null) {
                    this.mousePressLocation = new Point2D(event.getScreenX(), event.getScreenY());
                    this.mouseChange = new Point2D(0, 0);
                    timer.start();
                } else {
                    this.mouseChange = new Point2D(event.getScreenX() - this.mousePressLocation.getX(), event.getScreenY() - this.mousePressLocation.getY());
                }
            }
            for (EventSet handler : this.eventSets) {
                handler.onMouseMoved(event);
            }
        });

        this.stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            for (EventSet handler : this.eventSets) {
                handler.onKeyUpEvent(event);
            }
            switch(event.getCode()) {
                case CONTROL:
                    timer.stop();
                    this.mouseChange = this.mousePressLocation = null;
                break;
                default:
                break;
            }
        });

        
    }

    private void setupCameraZoom() {
        this.stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            for (EventSet handler : this.eventSets) {
                handler.onScrollEvent(event);
            }
            
            double percentHoriz = Math.cos(Math.toRadians(angleAboutPerpendicularSight.get()));
            double percentVert = -Math.sin(Math.toRadians(angleAboutPerpendicularSight.get()));
            double percentX = Math.sin(Math.toRadians(angleAboutYAxis.get()));
            double percentZ = Math.cos(Math.toRadians(angleAboutYAxis.get()));
            Point3D directions = new Point3D(percentHoriz*percentX, percentVert, percentHoriz*percentZ);
            double resultant = Math.pow(Math.pow(directions.getX(), 2)+Math.pow(directions.getY(), 2)+Math.pow(directions.getZ(), 2), 1/2.0);
            double HVResultant = Math.pow(Math.pow(percentHoriz, 2) + Math.pow(percentVert, 2), 1.0/2);
            double HorizResultant = Math.pow(Math.pow(percentX, 2) + Math.pow(percentZ, 2), 1.0/2);

            System.out.println();
            System.out.println("HorizVert Resultant: " + HVResultant);
            System.out.println("\t%H: " + percentHoriz);
            System.out.println("\t%V: " + percentVert);

            System.out.println("Horiz Resultant: " + HorizResultant);
            System.out.println("\t%X: " + percentX);
            System.out.println("\t%Z: " + percentZ);
            
            System.out.println("Resultant: " + resultant);

            this.camera.translateXProperty().set(camera.getTranslateX() + scrollDistance*directions.getX()*Math.signum(event.getDeltaY()));
            this.camera.translateYProperty().set(camera.getTranslateY() + scrollDistance*directions.getY()*Math.signum(event.getDeltaY()));
            this.camera.translateZProperty().set(camera.getTranslateZ() + scrollDistance*directions.getZ()*Math.signum(event.getDeltaY()));
            
            this.chatter.writeToThread(new LocationUpdate(new javafx.geometry.Point3D(camera.getTranslateX(), camera.getTranslateY(), camera.getTranslateZ())));
        });
    }

   


}
