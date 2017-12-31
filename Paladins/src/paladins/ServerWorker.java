/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author MarcoSilva
 */
public class ServerWorker implements Runnable {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
	// Todos os jogadores inscritos
    private JogadoresInscritos jogadores;
	// Jogador que se encontra nesta thread
	private String jogSessao;
	// Salas de matchmaking de acordo com o rank
	private HashMap<Integer, Matchmaking> salasRank;
	// Criadores de equipas de acordo com o rank e a partida
	private HashFazEquipas hashFE;
	private HashChats hashC;
	private HashMap<String, Heroi> herois;
	private HashTimers hashT;
	private HashJogos hashJ;

	

    public ServerWorker(Socket socket, JogadoresInscritos jogadores, 
						HashMap<Integer, Matchmaking> salasRank, 
						HashFazEquipas hashFE,
						HashChats hashC,
						HashMap<String, Heroi> herois,
						HashTimers timers,
						HashJogos hashJ) {
        
        this.socket = socket;
        this.jogadores = jogadores;
		this.jogSessao = null;
		this.salasRank = salasRank;
		this.hashFE = hashFE;
		this.hashC = hashC;
		this.herois = herois;
		this.hashT = timers;
		this.hashJ = hashJ;

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
		boolean sair = false;
		

        try {

            String option = in.readLine();

			// REGISTO ---------------------------------------------------------
            if (option != null && option.equals("1")) {
                boolean registou = false;
                
                while (!registou) {
                    username = in.readLine();
                    if (this.jogadores.containsJogador(username)) {
                        out.println("Indisponivel");
                    } else {
						out.println("OK");

                        password = in.readLine();
                        Jogador novo_jogador = new Jogador(username, password, 0);			
                        this.jogadores.putJogador(novo_jogador);
						this.jogSessao = username;
                        registou = true;
                    }
                }
            }
			// AUTENTICAÇÃO ----------------------------------------------------
			else if (option != null && option.equals("2")) {
                boolean iniciou_sessao = false;
		
                while(!iniciou_sessao){
					username = in.readLine();
					if (!this.jogadores.containsJogador(username)){
						out.println("nao existe");
					}
					else {
						out.println("username válido");

						while (!iniciou_sessao) {
							password = in.readLine();
							Jogador aux = this.jogadores.getJogador(username);
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
			
			//SESSÃO INICIADA --------------------------------------------------
			if (jogSessao != null) {
				try {
					while (!sair) {
						option = in.readLine();

						//MATCHMAKING ----------------------------------------------
						if (option != null && option.equals("1")) {
							int meuRank = jogadores.getJogador(jogSessao).getRank();

							System.out.println("[ " + jogSessao + " ]" + " meuRank: " + meuRank);
							
							//Carimbo que diz a que partida pertenço dentro do mesmo rank
							int minhaPartida = -1;

							//Sala -> é o número da queue em que entra
							int minhaSala = -1;

							//Vai buscar a sala indicada para o rank do jogSessao e faz queue
							int numJogMeuRank = salasRank.get(meuRank).getNumJog();
							if (meuRank != 9 && meuRank != 0) {
								int numJogAntRank = salasRank.get(meuRank - 1).getNumJog();
								if (numJogMeuRank > numJogAntRank) {
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

							
							
							//FAZER EQUIPAS --------------------------------------------
							// Cada equipa pode ser identificada unicamente com base na
							// minhaSala e na minhaPartida.

							hashFE.criaFazEquipa(minhaSala, minhaPartida);

							int minhaEquipa = hashFE.getFazEquipa(minhaSala, minhaPartida).fazEquipa(meuRank);

							out.println(minhaSala + "  " + minhaEquipa);

							//ESCOLHA DOS HERÓIS ---------------------------------------
							// Neste ponto sabemos: minhaSala, minhaPartida, minhaEquipa.
							// Estes dados identificam exatamente todo o que precisamos
							// para iniciar o chat para a escolha dos heróis.
							hashC.criaChat(minhaSala, minhaPartida, herois);

							ChatEscolhaHerois meuChat = hashC.getChat(minhaSala, minhaPartida);

							Thread je = new Thread( new TrataJogadorEscrita(this.out, meuChat) );
							je.start();

							Thread jl = new Thread( new TrataJogadorLeitura(in, this.out, meuChat, minhaEquipa, jogSessao) );
							jl.start();

							//TIMER                        
							hashT.criaTimer(minhaSala, minhaPartida, meuChat);

							Timer meuTimer = hashT.getTimer(minhaSala, minhaPartida);

							Thread timer = new Thread( meuTimer );
							timer.start();

							//Sincroniza todas as threads utilizadas no chat
							timer.join();
							je.join();
							jl.join();

							// Se não houve timeout e for para jogar
							if (meuChat.isJogar()) {
						
								String meuHeroi = meuChat.getHeroi(minhaEquipa, jogSessao);

								//CÁLCULO DO RESULTADO -------------------------
								hashJ.criaJogo(minhaSala, minhaPartida);

								Jogo meuJogo = hashJ.getJogo(minhaSala, minhaPartida);
								Thread jogar = new Thread( meuJogo );
								jogar.start();

								// Sincroniza com o final do jogo
								jogar.join();

								int resultadoMeuJogo = meuJogo.getResultado();
								
								if (minhaEquipa == resultadoMeuJogo) {
									if (meuRank != 9) {
										meuRank++;
										jogadores.getJogador(jogSessao).setRank(meuRank);
									}
									out.println("A sua equipa ganhou!");
								}
								else {
									if (meuRank != 0) {
										meuRank--;
										jogadores.getJogador(jogSessao).setRank(meuRank);
									}
									out.println("A sua equipa perdeu...");
								}
							}
						}
						else if (option == null || option.equals("0"))
							sair = true;
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

            //this.in.close();
            //this.out.close();
            //this.socket.close();

        } catch (IOException e) {
        }
    }

}
