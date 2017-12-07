/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

/**
 *
 * @author MarcoSilva
 */
public class Jogador {

    /* Variáveis de instância */
    private String username;
    private String password;

    /* Construtor vazio */
    public Jogador() {
        this.username = "NA";
        this.password = "NA";//colocamos caracteres aleatórios para segunrança??
    }

    /* Construtor parametrizado */
    public Jogador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /* Método privado apenas utilizado nesta classe no método checkPassword */
    private String getPassword() {
        return this.password;
    }

    /* Método utilizado para verificação da password de um Jogador */
    public boolean checkPassword(String password) {
        return this.getPassword().equals(password);
    }

}
