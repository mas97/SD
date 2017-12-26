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
public class Jogador {

    /* Variáveis de instância */
    private String username;
    private String password;
	private int rank;
    //eventualmente colocar uma lista de partidas disputadas

    /* Construtor vazio */
    public Jogador() {
        this.username = "NA";
        this.password = "NA";
		this.rank = 0;
    }

    /* Construtor parametrizado */
    public Jogador(String username, String password, int rank) {
        this.username = username;
        this.password = password;
		this.rank = rank;
    }

    /* Método privado apenas utilizado nesta classe no método checkPassword */
    private String getPassword() {
        return this.password;
    }

	public String getUsername() {
		return username;
	}

	public int getRank() {
		return rank;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

    /* Método utilizado para verificação da password de um Jogador */
    public boolean checkPassword(String password) {
        return this.getPassword().equals(password);
    }

}
