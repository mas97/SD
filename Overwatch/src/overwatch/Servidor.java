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
		HashFazEquipas hashFE = new HashFazEquipas();
		
		// Hash com os dados necessários para criar uma equipa
		HashChats hashC = new HashChats();
		
		// Hash com o registo de todos os jogadores
		HashMap<String, Jogador> jogadoresHash = new HashMap<>();
		JogadoresInscritos jogadores = new JogadoresInscritos(jogadoresHash);
		
		//HashMap com os 30 heróis
		HashMap<String, Heroi> herois = new HashMap<>();
		herois.put("Barik", new Heroi("Barik"));
		herois.put("Buck", new Heroi("Buck"));
		herois.put("Cassie", new Heroi("Cassie"));
		herois.put("Fernando", new Heroi("Fernando"));
		herois.put("Grohk", new Heroi("Grohk"));
		herois.put("Pip", new Heroi("Pip"));
		herois.put("Ruckus", new Heroi("Ruckus"));
		herois.put("Skye", new Heroi("Skye"));
		herois.put("Evie", new Heroi("Evie"));
		herois.put("Grover", new Heroi("Grover"));
		herois.put("Kinessa", new Heroi("Kinessa"));
		herois.put("Androxus", new Heroi("Androxus"));
		herois.put("Ying", new Heroi("Ying"));
		herois.put("Drogoz", new Heroi("Drogoz"));
		herois.put("Bomb King", new Heroi("Bomb King"));
		herois.put("Viktor", new Heroi("Viktor"));
		herois.put("Makoa", new Heroi("Makoa"));
		herois.put("Mal'Damba", new Heroi("Mal'Damba"));
		herois.put("Sha Lin", new Heroi("Sha Lin"));
		herois.put("Tyra", new Heroi("Tyra"));
		herois.put("Torvald", new Heroi("Torvald"));
		herois.put("Maeve", new Heroi("Maeve"));
		herois.put("Inara", new Heroi("Inara"));
		herois.put("Lex", new Heroi("Lex"));
		herois.put("Seris", new Heroi("Seris"));
		herois.put("Willo", new Heroi("Willo"));
		herois.put("Ash", new Heroi("Ash"));
		herois.put("Zhin", new Heroi("Zhin"));
		herois.put("Lian", new Heroi("Lian"));
		herois.put("Jenos", new Heroi("Jenos"));
		
		
        try {
            ServerSocket sSocket = new ServerSocket(12345);
            while (true) {
                Socket clSocket = sSocket.accept();
                Thread t = new Thread(new ServerWorker(clSocket, jogadores, salasRank, hashFE, hashC, herois));
                t.start();
            }

        } catch (IOException e) {
        }
    }

}
