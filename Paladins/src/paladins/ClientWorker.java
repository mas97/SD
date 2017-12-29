/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author MarcoSilva
 */
public class ClientWorker implements Runnable {

    private BufferedReader in;
	private PrintWriter out;
//	private JavaRobotRescue robot;
	
    public ClientWorker(BufferedReader in, PrintWriter out){ //, JavaRobotRescue robot) {
        this.in = in;
		this.out = out;
//		this.robot = robot;
    }

    public void run() {

        String buffer = "init";

        try {
			// Se receber start ou timeout quer dizer que a escolha dos heróis
			// acabou.
            while (!buffer.equals("start") && !buffer.equals("timeout")) {
                buffer = in.readLine();
                System.out.println(buffer);
            }
			System.out.println("Prima enter para começar o jogo");
			//Para desligar o TrataJogadorLeitura
			out.println("morre");

			//Para desligar o ciclo de leitura do herói do jogador.
//			robot.typeEnter();
        } catch (IOException e) {
			e.printStackTrace();
        }
    }

}
