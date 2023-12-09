package org.example.ServerRpc;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;
import org.example.Impl.JeuxImpl;

import java.io.IOException;

public class ServerRpc {
    public static void main(String[] args) throws
            XmlRpcException, IOException {

        WebServer webServer = new WebServer(5005);
        XmlRpcServer xmlRpcServer =

                webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new

                PropertyHandlerMapping();

        phm.addHandler("Calculator",
                JeuxImpl.class);

        xmlRpcServer.setHandlerMapping(phm);
        webServer.start();
        System.out.println("Server is running...");
    }
}


