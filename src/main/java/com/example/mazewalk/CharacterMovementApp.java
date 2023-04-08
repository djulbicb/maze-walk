package com.example.mazewalk;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CharacterMovementApp extends Application {

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;
    private static final int CHARACTER_WIDTH = 50;
    private static final int CHARACTER_HEIGHT = 50;
    private static final int PANE_WIDTH = 1600;
    private static final int PANE_HEIGHT = 1200;
    private static final int PANE_BOUNDARY = 100;

    private ImageView character;
    private Pane pane;
    private TranslateTransition tt;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create the character image
        Image characterImage = new Image("https://i.imgur.com/4eD1dJN.png");
        character = new ImageView(characterImage);
        character.setFitWidth(CHARACTER_WIDTH);
        character.setFitHeight(CHARACTER_HEIGHT);

        // create the pane
        pane = new Pane(character);
        pane.setPrefSize(PANE_WIDTH, PANE_HEIGHT);

        // create the scene
        Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

        // create the translate transition for the pane
        tt = new TranslateTransition(Duration.millis(500), pane);

        // register the key event handler for the scene
        scene.setOnKeyPressed(event -> {
            double x = character.getLayoutX();
            double y = character.getLayoutY();
            switch (event.getCode()) {
                case UP:
                    y -= 10;
                    break;
                case DOWN:
                    y += 10;
                    break;
                case LEFT:
                    x -= 10;
                    break;
                case RIGHT:
                    x += 10;
                    break;
                default:
                    break;
            }
            // update the character position
            character.relocate(x, y);

            // slide the pane to follow the character
            double dx = -x + SCENE_WIDTH/2 - CHARACTER_WIDTH/2;
            double dy = -y + SCENE_HEIGHT/2 - CHARACTER_HEIGHT/2;
            dx = Math.min(Math.max(dx, -PANE_WIDTH + SCENE_WIDTH + PANE_BOUNDARY), -PANE_BOUNDARY);
            dy = Math.min(Math.max(dy, -PANE_HEIGHT + SCENE_HEIGHT + PANE_BOUNDARY), -PANE_BOUNDARY);
            tt.setToX(dx);
            tt.setToY(dy);
            tt.play();
        });

        // show the stage
        primaryStage.setScene(scene);
        primaryStage.show();

        // set the focus on the scene to enable key events
        // scene.requestFocus();
        //scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

