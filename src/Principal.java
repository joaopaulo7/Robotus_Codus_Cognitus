

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import neatRobotus.*;

import robocode.BattleResults;
import robocode.control.events.BattleCompletedEvent;


public class Principal {
	public static Scanner s;
	public static BattleResults[] evento;
	
	public static void main(String Args[]){
		s = new Scanner(System.in);
		Populacao.setOutput( true, 0, 201);
		Populacao.setOutput( true, 0, 201);
		Populacao.setOutput( true, 0, 5);
		Populacao.setOutput( true, 0, 181);
		Populacao.setOutput( true, 0, 181);
		Populacao.setOutput( true, 0, 181);
		Populacao.setOutput( true, 0, 181);
		Populacao.populacaoInit(21, 1);
		
		BattleRunner batalha = new BattleRunner();
		int maxFit = 0, count = 0, numGenes, numNod;
		double maxFitEq = 0,  sumFit = 0, sumFitEq =0, maxFitEq0 = 0, maxFit0 = 0;
		Genoma genoma;
		do{
			if( count % Populacao.TAMANHO_GERACAO == 0) {
				maxFitEq0 = 0;
				maxFit0 = 0;
			}
			Populacao.genese();
			batalha.startBatalha( false);
			
			if( evento[0].getTeamLeaderName() == "neatRobotus.RobotusCodus*")
				break;
			
			if( evento[1].getScore() > maxFit)
				maxFit = evento[1].getScore();
			
			if( evento[1].getScore() > maxFit0)
				maxFit0 = evento[1].getScore();
			
			sumFit += evento[1].getScore();
			
			Populacao.setFitness( new double[] {evento[1].getScore()});
			
			numGenes = 25;
			numNod = 10;
			
			double fit = Populacao.getGenoma().getFitness();
			sumFitEq += fit;
			
			if( fit > maxFitEq)
				maxFitEq = fit;
			
			if( fit > maxFitEq0)
				maxFitEq0 = fit;
			
			System.out.println( Populacao.getGenes() - (Populacao.getNodulos()-28 + Populacao.getGeracao()+1));
			
			
			System.out.println( "\n|Testes: " +count +" |Geração: " +Populacao.getGeracao() +" |Testes na geração: "+ count%Populacao.TAMANHO_GERACAO +"\n|genes: "+ Populacao.getGenes() +"|Nódulos: " + Populacao.getNodulos() +"\n|Especies:" + Populacao.getEspecies() +"\n|Maior fitness:"+ maxFit +" |Maior fitness0:"+maxFit0+" |Fitness Médio: "+ sumFit/count+" |Fitness atual: " +evento[1].getScore()+"\n|Maior fitness especializado:"+ maxFitEq +"|Maior fitness especializado:"+ maxFitEq0 +" |Fitness Especializado Médio: " +Populacao.medFit+" |Especializado atual: "+fit);
			
			count++;
		}while( true);
		batalha.startBatalha( false);
		for( int  j = 0; j< 10; j++) {
		Genoma g = new Genoma();
		//double v1[] = { 1.0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1} ;
		double v[] = g.ativar( new double[] { 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, 1} );
			for( int i = 0; i < 7; i++){
				System.out.println(v[i]);
			}
		}
	    //batalha.startBatalha(true);
		//s.nextInt();
	}
}
