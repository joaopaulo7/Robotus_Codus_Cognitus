package neatRobotus;

import java.util.ArrayList;

public abstract class Especie {
	
	protected static int numEspc = 0,  disjoint = 0, excess = 0;
	protected static int especies[] = new int[1000];
	private static double c0 = 1, c1 = 1, c2 = 0.4, constEspec = 3, deltaAlt = 0;
	
	
	public static void formarEspecies( ArrayList<Genoma> lista){
		int j = 0, h = 0, nodulos;
		double  sumPeso = 0.0, numPesos = 0;

		ArrayList<RefDouble[]> g0;
		ArrayList<RefDouble[]> g1;
		
		Especie.numEspc = 0;
		
		for(int i = 0; i < lista.size(); i++)
			for(int k = 0; k < lista.get(i).inovacao.size(); k++)
				System.out.println( lista.get(i).inovacao.get(k)[0].valor+"::"+lista.get(i).inovacao.get(k)[1].valor+"->"+lista.get(i).inovacao.get(k)[2].valor);
		
		for(int i = 0; i < lista.size(); i++)
			lista.get(i).especie = -1;
		
		int i = 0;
		
		System.out.println(lista.size());
		
		while( j < lista.size()) {
			Especie.especies[j] = 0;
			g0 = lista.get(j).inovacao;
			h = j+1;
			sumPeso = 0;
			while( lista.get(j).especie == -1 && h < lista.size()) {
				g1 = lista.get(h).inovacao;
				//
				if( lista.get(j).nodulos.size() > lista.get(h).nodulos.size())
					nodulos = lista.get(j).nodulos.size()-Genoma.NUM_NODULOSBASE;
				else
					nodulos = lista.get(h).nodulos.size()-Genoma.NUM_NODULOSBASE;
				i = 0;
				while( i < g0.size() || i < g1.size())
				{
					if(( i < g0.size() && i < g1.size())){
						if( g0.get(i)[0].valor - g1.get(i)[0].valor > -0.1 && g0.get(i)[0].valor - g1.get(i)[0].valor < 0.1){
							sumPeso += Math.abs( g0.get(i)[3].valor - g1.get(i)[3].valor);
							i++;
							numPesos++;
						}
						else
						{
							disjoint ++;
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
				if( nodulos == 0)
					nodulos = 1;
				deltaAlt = ( Especie.c0* excess/nodulos) + ( Especie.c1* disjoint/nodulos) +( Especie.c2* sumPeso/numPesos);
				System.out.println( deltaAlt);
				if( deltaAlt < Especie.constEspec) {
					lista.get(h).especie = Especie.numEspc;
					Especie.especies[Especie.numEspc]++;
				}
				deltaAlt = 0;
				excess = 0;
				disjoint = 0;
				numPesos = 0;
				sumPeso = 0;
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
