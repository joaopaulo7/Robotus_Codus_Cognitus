package neatRobotus;

import java.util.ArrayList;


public class Nodulo implements java.io.Serializable, Cloneable{ //Unidade que vai armazenar os valores entre conexões(sinapses).


	
	private static final long serialVersionUID = 1L;
	protected ArrayList<Conexao> posterior = new ArrayList<Conexao>();
	public int id = 0;
	
	
	public Nodulo copiar(){ //Copia o nódulo
	    try {
	        return ( Nodulo) this.clone();
	    } catch (final CloneNotSupportedException ex) {
	        throw new AssertionError();
	    }
	}
	
	
	//GETS E SETS
	
	public void ativar( double soma){ //define o valor do nódulo
		if( !this.posterior.isEmpty()){
			for( int i =0; i < this.posterior.size(); i++){
				System.out.println("opa");
				this.posterior.get(i).ativar( soma);
			}
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
		return valor%360;
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
		}else
			return 0.0;
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
			return valor%5;
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
			return valor%100;
		}
}