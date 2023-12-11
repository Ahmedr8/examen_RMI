package org.example.contract;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.example.model.History;

public interface JeuxInterface extends Remote {
    public String  choice_gen() throws RemoteException;
    public int gagnant_det(int x,int y,String uuid,int num_round) throws RemoteException;
    public int score(int x,int y,String uuid) throws RemoteException;
    public boolean initClientUuid(String uuid) throws RemoteException;
    public String showHistory(String uuid) throws RemoteException;
}
