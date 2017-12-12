/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author MarcoSilva
 */
public class Partida {
    
    private List<Heroi> herois_equipa1;
    private Lock lock1;
    
    private List<Heroi> herois_equipa2;
    private Lock lock2;
    
    //construtor terá como parametro um apontador para a estrutura de dados de herois
    public Partida(){
        this.herois_equipa1 = new ArrayList<Heroi> ();
        this.lock1 = new ReentrantLock();
        
        this.herois_equipa2 = new ArrayList<Heroi> ();
        this.lock2 = new ReentrantLock();
    }
}
