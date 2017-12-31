/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Timer implements Runnable {

    //número de jogadores que estão em espera para iniciar a escolha de personagens
    private int em_espera;
	private ChatEscolhaHerois chat;
	private boolean acabouGeral = false;

    public Timer(ChatEscolhaHerois chat) {
        this.em_espera = 0;
		this.chat = chat;
    }
    
    @Override
    public synchronized void run() {
        try {
			boolean acabou = acabouGeral;
			this.em_espera++;
			if (em_espera < 2) {
				while (acabou == acabouGeral)
					wait();
            } else {
                Thread.currentThread().sleep(20000);
				chat.add("timeout");
				acabouGeral = true;
                this.em_espera = 0;
                notifyAll();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
