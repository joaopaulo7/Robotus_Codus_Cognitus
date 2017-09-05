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
		Populacao.populacaoInit(0);
		BattleRunner batalha = new BattleRunner();
		int maxFit = 0, count = 0, numGenes, numNod;
		double maxFitEq = 0,  sumFit = 0, sumFitEq =0;
		Genoma genoma;
		do{	
			
			Populacao.genese();
			batalha.startBatalha( false);
			
			if( evento[0].getTeamLeaderName() == "neatRobotus.RobotusCodus*")
				break;
			
			if( evento[1].getScore() > maxFit)
				maxFit = evento[1].getScore();
			
			sumFit += evento[1].getScore();
			
			Populacao.setFitness( evento[1].getScore());
			
			
			numGenes = Populacao.getGenoma().genes.size();
			numNod = Populacao.getGenoma().nodulos.size();
			
			double fit = Populacao.getGenoma().getFitness();
			sumFitEq += fit;
			
			if( fit > maxFitEq)
				maxFitEq = fit;
			
			System.out.println( "\n|Testes: " +count +" |Geração: " +Populacao.geracao +" |Testes na geração: "+ count%300 +"\n|genes: " +numGenes+"|Nódulos: " +numNod +"\n|Especies:" +Especie.numEspc+"\n|Maior fitness:"+ maxFit +" |Fitness Médio: "+ sumFit/count+" |Fitness atual: " +evento[1].getScore()+"\n|Maior fitness especializado:"+ maxFitEq +" |Fitness Especializado Médio: " +Populacao.medFit+" |Especializado atual: "+fit);
			
			count++;
		}while(true);
		batalha.startBatalha(true);
		Genoma g[] = new Genoma[10];
		for( int i = 0; i < 10; i++){
			g[i] = new Genoma(i);
		}
	    //batalha.startBatalha(true);
		//s.nextInt();
	}
}
