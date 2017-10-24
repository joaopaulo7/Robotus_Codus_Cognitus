package neatRobotus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Populacao{
	protected static ArrayList<Genoma> genomas = new ArrayList<Genoma>();
	protected static int testados;
	protected static int maxGenoma = 0;
	protected static int geracao = 0;
	
	public static double medFit = 0;
	private static double atMedFit = 0;
	private static int numStatic = 0;
	
	public static int TAMANHO_GERACAO = 120;
	
	
	public static void populacaoInit( int input, int testados){
		Populacao.testados = testados;
		Genoma.genomaInit(input);
		for( int i = 0; i < Populacao.TAMANHO_GERACAO; i++)
			Populacao.genomas.add(new Genoma());
		Collections.sort(genomas);
		Especie.formarEspecies( Populacao.genomas);
	}
	
	public static void populacaoInit( int input, int testados, int geracao){
		Populacao.testados = testados;
		Genoma.genomaInit(input);
		try {
	         FileInputStream fileIn = new FileInputStream("/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/Genomas/Teste/Geracao"+geracao+".ser");
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
	     Populacao.geracao = geracao;
	     Especie.formarEspecies( Populacao.genomas);
	     Collections.sort(genomas);
	}
	
	public static void genese(){
		if(Populacao.maxGenoma < TAMANHO_GERACAO)
		{
			for( int i = 0; i < Populacao.testados; i++) {
				int rand = (int) ( Math.random()*300)%(TAMANHO_GERACAO-maxGenoma);
				Genoma g = genomas.remove( rand);
				genomas.add(g);
				Populacao.salvar( g, i, "/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma");
			}
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
			Collections.sort( Populacao.genomas);
			if( Populacao.geracao%10 == 0)
				Populacao.salvar( Populacao.genomas.get(0), Populacao.geracao/10, "/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/Genomas/Gernoma");
			if( Populacao.geracao%10 == 0)
				Populacao.salvar(Populacao.genomas);
			Populacao.geracao++;
		}
	}
	
	//############################GETS E SETS#################INÍCIO
	public static void setFitness( double fit[]){
		for( int  i = 0; i < Populacao.testados; i++) {
			Populacao.genomas.get(TAMANHO_GERACAO - Populacao.testados +i).setFitness(fit[i]);
			Populacao.atMedFit += Populacao.genomas.get(Populacao.maxGenoma).getFitness()/Populacao.TAMANHO_GERACAO;
			Populacao.maxGenoma++;
		}
	}
	
	public static Genoma getGenoma(){
		return Populacao.genomas.get( Populacao.TAMANHO_GERACAO -1);
	}
	public static int getEspecies() {
		return Especie.numEspc;
	}
	public static int getGeracao() {
		return Populacao.geracao;
	}
	
	public static int getGenes() {
		return Populacao.getGenoma().genes.size();
	}
	
	public static int getNodulos() {
		return Populacao.getGenoma().nodulos.size();
	}
	
	public static void setOutput( boolean modular, double min, double max) {
		if( modular)
			Genoma.outputInit( new double[] { 1, min, max} );
		else
			Genoma.outputInit( new double[] { 0, min, max} );
	}
	
	
	//############################GETS E SETS#################FIM
	
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
	
	protected static boolean salvar(Genoma g, int i, String local){
		try 
		{
	         FileOutputStream fileOut = new FileOutputStream(local+i+".ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(g);
	         out.close();
	         fileOut.close();
	         System.out.printf("Objeto salvo(serializado) em: "+local+i+".ser");
	         return true;
		}
		catch(IOException e)
		{
	         e.printStackTrace();
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
		catch(IOException e)
		{
	         e.printStackTrace();
	 		return false;
		}
	}
	
	protected static void selecionar(){
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
			if(( g0.especie == g1.especie && g0.getFitness() > g1.getFitness())&& Especie.numEspc > 30)
			{
				Genoma filho = Populacao.crossOver( g0, g1);
				perpetuados.add( filho);
			}
			else if( g0.especie == g1.especie && Especie.numEspc > 30)
			{
				perpetuados.add( Populacao.crossOver( g1, g0));
			}
			else if ( g0 != g1)
			{
				Genoma copia = g1.copiar();
				perpetuados.add(copia);
			}
		}
		
		for(int i = 0; i < TAMANHO_GERACAO/3; i++)
		{
			Genoma copia = g[i].copiar();
			perpetuados.add(copia);
		}
		Especie.formarEspecies( perpetuados);
		Populacao.genomas = perpetuados;
	}
	
	
	public static void debug(){
		/* DEBUG*/
		for(int i = 0; i < Populacao.genomas.size(); i++)
		{
			System.out.println(Populacao.genomas.get(i).getFitness());
		}
	}
}
