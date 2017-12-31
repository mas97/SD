/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

/**
 *
 * @author MarcoSilva
 */
public class TestClass {

    public static void main(String[] args) {

        System.out.println("Iniciando o servidor de jogo...");
        Thread server = new Thread(new Servidor());
        server.start();

        NameGenerator gen = new NameGenerator();

        for (int i = 0; i < 100; i++) {
            String username = gen.getName();
            String password = gen.getName();

            System.out.println("Jogador criado com o username " + username + " e com a password " + password);
            
        }
    }
}
