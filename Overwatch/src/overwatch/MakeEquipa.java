/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

/**
 *
 * @author rokai
 */
public class MakeEquipa {
	final int N;
	int numJog = 0;
	int stage = 0;

	public MakeEquipa(int n) { this.N = n; }

	public synchronized void equipa() throws InterruptedException {
		int local = stage;
		numJog++;
		if (numJog < N) {
			while (local == stage)
				wait();
		} else {
			stage++;
			numJog = 0;
			notifyAll();
		}
	}
}
