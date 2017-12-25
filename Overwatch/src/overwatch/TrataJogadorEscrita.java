/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author rokai
 */
public class TrataJogadorEscrita {
	Socket cs;
	ChatEscolhaHerois chat;
    
    TrataJogadorEscrita (Socket cs, ChatEscolhaHerois c) {
        this.cs = cs;
		this.chat = c;
    }
    
    public void run() {
        
		try {
			PrintWriter out = new PrintWriter( cs.getOutputStream(), true );
			chat.writeloop(out);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
        
    }


}
