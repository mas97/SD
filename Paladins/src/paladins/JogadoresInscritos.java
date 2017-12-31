/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.util.HashMap;
import java.util.Map;


public class JogadoresInscritos {
	private Map<String, Jogador> jogadores;
	
	public JogadoresInscritos(HashMap<String, Jogador> jogadores) {
		this.jogadores = jogadores;
	}
	
	public synchronized void putJogador (Jogador j) {
		jogadores.put(j.getUsername(), j);
	}
	
	public synchronized boolean containsJogador (String username) {
		return jogadores.containsKey(username);
	}

	public synchronized Jogador getJogador (String username) {
		return jogadores.get(username);
	}
}
