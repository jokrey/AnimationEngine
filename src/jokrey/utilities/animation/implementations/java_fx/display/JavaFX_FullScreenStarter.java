package jokrey.utilities.animation.implementations.java_fx.display;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jokrey.utilities.animation.implementations.java_fx.pipeline.AnimationDrawerJavaFX;
import jokrey.utilities.animation.engine.AnimationEngine;
import jokrey.utilities.animation.pipeline.AnimationPipeline;

import java.awt.*;

public class JavaFX_FullScreenStarter extends Application {
    private static AnimationEngine engineToRun;
    private static AnimationPipeline pipeline;
    public static void start(AnimationEngine engineToRun_g) {
        start(engineToRun_g, new AnimationPipeline(new AnimationDrawerJavaFX()));
    }
    public static void start(AnimationEngine engineToRun_g, AnimationPipeline pipe_g) {
        if(! (pipe_g.getDrawer() instanceof AnimationDrawerJavaFX))
            throw new IllegalArgumentException("FX needs the fx drawer you f***");
        engineToRun = engineToRun_g;
        pipeline = pipe_g;
        launch(JavaFX_FullScreenStarter.class);
    }

    /**
     * Äquivalent to EXIT_ON_CLOSE
     * @throws Exception
     */
    @Override public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    @Override public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Engine");
        Group root = new Group();
        AnimationCanvas canvas = new AnimationCanvas(engineToRun, pipeline);
        Rectangle2D mouseScreenBounds = Screen.getScreens().get(MouseInfo.getPointerInfo().getLocation().x/1920).getVisualBounds();
        canvas.setWidth(mouseScreenBounds.getWidth());
        canvas.setHeight(mouseScreenBounds.getHeight());
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        primaryStage.setX(mouseScreenBounds.getMinX());
        primaryStage.setY(mouseScreenBounds.getMinY());

        canvas.start();
    }
}
