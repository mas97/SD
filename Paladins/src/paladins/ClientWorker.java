/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author MarcoSilva
 */
public class ClientWorker implements Runnable {

    private BufferedReader in;
	private PrintWriter out;
	private boolean jogar;
	private HashMap<String, Heroi> herois;
	private String username;
	
    public ClientWorker(BufferedReader in, PrintWriter out, HashMap<String, Heroi> herois, String username) {
        this.in = in;
		this.out = out;
		this.jogar = false;
		this.herois = herois;
		this.username = username;
	}

	public boolean isJogar() {
		return jogar;
	}

    public void run() {

//        String buffer = "\nO herói já se encontra escolhido.\n";
		String buffer = "Heroi ja se encontra escolhido";
		boolean primeiro = true;

        try {
			String heroiEscolhido = null;
			Random generator = new Random();
			Object[] values = herois.values().toArray();
			// Se receber start ou timeout quer dizer que a escolha dos heróis
			// acabou.
            while (!buffer.equals("start") && !buffer.equals("timeout")) {
				if (buffer.equals("Heroi ja se encontra escolhido")) {
					if (!primeiro) {
						System.out.println("[ " + username + " ] Herói escolhido igual: " + heroiEscolhido);
					}
					heroiEscolhido = ((Heroi) values[generator.nextInt(values.length)]).getNome();
//					if (!primeiro) {
//						System.out.println("[ " + username + " ] Herói escolhido igual: " + heroiEscolhido);
//					}
					buffer = heroiEscolhido;
					out.println(buffer);
				}
				primeiro = false;
				buffer = in.readLine();
				if (!buffer.equals("start"))
					System.out.println("\n"+buffer);
				
            }
			// Guarda se vamos a jogo ou não
			if (buffer.equals("start"))
				jogar = true;
			
			//Para desligar o TrataJogadorLeitura
			out.println("morre");
			
        } catch (IOException e) {
			e.printStackTrace();
        }
    }

}
