package neatRobotus;

public class Conexao {
	private Nodulo anterior = null;
	private Nodulo posterior = null;
	private double peso = Math.random()*400-2;
	private boolean ativado = true;
	private int inovacao = 0;
	
	public Conexao( Nodulo n, Nodulo n0){
		this.anterior = n;
		this.posterior = n0;
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
	public void setPeso( double novo){
		this.peso = novo;
	}
	
	public int getInovacao( int valor){
		return this.inovacao;
	}
	public void setInovacao( int novo){
		this.inovacao = novo;
	}
	
	//ALTERAR ESTADO
	
	public void mudarEstado(){
		this.ativado = !this.ativado;
	}
	
	//FUNCIONAR
	
	public void ativar(){
		this.anterior.setValor(this.anterior.getValor()*this.peso);
	}
	
}
