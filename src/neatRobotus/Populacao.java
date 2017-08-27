package neatRobotus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Populacao{
	protected static ArrayList<Genoma> genomas = new ArrayList<Genoma>();
	protected static int maxGenoma = -1;
	protected static int geracao = 0;
	
	private static int TAMANHO_GERACAO = 24;
	private static Scanner s;
	
	
	public static void populacaoInit(){
		for( int i = 0; i < Populacao.TAMANHO_GERACAO; i++)
			Populacao.genomas.add(new Genoma(1));
	}
	public static Genoma getGenoma(){
		return Populacao.genomas.get(Populacao.maxGenoma);
	}
	
	public static void genese(){
		if(Populacao.maxGenoma < TAMANHO_GERACAO-1)
		{
			Populacao.maxGenoma++;
			Genoma g = genomas.get(Populacao.maxGenoma);
			Populacao.salvar(g);
		}
		else
		{
			Populacao.geracao++;
			Populacao.maxGenoma = 0;
			Populacao.selecionar();
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
		try 
		{
	         FileOutputStream fileOut = new FileOutputStream("/home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(g);
	         out.close();
	         fileOut.close();
	         System.out.printf("Objeto salvo(serializado) em: /home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
	         
	         
	        /* fileOut = new FileOutputStream("/home/joao/workspace/Robotus_Codus_Cognitus/Genomas/Teste/Geracao"+Populacao.geracao+"/genoma"+(genomas.size()-1)+".ser");
	         out = new ObjectOutputStream(fileOut);
	         out.writeObject(g);
	         out.close();
	         fileOut.close();
	         System.out.printf("Objeto salvo(serializado) em: /home/joao/workspace/Robotus_Codus_Cognitus/Genomas/Teste/Geracao"+Populacao.geracao+"genoma"+genomas.size()+".ser");
	         */return true;
		}
		catch(IOException i)
		{
	         i.printStackTrace();
	 		return false;
		}
	}
	
	protected static void selecionar(){
		s = new Scanner(System.in); 
		ArrayList<Genoma> perpetuados = new ArrayList<Genoma>();
		Genoma g[] = new Genoma[TAMANHO_GERACAO/3];
		
		Collections.sort(Populacao.genomas);
		
		for(int i = 0; i < Populacao.TAMANHO_GERACAO/3; i++)
		{
			g[i] = Populacao.genomas.get(i);
		}
		for(int i = 0; i < TAMANHO_GERACAO/3; i++)
		{
			perpetuados.add(g[i]);
		}
		for(int i = 0; i < TAMANHO_GERACAO/3; i++)
		{
			perpetuados.add( new Genoma( Populacao.geracao));
		}
		
		for(int i = 0; i < TAMANHO_GERACAO/3; i++)
		{
			Genoma copia = g[1].copiar();
			copia.mutar( 1);
			perpetuados.add(copia);
		}
		Populacao.genomas = perpetuados;
	}
	
	public static void debug(){
		s = new Scanner(System.in);
		/* DEBUG*/
		for(int i = 0; i < Populacao.genomas.size(); i++)
		{
			System.out.println(Populacao.genomas.get(i).fitness);
		}
		s.nextInt();
	}
}
