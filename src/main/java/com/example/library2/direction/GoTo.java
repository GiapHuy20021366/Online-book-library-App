package com.example.library2.direction;

import com.example.library2.utilities.ShowOnBack;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Stack;

public class GoTo {
    private final Stack<Scene> scenes = new Stack<>();
    private static final GoTo dir = new GoTo();

    private GoTo() {
    }

    public static GoTo getInstance() {
        return dir;
    }

    public void put(Scene scene) {
        scenes.push(scene);
    }


    public boolean hasPrev() {
        return !scenes.isEmpty();
    }

    public Scene getPrev() {
        if (!hasPrev()) {
            throw new IndexOutOfBoundsException("No scene at Prev");
        }
        return scenes.pop();
    }

    public Scene getFirst() {
        Scene sceneF = null;
        while (!scenes.isEmpty()) {
            sceneF = scenes.pop();
        }
        return sceneF;
    }

}
