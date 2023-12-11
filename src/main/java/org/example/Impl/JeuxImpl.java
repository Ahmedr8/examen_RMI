package org.example.Impl;

import org.example.contract.JeuxInterface;
import org.example.model.Game;
import org.example.model.History;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.rmi.RemoteException;
import java.rmi.Remote;

public class JeuxImpl extends UnicastRemoteObject  implements JeuxInterface {
    static HashMap<String, History> histories = new HashMap<>();
    static HashMap<String,String> all = new HashMap<>();

    public JeuxImpl() throws RemoteException {
    }

    public boolean initClientUuid(String uuid) throws RemoteException{
        System.out.println("[initClientUuid]: client créé avec l'id: "+uuid);
        History history = new History(0,0,0);
         System.out.println("INIT"+history);
        Game game = new Game(0,0,0);
        history.getGames().add(game);
        histories.put(uuid,history);
        return true;
    }

    @Override
    public String choice_gen() throws RemoteException{
        int randomNumber = new Random().nextInt(3)+1;
        String Msg_log="[choice_gen]: le serveur a généré le choix ";
        switch (randomNumber){
            case 1:
                Msg_log=Msg_log.concat("Pierre \uD83D\uDDFF");
                break;
            case 2:
                Msg_log=Msg_log.concat("Papier \uD83D\uDCC4");
                break;
            case 3:
                Msg_log=Msg_log.concat("Ciseaux ✂");
                break;
        }
        System.out.println(Msg_log);
        return String.format ("%04d", randomNumber);
    }

    @Override
    public int gagnant_det(int x, int y,String uuid,int num_round) throws RemoteException {
        /*
        1=pierre
        2=papier
        3=Ciseaux
         */
        System.out.println("[gagnant_det] le serveur détermine le gagnant de la round pour l'utilisateur "+uuid);
        History history = histories.get(uuid);
        int nb_game = history.getGames().size();
        Game game = history.getGames().get(nb_game-1);
        String[] items = {"Pierre","Feuille","Ciseaux"};
        String roundGuesser = "USER :" + items[x-1] + " BOT :" + items[y-1];
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
    public int score(int x, int y,String uuid) throws RemoteException {
        System.out.println("[score] Le serveur détermine le gagnant de la partie pour l'utilisateur"+uuid);
        int res= 0;
        if (x>y) {
            res= 1;
        }else if(x==y){
            res= 3;
        }else {
            res= 2;
        }
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
    public String showHistory(String uuid) throws RemoteException {
        System.out.println("[showHistory] le serveur montre l'historique pour le client "+uuid);
        StringBuilder result = new StringBuilder();
        History history = histories.get(uuid);
        System.out.println("SHOW HISTORY:"+history);
        result.append("History:\n").append("Games Total Score:").append(history.getW()).append("W\t").append(history.getL()).append("L\t").append(history.getD()).append("D\n");
        ArrayList<Game> games = history.getGames();
        int nbGame = 1;
        int nbRound;
        for(Game game : games){
            nbRound = 1;
            if (!game.equals(games.get(games.size()-1)))
            {
                ArrayList<String> rounds = game.getRounds();
                result.append("[GAME ").append(nbGame).append("]\n");
                result.append("Score: ").append(game.getwRound()).append("W\t").append(game.getlRound()).append("L\t").append(game.getdRound()).append("D\n");
                nbGame++;
                for (String round : rounds)
                {
                    result.append("[ROUND ").append(nbRound).append("]").append(round).append("\n");
                    nbRound++;
                }
            }
        }
        return result.toString();
     }
}
