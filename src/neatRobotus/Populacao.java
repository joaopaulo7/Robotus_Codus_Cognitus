package neatRobotus;

public class Populacao{
	public static Genoma ultimoGenoma = new Genoma();
	public boolean teste;
	
	public static void novoGenoma(){
		Populacao.ultimoGenoma = new Genoma();
	}
}
