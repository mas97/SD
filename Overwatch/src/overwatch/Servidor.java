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

		// Map que dado um rank (0-8) corresponde a respetiva sala de matchmaking
		HashMap<Integer, Matchmaking> salasRank = new HashMap<>();
		//Cria as 9 salas para fazer o matchmaking
		for (int i = 0; i < 9; i++)	
			salasRank.put(i, new Matchmaking(2));
		
		// Hash com os dados necessários para criar uma equipa
		HashFazEquipas ce = new HashFazEquipas();
		
		// Hash com o registo de todos os jogadores
		HashMap<String, Jogador> jogadoresHash = new HashMap<>();
		JogadoresInscritos jogadores = new JogadoresInscritos(jogadoresHash);
		
		//HashMap com os 30 heróis
		HashMap<String, Heroi> herois = new HashMap<>();
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Buck", new Heroi("Buck"));
		herois.put("Cassie", new Heroi("Cassie"));
		herois.put("Fernando", new Heroi("Fernando"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Barik", new Heroi("Barik"));
		
        try {
            ServerSocket sSocket = new ServerSocket(12345);
            while (true) {
                Socket clSocket = sSocket.accept();
                Thread t = new Thread(new ServerWorker(clSocket, jogadores, salasRank, ce, herois));
                t.start();
            }

        } catch (IOException e) {
        }
    }

}
