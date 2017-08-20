package neatRobotus;


public class Nodulo { //Unidade que vai armazenar os valores entre conexões(sinapses).
	protected double valor = 0.0;
	
	public double getValor(){ //retorna o valor do nódulo
		return this.valor;
	}
	
	public void setValor( double novoValor){ //define o valor do nódulo
		this.valor = novoValor;
	}
	
}

//#INPUT#

class Input extends Nodulo{
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
}

//Os outputs são diferênciados pela formatação da saída.

class OutputAngular extends Output{

	public OutputAngular(String nome) {
		super(nome);
	}
	
	public double calcularSaida()
	{
		return this.valor%360;
	}
}

class OutputBool extends Output{

	public OutputBool(String nome) {
		super(nome);
	}
	
	public boolean calcularSaida()
	{
		if( this.valor > 0)
			return true;
		else
			return false;
	}
}

class OutputNorm extends Output{

		public OutputNorm(String nome) {
			super(nome);
		}
		
		public double calcularSaida()
		{
			return valor;
		}
}