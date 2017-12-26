/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

/**
 *
 * @author rokai
 */
public class FazEquipas {
	
	final int rankSala;
	int numMaioresEquip1;
	int numPreenchidosEquip1;
	int numMaioresEquip2;
	int numPreenchidosEquip2;

	public FazEquipas(int rankSala) { 
		this.rankSala = rankSala;
		this.numMaioresEquip1 = 0;
		this.numPreenchidosEquip1 = 0;
		this.numMaioresEquip2 = 0;
		this.numPreenchidosEquip2 = 0;
	}

	public synchronized int fazEquipa(int rankJog) throws InterruptedException {
		//Equipa: 1 ou 2
		int numEquipa = 0;
		//Colunas iguais
		if (numPreenchidosEquip1 == numPreenchidosEquip2) {
			if (rankJog > rankSala) { //Colunas iguais e sou maior
				if (numMaioresEquip1 <= numMaioresEquip2) {
					numEquipa = 1;
					numMaioresEquip1++;
					numPreenchidosEquip1++;
				}
				else {
					numEquipa = 2;
					numMaioresEquip2++;
					numPreenchidosEquip2++;
				}
			}
			else { //Colunas iguais e sou menor
				if (numMaioresEquip1 >= numMaioresEquip2) {
					numEquipa = 1;
					numPreenchidosEquip1++;
				}
				else {
					numEquipa = 2;
					numPreenchidosEquip2++;
				}
			}
		}
		//Coluna da esquerda é menor
		else if (numPreenchidosEquip1 <= numPreenchidosEquip2) {
			numEquipa = 1;
			numPreenchidosEquip1++;
			if (rankJog > rankSala)
				numMaioresEquip1++;
		}
		//Coluna da direita é menor
		else {
			numEquipa = 2;
			numPreenchidosEquip2++;
			if (rankJog > rankSala)
				numMaioresEquip2++;
		}
			
		return numEquipa;
	}
}
