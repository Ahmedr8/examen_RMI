package org.example.model;
import java.util.ArrayList;
public class Game {
    private ArrayList<String> rounds;
    private int wRound;
    private int lRound;
    private int dRound;
    public Game(int wRound,int lRound, int dRound){
        rounds = new ArrayList<>();
        this.wRound = wRound;
        this.lRound = lRound;
        this.dRound = dRound;
    }
    public ArrayList<String> getRounds() {
        return rounds;
    }

    public void setRounds(ArrayList<String> rounds) {
        this.rounds = rounds;
    }

    public int getwRound() {
        return wRound;
    }

    public void setwRound(int wRound) {
        this.wRound = wRound;
    }

    public int getlRound() {
        return lRound;
    }

    public void setlRound(int lRound) {
        this.lRound = lRound;
    }

    public int getdRound() {
        return dRound;
    }

    public void setdRound(int dRound) {
        this.dRound = dRound;
    }
}
