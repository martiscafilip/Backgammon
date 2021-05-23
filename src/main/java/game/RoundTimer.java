package game;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class RoundTimer {
    private Label timer;
    private int minutes;
    private int seconds;
    private int millisecond;

    public RoundTimer(Label timer){
        this.timer = timer;
    }

    public void update(int millisecond){
        this.millisecond += millisecond;
        if(this.millisecond > 1000){
            this.seconds++;
            this.millisecond = 0;
        }
        if(this.seconds > 60){
            this.minutes += 1;
            this.seconds = 0;
        }

        updateText();
    }

    public void updateText() {
        String s = minutes + " : " + seconds;
        timer.setText(s);
    }
}
