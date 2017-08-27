package neatRobotus;

import java.util.ArrayList;

public class Genoma implements java.io.Serializable, Cloneable, Comparable<Genoma> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected int fitness = 0;
	
	protected int numeroTestes = 1;
	
	protected static ArrayList<int[]> inovacaoUni = new ArrayList<int[]>();
	protected ArrayList<Conexao> genes = new ArrayList<Conexao>();
	protected ArrayList<Nodulo> nodulos = new ArrayList<Nodulo>();
	protected ArrayList<Output> outputs = new ArrayList<Output>();
	
	
	protected double MUTAR_CONEXAO = 0.35;
	protected double MUTAR_NODULO = 0.07;
	protected static double MUTAR_PESO = 0.7;
	
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
			for(int i = 0; i< this.nodulos.size(); i++)
				this.nodulos.get(i).id = i;
			
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
			homun.genes.add( new Conexao( homun.nodulos.get( idAnt), homun.nodulos.get( idPos), conexoes.get(j).getPeso()));
			homun.genes.get(homun.genes.size()-1).setInovacao(homun.addInov( idAnt, idPos));
			j++;
		}
		return homun;
	}
	
	//Gets e Sets
	public void setFitness( int fit){
		this.fitness = (fit+this.fitness)/this.numeroTestes;
	}
	
	public int getFitness()
	{
		return this.fitness;
	}
	
	//Mutações
	public void mutar( int potencial){
		int i = 0;
		while(i <= potencial){
			if( Math.random() < this.MUTAR_CONEXAO){
				this.MUTAR_CONEXAO *= 0.9;
				//encontra 2 nódulos possíveis
				int idAnt = this.noduloAleatorio( true, -1);
				int idPos = this.noduloAleatorio( false, idAnt);
				//cria a conexão
				Conexao c = new Conexao( this.nodulos.get(idAnt), this.nodulos.get(idPos));
				this.nodulos.get(idAnt).addSaida(c);
				//Adiciona gene
				c.setInovacao(this.addInov( idAnt, idPos));
				this.addGenes(c);
				//fim
				System.out.println("Nova conexao");
				i++;
			}
			if( Math.random() < this.MUTAR_NODULO && !genes.isEmpty()){
				this.MUTAR_NODULO *= 0.9;
				this.nodulos.add(this.adicionarNodulo(this.conexaoAleatoria()));
				i++;
			}
			if( Math.random() < Genoma.MUTAR_PESO && !genes.isEmpty()){
				this.genes.get(( int)(Math.random()*100)%this.genes.size());
				i++;
			}
		}
	}
	
	//Ativação da rede
	public double[] ativar( double v[]){
		this.numeroTestes++;
		double g[] = new double[10]; 
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
		return genes.get(( int)(Math.random()*100)%this.genes.size());
	}
	
	private Nodulo adicionarNodulo( Conexao c){
		Nodulo proximoNodulo = c.getPosterior();
		Nodulo novoNodulo = new Nodulo();
		Conexao novaConexao = new Conexao( novoNodulo, proximoNodulo);
		//Ajusta os nódulos
		novoNodulo.id = this.nodulos.size();
		novoNodulo.addSaida(novaConexao);
		c.setPosterior( novoNodulo);
		//Adicionar 1ª conexao nas inovações
		c.setInovacao(this.addInov(c.getAnterior().getId(), novoNodulo.getId()));
		//Adicionar 2ª conexao nas inovações
		novaConexao.setInovacao(this.addInov(novoNodulo.getId(), proximoNodulo.getId()));
		//Adicionar genes
		this.addGenes( novaConexao);
		
		return novoNodulo;
	}
	
	public int addInov(int n0, int n1){
		int i = 0;
		while(( i < Genoma.inovacaoUni.size()) && (Genoma.inovacaoUni.get(i)[1] != n0 || Genoma.inovacaoUni.get(i)[2] != n1)){
				i++;
		}
		if(i == Genoma.inovacaoUni.size())
		{
			int v[] = { Genoma.inovacaoUni.size(), n0, n1};
			Genoma.inovacaoUni.add(v);
			return Genoma.inovacaoUni.size();
		}
		else
		{
			return i;
		}
		
	}
	
	public void addGenes( Conexao conexao){
		this.genes.add(conexao);
		this.genes.get(this.genes.size()-1).setInovacao(Genoma.inovacaoUni.size());
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
			System.out.println(Genoma.inovacaoUni.get(i)[0]+":Universais:"+Genoma.inovacaoUni.get(i)[1]+"-->"+Genoma.inovacaoUni.get(i)[2]);
		}
	}

}
