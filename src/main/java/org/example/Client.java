package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;
public class Client {


    public static void main(String[] args) throws IOException {
        String str="";  String str1="";
        String uuid = UUID.randomUUID().toString();
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 5001);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        InputStreamReader in= new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(in);
        String res;
        printWriter.println(uuid);
        printWriter.flush();
        while (!str.equals("3")){
            String strServer= bufferedReader.readLine();
            System.out.println(strServer);
            System.out.println("write choice : ");
            str = scanner.nextLine();
            printWriter.println(str);
            printWriter.flush();
            if(str.equals("3")){
                res = bufferedReader.readLine();
                System.out.println(res);
            }else if(str.equals("1")){
                for (int i=1;i<=3;i++) {
                    res = bufferedReader.readLine();
                    System.out.println(res);
                    str1 = scanner.nextLine();
                    printWriter.println(str1);
                    printWriter.flush();
                    res = bufferedReader.readLine();
                    System.out.println(res);
                    res = bufferedReader.readLine();
                    System.out.println(res);
                    res = bufferedReader.readLine();
                    System.out.println(res);
                }
                res=bufferedReader.readLine();
                System.out.println(res);

            }
        }

    }
}

