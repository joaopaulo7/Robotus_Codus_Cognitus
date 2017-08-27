package neatRobotus;

import java.util.ArrayList;

public class Genoma implements java.io.Serializable, Cloneable, Comparable<Genoma> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected int fitness = 0;
	protected static ArrayList<int[]> inovacaoUni = new ArrayList<int[]>();
	protected ArrayList<int[]> inovacao = new ArrayList<int[]>();
	protected ArrayList<Conexao> genes = new ArrayList<Conexao>();
	protected ArrayList<Nodulo> nodulos = new ArrayList<Nodulo>();
	protected ArrayList<Output> outputs = new ArrayList<Output>();
	
	
	protected static double MUTAR_CONEXAO = 0.03;
	protected static double MUTAR_NODULO = 0.01;
	protected static double MUTAR_PESO = 0.8;
	
	//Inicialização dos Inputs e Outputs
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
	
	public Genoma( ArrayList<Conexao> conexaoAns, ArrayList<Nodulo> nodulosAns)
	{
		for(int i = 0; i< conexaoAns.size(); i++)
			this.genes.add((Conexao) conexaoAns.get(i).copiar());
		for(int i = 0; i< nodulosAns.size(); i++)
			this.nodulos.add((Nodulo) nodulosAns.get(i).copiar());
	}
	
	//Gets e Sets
	public void setFitness( int fit){
		this.fitness = fit;
	}
	
	public int getFitness()
	{
		return this.fitness;
	}
	
	//Mutações
	public void mutar( int potencial){
		int i = 0;
		while(i <= potencial){
			if( Math.random() < Genoma.MUTAR_CONEXAO){
				//encontra 2 nódulos possíveis
				int idAnt =this.noduloAleatorio( true, -1);
				Nodulo n0 = this.nodulos.get(idAnt);
				Nodulo n1 = this.nodulos.get(this.noduloAleatorio( false, idAnt));
				//cria a conexão
				Conexao c = new Conexao( n0, n1);
				n0.addSaida(c);
				//Adiciona gene
				this.addInov(c.getAnterior().getId(), c.getPosterior().getId());
				this.addGenes(c);
				//fim
				System.out.println("Nova conexao");
				i++;
			}
			if( Math.random() < Genoma.MUTAR_NODULO && !genes.isEmpty()){
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
		double g[] = new double[10]; 
		for( int i = 0; i < v.length; i++)
			this.nodulos.get(i).ativar(v[i]);
		for( int i = 0; i < outputs.size(); i++)
			g[i] = this.outputs.get(i).calcularSaida();
		
		return g;
	}
	
	//Ferramentas
	private int noduloAleatorio( boolean eAnt, int proibido){
		int id = 0;
		if(eAnt){
				id = ( int)(Math.random()*100)%(this.nodulos.size()-10);
				if(id >18 && id < 29)
					id = id-10;
				System.out.println("Tentando ant nod"+id);
		}
		else
		{
			while(true){
				id = ( int)((Math.random()*100)%(this.nodulos.size()-19)+19);
				if(id != proibido){
					System.out.println("Tentando prox nod"+id);
					break;
				}
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
		this.addInov(c.getAnterior().getId(), novoNodulo.getId());
		//Adicionar 2ª conexao nas inovações
		this.addInov(novoNodulo.getId(), proximoNodulo.getId());
		//Adicionar genes
		this.addGenes( novaConexao);
		
		return novoNodulo;
	}
	
	public boolean addInov(int n0, int n1){
		for(int i = 0; i < Genoma.inovacaoUni.size(); i++){
			if((Genoma.inovacaoUni.get(i)[0] == n0) && (Genoma.inovacaoUni.get(i)[1] == n1))
				return false;

		}
		int v[] = { n0, n1};
		this.inovacao.add(v);
		Genoma.inovacaoUni.add(v);
		return true;
	}
	
	public void addGenes( Conexao conexao){
		this.genes.add(conexao);
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
		System.out.println(this.inovacao.size());
		for(int i = 0; i< this.nodulos.size(); i++){
			System.out.println("/:Nódulos:"+this.nodulos.get(i).id+"::");
		}
		for(int i = 0; i< this.inovacao.size(); i++){
			System.out.println((i)+":Próprias:"+this.inovacao.get(i)[0]+"-->"+this.inovacao.get(i)[1]);
		}
		for(int i = 0; i< Genoma.inovacaoUni.size(); i++){
			System.out.println((i)+":Universais:"+Genoma.inovacaoUni.get(i)[0]+"-->"+Genoma.inovacaoUni.get(i)[1]);
		}
	}

}
