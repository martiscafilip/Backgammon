package game;

import javafx.application.Platform;

import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class GameManager {
    private Player player1;
    private Player player2;
    private Board board;
    private Dice dice;
    private RoundTimer roundTimer;
    private GameStatus gameStatus;

    private Player first = null;
    private Player second = null;
    private int round = 0;

    private int player1Sum = 0;
    private int player2Sum = 0;
    private int wait = 20;
    private boolean firstStart = false;
    private boolean secondStart = false;

    Timer timer1;
    Timer timer2;
    TimerTask task1;
    TimerTask task2;

    public GameManager(Player player1, Player player2, Board board, Dice dice, RoundTimer roundTimer){
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.dice = dice;
        this.roundTimer = roundTimer;
    }

    public void startGame(){
        player1.disablePlayerMove(true);
        player2.disablePlayerMove(true);

        gameStatus.setText("Decide who is first");
        decideFirst();

        dice.disableDice();
        round  = 1;
        startSwitchRounds();
    }

    public void decideFirst(){

        task1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if (player1Sum == 0) {
                            roundTimer.update(100);
                            if (dice.canUse()) {
                                System.out.println("Am intrat pentru pl1");
                                player1Sum = player1Sum + dice.getValueDice1() + dice.getValueDice2();
                                dice.consumeAll();
                                dice.canRoll(false);
//                        dice.canRoll = true;
//                        dice.getRollButton().setDisable(true);
                            }
                        } else {
                            wait--;
                            System.out.println("wait: " + wait);
                            if (wait == 0) {
                                dice.canRoll(true);
                                dice.rollAction();
                            }

                            if (wait == -20) {
                                player2Sum = player2Sum + dice.getValueDice1() + dice.getValueDice2();
                                System.out.println("Am terminat timer-ul");
                                if (player1Sum > player2Sum) {
                                    System.out.println("Player1 Win!! " + player1Sum);
                                    first = player1;
                                    second = player2;
                                } else {
                                    System.out.println("Player2 Win!! " + player2Sum);
                                    first = player2;
                                    second = player1;
                                }
                                first.myTurn(true);
                                second.myTurn(false);
                                gameStatus.setText(first.getPlayerLabel().getText() +" start the game!");

                                System.out.println("");
                                System.out.println("Player1: " + player1Sum);
                                System.out.println("Player2: " + player2Sum);
                                dice.consumeAll();
                                dice.canRoll(true);
                                wait = 0;
                                timer1.cancel();
                            }
                        }
                    }
                });
            }
        };

        timer1 = new Timer();
        timer1.schedule(task1,0,100L);
    }

    public void startSwitchRounds(){
        task2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println("Tesk2");
                        if(first != null && second != null) {
                            if (round % 2 == 1) {
//                                System.out.println("R1: dice.cu: " + dice.canUse());
                                if(! (first instanceof Bot)) {
                                    if (dice.canUse() && !firstStart) {
                                        firstStart = true;
                                        first.highPossibleMoves();
                                    }

                                    if (!first.hasMoves() && firstStart) {
                                        round++;
                                        gameStatus.setText("It's " + second.getPlayerLabel().getText() + " turn");
                                        wait = 0;
                                        System.out.println("p1 Round nb:" + round);
                                        dice.consumeAll();
                                        dice.disableDice();
                                        dice.canRoll(true);
                                        firstStart = false;
                                        first.myTurn(false);
                                        second.myTurn(true);
                                        first.removeSpikeHigh();
                                        first.removePieceHigh();
                                    }
                                } else {
                                    if(wait == 0){
                                        dice.canRoll(false);
                                    }
                                    if(wait == 10){
                                        dice.canRoll(true);
                                        dice.rollAction();
                                    }
                                    if(wait == 30){
                                        first.highPossibleMoves();
                                        first.disablePlayerMove(true);
                                    }
                                    if(wait > 30 && wait % 15 == 0){
                                        if(first.hasMoves()){
                                            first.removePieceHigh();
                                            first.removeSpikeHigh();
                                            ((Bot) first).makeMove();
                                        } else {
                                            round ++;
                                            gameStatus.setText("It's " + second.getPlayerLabel().getText() + " turn");
                                            wait = 0;
                                            System.out.println("p1 Round b:" + round);
                                            dice.consumeAll();
                                            dice.disableDice();
                                            dice.canRoll(true);
                                            firstStart = false;
                                            first.myTurn(false);
                                            second.myTurn(true);
                                            first.removeSpikeHigh();
                                            first.removePieceHigh();
                                        }
                                    }
                                    wait++;
                                }
                            }

                            if (round % 2 == 0) {
                                if (!(second instanceof Bot)) {

                                    if (dice.canUse() && !secondStart) {
                                        secondStart = true;
                                        second.highPossibleMoves();
                                    }

                                    if (!second.hasMoves() && secondStart) {
                                        round++;
                                        gameStatus.setText("It's " + first.getPlayerLabel().getText() + " turn");
                                        System.out.println("p2 Round nb:" + round);
                                        wait = 0;
                                        dice.consumeAll();
                                        dice.disableDice();
                                        dice.canRoll(true);
                                        secondStart = false;
                                        second.myTurn(false);
                                        first.myTurn(true);
                                        second.removeSpikeHigh();
                                        second.removePieceHigh();
                                    }

                                } else {
                                    if(wait == 0){
                                        dice.canRoll(false);
                                    }
                                    if (wait == 10) {
                                        dice.canRoll(true);
                                        dice.rollAction();
                                    }
                                    if(wait == 30){
                                        second.highPossibleMoves();
                                        second.disablePlayerMove(true);
                                    }
                                    if (wait > 30 && wait % 15 ==0) {
//                                        second.highPossibleMoves();
                                        if (second.hasMoves()) {
                                            second.removePieceHigh();
                                            second.removeSpikeHigh();
                                            ((Bot) second).makeMove();
                                        } else {
                                            round++;
                                            gameStatus.setText("It's " + first.getPlayerLabel().getText() + " turn");
                                            wait = 0;
                                            System.out.println("p2 Round b:" + round);
                                            dice.consumeAll();
                                            dice.disableDice();
                                            dice.canRoll(true);
                                            firstStart = false;
                                            second.myTurn(false);
                                            first.myTurn(true);
                                            second.removeSpikeHigh();
                                            second.removePieceHigh();
                                        }
                                    }
                                    wait++;
                                }
                            }


                            if(first.getStorage().pieces.size() == 15){
                                System.out.println("Player1 Win!!!!!");
                                gameStatus.setText(first.getPlayerLabel().getText() + " win the game!!!");
                                first.removePieceHigh();
                                first.removeSpikeHigh();
                                first.myTurn(false);
                                dice.consumeAll();
                                dice.canRoll(false);
                                timer2.cancel();
                            }

                            if(second.getStorage().pieces.size() == 15){
                                System.out.println("Player2 Win!!!!!");
                                gameStatus.setText(second.getPlayerLabel().getText() + " win the game!!!");
                                second.removePieceHigh();
                                second.removeSpikeHigh();
                                second.myTurn(false);
                                dice.consumeAll();
                                dice.canRoll(false);
                                timer2.cancel();
                            }

                        }
                    }
                });
            }
        };

        timer2 = new Timer();
        timer2.schedule(task2, 0, 100L);
    }

    public void round(Player player, boolean start, Player player2){
        if(! (player instanceof Bot)) {
            if (dice.canUse() && !start) {
                start = true;
                player.highPossibleMoves();
            }

            if (!player.hasMoves() && start) {
                round++;
                wait = 0;
                System.out.println("p1 Round nb:" + round);
                dice.consumeAll();
                dice.disableDice();
                dice.canRoll(true);
                start = false;
                player.myTurn(false);
                player2.myTurn(true);
                player.removeSpikeHigh();
                player.removePieceHigh();
            }
        } else {
            if(wait == 0){
                dice.canRoll(false);
            }
            if(wait == 10){
                dice.canRoll(true);
                dice.rollAction();
            }
            if(wait == 30){
                player.highPossibleMoves();
            }
            if(wait > 30 && wait % 9 == 0){
                if(player.hasMoves()){
                    player.removePieceHigh();
                    player.removeSpikeHigh();
                    ((Bot) player).makeMove();
                } else {
                    round ++;
                    wait = 0;
                    System.out.println("p1 Round b:" + round);
                    dice.consumeAll();
                    dice.disableDice();
                    dice.canRoll(true);
                    start = false;
                    player.myTurn(false);
                    player2.myTurn(true);
                    player.removeSpikeHigh();
                    player.removePieceHigh();
                }
            }
            wait++;
        }
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
