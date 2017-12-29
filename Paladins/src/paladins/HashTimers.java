/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rokai
 */
public class HashTimers {
	//Dado uma sala e uma partida, devolve a partida respetiva
	private Map<Integer, Map<Integer, Timer>> timers;
	
	public HashTimers () {
		this.timers = new HashMap<>();
	}
	
	public synchronized Timer getTimer(int rankSala, int codPartida) {
		return timers.get(rankSala).get(codPartida);
	}

	public synchronized void criaTimer(int rankSala, int codPartida, ChatEscolhaHerois chat) {
		//Se não contém sala, então cria-se um hashmap com a partida lá dentro
		if(!timers.containsKey(rankSala)) {
			Timer t = new Timer(chat);
			HashMap<Integer, Timer> newTimer = new HashMap<>();
			newTimer.put(codPartida, t);
			timers.put(rankSala, newTimer);
		}
		// Se já contém uma sala para o rank específico e não tem uma partida, 
		// então basta criar uma partida.
		else if (!timers.get(rankSala).containsKey(codPartida)){
			Timer t = new Timer(chat);
			timers.get(rankSala).put(codPartida, t);
		}
	}
}
