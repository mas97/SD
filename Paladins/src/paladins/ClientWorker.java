/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author MarcoSilva
 */
public class ClientWorker implements Runnable {

    private BufferedReader in;
    private String nickname;

    public ClientWorker(BufferedReader in) {
        this.in = in;
    }

    public void run() {

        String buffer = "init";

        try {
            while (true) {
                
                buffer = in.readLine();
                System.out.print(buffer + ": ");
                buffer = in.readLine();
                System.out.println(buffer);
            }

        } catch (IOException e) {
        }
    }

}
