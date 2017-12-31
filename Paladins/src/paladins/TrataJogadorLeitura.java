/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


//LÊ DO CLIENTE E MANDA PARA A ESTRUTURA
public class TrataJogadorLeitura implements Runnable {
	PrintWriter out;
	BufferedReader in;
	ChatEscolhaHerois chat;
	int equipa;
	String username;
    
    TrataJogadorLeitura (BufferedReader in, PrintWriter out, ChatEscolhaHerois c, int equipa, String username) {
		this.in = in;
		this.out = out;
		this.chat = c;
		this.equipa = equipa;
		this.username = username;
    }
    
    public void run() {
        
        try {

			String current;
			// Se receber um morre do ClienteWorker então tem de terminar.
	        while ((current = in.readLine()) != null && !current.equals("morre"))
	        {
				//Filtrar a info antes de chegar ao chat para difundir
				if(!current.equals("timeout") && !current.equals("start") && chat.existe(current)) {
					if (!chat.isEscolhido(current, equipa)) {
						chat.escolheHeroi(current, equipa, username);
					}
					else
						out.println("\nO herói já se encontra escolhido.\n");
				}
				else 
					out.println("\nO herói escolhido não existe.\n");
	        }
	        
	    } catch ( IOException e) { e.printStackTrace(); }
        
    }

}
