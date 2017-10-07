package neatRobotus;

import java.util.ArrayList;

public abstract class Especie {
	
	protected static int numEspc = 0;
	protected static int especies[] = new int[300];
	private static double c0 = 1, c1 = 1, c2 = 0.4, constEspec = 3;
	
	
	public static void formarEspecies( ArrayList<Genoma> lista){
		int j = 0, h = 0, nodulos;
		ArrayList< double[]> g0, g1;
		
		Especie.numEspc = 0;
		
		for(int i = 0; i < lista.size(); i++)
			lista.get(i).especie = -1;
		
		System.out.println(lista.size());
		
		while( j < lista.size()) {
			Especie.especies[j] = 0;
			g0 = lista.get(j).inovacao;
			h = j+1;
			while( lista.get(j).especie == -1 && h < lista.size()) {
				g1 = lista.get(h).inovacao;
				int i = 0, disjoint = 0, excess = 0, numPesos = 0;
				double sumPeso = 0.0;
				//
				if( lista.get(j).nodulos.size() > lista.get(h).nodulos.size())
					nodulos = lista.get(j).nodulos.size()-Genoma.NUM_NODULOSBASE+1;
				else
					nodulos = lista.get(h).nodulos.size()-Genoma.NUM_NODULOSBASE+1;
				//
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
				System.out.println( "("+disjoint +"/"+nodulos+")"+"+(" +excess+"/"+nodulos+")" +"+("+sumPeso+"/"+numPesos+")");
				if( numPesos == 0)
					numPesos = 1;
				double deltaAlt = ( Especie.c0* excess/nodulos) + ( Especie.c1* disjoint/nodulos) +( Especie.c2* sumPeso/numPesos) - Especie.constEspec;
				System.out.println( deltaAlt);
				if( deltaAlt < Especie.constEspec) {
					lista.get(h).especie = Especie.numEspc;
					Especie.especies[Especie.numEspc]++;
				}
				h++;
			}
			if( lista.get(j).especie == -1) {
				Especie.especies[Especie.numEspc]++;
				lista.get(j).especie = Especie.numEspc;
				Especie.numEspc++;
			}
			j++;
		}
	}
}
