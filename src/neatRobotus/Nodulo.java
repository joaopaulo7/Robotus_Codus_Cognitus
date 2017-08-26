package neatRobotus;


public class Nodulo implements java.io.Serializable, Cloneable{ //Unidade que vai armazenar os valores entre conexões(sinapses).


	
	private static final long serialVersionUID = 1L;
	private Conexao anterior = null;
	private Conexao posterior = null;
	protected int profundidade = 0;
	public int id = 0;
	
	protected double valor = 0.0;
	
	
	public Nodulo copiar(){ //Copia o nódulo
	    try {
	        return ( Nodulo) this.clone();
	    } catch (final CloneNotSupportedException ex) {
	        throw new AssertionError();
	    }
	}
	
	
	//GETS E SETS
	public double getValor(){ //retorna o valor do nódulo
		return this.valor;
	}
	
	public void setValor( double novoValor){ //define o valor do nódulo
		this.valor = novoValor;
	}
	public Conexao getEntrada(){
		return this.anterior;
	}
	public Conexao getSaida(){
		return this.posterior;
	}
	public void setEntrada(Conexao nova){
		this.anterior = nova;
	}
	public void setSaida( Conexao nova){
		this.posterior = nova;
	}
	
	public int getProfundidade(){
		return this.profundidade;
	}
	
	//#FERRAMENTAS#
	
	public void ajustaProfundidade( int profAnt){
		if( this.posterior != null)
			return;
		else{
			this.profundidade = profAnt+1;
			this.posterior.ajustaProfundidade();
		}	
	}
	
	public void ajustaProfundidade(){
		if( this.posterior != null)
			return;
		else{
			this.profundidade++;
			this.posterior.ajustaProfundidade();
		}	
	}
	
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


	private static final long serialVersionUID = 1L;
	

	public Output( String nome){
		super(nome);
		this.profundidade = 1;
	}
	
	public double calcularSaida(){ return 0.0; }
}

//Os outputs são diferênciados pela formatação da saída.

class OutputAngular extends Output{

	public OutputAngular(String nome) {
		super(nome);
	}
	
	private static final long serialVersionUID = 1L;
	
	public double calcularSaida()
	{
		return this.valor%360;
	}
}

class OutputBool extends Output{


	private static final long serialVersionUID = 1L;

	public OutputBool(String nome) {
		super(nome);
	}
	
	public double calcularSaida()
	{
		if( this.valor > 0)
			return 1;
		else
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
			return valor%10;
		}
}

class OutputWalk extends Output{


	private static final long serialVersionUID = 1L;

		public OutputWalk(String nome) {
			super(nome);
		}
		
		public double calcularSaida()
		{
			return valor%100;
		}
}