/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author MarcoSilva
 */
public class Servidor {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        //map de outputs que será utilizado para partilhar as mensagens com os outros clientes 
        HashMap<String, Jogador> jogadores = new HashMap<> ();
		HashMap<Integer, Matchmaking> salasRank = new HashMap<>();
		//Cria as 9 salas para fazer o matchmaking
		for (int i = 0; i < 9; i++)	
			salasRank.put(i, new Matchmaking(2));

        try {
            ServerSocket sSocket = new ServerSocket(12345);
            while (true) {
                Socket clSocket = sSocket.accept();
                Thread t = new Thread(new ServerWorker(clSocket, jogadores, salasRank));
                t.start();
            }

        } catch (IOException e) {
        }
    }

}
