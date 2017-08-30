package neatRobotus;

import java.util.ArrayList;

public class Especie {
	
	public static int numEspecies = 0;
	private static double c0 = 1, c1 = 1, c2 = 0.4, constEspec = 3;
	
	
	public static int mesmaEspecie( ArrayList<double[]> g0, ArrayList<double[]> g1, int nodulos){
		int i = 0, disjoint = 0, excess = 0, numPesos = 0;
		double sumPeso = 0.0;
		if( 20 > g0.size() || 20 > g1.size())
			nodulos = 1;
		while( i < g0.size() || i < g1.size())
		{
			if(( i < g0.size() && i < g1.size())){
				if( g0.get(i)[0] == g1.get(i)[0]){
					sumPeso += Math.abs( g0.get(i)[3] - g1.get(i)[3]);
					i++;
					numPesos++;
				}
				else
				{
					disjoint++;
					i++;
				}
			}
			else
			{
				i++;
				excess++;
			}
		}
		//System.out.println( "("+disjoint +"/"+nodulos+")"+"+(" +excess+"/"+nodulos+")" +"+("+sumPeso+"/"+numPesos+")");
		if( numPesos ==0)
			numPesos = 1;
		double deltaAlt = ( Especie.c0* excess/nodulos) + ( Especie.c1* disjoint/nodulos) +( Especie.c2* sumPeso/numPesos) - Especie.constEspec;
		//System.out.println( deltaAlt);
		if( deltaAlt < 0)
			return 1;
		return 0;
	}
}
