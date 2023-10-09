package com.example.lightsoutgame;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

/**
 * @author Reiss Oiveros
 * @version October 2023
 */

public class MainActivity extends AppCompatActivity {

    private static final int GRID_SIZE = 5;
    private GridLayout gridLayout;
    private TextView winMessage;
    private Button[][] buttons;
    private boolean[][] lights;
    private Random random = new Random();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        winMessage = findViewById(R.id.winMessage);
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

        resetPuzzle((View)gridLayout); //call reset method with type casted input of gridLayout
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
                buttons[i][j].setBackgroundColor(lights[i][j] ? Color.BLACK : Color.GRAY);
            }
        }
    }

    /**
     * checks through each value in the lights double array. If all
     * values are false method returns true
     */
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
            Log.i("Win Message", "You Win!");
            winMessage.setText("You Win!");
            winMessage.setBackgroundColor(Color.GREEN);
        }
    }

    public void resetPuzzle(View view) {
        winMessage.setText("");
        winMessage.setBackgroundColor(Color.WHITE);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                lights[i][j] = random.nextBoolean();
            }
        }
        updateUI();
    }
}


