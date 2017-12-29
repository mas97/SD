/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MarcoSilva
 */
public class Timer implements Runnable {

    //número de jogadores que estão em espera para iniciar a escolha de personagens
    private int em_espera;

    public Timer(HashChats chats) {
        this.em_espera = 0;
    }
    
    @Override
    public void run() {
        try {
            if (em_espera < 10) {
                this.em_espera++;
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Thread.currentThread().sleep(30000);
                notifyAll();
                this.em_espera = 0;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
