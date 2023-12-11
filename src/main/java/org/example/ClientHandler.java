package org.example;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.example.model.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends Thread {
    Socket socket;
    InputStreamReader in;
    BufferedReader bufferReader;
    PrintWriter printWriter;
    public ClientHandler(Socket socket) throws IOException {
        this.socket=socket;
        in = new InputStreamReader(socket.getInputStream());
        bufferReader = new BufferedReader(in);
        printWriter=new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        Map<String, String> choix = new HashMap<>();
        choix.put("1","Pierre \uD83D\uDDFF");
        choix.put("2","Papier \uD83D\uDCC4");
        choix.put("3","Ciseaux ✂");
        String str,jeu;
        String uuid = "";
        System.out.println("Client a envoyer une requete:"+socket);
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        try {
            config.setServerURL(new
                    URL("http://localhost:5005/xmlrpc"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        int gagnant,a,b;
        String jeu_bot;
        int res_finale,x,y;
        while (!socket.isClosed()) {
            try {
                if (uuid.equals("")) {
                    uuid = bufferReader.readLine();
                    boolean test = (boolean)client.execute("Calculator." + "initClientUuid", new Object[]{uuid});
                }
                // CHECK
                printWriter.println("Donner votre choix");
                printWriter.flush();
                printWriter.println("1-Jouer Pierre \uD83D\uDDFF | Papier \uD83D\uDCC4 | Ciseaux ✂.");
                printWriter.flush();
                printWriter.println("2-Connaître l'historique des parties.");
                printWriter.flush();
                printWriter.println("3-Quitter");
                printWriter.flush();
                str = bufferReader.readLine();
                int choix_menu=Integer.parseInt(str);
                if(choix_menu==1){
                    x=0;
                    y=0;
                    for (int i=1;i<=3;i++){
                    printWriter.println("[ROUND "+i+"]");
                    printWriter.flush();
                    printWriter.println("1-Pierre \uD83D\uDDFF");
                    printWriter.flush();
                    printWriter.println("2-Papier \uD83D\uDCC4");
                    printWriter.flush();
                    printWriter.println("3-Ciseaux ✂");
                    printWriter.flush();
                    jeu = bufferReader.readLine();
                    printWriter.println("USER 👨:"+choix.get(jeu));
                    printWriter.flush();
                    jeu_bot = (String) client.execute("Calculator." + "choice_gen", new Object[]{});
                    b=Integer.parseInt(jeu_bot);
                    jeu_bot=String.valueOf(b);
                    printWriter.println("BOT \uD83E\uDD16:"+choix.get(jeu_bot));
                    printWriter.flush();
                    a=Integer.parseInt(jeu);
                    gagnant=(int) client.execute("Calculator." + "gagnant_det", new
                            Object[]{a,b,uuid,i});
                    printWriter.println("LE GAGNANT DE CE ROUND EST "+ (gagnant == 1 ? "USER 👨" : "BOT \uD83E\uDD16"));
                    printWriter.flush();
                    if(gagnant==1)
                        x=x+1;
                    else if(gagnant==2)
                        y=y+1;
                    else
                    {
                        x++;
                        y++;
                    }
                    }
                    res_finale=(int) client.execute("Calculator." + "score", new
                            Object[]{x,y,uuid});
                    printWriter.println("LE GAGNANT FINALE EST : "+ (res_finale == 1 ? "USER 👨" : "BOT \uD83E\uDD16"));
                    printWriter.flush();
                }
                if(choix_menu==2){
                    String historique = (String) client.execute("Calculator." + "showHistory", new
                            Object[]{uuid});
                    System.out.println(historique);
                }
                if(choix_menu==3){
                    printWriter.println("END");
                    printWriter.flush();
                    socket.close();
                }
            } catch (IOException | XmlRpcException e) {
                throw new RuntimeException(e);
            }


        }


    }
}
