package org.example.ServerRmi;

import org.example.Impl.JeuxImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerRmi {
 public static void main(String[] args) throws RemoteException, MalformedURLException{
     LocateRegistry.createRegistry(5006);
     JeuxImpl jeuxImpl = new JeuxImpl();
     Naming.rebind("rmi://localhost:5006/Jeux/PFC", jeuxImpl);

 }
}
