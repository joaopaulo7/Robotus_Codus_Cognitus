package neatRobotus;

class Conexao implements java.io.Serializable, Comparable<Conexao>{
	
	private static final long serialVersionUID = 1L;
	
	private Nodulo anterior = null;
	private Nodulo posterior = null;
	private double peso = ((Math.random()*200)%200)-100;;
	private boolean ativado = true;
	private int inovacao = 0;
	
	public Conexao( Nodulo n, Nodulo n0){
		this.anterior = n;
		this.posterior = n0;
	}
	
	public Conexao( Nodulo n, Nodulo n0, double peso){
		this.anterior = n;
		this.posterior = n0;
		this.peso = peso;
	}
	
	
	//GETS E SETS
	public Nodulo getAnterior(){
		return this.anterior;
	}
	public void setAnterior( Nodulo novo){
		this.anterior = novo;
	}
	
	public Nodulo getPosterior(){
		return this.posterior;
	}
	public void setPosterior( Nodulo novo){
		this.posterior = novo;
	}
	
	public void mutarPeso(){
		this.peso =((Math.random()*200)%200)-100;
	}
	
	public double getPeso(){
		return this.peso;
	}
	
	public int getInovacao(){
		return this.inovacao;
	}
	
	public void setInovacao( int novo){
		this.inovacao = novo;
	}
	
	public boolean getEstado(){
		return this.ativado;
	}
	
	
	//ALTERAR ESTADO
	
	public void mudarEstado(){
		this.ativado = !this.ativado;
	}
	
	//FUNCIONAR
	
	public void ativar( double soma){
		this.posterior.ativar(soma*this.peso);
	}

	public int compareTo(Conexao outra) {
		if( this.inovacao > outra.getInovacao())
			return 1;
		if( this.inovacao < outra.getInovacao())
			return -1;
		return 0;
	}
	
	//FERRAMENTAS
	
}
