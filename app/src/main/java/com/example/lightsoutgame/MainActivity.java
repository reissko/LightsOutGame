package com.example.lightsoutgame;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

/**
 * @author Reiss Oiveros
 * @version October 2023
 */

public class MainActivity extends AppCompatActivity {

    private static final int GRID_SIZE = 5;
    private GridLayout gridLayout;
    private Button[][] buttons;
    private boolean[][] lights;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        buttons = new Button[GRID_SIZE][GRID_SIZE];
        lights = new boolean[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundColor(lights[i][j] ? Color.BLACK : Color.WHITE);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int row = -1, col = -1;
                        // Find the clicked button's position in the grid
                        for (int i = 0; i < GRID_SIZE; i++) {
                            for (int j = 0; j < GRID_SIZE; j++) {
                                if (view == buttons[i][j]) {
                                    row = i;
                                    col = j;
                                    break;
                                }
                            }
                        }
                        if (row != -1 && col != -1) {
                            toggleLights(row, col);
                            updateUI();
                            checkWin();
                        }
                    }
                });
                gridLayout.addView(buttons[i][j]);
            }
        }

        resetPuzzle((View)gridLayout);
    }

    private void toggleLights(int row, int col) {
        lights[row][col] = !lights[row][col];
        if (row > 0) lights[row - 1][col] = !lights[row - 1][col]; // Toggle above
        if (row < GRID_SIZE - 1) lights[row + 1][col] = !lights[row + 1][col]; // Toggle below
        if (col > 0) lights[row][col - 1] = !lights[row][col - 1]; // Toggle left
        if (col < GRID_SIZE - 1) lights[row][col + 1] = !lights[row][col + 1]; // Toggle right
    }

    private void updateUI() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setBackgroundColor(lights[i][j] ? Color.BLACK : Color.WHITE);
            }
        }
    }

    private void checkWin() {
        boolean win = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!lights[i][j]) {
                    win = false;
                    break;
                }
            }
        }
        if (win) {
            // You can add a win message or other indication here.
        }
    }

    public void resetPuzzle(View view) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                lights[i][j] = random.nextBoolean();
            }
        }
        updateUI();
    }
}


