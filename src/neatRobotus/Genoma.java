package neatRobotus;

import java.util.ArrayList;
import java.util.Collections;

public class Genoma implements java.io.Serializable, Cloneable, Comparable<Genoma> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int fitness = 0;
	
	
	protected Bias bias = new Bias(" Bias");
	protected static ArrayList<int[]> inovacaoUni = new ArrayList<int[]>();
	protected ArrayList<double[]> inovacao = new ArrayList<double[]>();
	protected ArrayList<Conexao> genes = new ArrayList<Conexao>();
	protected ArrayList<Nodulo> nodulos = new ArrayList<Nodulo>();
	protected ArrayList<Output> outputs = new ArrayList<Output>();
	protected int count = 1;
	
	protected double MUTAR_CONEXAO = 0.2;
	protected double MUTAR_NODULO = 0.05;
	protected static double MUTAR_PESO = 0.8;
	
	//Criacao de genomas
	public Genoma( int potencialMuta){
		//Declaração de Inputs
			//Relacionados ao próprio robô
				this.nodulos.add(new Input("Altura do campo(y)"));
				this.nodulos.add(new Input("Largura do campo(x)"));
				this.nodulos.add(new Input("Energia do robô"));
				this.nodulos.add(new Input("Taxa de esfriamento"));
				this.nodulos.add(new Input("Rotação do canhão"));
				this.nodulos.add(new Input("Temperatura da arma"));
				this.nodulos.add(new Input("Rotação do robô"));
				this.nodulos.add(new Input("Altura do robô"));
				this.nodulos.add(new Input("Número de robôs restantes"));
				this.nodulos.add(new Input("Rotação do radar"));
				this.nodulos.add(new Input("Velociade do robô"));
				this.nodulos.add(new Input("Posiçao do robô"));
				this.nodulos.add(new Input("Posição X em que o robô está"));
				this.nodulos.add(new Input("Posição Y em que o robô está"));
				
			//Relacionado a outros robôs
				this.nodulos.add(new Input("Direção do robô scaneado"));
				this.nodulos.add(new Input("Distânciado robô scaneado"));
				this.nodulos.add(new Input("Energia do robô scaneado"));
				this.nodulos.add(new Input("Angulo do robô scaneado"));
				this.nodulos.add(new Input("Velociade do robô scaneado"));
				
		
		//Declaração de Outputs
			this.outputs.add(new OutputWalk("Andar para frente"));
			this.outputs.add(new OutputWalk("Andar para trás"));
			this.outputs.add(new OutputBool("Não fazer nada"));
			this.outputs.add(new OutputShoot("Atirar"));
			this.outputs.add(new OutputAngular("Rotacionar robô a esquerda"));
			this.outputs.add(new OutputAngular("Rotacionar robô para a direita"));
			this.outputs.add(new OutputAngular("Rotacionar arma para a esquerda"));
			this.outputs.add(new OutputAngular("Rotacionar arma para a direita"));
			this.outputs.add(new OutputAngular("Rotacionar radar para a direita"));
			this.outputs.add(new OutputAngular("Rotacionar radar para a direita"));

			
			for(int i = 0; i< this.outputs.size(); i++)
				this.nodulos.add(this.outputs.get(i));
			for(int i = 0; i< this.nodulos.size(); i++){
				this.nodulos.get(i).id = i;
				if(i > 19)
					this.bias.addSaida( new Conexao(bias, this.nodulos.get(i)));
			}
			
			this.mutar(potencialMuta);
	}
	
	public static Genoma montarGenoma( ArrayList<Conexao> conexoes, int numNodulos)
	{
		Genoma homun = new Genoma(-1);
		for( int i = 29; i < numNodulos; i++)
		{
			homun.nodulos.add( new Nodulo());
			homun.nodulos.get(i).id = homun.nodulos.size()-1;
		}
		int j = 0;
		while( j < conexoes.size())
		{
			int idAnt = conexoes.get(j).getAnterior().id;
			int idPos = conexoes.get(j).getPosterior().id;
			homun.adicionarConexao(idAnt, idPos);
			j++;
		}
		return homun;
	}
	
	//Gets e Sets
	public void setFitness( int fit){
		this.fitness += fit;
		this.count++;
	}
	
	public int getFitness()
	{
		return this.fitness/this.count;
	}
	
	//Mutações
	public void mutar( int potencial){
		int i = 0;
		while(i <= potencial){
			if( Math.random() < this.MUTAR_CONEXAO || genes.isEmpty()){
				//this.MUTAR_CONEXAO *= 0.7;
				//encontra 2 nódulos possíveis
				int idAnt = this.noduloAleatorio( true, -1);
				int idPos = this.noduloAleatorio( false, idAnt);
				if( this.existe( idAnt, idPos)){
					this.adicionarConexao(idAnt, idPos);
					i++;
				}
			}
			else if( Math.random() < this.MUTAR_NODULO && !genes.isEmpty()){
				//this.MUTAR_NODULO *= 0.7;
				this.adicionarNodulo(this.conexaoAleatoria());
				i++;
			}
			else if( Math.random() < Genoma.MUTAR_PESO && !genes.isEmpty()){
				this.mutarPeso();
				i++;
			}
			Collections.sort(this.genes);
		}
	}
	
	//Ativação da rede
	public double[] ativar( double v[]){
		double g[] = new double[10];
		this.bias.ativar(1);
		for( int i = 0; i < v.length; i++)
			this.nodulos.get(i).ativar(v[i]);
		for( int i = 0; i < outputs.size(); i++)
			g[i] = this.outputs.get(i).calcularSaida();
		return g;
	}
	
	//Ferramentas
	private int noduloAleatorio( boolean eAnt, int proibido){
		int id = ( int)(( Math.random()*10000)%( this.nodulos.size()));
		if(eAnt){
				if(id >18 && id < 29)
					id -=10;
				System.out.println("Tentando ant nod"+id);
		}
		else
		{
			while(id == proibido || id < 19){
				id = ( int)(( Math.random()*10000)%( this.nodulos.size()-19)+19);
					System.out.println("Tentando prox nod"+id);
			}
		}
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
		adicionarConexao( novoNodulo.id, proximoNodulo.id);
		//Adicionar 1ª conexao nas inovações
		c.setInovacao(this.addInov(c.getAnterior().getId(), novoNodulo.getId(), c.getPeso()));
		//adiciona a conexao do BIAS ao novo nódulo
		this.bias.addSaida( new Conexao( this.bias, novoNodulo));
	}
	
	private void adicionarConexao( int idAnt, int idPos){
		//cria a conexão
		Conexao c = new Conexao( this.nodulos.get(idAnt), this.nodulos.get(idPos), 1);
		this.nodulos.get(idAnt).addSaida(c);
		//Adiciona gene
		c.setInovacao( this.addInov( idAnt, idPos, c.getPeso()));
		this.genes.add(c);
		//fim
	}
	
	public int addInov(int n0, int n1, double peso){	
		for( int i = 0; i < Genoma.inovacaoUni.size(); i++)
		{
			if( Genoma.inovacaoUni.get(i)[1] == n0 && Genoma.inovacaoUni.get(i)[2] == n1)
			{
				double v[] = { i, n0, n1, peso};
				this.inovacao.add(v);
				return i;
			}
		}
		double inov[] = { Genoma.inovacaoUni.size(), n0, n1, peso};
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
		int id  = ( int)(( Math.random()*10000)%( this.genes.size()+this.nodulos.size()-10));
		if( id >= this.genes.size())
		{
			this.bias.mutarAleatorio();
		}
		else
		{
			this.genes.get(id).mutarPeso();
		}
			
	}
	
	public Genoma copiar(){ //Copia o Gonoma
	    try {
	        return ( Genoma) this.clone();
	    } catch (final CloneNotSupportedException ex) {
	        throw new AssertionError();
	    }
	}
	
	public int compareTo(Genoma outro) {
		if(this.fitness < outro.fitness)
			return 1;
		if(this.fitness > outro.fitness)
			return -1;
		return 0;
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
