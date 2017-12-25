/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author rokai
 */
public class TrataJogadorLeitura {
	Socket cs;
	ChatEscolhaHerois chat;
    
    TrataJogadorLeitura (Socket cs, ChatEscolhaHerois c) {
        this.cs = cs;
		this.chat = c;
    }
    
    public void run() {
        
        try {
            
	        BufferedReader in = new BufferedReader( new InputStreamReader( cs.getInputStream() ) );

	        System.out.println("New Connection Received");

			String current;
	        while ((current = in.readLine()) != null)
	        {
				//Dentro do add temos controlo de concorrência
				chat.add(current);
	            System.out.println("echo: " + current);
	        }

	        in.close();
	        cs.close();
	        System.out.println("Connection Closed");
	        
	    } catch ( IOException e) { e.printStackTrace(); }
        
    }

}
