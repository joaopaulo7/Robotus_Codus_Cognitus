package neatRobotus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;


public class Principal {
	public static Scanner s;
	
	public static void main(String Args[]){
		s = new Scanner(System.in);
		BattleRunner batalha = new BattleRunner();
		Populacao.populacaoInit();
		do{
			Populacao.genese();
			batalha.startBatalha(false);
			//if( count == 24)
				//Populacao.debug();
		}while(Populacao.getGenoma().getFitness() <= 10000);
		Genoma genoma0 = new Genoma(1);
		Genoma genoma1 = new Genoma(10);
		double r[] = genoma0.ativar( new double[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
		for( int i = 0; i < 10; i++)
			System.out.println(r[i]);
		genoma0.mostrarGenoma();
		genoma1.mostrarGenoma();
		Populacao.crossOver( genoma1, genoma0).mostrarGenoma();
	    //batalha.startBatalha(true);
		//s.nextInt();
	}
}
