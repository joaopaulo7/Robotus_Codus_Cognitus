package neatRobotus;

public class Populacao{
	public static Genoma ultimoGenoma = new Genoma();
	
	public static void novoGenoma(){
		Populacao.ultimoGenoma = new Genoma();
	}
}
