package neatRobotus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Genoma implements java.io.Serializable, Cloneable, Comparable<Genoma> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private double fitness = 0;
	private int numFit = 0;
	
	protected Bias bias = new Bias();
	protected static ArrayList<int[]> inovacaoUni = new ArrayList<int[]>();
	protected ArrayList<RefDouble[]> inovacao = new ArrayList<RefDouble[]>();
	protected ArrayList<Conexao> genes = new ArrayList<Conexao>();
	protected ArrayList<Nodulo> nodulos = new ArrayList<Nodulo>();
	protected static ArrayList<double []> outputsType = new ArrayList<double[]>();
	protected ArrayList<Output> outputs = new ArrayList<Output>();
	protected int especie = -1;
	
	protected double MUTAR_CONEXAO = 0.012;
	protected double MUTAR_NODULO = 0.006;
	protected static double MUTAR_PESO = 1;
	protected  int numInput = 0;
	protected static int NUM_INPUT = 0;
	protected int numOutput = 0;
	protected static int NUM_OUTPUT = 0;
	protected int nodulosBase = 0;
	protected static int NUM_NODULOSBASE = 0;
	
	//Criacao de genomas
	
	protected static void genomaInit( int input){
		Genoma.NUM_INPUT = input;
		System.out.println(input);
		Genoma.NUM_NODULOSBASE = Genoma.NUM_INPUT + Genoma.NUM_OUTPUT;
	}
	
	protected static void outputInit( double v[]){
		Genoma.outputsType.add(v);
		Genoma.NUM_OUTPUT++;
	}
	
	public Genoma(){
		this.numInput = Genoma.NUM_INPUT;
		this.numOutput = Genoma.NUM_OUTPUT;
		this.nodulosBase = Genoma.NUM_NODULOSBASE;
		System.out.println(this.nodulosBase);
		//Declaração de Inputs
			//Relacionados ao próprio robô
			for( int i = 0; i < Genoma.NUM_INPUT; i++)
			{
				this.nodulos.add(new Input());
			}
				
		
		//Declaração de Outputs
			for( int i = 0; i < Genoma.outputsType.size(); i++)
			{
				double v[] = Genoma.outputsType.get(i);
				this.outputs.add(new Output( v[0], v[1], v[2]));
			}

			
			for(int i = 0; i< this.outputs.size(); i++) 
				this.nodulos.add(this.outputs.get(i));
			for(int i = 0; i< this.nodulos.size(); i++){
				this.nodulos.get(i).id = i;
				if(i >= this.numInput)
					this.bias.addSaida( new Conexao(bias, this.nodulos.get(i), new RefDouble(0)));
			}
			this.mutar(1);
	}
	
	public static Genoma montarGenoma( ArrayList<Conexao> conexoes, int numNodulos, Bias bias)
	{
		Genoma homun = new Genoma();
		for( int i = Genoma.NUM_NODULOSBASE; i < numNodulos; i++)
		{
			homun.nodulos.add( new Nodulo());
			homun.nodulos.get(i).id = homun.nodulos.size()-1;
		}
		for( int i = Genoma.NUM_INPUT; i < numNodulos-1; i++)
				homun.bias.addSaida( new Conexao(homun.bias, homun.nodulos.get(i), bias.posterior.get(i-Genoma.NUM_INPUT).getPeso()));
		int j = 0;
		while( j < conexoes.size())
		{
			int idAnt = conexoes.get(j).getAnterior().id;
			int idPos = conexoes.get(j).getPosterior().id;
			homun.adicionarConexao(idAnt, idPos, conexoes.get(j).getPeso());
			j++;
		}
		return homun;
	}
	
	//Gets e Sets
	public void setFitness( double fit){
		//this.numFit++;
		this.fitness = fit/Especie.especies[this.especie];
	}
	
	public double getFitness()
	{
		return this.fitness;///this.numFit;
	}
	
	//Mutações
	protected void mutar( int potencial){
		int i = 0;
		while(i < potencial){
			double rand = Math.random();
			if( rand < this.MUTAR_CONEXAO || genes.isEmpty()){
				//this.MUTAR_CONEXAO *= 0.7;
				//encontra 2 nódulos possíveis
				int idAnt = this.noduloAleatorio( true, -1);
				int idPos = this.noduloAleatorio( false, idAnt);
				if( this.existe( idAnt, idPos)){
					this.adicionarConexao(idAnt, idPos);
					i++;
				}
			}
			else {
				rand = Math.random();
				if( rand < this.MUTAR_NODULO && !genes.isEmpty()) {
					//this.MUTAR_NODULO *= 0.7;
					this.adicionarNodulo(this.conexaoAleatoria());
					i++;
				}
				else if( !genes.isEmpty()){
					this.mutarPeso();
					System.out.println(" Alteração");
					i++;
				}
			}
		Collections.sort(this.genes);
		}
	}
	
	//Ativação da rede
	public double[] ativar( double v[]){
		double g[] = new double[this.numOutput];
		this.bias.ativar(1);
		for( int i = 0; i < this.numInput; i++)
			this.nodulos.get(i).ativar(v[i]);
		for( int i = 0; i < this.numOutput; i++)
			g[i] = this.outputs.get(i).calcularSaida();
		return g;
	}
	
	//Ferramentas
	private int noduloAleatorio( boolean eAnt, int proibido){
		int id = ( int)(( Math.random()*10000)%( this.nodulosBase));
		if(eAnt){
				if(id >Genoma.NUM_INPUT && id < Genoma.NUM_NODULOSBASE)
					id -= Genoma.NUM_OUTPUT;
				System.out.println("Tentando ant nod"+id);
		}
		else
		{
			while(id == proibido || id < Genoma.NUM_INPUT){
				id = ( int)(( Math.random()*10000)%( this.nodulosBase- Genoma.NUM_INPUT)+ Genoma.NUM_INPUT);
					System.out.println("Tentando prox nod"+id);
			}
		}
		System.out.println(id);
		return id;
	}
	
	private Conexao conexaoAleatoria(){
		return genes.get(( int)(Math.random()*10000)%this.genes.size());
	}
	
	private void adicionarNodulo( Conexao c){
		Nodulo proximoNodulo = c.getPosterior();
		Nodulo novoNodulo = new Nodulo();
		c.setPosterior( novoNodulo);
		//Ajusta os nódulos
		novoNodulo.id = this.nodulos.size();
		this.nodulos.add(novoNodulo);
		adicionarConexao( novoNodulo.id, proximoNodulo.id, new RefDouble(1.0));
		//Adicionar 1ª conexao nas inovações
		c.setInovacao(this.addInov(c.getAnterior().getId(), novoNodulo.getId(), c.getPeso()));
		//adiciona a conexao do BIAS ao novo nódulo
		this.bias.addSaida( new Conexao( this.bias, novoNodulo, new RefDouble(0)));
	}
	
	private void adicionarConexao( int idAnt, int idPos, RefDouble peso){
		//cria a conexão
		Conexao c = new Conexao( this.nodulos.get(idAnt), this.nodulos.get(idPos), peso);
		this.nodulos.get(idAnt).addSaida(c);
		//Adiciona gene
		c.setInovacao( this.addInov( idAnt, idPos, c.getPeso()));
		this.genes.add(c);
		//fim
	}
	
	private void adicionarConexao( int idAnt, int idPos){
		//cria a conexão
		Conexao c = new Conexao( this.nodulos.get(idAnt), this.nodulos.get(idPos));
		this.nodulos.get(idAnt).addSaida(c);
		//Adiciona gene
		c.setInovacao( this.addInov( idAnt, idPos, c.getPeso()));
		this.genes.add(c);
		//fim
	}
	
	public int addInov(int n0, int n1, RefDouble refDouble){	
		for( int i = 0; i < Genoma.inovacaoUni.size(); i++)
		{
			if( Genoma.inovacaoUni.get(i)[1] == n0 && Genoma.inovacaoUni.get(i)[2] == n1)
			{
				RefDouble v[] = { new RefDouble(i), new RefDouble(n0), new RefDouble(n1), refDouble};
				this.inovacao.add(v);
				return i;
			}
		}
		RefDouble inov[] = { new RefDouble(Genoma.inovacaoUni.size()), new RefDouble(n0), new RefDouble(n1), refDouble};
		int v[] = { Genoma.inovacaoUni.size(), n0, n1};
		Genoma.inovacaoUni.add(v);
		this.inovacao.add(inov);
		return Genoma.inovacaoUni.size()-1;
	}
	
	private boolean existe(int n0, int n1){	
		for( int i = 0; i < this.genes.size(); i++)
		{
			if( this.genes.get(i).getAnterior().id == n0 && this.genes.get(i).getPosterior().id == n1)
			{
				return false;
			}
		}
		return true;
	}
	
	public void mutarPeso(){
			int id  = ( int)(( Math.random()*1000)%( this.genes.size()));
			System.out.println(id);
			this.genes.get(id).mutarPeso();
			
	}
	
	public Genoma copiar(){ //Copia o Gonoma
	        Genoma.salvar( this, "/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/src/neatRobotus/GenomaAuxiliar");
	        Genoma g = (Genoma) Genoma.carregar("/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/src/neatRobotus/GenomaAuxiliar.ser");
	        g.mutar(1);
	        if(this.equals(g)) System.exit(1);
	        g.fitness = 0;
	        g.numFit = 0;
	        return g;
	}
	
	public int compareTo(Genoma outro) {
		if(this.getFitness() < outro.getFitness())
			return 1;
		if(this.getFitness() > outro.getFitness())
			return -1;
		return 0;
	}
	
	protected static boolean salvar(Genoma g, String local){
		try 
		{
	         FileOutputStream fileOut = new FileOutputStream(local+".ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(g);
	         System.out.println("Objeto salvo(serializado) em: "+local+".ser");
	         return true;
		}
		catch(IOException e)
		{
	         e.printStackTrace();
	 		return false;
		}
	}
	
	protected static  Genoma carregar( String local) {
		Genoma genoma;
	     try {
	         FileInputStream fileIn = new FileInputStream( local);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         System.out.println("Objeto carregado(serializado) em: "+local+".ser");
	         return (Genoma) in.readObject();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Genoma não encontrado");
	         c.printStackTrace();
	         return null;
	      }
	}
	
	
//################DEBUG#####################
	public void mostrarGenoma(){
		System.out.println(Genoma.inovacaoUni.size());
		for(int i = 0; i< this.nodulos.size(); i++){
			System.out.println("/:Nódulos:"+this.nodulos.get(i).id+"::");
		}
		for(int i = 0; i< this.genes.size(); i++){
			//System.out.println(this.inovacao.get(i)[0]+":Próprias:"+this.inovacao.get(i)[1]+"-->"+this.inovacao.get(i)[2]);
			System.out.println(this.genes.get(i).getInovacao()+":Próprias:"+this.genes.get(i).getAnterior().id+"-->"+this.genes.get(i).getPosterior().id);
		}
		for(int i = 0; i< Genoma.inovacaoUni.size(); i++){
			System.out.println(i+":Universais:"+Genoma.inovacaoUni.get(i)[1]+"-->"+Genoma.inovacaoUni.get(i)[2]);
		}
	}

}
