package org.example;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;

import org.example.Impl.JeuxImpl;
public class ClientRMI {
    public static void main(String[] args) throws
            MalformedURLException, NotBoundException, RemoteException {
        String uuid = UUID.randomUUID().toString();
        JeuxImpl stub = (JeuxImpl)
                Naming.lookup("rmi://localhost:5006/Jeux/PFC");

    }
}
