package neatRobotus;

import neatRobotusInit.*;

public class Principal {
	public static void main(String Args[]){
		BattleRunner batalha = new BattleRunner();
		Populacao p = new Populacao();
		do{
			Populacao.novoGenoma();
			batalha.startBatalha(false);
		}while(Populacao.ultimoGenoma.getFitness() <= 0);
		
		double[] r = Populacao.ultimoGenoma.ativar(new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
		System.out.println("Bom dia: "+r.length);
		for(int i = 0; i < r.length; i++)
			System.out.println(r[i]+"\n");
		batalha.startBatalha(true);
		System.out.println("\nHello Nothingness: "+ Populacao.ultimoGenoma.getFitness()+"\n"+Math.random()+p.teste);
	}
}
