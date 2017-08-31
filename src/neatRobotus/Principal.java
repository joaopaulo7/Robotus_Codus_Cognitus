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
		double maxFitEq = 0,  sumFit = 0, sumFitEq =0;
		Genoma genoma;
		do{
			Populacao.genese();
			
			
		     try {
		         FileInputStream fileIn = new FileInputStream("/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         genoma = (Genoma) in.readObject();
		         in.close();
		         fileIn.close();
		      }catch(IOException i) {
		         i.printStackTrace();
		         return;
		      }catch(ClassNotFoundException c) {
		         System.out.println("Genoma não encontrado");
		         c.printStackTrace();
		         return;
		      }
			
			
			double v[] = new double[17];
			for(int i = 0; i < 17; i++)
				v[i]= 1;
			double r[] = genoma.ativar(v);
			for(int i = 0; i < 7; i++)
				System.out.println(r[i]);
			
			batalha.startBatalha(false);
			numGenes = Populacao.getGenoma().genes.size();
			numNod = Populacao.getGenoma().nodulos.size();
			if( evento[0].getTeamLeaderName() == "neatRobotus.RobotusCodus*")
				break;
			if( evento[1].getScore() > maxFit)
				maxFit = evento[1].getScore();
			sumFit += evento[1].getScore();
			Populacao.setFitness( evento[1].getScore());
			double fit = Populacao.getGenoma().getFitness();
			sumFitEq += fit;
			if( fit > maxFitEq)
				maxFitEq = fit;
			System.out.println( "\n|Testes: " +count +" |Geração: " +Populacao.geracao +" |Testes na geração: "+ count%360 +"\n|genes: " +numGenes+"|Nódulos: " +numNod +"\n|Especies:"+Especie.numEspecies+"\n|Maior fitness:"+ maxFit +" |Fitness Médio: "+ sumFit/count+" |Fitness atual: " +evento[1].getScore()+"\n|Maior fitness especializado:"+ maxFitEq +" |Fitness Especializado Médio: " +sumFitEq/count+" |Especializado atual: "+fit);
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
