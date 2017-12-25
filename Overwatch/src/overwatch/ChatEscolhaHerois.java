/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

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
	private List<Heroi> herois_equipa1;
    private ReentrantLock lock1;
    
	//Heróis escolhidos da equipa 2
    private List<Heroi> herois_equipa2;
    private ReentrantLock lock2;

	public ChatEscolhaHerois(HashMap<String, Heroi> herois) {
		this.log = new ArrayList<>();
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

	public String escolheHeroi(int minhaEquipa) {
		
	}

}
