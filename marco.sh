#!/usr/bin/env bash

cp -r /home/rokai/Dropbox/1º\ Semestre/4_SD/4_Projeto/mas97/Paladins /home/rokai/Documents/

cd /home/rokai/Documents/Paladins/src/paladins/

javac -d . ChatEscolhaHerois.java FazEquipas.java HashChats.java HashFazEquipas.java HashJogos.java HashTimers.java Heroi.java JogadoresInscritos.java Jogador.java Jogo.java Matchmaking.java Partida.java ServerWorker.java Servidor.java Timer.java TrataJogadorEscrita.java TrataJogadorLeitura.java

javac -d . Cliente.java ClientWorker.java

echo "Devia começar agora o servidor"

java paladins.Servidor
