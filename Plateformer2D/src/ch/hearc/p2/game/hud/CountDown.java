
package ch.hearc.p2.game.hud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CountDown implements ActionListener {
    private int timeMinute;
    private int timeSecond;
    private boolean isFinished = false;

    public CountDown(int initMinute, int initSecond) {
	super();
	this.timeMinute = initMinute;
	this.timeSecond = initSecond;
	this.isFinished = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (this.timeMinute == 0 && this.timeSecond == 0)
	    isFinished = true;
	else {
	    if (this.timeSecond == 0) {
		this.timeMinute--;
		this.timeSecond = 59;
	    } else
		this.timeSecond--;
	}
    }

    public int getMinutes() {
	return timeMinute;
    }

    public int getSeconds() {
	return timeSecond;
    }

    public boolean isFinished() {
	return isFinished;
    }
}