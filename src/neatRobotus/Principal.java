package neatRobotus;

public class Principal {
	public static void main(String Args[]){
		Genoma g = new Genoma();
		BattleRunner batalha = new BattleRunner( g);
		batalha.startBatalha();
		
		System.out.println("\nHello Nothingness: "+ g.getFitness());
	}
}
