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
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //HashMap com os 30 heróis
        HashMap<String, Heroi> herois = new HashMap<>();
        herois.put("Barik", new Heroi("Barik"));
        herois.put("Buck", new Heroi("Buck"));
        herois.put("Cassie", new Heroi("Cassie"));
        herois.put("Fernando", new Heroi("Fernando"));
        herois.put("Grohk", new Heroi("Grohk"));
        herois.put("Pip", new Heroi("Pip"));
        herois.put("Ruckus", new Heroi("Ruckus"));
        herois.put("Skye", new Heroi("Skye"));
        herois.put("Evie", new Heroi("Evie"));
        herois.put("Grover", new Heroi("Grover"));
        herois.put("Kinessa", new Heroi("Kinessa"));
        herois.put("Androxus", new Heroi("Androxus"));
        herois.put("Ying", new Heroi("Ying"));
        herois.put("Drogoz", new Heroi("Drogoz"));
        herois.put("Bomb King", new Heroi("Bomb King"));
        herois.put("Viktor", new Heroi("Viktor"));
        herois.put("Makoa", new Heroi("Makoa"));
        herois.put("Mal'Damba", new Heroi("Mal'Damba"));
        herois.put("Sha Lin", new Heroi("Sha Lin"));
        herois.put("Tyra", new Heroi("Tyra"));
        herois.put("Torvald", new Heroi("Torvald"));
        herois.put("Maeve", new Heroi("Maeve"));
        herois.put("Inara", new Heroi("Inara"));
        herois.put("Lex", new Heroi("Lex"));
        herois.put("Seris", new Heroi("Seris"));
        herois.put("Willo", new Heroi("Willo"));
        herois.put("Ash", new Heroi("Ash"));
        herois.put("Zhin", new Heroi("Zhin"));
        herois.put("Lian", new Heroi("Lian"));
        herois.put("Jenos", new Heroi("Jenos"));

        Socket socket = null;

        BufferedReader in = null;

        PrintWriter out = null;

        try {
            socket = new Socket("localhost", 12345);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
        }

        String buffer = "init";

        boolean inicio_sessao = false;
		boolean sair = false;

        BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter outUser = new PrintWriter(System.out, true);

        try {
            
            String option = "init";

            while (!inicio_sessao) {
                
                //coloquei este ciclo meio javardo para colocar a interface um pouco mais robusta no caso de
                // não inserirmos 1 ou 2 no menu (não fiz uma coisa mais bonita porque não estava a acertar com a condição correta)
                while (true){
                System.out.print("\033[H\033[2J");
                System.out.println("Bem vindo ao Overwatch!\n"
                        + "Para se registar selecione 1.\n"
                        + "Se já tem uma conta, selecione 2 para iniciar sessão.");

                option = inputUser.readLine();
                
                if (option.equals("1") || option.equals("2"))
                    break;
                }
                
                out.println(option);

                boolean username_valido = false;

                // REGISTO -----------------------------------------------------
                boolean opcao_correta = false;
                
                    if (option.equals("1")) {
                        opcao_correta = true;
                        
                        while (!username_valido) {
                            System.out.print("\033[H\033[2J");
                            System.out.print("Introduza o nome de utilizador que pretende associar à sua conta: ");
                            buffer = inputUser.readLine();

                            out.println(buffer);

                            if (in.readLine().equals("OK")) {
                                username_valido = true;
                                System.out.print("O nome de utilizador encontra-se disponível.\n"
                                        + "Introduza uma palavra passe de registo: ");
                                buffer = inputUser.readLine();
                                out.println(buffer);
                                inicio_sessao = true;
                            } else { //talvez precisamos aqui de uma var status para verificar o que o servidor respondeu
                                System.out.print("\033[H\033[2J");
                                System.out.print("O nome de utilizador não se encontra disponível.\n"
                                        + "Se pretender cancelar o processo de registo selecione a opção 0.");
                                if (inputUser.readLine().equals("0")) {
                                    break;
                                }
                            }

                        }
                    } // AUTENTICAÇÃO ------------------------------------------------
                    else if (option.equals("2")) {
                        boolean username_existe = false;
                        opcao_correta = true;

                        while (!username_existe) {
                            System.out.print("\033[H\033[2J");
                            System.out.print("Introduza o seu nome de utilizador: ");
                            buffer = inputUser.readLine();
                            out.println(buffer);

                            buffer = in.readLine();

                            if (buffer.equals("username válido")) {
                                username_existe = true;

                                boolean password_valida = false;

                                while (!password_valida) {
                                    System.out.print("Introduza a sua palavra passe: ");
                                    buffer = inputUser.readLine();
                                    out.println(buffer);
                                    if (in.readLine().equals("palavra-passe válida")) {
                                        password_valida = true;
                                        inicio_sessao = true;
                                    }
                                }
                            }
                        }
                    }
                

            }

            // SESSÃO INICIADA -------------------------------------------------
            System.out.print("\033[H\033[2J");
            System.out.println("Iniciou sessão com sucesso.\n"
                    + "Que deseja fazer?");

            // MATCHMAKING -----------------------------------------------------
			while(!sair) {
				System.out.println("1 - JOGAR\n"
						+ "0 - SAIR");
				System.out.print("Opção: ");
				buffer = inputUser.readLine();
				out.println(buffer);

				if (buffer.equals("1")) {
					System.out.print("\033[H\033[2J");
					System.out.println("Em fila de espera para jogar...");
					String resposta = in.readLine();
					System.out.println("Foi encontrada uma sala!");
					System.out.println("Equipa: " + resposta);

					//ESCOLHA DOS HEROIS -----------------------------------------------
					System.out.print("\033[H\033[2J");
					System.out.println("ESCOLHA DOS HEROIS");
					for (String h : herois.keySet()) {
						System.out.println(h);
					}
					
					//FASE DE CHAT
					//inicialização do worker do cliente que irá ouvir constantemente qq mensagem que chegue
					Scanner inputAux = new Scanner(System.in);
					ClientWorker cw = new ClientWorker(in, out);
					Thread t = new Thread(cw);
					t.start();		


					//Permite ao jogador escolher o herói
					System.out.print("Escolha o seu herói: ");
					while ((buffer = inputAux.nextLine()) != null && !buffer.equals("")) {
						out.println(buffer);
						System.out.print("Escolha o seu herói: ");
					}
					
					//Main thread espera que a escolha do herói acabe.
					t.join();
					
					System.out.print("\033[H\033[2J");
					if (cw.isJogar()) {
						//Lê o resultado do jogo
						buffer = in.readLine();
						System.out.println(buffer);
					}
				}
				else if (buffer.equals("0")) {
					sair = true;
				}
			}

            // SAIR ------------------------------------------------------------
            System.out.print("\033[H\033[2J");
            System.out.print("Desligando");
            sleep(1000);
            System.out.print(".");
            sleep(1000);
            System.out.print(".");
            sleep(1000);
            System.out.println(".");

            inputUser.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
