package neatRobotus;

public class Populacao extends RobotusCodus {
	public static Genoma ultimoGenoma = null;
	
	public void NovoGenoma(){
		Populacao.ultimoGenoma = new Genoma();
	}
}
