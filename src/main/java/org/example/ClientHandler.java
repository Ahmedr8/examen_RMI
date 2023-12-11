package org.example;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.example.contract.JeuxInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends Thread{
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
    public String chooseMode() throws IOException {
        printWriter.println("Choisir le middleware:");
        printWriter.flush();
        printWriter.println("1-RPC");
        printWriter.flush();
        printWriter.println("2-RMI");
        printWriter.flush();
        return bufferReader.readLine();
    }
    public String chooseMenuOption() throws IOException{

        printWriter.println("Donner votre available_choices");
        printWriter.flush();
        printWriter.println("1-Jouer Pierre \uD83D\uDDFF | Papier \uD83D\uDCC4 | Ciseaux ✂.");
        printWriter.flush();
        printWriter.println("2-Connaître l'historique des parties.");
        printWriter.flush();
        printWriter.println("3-Quitter");
        printWriter.flush();
        return bufferReader.readLine();
    }
    public String readUserRoundChoice(int round_number) throws IOException {
        printWriter.println("[ROUND " + round_number + "]");
        printWriter.flush();
        printWriter.println("1-Pierre \uD83D\uDDFF");
        printWriter.flush();
        printWriter.println("2-Papier \uD83D\uDCC4");
        printWriter.flush();
        printWriter.println("3-Ciseaux ✂");
        printWriter.flush();
        return bufferReader.readLine();
    }
    @Override
    public void run() {
        String middlewareMode;
        Map<String, String> available_choices = new HashMap<>();
        available_choices.put("1","Pierre \uD83D\uDDFF");
        available_choices.put("2","Papier \uD83D\uDCC4");
        available_choices.put("3","Ciseaux ✂");
        String user_choice;
        String uuid = "";

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        try {
            config.setServerURL(new
                    URL("http://localhost:5005/xmlrpc"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        JeuxInterface game_stub = null;
        try {
             game_stub = (JeuxInterface) Naming.lookup("rmi://localhost:5006/Jeux/PFC");
        }catch(Exception e){
            System.out.println(e.toString());
        }
         XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        int gagnant;
        int user_choice_to_int;
        int bot_choice_to_int;
        String bot_choice ="";
        String menu_option="";
        boolean test;
        int res_finale,user_win_rounds,bot_win_rounds;
        int game_number=0;
        try {
            middlewareMode = chooseMode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (!socket.isClosed()) {
            try {

                if (uuid.isEmpty()) {
                    uuid = bufferReader.readLine();
                    if (middlewareMode.equals("1"))
                        test = (boolean) client.execute("Calculator." + "initClientUuid", new Object[]{uuid});
                    else {
                        if (game_stub != null)
                            game_stub.initClientUuid(uuid);
                    }
                }
                    menu_option = chooseMenuOption();
                    int available_choices_menu=Integer.parseInt(menu_option);
                    switch(available_choices_menu){
                        case 1:
                            game_number++;
                            user_win_rounds = 0;
                            bot_win_rounds = 0;
                            for (int i = 1; i <= 3; i++) {
                                user_choice = readUserRoundChoice(i);
                                printWriter.println("USER 👨:" + available_choices.get(user_choice));
                                printWriter.flush();
                                if(middlewareMode.equals("1")) {
                                    bot_choice = (String) client.execute("Calculator." + "choice_gen", new Object[]{});
                                }
                                else
                                {
                                    System.out.println("RMI");
                                    if(game_stub != null){
                                       bot_choice = game_stub.choice_gen();
                                    }
                                }
                                bot_choice_to_int = Integer.parseInt(bot_choice);
                                bot_choice = String.valueOf(bot_choice_to_int);
                                printWriter.println("BOT \uD83E\uDD16:" + available_choices.get(bot_choice));
                                printWriter.flush();
                                user_choice_to_int = Integer.parseInt(user_choice);
                                if (middlewareMode.equals("1")){
                                        gagnant = (int) client.execute("Calculator." + "gagnant_det", new
                                        Object[]{user_choice_to_int, bot_choice_to_int, uuid, i});
                                }
                                else
                                {
                                    if (game_stub != null)
                                        gagnant = game_stub.gagnant_det(user_choice_to_int,bot_choice_to_int,uuid,i);
                                    else
                                        gagnant = -1;
                                }
                                printWriter.println("LE GAGNANT DE CE ROUND EST " + (gagnant == 1 ? "USER 👨" : "BOT \uD83E\uDD16"));
                                printWriter.flush();
                                if (gagnant == 1)
                                    user_win_rounds++;
                                else if (gagnant == 2)
                                    bot_win_rounds++;
                                else {
                                    user_win_rounds++;
                                    bot_win_rounds++;
                                }
                            }
                            if(middlewareMode.equals("1")) {
                                res_finale = (int) client.execute("Calculator." + "score", new
                                        Object[]{user_win_rounds, bot_win_rounds, uuid});
                            }
                            else
                            {
                                if(game_stub!=null)
                                    res_finale = game_stub.score(user_win_rounds, bot_win_rounds, uuid);
                                else
                                    res_finale = -1;
                            }
                            printWriter.println("LE GAGNANT FINALE EST : " + (res_finale == 1 ? "USER 👨" : "BOT \uD83E\uDD16"));
                            printWriter.flush();
                            break;

                        case 2:
                            String historique = "";
                            if(middlewareMode.equals("1"))
                            {
                                historique = (String) client.execute("Calculator." + "showHistory", new
                                        Object[]{uuid});
                            }
                            else
                            {
                                if(game_stub != null)
                                    historique = game_stub.showHistory(uuid);
                            }
                            printWriter.println(game_number);
                            printWriter.flush();
                            for (int i=1;i<=game_number*5+2;i++){
                                printWriter.println(historique.substring(0,historique.indexOf("\n")));
                                historique=historique.substring(historique.indexOf("\n")+1);
                                printWriter.flush();
                            }
                            break;

                        case 3:
                            printWriter.println("END");
                            printWriter.flush();
                            socket.close();
                            break;

                    }


            } catch (IOException | XmlRpcException e) {
                throw new RuntimeException(e);
            }


        }


    }
}
