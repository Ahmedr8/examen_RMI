package org.example.Impl;

import org.example.contract.JeuxInterface;
import org.example.model.Game;
import org.example.model.History;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

public class JeuxImpl implements JeuxInterface {
    static HashMap<String, History> histories = new HashMap<>();
     public boolean initClientUuid(String uuid){
        History history = new History(0,0,0);
        Game game = new Game(0,0,0);
        history.getGames().add(game);
        histories.put(uuid,history);
         System.out.println(histories.isEmpty());
        return true;
    }

    @Override
    public String choice_gen() {
        int randomNumber = new Random().nextInt(3)+1;
        return String.format ("%04d", randomNumber);
    }

    @Override
    public int gagnant_det(int x, int y,String uuid,int num_round) {
        /*
        1=pierre
        2=papier
        3=Ciseaux
         */
        System.out.println(uuid);
        // TO SEE LATER WHETHER TO ADD \n or NOT
        History history = histories.get(uuid);
        System.out.println(histories.isEmpty());
        System.out.println(history);
        int nb_game = history.getGames().size();
        System.out.println(nb_game);
        Game game = history.getGames().get(nb_game-1);
        System.out.println(game);
        String[] items = {"Pierre","Papier","Ciseaux"};
        String roundGuesser = "USER : " + items[x-1] + " BOT :" + items[y-1];
        System.out.println(roundGuesser);
        if(x==1 && y==3 || x==2 && y==1 || x==3 && y==2){
            roundGuesser+= " --> USER WINS" ;
            game.getRounds().add(roundGuesser);
            game.setwRound(game.getwRound() + 1);
            return 1;
        }else if(x==1 && y==2 ||x==2 && y==3 || x==3 && y==1){
            roundGuesser+= " --> BOT WINS" ;
            game.getRounds().add(roundGuesser);
            game.setlRound(game.getlRound() + 1);
            return 2;
        }else  {
            roundGuesser+= " --> DRAW";
            game.getRounds().add(roundGuesser);
            game.setdRound(game.getdRound() + 1);
            return 3;
        }
    }

    @Override
    public int score(int x, int y,String uuid) {
        int res= 0;
        if (x>y) {
            res= 1;
        }else if(x==y){
            res= 3;
        }else {
            res= 2;
        }
        System.out.println("res="+res);
        History history = histories.get(uuid);
        if (res == 1) {
            history.setW(history.getW() + 1);
        } else {
            if (res == 2) {
                history.setL(history.getL() + 1);
            } else {
                history.setD(history.getD() + 1);
            }
        }
        Game game = new Game(0,0,0);
        history.getGames().add(game);
        return res;
    }
    public String showHistory(String uuid){
        StringBuilder result = new StringBuilder();
        History history = histories.get(uuid);
        result.append("History:\n").append("Games Total Score:").append(history.getW()).append("W\t").append(history.getL()).append("L\t").append(history.getD()).append("D\n");
        ArrayList<Game> games = history.getGames();
        int nbGame = 1;
        int nbRound = 1;
        for(Game game : games){
           ArrayList<String> rounds = game.getRounds();
            result.append("[GAME ").append(nbGame).append("]\n");
            result.append("Score: ").append(game.getwRound()).append("W\t").append(game.getlRound()).append("L\t").append(game.getdRound()).append("D\n");
            nbGame++;
            for(String round : rounds){
            result.append("[ROUND ").append(nbRound).append("]").append(round).append("\n");
            nbRound++;
           }
        }
        return result.toString();
     }
}
