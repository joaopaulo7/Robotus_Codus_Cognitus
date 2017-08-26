package neatRobotus;


public class Principal {
	public static void main(String Args[]){
		BattleRunner batalha = new BattleRunner();
		Populacao.novoGenoma();
		do{
			Populacao.genese();
			batalha.startBatalha(false);
		}while(Populacao.getGenoma().getFitness() <= 10 );
		double[] r = Populacao.getGenoma().ativar(new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
		System.out.println("Bom dia: "+r.length);
		for(int i = 0; i < r.length; i++)
			System.out.println(r[i]+"\n");
		batalha.startBatalha(true);
		System.out.println("\nHello Nothingness: "+ Populacao.getGenoma().getFitness()+"\n"+Math.random());
	}
}
