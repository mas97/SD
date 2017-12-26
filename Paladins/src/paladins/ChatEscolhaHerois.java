/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rokai
 */
public class ChatEscolhaHerois {
	private ArrayList<String> log;
	
	//Hash dos possíveis heróis.
	private HashMap<String, Heroi> herois;
	
	//Heróis escolhidos da equipa 1
	// Key -> nome herói
	// Value -> username
	private HashMap<String, String> herois_equipa1;
    private ReentrantLock lock1;
    
	//Heróis escolhidos da equipa 2
	// Key -> nome herói
	// Value -> username
    private HashMap<String, String> herois_equipa2;
    private ReentrantLock lock2;

	public ChatEscolhaHerois(HashMap<String, Heroi> herois) {
		this.log = new ArrayList<>();
		this.herois_equipa1 = new HashMap<>();
		this.herois_equipa2 = new HashMap<>();
		this.herois = herois;
	}

	public synchronized void add (String s)
	{
		log.add(s);
		notifyAll();
	}
	
	public void writeloop (PrintWriter pw)
	{
		int i = 0;
		String s;
		try {
			while (true) {
				synchronized (this) {
					while (i >= log.size()) wait();
					s = log.get(i);
				}

				pw.println(s);
				i++;
			}
		} catch (InterruptedException ex) {
			Logger.getLogger(ChatEscolhaHerois.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private HashMap<String, String> daEquipa (int equipa) {
		if (equipa == 1) {
			return herois_equipa1;
		}
		return herois_equipa2;
	}
	
	public String getHeroi(int equipa, String username) {
		String res = null;
		HashMap<String, String> heroisE = this.daEquipa(equipa);
		for (String h : heroisE.keySet()) {
			if (heroisE.get(h).equals(username))
				res = h;
		}
		return res;
	}

	public boolean existe (String inputJogador) {
		return this.herois.containsKey(inputJogador);
	}
	
	public boolean isEscolhido(String inputJogador, int equipa) {
		HashMap<String, String> heroisE = this.daEquipa(equipa);
		return heroisE.containsKey(inputJogador);
	}

	public void escolheHeroi(String heroi, int equipa, String username) {
		HashMap<String, String> heroisE = this.daEquipa(equipa);
		heroisE.put(heroi, username);
		
		this.add(username + " escolheu: " + heroi);
	}
}
