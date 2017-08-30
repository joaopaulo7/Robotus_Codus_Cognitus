package neatRobotus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import robocode.BattleResults;
import robocode.control.events.BattleCompletedEvent;


public class Principal {
	public static Scanner s;
	public static BattleResults[] evento;
	
	public static void main(String Args[]){
		s = new Scanner(System.in);
		Populacao.populacaoInit();
		BattleRunner batalha = new BattleRunner();
		int maxFit = 0, count = 0, numGenes, numNod;
		double maxFitEq = 0;
		do{
			Populacao.genese();
			//Populacao.getGenoma().mostrarGenoma();
			batalha.startBatalha(false);
			numGenes = Populacao.getGenoma().genes.size();
			numNod = Populacao.getGenoma().nodulos.size();
			if( evento[0].getTeamLeaderName() == "neatRobotus.RobotusCodus*")
				break;
			if( evento[1].getScore() > maxFit)
				maxFit = evento[1].getScore();
			Populacao.setFitness( evento[1].getScore());
			double fit = Populacao.getGenoma().getFitness();
			if( fit > maxFitEq)
				maxFitEq = fit;
			System.out.println( "\n|Testes: " +count +" |Geração: " +Populacao.geracao +" |Testes na geração: "+ count%60 +"\n|genes: " +numGenes+"|Nódulos: " +numNod +"\n|Especies:"+Especie.numEspecies+"\n|Maior fitness:"+ maxFit +"\n|Maior fitness especializado:"+ maxFitEq);
			count++;
		}while(Populacao.getGenoma().getFitness() <= 400);
		batalha.startBatalha(true);
		Genoma g[] = new Genoma[10];
		for( int i = 0; i < 10; i++){
			g[i] = new Genoma(i);
		}
	    //batalha.startBatalha(true);
		//s.nextInt();
	}
}
