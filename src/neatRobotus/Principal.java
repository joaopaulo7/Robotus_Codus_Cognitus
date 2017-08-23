package neatRobotus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import neatRobotusInit.*;

public class Principal {
	public static void main(String Args[]){
		BattleRunner batalha = new BattleRunner();
		Genoma g;
		do{
			Populacao.novoGenoma();
			g = Populacao.ultimoGenoma;
			try {
		         FileOutputStream fileOut =
		         new FileOutputStream("/home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(g);
		         out.close();
		         fileOut.close();
		         System.out.printf("Objeto salvo(serializado) em: /home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
		      }catch(IOException i) {
		         i.printStackTrace();
		      }
			batalha.startBatalha(false);
		}while(Populacao.ultimoGenoma.getFitness() <= 0);
		double[] r = Populacao.ultimoGenoma.ativar(new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
		System.out.println("Bom dia: "+r.length);
		for(int i = 0; i < r.length; i++)
			System.out.println(r[i]+"\n");
		batalha.startBatalha(true);
		System.out.println("\nHello Nothingness: "+ Populacao.ultimoGenoma.getFitness()+"\n"+Math.random());
	}
}
