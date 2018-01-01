/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


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
	
	//Resultado da escolha dos herois (timeout/start)
	private boolean jogar;

	public ChatEscolhaHerois(HashMap<String, Heroi> herois) {
		this.log = new ArrayList<>();
		this.herois_equipa1 = new HashMap<>();
		this.herois_equipa2 = new HashMap<>();
		this.herois = herois;
		this.jogar = false;
		this.lock1 = new ReentrantLock();
		this.lock2 = new ReentrantLock();
	}

	public synchronized void add (String s)
	{
		log.add(s);
		notifyAll();
	}
	
	public void writeloop (PrintWriter pw)
	{
		int i = 0;
		String s = "init";
		try {
			// Se estiver escrito timeout ou start no log,
			// quer dizer que o tempo de escolha dos heróis acabou.
			while (!s.equals("timeout") && !s.equals("start")) {
				synchronized (this) {
					while (i >= log.size()) {
						wait();
					}
					s = log.get(i);
				}
				
				//Quando o timer acaba é preciso ver se todos escolher um herói.
				if (s.equals("timeout")) {
					int count1 = herois_equipa1.size();
					int count2 = herois_equipa2.size();
//					System.out.println("Count1: " + count1 + " || Count2: " + count2);
					if (count1 == 5 && count2 == 5) {
						s = "start";
						this.jogar = true;
					}
				}
				
				pw.println(s);
				i++;
			}
			
		} catch (InterruptedException ex) {
			Logger.getLogger(ChatEscolhaHerois.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean isJogar() {
		return jogar;
	}
	
	private HashMap<String, String> daEquipa (int equipa) {
		if (equipa == 1) {
			return herois_equipa1;
		}
		return herois_equipa2;
	}
	
	private ReentrantLock daLock (int equipa) {
		if (equipa == 1) {
			return lock1;
		}
		return lock2;
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

	//Coloquei synchronized por causa do "put".
	public void escolheHeroi(String heroiEscolhido, int equipa, String username) {
		HashMap<String, String> heroisE = this.daEquipa(equipa);
		// Já escolheu um herói e quer substituir
		
		this.daLock(equipa).lock();
		try {
			if (heroisE.containsValue(username))
				for (String heroi: heroisE.keySet()) {
					if (heroisE.get(heroi).equals(username)) {
						heroisE.remove(heroi);
						break;
					}
				}
			
			// Se tinha lá um escolhido foi removido.
			// Inserir a nova opção.
			heroisE.put(heroiEscolhido, username);
		} finally {
			this.daLock(equipa).unlock();
		}
		
		//Adiciona a mensagem ao chat para ser difundida
		this.add("[ Equipa " + equipa + " ]  " + username + " escolheu: " + heroiEscolhido);
	}
}
