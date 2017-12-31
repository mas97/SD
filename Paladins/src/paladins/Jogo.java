/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rokai
 */
public class Jogo implements Runnable{
	
	private int resultado;
	private boolean acabouGeral = false;
	private int em_espera;
	
	public Jogo() {
		this.resultado = 0;
		this.em_espera = 0;
	}
	
	public int getResultado () {
		return resultado;
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
				acabouGeral = true;
                this.em_espera = 0;
				this.resultado = (Math.random()<0.5)?1:2;
                notifyAll();
            }
		} catch (InterruptedException ex) {
			Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

	int joga(int meuRank) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
