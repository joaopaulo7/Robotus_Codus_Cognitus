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
	
	private static int TAMANHO_GERACAO = 360;
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
			Especie.numEspecies = 0;
		}
	}
	
	public static Genoma crossOver( Genoma mae, Genoma pai){
		int i = 0, j = 0;
		ArrayList<Conexao> filho = new ArrayList<Conexao>();
		if( pai.genes. isEmpty())
		{
			filho = mae.genes;
		}
		else if( mae.genes.isEmpty())
		{
			filho = pai.genes;
		}
		else
		{
			while( i < mae.genes.size() || j < pai.genes.size())
			{
				if(( i < mae.genes.size() && j < pai.genes.size())){
						if(mae.genes.get(i).getInovacao() == pai.genes.get(j).getInovacao())
						{
							filho.add(mae.genes.get(i));
							i++;
							j++;
						}
						else if(  mae.genes.get(i).getInovacao() < pai.genes.get(j).getInovacao())
						{
							filho.add(mae.genes.get(i));
							i++;
						}
						else
						{
							filho.add(pai.genes.get(j));
							j++;
						}
				}
				else
				{
					if( j == pai.genes.size())
					{
						filho.add(mae.genes.get(i));
						i++;
					}
					else{
						filho.add(pai.genes.get(j));
						j++;
					}
				}
			}
		}
		if( mae.nodulos.size() > pai.nodulos.size())
			return Genoma.montarGenoma( filho, mae.nodulos.size(), mae.bias);
		else
			return Genoma.montarGenoma( filho, pai.nodulos.size(), pai.bias);
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

		while( perpetuados.size()-1 < 2*TAMANHO_GERACAO/3){
			Genoma g0 = g[(int) ((Math.random()*100)%TAMANHO_GERACAO/3)];
			Genoma g1 = g[(int) ((Math.random()*100)%TAMANHO_GERACAO/3)];
			if( Especie.mesmaEspecie(g0.inovacao, g1.inovacao, g0.nodulos.size()) == 1 && g0.getFitness() > g1.getFitness())
			{
				perpetuados.add( Populacao.crossOver( g0, g1));
			}
			else if( Especie.mesmaEspecie(g0.inovacao, g1.inovacao, g1.nodulos.size()) == 1)
			{
				perpetuados.add( Populacao.crossOver( g1, g0));
			}
		}
		
		for(int i = 0; i < TAMANHO_GERACAO/3; i++)
		{
			Genoma copia = g[i].copiar();
			copia.mutar( 1);
			perpetuados.add(copia);
		}
		Populacao.genomas = perpetuados;
	}
	
	public static double setFitness( double fitBrut){
		Genoma testado = Populacao.getGenoma();
		int maxNod, tamEsp = 1;
		System.out.println("CONTADOR");
		for( int i = 0; i < Populacao.genomas.size(); i++){
			maxNod = testado.nodulos.size() - 25;
			if( Populacao.genomas.get(i).nodulos.size() > maxNod)
				maxNod = Populacao.genomas.get(i).nodulos.size() - 25;
			tamEsp += Especie.mesmaEspecie( testado.inovacao, Populacao.genomas.get(i).inovacao, maxNod);
		}
		System.out.println("DIVISAO");
		if( tamEsp == 1)
			Especie.numEspecies++;
		testado.setFitness( fitBrut/tamEsp);
		return fitBrut/tamEsp;
	}
	
	public static void debug(){
		s = new Scanner(System.in);
		/* DEBUG*/
		for(int i = 0; i < Populacao.genomas.size(); i++)
		{
			System.out.println(Populacao.genomas.get(i).getFitness());
		}
		s.nextInt();
	}
}
