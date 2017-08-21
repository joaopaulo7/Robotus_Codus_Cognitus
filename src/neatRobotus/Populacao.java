package neatRobotus;

public class Populacao extends RobotusCodus {
	public static Genoma ultimoGenoma = new Genoma();
	
	public static void novoGenoma(){
		Populacao.ultimoGenoma = new Genoma();
	}
}
