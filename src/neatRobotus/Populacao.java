package neatRobotus;

public class Populacao extends RobotusCodus{
	public static Genoma ultimoGenoma = null;
	public boolean teste;
	
	public static void novoGenoma(){
		Populacao.ultimoGenoma = new Genoma();
	}
}
