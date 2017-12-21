/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author MarcoSilva
 */
public class ServerWorker implements Runnable {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
	//Partilhado por todas as threads
    private Map<String, Jogador> jogadores;
	private String jogSessao;
	private HashMap<Integer, Matchmaking> salasRank;
	private CriadoresEquipas ce;

	

    public ServerWorker(Socket socket, Map<String, Jogador> jogadores, 
						HashMap<Integer, Matchmaking> salasRank, 
						CriadoresEquipas ce) {
        this.socket = socket;
        this.jogadores = jogadores;
		this.jogSessao = null;
		this.salasRank = salasRank;
		this.ce = ce;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Erro no establecimento da ligação.");
        }
    }

    public void run() {

        String username = null;
        String password = null;

        try {

            String option = in.readLine();

            if (option.equals("1")) {
                boolean registou = false;
                
                while (!registou) {
                    username = in.readLine();
                    if (this.jogadores.containsKey(username)) {
                        out.println("Indisponivel");
                    } else {
						out.println("OK");

                        password = in.readLine();
                        Jogador novo_jogador = new Jogador(username, password, 0);
						//Fazer "lock" para escrita segura
                        this.jogadores.put(username, novo_jogador);
						this.jogSessao = username;
                        registou = true;
                    }
                }
            } else if (option.equals("2")) {
                boolean iniciou_sessao = false;
		
                while(!iniciou_sessao){
					username = in.readLine();
					if (!this.jogadores.containsKey(username)){
						out.println("nao existe");
					}
					else {
						out.println("username válido");

						while (!iniciou_sessao) {
							password = in.readLine();
							Jogador aux = this.jogadores.get(username);
							if (aux.checkPassword(password)){
								out.println("palavra-passe válida");
								this.jogSessao = username;
								iniciou_sessao = true;
							}
							else {
								out.println("palavra-passe errada");
							}
						}
					}
                }
            }
			
			//SESSÃO INICIADA
			if (jogSessao != null) {
				try {
					//MATCHMAKING
					int meuRank = jogadores.get(jogSessao).getRank();
					
					//Carimbo que diz a que partida pertenço dentro do mesmo rank
					int minhaPartida = -1;
					
					//Sala -> é o número da queue em que entra
					int minhaSala = -1;
					
					//Vai buscar a sala indicada para o rank do jogSessao e faz queue
					int numJogMeuRank = salasRank.get(meuRank).getNumJog();
					if (meuRank != 9 && meuRank != 0) {
						int numJogAntRank = salasRank.get(meuRank - 1).getNumJog();
						if (numJogMeuRank >= numJogAntRank) {
							minhaPartida = salasRank.get(meuRank).queue();
							minhaSala = meuRank;
						}
						else {
							minhaPartida = salasRank.get(meuRank - 1).queue();
							minhaSala = meuRank - 1;
						}
					}
					else if (meuRank == 9) {
						minhaPartida = salasRank.get(meuRank - 1).queue();
						minhaSala = meuRank - 1;
					}
					else {
						minhaPartida = salasRank.get(meuRank).queue();
						minhaSala = meuRank;
					}
					
					//FAZER EQUIPAS
					// Cada equipa pode ser identificada unicamente com base na
					// minhaSala e na minhaPartida.
					
					Tuplo t = new Tuplo<>(minhaSala, minhaPartida);
					
//					MakeEquipa me = new MakeEquipa(0);
//					ce.acrescentaMakeEquipa(t, me);
//					System.out.println(ce.existeMakeEquipa(t));
					
					ce.criaMakeEquipa(t, minhaSala);
					
					int minhaEquipa = ce.getMakeEquipa(t).makeEquipa(meuRank);
					
					out.println(minhaEquipa);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

            this.in.close();
            this.out.close();
            this.socket.close();

        } catch (IOException e) {
        }
    }

}
