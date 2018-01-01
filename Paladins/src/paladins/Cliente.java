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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MarcoSilva
 */
public class Cliente implements Runnable {

    private final String username;
    private final String password;

    public Cliente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
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
			e.printStackTrace();
        }

        String buffer = "init";

        boolean inicio_sessao = false;
        boolean sair = false;

        BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));

        try {

            String option = "init";

            while (!inicio_sessao) {
				
				option = "1";

                out.println(option);

                boolean username_valido = false;

                // REGISTO -----------------------------------------------------
                boolean opcao_correta = false;

                if (option.equals("1")) {
                    opcao_correta = true;
					
                    while (!username_valido) {
                        buffer = this.username;

                        out.println(buffer);
						
                        if (in.readLine().equals("OK")) {
                            username_valido = true;
                            buffer = this.password;
                            out.println(buffer);
                            inicio_sessao = true;
                        }
                    }
                }
            }

            // SESSÃO INICIADA -------------------------------------------------
			
            // MATCHMAKING -----------------------------------------------------
            while (!sair) {

                buffer = "1";
                out.println(buffer);

                if (buffer.equals("1")) {
					System.out.println("[ " + username + " ] Queue");
                    String resposta = in.readLine();
                    System.out.println("[ " + username + " ] Equipa: " + resposta);

                    //ESCOLHA DOS HEROIS -----------------------------------------------
                    //inicialização do worker do cliente que irá ouvir constantemente qq mensagem que chegue
                    ClientWorker cw = new ClientWorker(in, out, herois, username);
                    Thread t = new Thread(cw);
                    t.start();
                    
                    System.out.println("[ " + username + " ] Escolha heroi");

                    //Main thread espera que a escolha do herói acabe.
                    t.join();

                    if (cw.isJogar()) {
						System.out.println("[ " + username + " ] Jogar");
                        //Lê o resultado do jogo
                        buffer = in.readLine();
                        System.out.println("[ " + username + " ] Resultado: " + buffer);
                    }
                } else if (buffer.equals("0")) {
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
