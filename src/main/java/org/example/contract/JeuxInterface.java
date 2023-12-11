package org.example.contract;

import org.example.model.History;

public interface JeuxInterface {
    public String  choice_gen();
    public int gagnant_det(int x,int y,String uuid,int num_round);
    public int score(int x,int y,String uuid);
    public boolean initClientUuid(String uuid);
    public String showHistory(String uuid);
}
