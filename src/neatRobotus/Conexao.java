package neatRobotus;

class Conexao implements java.io.Serializable, Comparable<Conexao>{
	
	private static final long serialVersionUID = 1L;
	
	private Nodulo anterior = null;
	private Nodulo posterior = null;
	private RefDouble peso = new RefDouble (Math.random()*10-5);
	private boolean ativado = true;
	private int inovacao = 0;
	
	public Conexao( Nodulo n, Nodulo n0){
		this.anterior = n;
		this.posterior = n0;
	}
	
	public Conexao( Nodulo n, Nodulo n0, RefDouble peso){
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
		double rand = Math.random();
		if( rand < 0.1) 
		{
			System.out.println(this.peso);
			this.peso.valor = Math.random()*10-5;
			System.out.println("PESSSSSSSSSSSSOOOOOOOOOOOOOOO");
			System.out.println(this.peso);
		}
		else if( rand < 0.55) 
		{
			System.out.println(this.peso);
			this.peso.valor *= 0.5;
			System.out.println("PESSSSSSSSSSSSOOOOOOOOOOOOOOO");
			System.out.println(this.peso);
		}
		else 
		{
			System.out.println(this.peso);
			this.peso.valor *= 1.5;
			System.out.println("PESSSSSSSSSSSSOOOOOOOOOOOOOOO");
			System.out.println(this.peso);
		}
	}
	
	public RefDouble getPeso(){
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
		this.posterior.ativar(soma*this.peso.valor);
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
