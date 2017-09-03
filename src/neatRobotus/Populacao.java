package neatRobotus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Populacao{
	protected static ArrayList<Genoma> genomas = new ArrayList<Genoma>();
	protected static int maxGenoma = 0;
	protected static int geracao = 0;
	
	public static double medFit = 0;
	private static double atMedFit = 0;
	private static int numStatic = 0;
	
	private static int TAMANHO_GERACAO = 300;
	private static Scanner s;
	
	
	public static void populacaoInit(){
		for( int i = 0; i < Populacao.TAMANHO_GERACAO; i++)
			Populacao.genomas.add(new Genoma(1));
		Especie.formarEspecies( Populacao.genomas);
	}
	
	public static void populacaoInit( int i) {
	     try {
	         FileInputStream fileIn = new FileInputStream("/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/Genomas/Teste/Geracao"+i+".ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Populacao.genomas = (ArrayList<Genoma>) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException e) {
	         e.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Genoma não encontrado");
	         c.printStackTrace();
	         return;
	      }
	     Populacao.geracao = i;
	     Especie.formarEspecies( Populacao.genomas);
	     Collections.sort(genomas);
	}
	
	public static Genoma getGenoma(){
		return Populacao.genomas.get( Populacao.maxGenoma -1);
	}
	
	public static void genese(){
		if(Populacao.maxGenoma < TAMANHO_GERACAO)
		{
			Genoma g = genomas.get(Populacao.maxGenoma);
			Populacao.salvar(g);
		}
		else
		{
			Populacao.numStatic++;
			if( atMedFit > medFit) {
				Populacao.numStatic = 0;
				Populacao.medFit = Populacao.atMedFit;
			}
			Populacao.atMedFit = 0;
			Populacao.maxGenoma = 0;
			Populacao.selecionar();
			Populacao.salvar(Populacao.genomas);
			Populacao.geracao++;
		}
	}
	
	public static void setFitness( double fit){
		Populacao.genomas.get(Populacao.maxGenoma).setFitness(fit);
		Populacao.atMedFit += Populacao.genomas.get(Populacao.maxGenoma).getFitness()/Populacao.TAMANHO_GERACAO;
		Populacao.maxGenoma++;
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
	         FileOutputStream fileOut = new FileOutputStream("/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(g);
	         out.close();
	         fileOut.close();
	         System.out.printf("Objeto salvo(serializado) em: /home/joao/eclipse-workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
	         return true;
		}
		catch(IOException i)
		{
	         i.printStackTrace();
	 		return false;
		}
	}
	
	protected static boolean salvar(ArrayList<Genoma> g){
		try 
		{
	         FileOutputStream fileOut = new FileOutputStream("/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/Genomas/Teste/Geracao"+Populacao.geracao+".ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(g);
	         out.close();
	         fileOut.close();
	         System.out.printf("Objeto salvo(serializado) em: /home/joao/eclipse-workspace/Robotus_Codus_Cognitus/Genomas/Teste/Geracao"+Populacao.geracao+".ser");
	         return true;
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

		while( perpetuados.size() < 2*TAMANHO_GERACAO/3){
			Genoma g0 = g[(int) ((Math.random()*100000)%TAMANHO_GERACAO/3)];
			Genoma g1 = g[(int) ((Math.random()*100000)%TAMANHO_GERACAO/3)];
			if(( g0.especie == g1.especie && g0.getFitness() > g1.getFitness()))
			{
				perpetuados.add( Populacao.crossOver( g0, g1));
			}
			else if( g0.especie == g1.especie)
			{
				perpetuados.add( Populacao.crossOver( g1, g0));
			}
		}
		
		for(int i = 0; i < TAMANHO_GERACAO/3; i++)
		{
			Genoma copia = g[i].copiar();
			copia.mutar( 1+ (( int)Populacao.numStatic/5));
			perpetuados.add(copia);
		}
		Especie.formarEspecies( perpetuados);
		Populacao.genomas = perpetuados;
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
