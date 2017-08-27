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
		do{
			Populacao.genese();
			Populacao.getGenoma().mostrarGenoma();
			batalha.startBatalha(false);
			if( evento[0].getTeamLeaderName() == "neatRobotus.RobotusCodus*")
				break;
			Populacao.getGenoma().setFitness( evento[1].getScore());
		}while(Populacao.getGenoma().getFitness() <= 300);
		batalha.startBatalha(true);
		Genoma g[] = new Genoma[10];
		for( int i = 0; i < 10; i++){
			g[i] = new Genoma(i);
		}
	    //batalha.startBatalha(true);
		//s.nextInt();
	}
}
