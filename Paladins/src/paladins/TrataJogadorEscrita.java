/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.PrintWriter;


//LÊ DA ESTRUTURA E MANDA PARA O CLIENTE
public class TrataJogadorEscrita implements Runnable {
	PrintWriter out;
	ChatEscolhaHerois chat;
    
    TrataJogadorEscrita (PrintWriter out, ChatEscolhaHerois c) {
		this.out = out;
		this.chat = c;
    }
    
    public void run() {
        
			chat.writeloop(this.out);
        
    }


}
