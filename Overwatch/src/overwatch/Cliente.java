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
 * @author MarcoSilva
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

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

		BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));

        try {

            while (!inicio_sessao) {

                System.out.println("Bem vindo ao Overwatch!\nPara se registar selecione 1.\nSe já tem uma conta, selecione 2 para iniciar sessão.");

                String option = inputUser.readLine();
		
				out.println(option);

                boolean username_valido = false;

                if (option.equals("1")) {
                    while (!username_valido) {
                        System.out.print("Introduza o nome de utilizador que pretende associar à sua conta: ");
                        buffer = inputUser.readLine();

						out.println(buffer);

                        if (in.readLine().equals("OK")) {
                            username_valido = true;
                            System.out.print("O nome de utilizador encontra-se disponível.\nIntroduza uma palavra passe de registo: ");
                            buffer = inputUser.readLine();
                            out.println(buffer);
                            inicio_sessao = true;
                        } else { //talvez precisamos aqui de uma var status para verificar o que o servidor respondeu
                            System.out.print("O nome de utilizador não se encontra disponível.\nSe pretender cancelar o processo de registo selecione a opção 0.");
                            if (inputUser.readLine().equals("0")) {
                                break;
                            }
                        }

                    }
                } else if (option.equals("2")) {
                    boolean username_existe = false;

                    while (!username_existe) {
                        System.out.print("Introduza o seu nome de utilizador: ");
                        buffer = inputUser.readLine();
                        out.println(buffer);

                        buffer = in.readLine();
                        System.out.println("buffer: " + buffer);
			
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
            
            System.out.println("Iniciou sessão com sucesso");
			String resposta = in.readLine();
			System.out.println("Equipa: " + resposta);

			inputUser.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
        }

    }
}
