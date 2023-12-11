package org.example.model;

import java.util.ArrayList;

public class History {
    private ArrayList<Game> games;
    //
    private int W;
    private int L;
    private int D;
    public History(int w, int l, int d) {
        games = new ArrayList<>();
        W = w;
        L = l;
        D = d;
    }
    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public int getW() {
        return W;
    }

    public void setW(int w) {
        W = w;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public int getD() {
        return D;
    }
    public void setD(int d) {
        D = d;
    }

}
