package neatRobotus;

import java.util.ArrayList;

public class Genoma {
	private int fitness = 0;
	protected ArrayList<Conexao> genes = new ArrayList<Conexao>();
	protected static ArrayList<Input> inputs = new ArrayList<Input>();
	protected static ArrayList<Output> outputs = new ArrayList<Output>();
	
	public static void InitGenoma(){
		
		//Declaração de Inputs
			//Relacionados ao próprio robô
				Genoma.inputs.add(new Input("Altura do campo(y)"));
				Genoma.inputs.add(new Input("Largura do campo(x)"));
				Genoma.inputs.add(new Input("Energia do robô"));
				Genoma.inputs.add(new Input("Taxa de esfriamento"));
				Genoma.inputs.add(new Input("Rotação do canhão"));
				Genoma.inputs.add(new Input("Temperatura da arma"));
				Genoma.inputs.add(new Input("Rotação do robô"));
				Genoma.inputs.add(new Input("Altura do robô"));
				Genoma.inputs.add(new Input("Número de robôs restantes"));
				Genoma.inputs.add(new Input("Rotação do radar"));
				Genoma.inputs.add(new Input("Velociade do robô"));
				Genoma.inputs.add(new Input("Posiçao do robô"));
				Genoma.inputs.add(new Input("Posição X em que o robô está"));
				Genoma.inputs.add(new Input("Posição Y em que o robô está"));
				
			//Relacionado a outros robôs
				Genoma.inputs.add(new Input("Direção do robô scaneado"));
				Genoma.inputs.add(new Input("Distânciado robô scaneado"));
				Genoma.inputs.add(new Input("Energia do robô scaneado"));
				Genoma.inputs.add(new Input("Angulo do robô scaneado"));
				Genoma.inputs.add(new Input("Velociade do robô scaneado"));
				
		
		//Declaração de Outputs
			Genoma.outputs.add(new OutputNorm("Andar para frente"));
			Genoma.outputs.add(new OutputNorm("Andar para trás"));
			Genoma.outputs.add(new OutputBool("Não fazer nada"));
			Genoma.outputs.add(new OutputNorm("Atirar"));
			Genoma.outputs.add(new OutputAngular("Rotacionar robô a esquerda"));
			Genoma.outputs.add(new OutputAngular("Rotacionar robô para a direita"));
			Genoma.outputs.add(new OutputAngular("Rotacionar arma para a esquerda"));
			Genoma.outputs.add(new OutputAngular("Rotacionar arma para a direita"));
			Genoma.outputs.add(new OutputAngular("Rotacionar radar para a direita"));
			Genoma.outputs.add(new OutputAngular("Rotacionar radar para a direita"));
	}
	
	public void setFitness( int fit){
		this.fitness = fit;
	}
	
	public int getFitness()
	{
		return this.fitness;
	}

	
	public double[] ativar( double v[]){
		double g[] = new double[10]; 
		for( int i = 0; i < v.length; i++)
			inputs.get(i).setValor(v[i]);
		for( int i = 0; i < this.genes.size(); i++)
			this.genes.get(i).ativar();
		for( int i = 0; i < outputs.size(); i++)
			g[i] = outputs.get(i).valor;
		
		return g;
	}
}
