package com.example.lightsoutgame;

public class LightsOutGame {
    private boolean[][] lights;

    public LightsOutGame() {
        lights = new boolean[5][5]; // Initialize a 5x5 grid of lights
    }

    public void toggleLight(int row, int col) {
        // Toggle the state of the light at the specified row and column and adjacent lights
        lights[row][col] = !lights[row][col];

    }

    public boolean isWin() {
        // Check if all lights are turned off
        for (boolean[] row : lights) {
            for (boolean light : row) {
                if (light) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isLightOn(int row, int col) {
        return lights[row][col];
    }
}

