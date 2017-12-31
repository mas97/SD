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
public class HashJogos {
	//Dado uma sala e uma partida, devolve o jogo respetivo
	private Map<Integer, Map<Integer, Jogo>> jogos;
	
	public HashJogos () {
		this.jogos = new HashMap<>();
	}
	
	public synchronized Jogo getJogo(int rankSala, int codPartida) {
		return jogos.get(rankSala).get(codPartida);
	}

	public synchronized void criaJogo(int rankSala, int codPartida) {
		//Se não contém sala, então cria-se um hashmap com o jogo lá dentro
		if(!jogos.containsKey(rankSala)) {
			Jogo j = new Jogo();
			HashMap<Integer, Jogo> newJogo = new HashMap<>();
			newJogo.put(codPartida, j);
			jogos.put(rankSala, newJogo);
		}
		// Se já contém uma sala para o rank específico e não tem uma partida, 
		// então basta criar um jogo.
		else if (!jogos.get(rankSala).containsKey(codPartida)){
			Jogo j = new Jogo();
			jogos.get(rankSala).put(codPartida, j);
		}
	}
}
