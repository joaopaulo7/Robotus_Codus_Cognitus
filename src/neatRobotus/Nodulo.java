package neatRobotus;

import java.util.ArrayList;


public class Nodulo implements java.io.Serializable{ //Unidade que vai armazenar os valores entre conexões(sinapses).


	
	private static final long serialVersionUID = 1L;
	protected ArrayList<Conexao> posterior = new ArrayList<Conexao>();
	public int id = 0;
	private boolean ativado = false;
	
	//GETS E SETS
	
	public void ativar( double soma){ //define o valor do nódulo
		if( !this.posterior.isEmpty() && !ativado){
			ativado = true;
			for( int i =0; i < this.posterior.size(); i++){
				this.posterior.get(i).ativar( soma);
			}
			ativado = false;
		}
	}
	
	public Conexao getSaida( int i){
		return this.posterior.get(i);
	}
	
	public void addSaida( Conexao nova){
		this.posterior.add(nova);
	}
	
	
	//#FERRAMENTAS#
	
	public int getId(){
		return this.id;
	}
}

//#INPUT#

class Input extends Nodulo{


	private static final long serialVersionUID = 1L;
	
	protected String nome = "#";
	
	public Input( String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
}


//#OUTPUTS#

class Output extends Input{


	public Output(String nome) {
		super(nome);
	}

	private static final long serialVersionUID = 1L;
	protected double valor = 0.0;
	
	public void ativar( double soma){
		this.valor = soma;
	}
	
	public double calcularSaida(){ return this.valor; }
}

//Os outputs são diferênciados pela formatação da saída.

class OutputAngular extends Output{

	public OutputAngular(String nome) {
		super(nome);
	}
	
	private static final long serialVersionUID = 1L;
	
	public double calcularSaida()
	{
		double valor = this.valor;
		this.valor = 0.0;
		return valor%180;
	}
}

class OutputBool extends Output{


	private static final long serialVersionUID = 1L;

	public OutputBool(String nome) {
		super(nome);
	}
	
	public double calcularSaida()
	{
		if( this.valor > 0){
			this.valor = 0.0;
			return 1;
		}else {
			this.valor = 0.0;
			return 0.0;
		}
	}
}

class OutputShoot extends Output{


	private static final long serialVersionUID = 1L;

		public OutputShoot(String nome) {
			super(nome);
		}
		
		public double calcularSaida()
		{
			double valor = this.valor;
			this.valor = 0.0;
			return valor%3;
		}
}

class OutputWalk extends Output{


	private static final long serialVersionUID = 1L;

		public OutputWalk(String nome) {
			super(nome);
		}
		
		public double calcularSaida()
		{
			double valor = this.valor;
			this.valor = 0.0;
			return Math.abs(valor%200);
		}
}
class Bias extends Input{
 
	public Bias(String nome) {
		super(nome);
	}
	private static final long serialVersionUID = 1L;
	
	public void mutarAleatorio(){
		int rand = ( int) (Math.random()*1000)%this.posterior.size();
		this.posterior.get(rand).mutarPeso();
	}
	

}