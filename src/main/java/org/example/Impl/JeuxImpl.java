package org.example.Impl;

import org.example.contract.JeuxInterface;

import java.util.Random;

public class JeuxImpl implements JeuxInterface {

    @Override
    public String choice_gen() {
        int randomNumber = new Random().nextInt (10000);
        String number = String.format ("%04d", randomNumber);
        return number;
    }

    @Override
    public int gagnant_det(int x, int y) {
        /*
        1=pierre
        2=papier
        3=Ciseaux
         */

        if(x==1 && y==3 || x==2 && y==1 || x==3 && y==2){
            return 1;
        }else if(x==1 && y==2 ||x==2 && y==3 || x==3 && y==1){
            return 2;
        }else  {
            return 3;
        }
    }

    @Override
    public int score(int x, int y) {
        if (x>y) {
            return 1;
        }else if(x==y){
            return 3;
        }else {
            return 2;
        }
    }
}
