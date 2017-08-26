package neatRobotus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Populacao{
	protected static ArrayList<Genoma> genomas = new ArrayList<Genoma>();
	protected static int maxGenoma = 0;
	protected static int geracao = 50;
	
	public static void novoGenoma(){
		Populacao.genomas.add(new Genoma(1));
	}
	public static Genoma getGenoma(){
		return Populacao.genomas.get(Populacao.genomas.size()-1);
	}
	
	public static void genese(){
		if(Populacao.maxGenoma < 20){
			Genoma g = new Genoma(geracao);
			genomas.add(g);
			Populacao.salvar(g);
			Populacao.maxGenoma++;
		}
		else
		{
			//Populacao.selecionar();
		}
	}
	
	private static Genoma crossOver( Genoma mae, Genoma pai){
		ArrayList<int[]> inovacaoFilho = new ArrayList<int[]>();
		int i = 0, jmae =0, jpai =0;
		while( jmae != mae.inovacao.size() && jpai != pai.inovacao.size())
		{
			if(mae.genes.get(jmae).getInovacao() == i && mae.genes.get(jmae).getEstado() && pai.genes.get(jpai).getInovacao() == i && pai.genes.get(jpai).getEstado())
			{
				if(Math.random() > 0.5)
					genes.add(mae.genes.get(i));
				else{
					genes.add(pai.genes.get(i));
					i++;
					jmae++;
					jpai++;
				}
			}
			else if(mae.genes.get(jmae).getInovacao() == i && !mae.genes.get(jmae).getEstado())
			{
				genes.add(mae.genes.get(i));
				i++;
				jmae++;
			}
			else if(pai.genes.get(jpai).getInovacao() == i)
			{
				genes.add(pai.genes.get(i));
				i++;
				jmae++;
			}
			else
				i++;
		}
		Genoma filho;
		if(mae.nodulos.size() < pai.nodulos.size())
			filho = new Genoma( genes, pai.nodulos);
		else
			filho = new Genoma( genes, mae.nodulos);
		return filho;
	}
	
	protected static boolean salvar(Genoma g){
		try {
			Genoma a = g;
	         FileOutputStream fileOut = new FileOutputStream("/home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(g);
	         out.close();
	         fileOut.close();
	         System.out.printf("Objeto salvo(serializado) em: /home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
	         
	         
	         fileOut = new FileOutputStream("/home/joao/workspace/Robotus_Codus_Cognitus/Genomas/Teste/Geracao"+Populacao.geracao+"/genoma"+(genomas.size()-1)+".ser");
	         out = new ObjectOutputStream(fileOut);
	         out.writeObject(a);
	         out.close();
	         fileOut.close();
	         System.out.printf("Objeto salvo(serializado) em: /home/joao/workspace/Robotus_Codus_Cognitus/Genomas/Teste/Geracao"+Populacao.geracao+"genoma"+genomas.size()+".ser");
	         return true;
		}
		catch(IOException i) {
	         i.printStackTrace();
	 		return false;
		}
	}
}
