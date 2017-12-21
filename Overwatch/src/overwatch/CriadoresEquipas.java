/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author rokai
 */
public class CriadoresEquipas {
	//Dado uma sala e um partida, devolve a partida respetiva
	private HashMap<Tuplo<Integer, Integer>, MakeEquipa> criadoresEquipas;
	
	public CriadoresEquipas () {
		this.criadoresEquipas = new HashMap<>();
	}
	
	public synchronized boolean existeMakeEquipa (Tuplo<Integer, Integer> t) {
		return criadoresEquipas.containsKey(t);
	}
	
	public synchronized void acrescentaMakeEquipa (Tuplo<Integer, Integer> t, MakeEquipa me) {
		criadoresEquipas.put(t, me);
	}
	
	public synchronized MakeEquipa getMakeEquipa(Tuplo<Integer, Integer> t) {
		return criadoresEquipas.get(t);
	}
	
	public synchronized void criaMakeEquipa(Tuplo t, int minhaSala) {
		if(!this.existeMakeEquipa(t)) {
			System.out.println("Criou maker");
			MakeEquipa p = new MakeEquipa(minhaSala);
			this.acrescentaMakeEquipa(t, p);
		}
	}
					
}
