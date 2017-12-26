/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author rokai
 */
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
            
//	        BufferedReader in = new BufferedReader( new InputStreamReader( cs.getInputStream() ) );
//			PrintWriter out = new PrintWriter(cs.getOutputStream(), true);

	        System.out.println("New Connection Received");

			String current;
	        while ((current = in.readLine()) != null)
	        {
				//Filtrar a info antes de chegar ao chat para difundir
				if(chat.existe(current)) {
					if (!chat.isEscolhido(current, equipa)) 
						chat.escolheHeroi(current, equipa, username);
					else
						out.println("esta escolhido");
				}
				else 
					out.println("nao existe");
	            System.out.println("echo: " + current);
	        }

//	        in.close();
//			out.close();
//	        cs.close();
	        System.out.println("Connection Closed");
	        
	    } catch ( IOException e) { e.printStackTrace(); }
        
    }

}
