/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rokai
 */
public class HashChats {
	//Dado uma sala e uma partida, devolve a partida respetiva
	private Map<Integer, Map<Integer, ChatEscolhaHerois>> chats;
	
	public HashChats () {
		this.chats = new HashMap<>();
	}
	
	public synchronized ChatEscolhaHerois getChat(int rankSala, int codPartida) {
		return chats.get(rankSala).get(codPartida);
	}

	public synchronized void criaChat(int rankSala, int codPartida, HashMap<String, Heroi> herois) {
		//Se não contém sala, então cria-se um hashmap com a partida lá dentro
		if(!chats.containsKey(rankSala)) {
			ChatEscolhaHerois c = new ChatEscolhaHerois(herois);
			HashMap<Integer, ChatEscolhaHerois> newChat = new HashMap<>();
			newChat.put(codPartida, c);
			chats.put(rankSala, newChat);
		}
		// Se já contém uma sala para o rank específico e não tem uma partida, 
		// então basta criar uma partida.
		else if (!chats.get(rankSala).containsKey(codPartida)){
			ChatEscolhaHerois c = new ChatEscolhaHerois(herois);
			chats.get(rankSala).put(codPartida, c);
		}
	}
}
