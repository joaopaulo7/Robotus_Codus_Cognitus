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
		int count = 0;
		boolean ver;
		do{
			count++;
			Populacao.genese();

			batalha.startBatalha(false);
			//if( count == 24)
				//Populacao.debug();
		}while(Populacao.getGenoma().getFitness() <= 200);
		Genoma genoma;
	    try {
	         FileInputStream fileIn = new FileInputStream("/home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
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
	    
	    double r[] = genoma.ativar( new double[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
	    for( int i = 0; i < 10; i++)
	    	System.out.println(r[i]);
	    genoma.mostrarGenoma();
	    batalha.startBatalha(true);
		s.nextInt();
	}
}
