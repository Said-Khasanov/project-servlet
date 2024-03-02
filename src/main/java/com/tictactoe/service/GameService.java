package com.tictactoe.service;

import com.tictactoe.model.*;

import java.util.Map;

public class GameService {
    private final Game game;
    private GameState gameState;
    private Sign winner;

    public GameService() {
        game = new Game();
        gameState = GameState.CONTINUES;
        winner = Sign.EMPTY;
    }

    public void doMoves(int playerSelectedIndex) {
        if (winner == Sign.EMPTY) {
            moveCross(playerSelectedIndex);
            if (gameState == GameState.CONTINUES) {
                moveNought();
            }
        }
    }

    private void moveCross(int playerSelectedIndex){
        Map<Integer, Sign> signMap = game.getField();
        Sign currentSign = signMap.get(playerSelectedIndex);
        if (currentSign == Sign.EMPTY)
        {
            signMap.put(playerSelectedIndex, Sign.CROSS);
            checkWin();
        } else {
            gameState = GameState.SKIP_MOVES;
        }
    }

    private void moveNought(){
        int emptyFieldIndex = game.getEmptyFieldIndex();
        if (emptyFieldIndex >= 0) {
            Map<Integer, Sign> signMap = game.getField();
            signMap.put(emptyFieldIndex, Sign.NOUGHT);
            checkWin();
        }
        else {
            gameState = GameState.DRAW;
        }
    }

    private void checkWin() {
        Sign checkWin = game.checkWin();
        if (Sign.CROSS == checkWin || Sign.NOUGHT == checkWin) {
            winner = checkWin;
            gameState = GameState.HAS_WINNER;
        }
    }

    public Game getGame() {
        return game;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Sign getWinner() {
        return winner;
    }
}
