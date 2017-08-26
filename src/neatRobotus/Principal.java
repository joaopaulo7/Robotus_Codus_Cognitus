package neatRobotus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Principal {
	public static void main(String Args[]){
		BattleRunner batalha = new BattleRunner();
		Populacao.novoGenoma();
		do{
			Populacao.genese();
			batalha.startBatalha(false);
		}while(Populacao.getGenoma().getFitness() <= 10 );
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
	     genoma.mostrarGenoma();
	}
}
