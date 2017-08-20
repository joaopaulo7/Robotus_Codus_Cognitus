package neatRobotus;

public class Principal {
	public static void main(String Args[]){
		BattleRunner batalha = new BattleRunner();
		int cont = 0;
		Genoma.InitGenoma();
		do{
			cont++;
			Populacao.ultimoGenoma = new Genoma();
			batalha.startBatalha( false);
		}while(Populacao.ultimoGenoma.getFitness() <= 0);
		
		double[] r = Populacao.ultimoGenoma.ativar(new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
		for(int i = 0; i < r.length; i++)
			System.out.println(r[i]+"\n");
		batalha.startBatalha( false);
		System.out.println("\nHello Nothingness: "+ Populacao.ultimoGenoma.getFitness()+"\n"+Math.random());
	}
}
