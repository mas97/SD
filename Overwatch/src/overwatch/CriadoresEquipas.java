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
public class CriadoresEquipas {
	//Dado uma sala e um partida, devolve a partida respetiva
	private Map<Integer, Map<Integer, FazEquipas>> criadoresEquipas;
	
	public CriadoresEquipas () {
		this.criadoresEquipas = new HashMap<>();
	}
	/*
	public synchronized boolean existeMakeEquipa (Tuplo<Integer, Integer> t) {
		return criadoresEquipas.containsKey(t);
	}
	
	public synchronized void acrescentaMakeEquipa (Tuplo<Integer, Integer> t, MakeEquipa me) {
		criadoresEquipas.put(t, me);
	}
*/
	
	public synchronized FazEquipas getFazEquipa(int rankSala, int partida) {
		return criadoresEquipas.get(rankSala).get(partida);
	}

	
	public synchronized void criaFazEquipa(int rankSala, int numPartida) {
		//Se não contém sala, então cria-se um hashmap com a partida lá dentro
		if(!criadoresEquipas.containsKey(rankSala)) {
			FazEquipas me = new FazEquipas(rankSala);
			HashMap<Integer, FazEquipas> newMakeEquipa = new HashMap<>();
			newMakeEquipa.put(numPartida, me);
			criadoresEquipas.put(rankSala, newMakeEquipa);
		}
		// Se já contém uma sala para o rank específico, então basta criar uma
		// partida
		else if (!criadoresEquipas.get(rankSala).containsKey(numPartida)){
			FazEquipas me = new FazEquipas(rankSala);
			criadoresEquipas.get(rankSala).put(numPartida, me);
		}
	}
					
}
