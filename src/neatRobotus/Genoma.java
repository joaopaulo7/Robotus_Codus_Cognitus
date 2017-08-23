package neatRobotus;

import java.util.ArrayList;

public class Genoma implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int fitness = 0;
	protected ArrayList<Conexao> genes = new ArrayList<Conexao>();
	protected ArrayList<Nodulo> nodulos = new ArrayList<Nodulo>();
	
	protected ArrayList<Input> inputs = new ArrayList<Input>();
	protected ArrayList<Output> outputs = new ArrayList<Output>();
	
	protected static double MUTAR_CONEXAO = 0.2;
	protected static double MUTAR_NODULO = 0.3;
	protected static double MUTAR_PESO = 0.5;
	
	//Inicialização dos Inputs e Outputs
	public Genoma(){
		//Declaração de Inputs
			//Relacionados ao próprio robô
				this.inputs.add(new Input("Altura do campo(y)"));
				this.inputs.add(new Input("Largura do campo(x)"));
				this.inputs.add(new Input("Energia do robô"));
				this.inputs.add(new Input("Taxa de esfriamento"));
				this.inputs.add(new Input("Rotação do canhão"));
				this.inputs.add(new Input("Temperatura da arma"));
				this.inputs.add(new Input("Rotação do robô"));
				this.inputs.add(new Input("Altura do robô"));
				this.inputs.add(new Input("Número de robôs restantes"));
				this.inputs.add(new Input("Rotação do radar"));
				this.inputs.add(new Input("Velociade do robô"));
				this.inputs.add(new Input("Posiçao do robô"));
				this.inputs.add(new Input("Posição X em que o robô está"));
				this.inputs.add(new Input("Posição Y em que o robô está"));
				
			//Relacionado a outros robôs
				this.inputs.add(new Input("Direção do robô scaneado"));
				this.inputs.add(new Input("Distânciado robô scaneado"));
				this.inputs.add(new Input("Energia do robô scaneado"));
				this.inputs.add(new Input("Angulo do robô scaneado"));
				this.inputs.add(new Input("Velociade do robô scaneado"));
				
		
		//Declaração de Outputs
			this.outputs.add(new OutputNorm("Andar para frente"));
			this.outputs.add(new OutputNorm("Andar para trás"));
			this.outputs.add(new OutputBool("Não fazer nada"));
			this.outputs.add(new OutputNorm("Atirar"));
			this.outputs.add(new OutputAngular("Rotacionar robô a esquerda"));
			this.outputs.add(new OutputAngular("Rotacionar robô para a direita"));
			this.outputs.add(new OutputAngular("Rotacionar arma para a esquerda"));
			this.outputs.add(new OutputAngular("Rotacionar arma para a direita"));
			this.outputs.add(new OutputAngular("Rotacionar radar para a direita"));
			this.outputs.add(new OutputAngular("Rotacionar radar para a direita"));
			for( int i = 0; i < 10; i++)
				nodulos.add(outputs.get(i));
			this.mutar();
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
	public void mutar(){
		while(true){
			if( Math.random() < Genoma.MUTAR_CONEXAO){
				this.genes.add(new Conexao(this.noduloAleatorio(true), this.noduloAleatorio(false)));
				System.out.println("nova conexao");
				return;
			}
			if( Math.random() < Genoma.MUTAR_NODULO && genes.size() !=0){
				this.nodulos.add(this.adicionarNodulo(this.conexaoAleatoria()));
				return;
			}
			if( Math.random() < Genoma.MUTAR_PESO && genes.size() !=0){
				this.genes.get(( int)(Math.random()*100)%this.genes.size());
				return;
			}
		}
	}
	
	//Ativação da rede
	public double[] ativar( double v[]){
		double g[] = new double[10]; 
		for( int i = 0; i < v.length; i++)
			this.inputs.get(i).setValor(v[i]);
		for( int i = 0; i < this.genes.size(); i++)
			this.genes.get(i).ativar();
		for( int i = 0; i < this.outputs.size(); i++)
			g[i] = this.outputs.get(i).getValor();
		
		return g;
	}
	
	//Ferramentas
	private Nodulo noduloAleatorio( boolean eAnt){
		Input n;
		if(eAnt){
			n = this.inputs.get(( int)(Math.random()*100)%this.inputs.size());
			System.out.println(n.getNome());
		}
		else
		{
			n = this.outputs.get(( int)(Math.random()*100)%this.outputs.size());
			System.out.println(n.getNome());
		}
		return ( Nodulo) n;
	}
	
	private Conexao conexaoAleatoria(){
		return genes.get(( int)(Math.random()*100)%this.genes.size());
	}
	
	private Nodulo adicionarNodulo( Conexao c){
		Nodulo proximoNodulo = c.getPosterior();
		Nodulo novoNodulo = new Nodulo();
		c.setPosterior(novoNodulo);
		genes.add(new Conexao( novoNodulo, proximoNodulo));
		return novoNodulo;
	}
}
