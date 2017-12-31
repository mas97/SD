/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author MarcoSilva
 */
public class ClientWorker implements Runnable {

    private BufferedReader in;
	private PrintWriter out;
	private boolean jogar;
	
    public ClientWorker(BufferedReader in, PrintWriter out) {
        this.in = in;
		this.out = out;
		this.jogar = false;
	}

	public boolean isJogar() {
		return jogar;
	}

    public void run() {

        String buffer = "init";

        try {
			// Se receber start ou timeout quer dizer que a escolha dos heróis
			// acabou.
            while (!buffer.equals("start") && !buffer.equals("timeout")) {
                buffer = in.readLine();
                System.out.println("\n"+buffer);
            }
			// Guarda se vamos a jogo ou não
			if (buffer.equals("start"))
				jogar = true;
			
			System.out.println("Prima enter para começar o jogo");
			//Para desligar o TrataJogadorLeitura
			out.println("morre");
			
        } catch (IOException e) {
			e.printStackTrace();
        }
    }

}
