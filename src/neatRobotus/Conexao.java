package neatRobotus;

public class Conexao implements java.io.Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Nodulo anterior = null;
	private Nodulo posterior = null;
	private double peso = Math.random()*400-2;
	private boolean ativado = true;
	private int inovacao = 0;
	private int profundidade = 0;
	
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
	
	public int getInovacao(){
		return this.inovacao;
	}
	
	public void setInovacao( int novo){
		this.inovacao = novo;
	}
	
	public boolean getEstado(){
		return this.ativado;
	}
	
	public void setProfundidade( int novo){
		this.profundidade = novo;
	}
	
	public int getProfundidade(){
		return this.profundidade;
	}
	
	
	//ALTERAR ESTADO
	
	public void mudarEstado(){
		this.ativado = !this.ativado;
	}
	
	//FUNCIONAR
	
	public void ativar(){
		this.posterior.setValor(this.anterior.getValor()*this.peso);
	}
	
	
	public Conexao copiar(){
	    try {
	        return ( Conexao) this.clone();
	    } catch (final CloneNotSupportedException ex) {
	        throw new AssertionError();
	    }
	}
	
	//FERRAMENTAS
	
	public void ajustaProfundidade(){
		if( this.posterior == null)
			return;
		else{
			//this.profundidade++;
			this.posterior.ajustaProfundidade();
		}	
	}
}
