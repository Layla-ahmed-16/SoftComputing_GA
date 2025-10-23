package genetic.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FloatChromosome extends Chromosome {
	private final double[] genes;

	public FloatChromosome(int length){ this.genes = new double[length]; }

	public static FloatChromosome randomInit(int length, double lo, double hi, Random rng){
		FloatChromosome c = new FloatChromosome(length);
		for(int i=0;i<length;i++) c.genes[i] = lo + rng.nextDouble() * (hi - lo);
		return c;
	}

	public double get(int idx){ return genes[idx]; }
	public void set(int idx, double val){ genes[idx] = val; markUnevaluated(); }
	@Override public int length(){ return genes.length; }

	@Override
	public List<Object> getGenes(){
		List<Object> out = new ArrayList<>(genes.length);
		for(double v:genes) out.add(v);
		return out;
	}

	@Override
	public FloatChromosome clone(){
		FloatChromosome c = new FloatChromosome(genes.length);
		System.arraycopy(this.genes, 0, c.genes, 0, genes.length);
		c.fitness = this.fitness; c.evaluated = this.evaluated;
		return c;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for(int i=0;i<genes.length;i++){
			if (i>0) sb.append(',');
			sb.append(String.format("%.4f", genes[i]));
		}
		sb.append(']');
		return sb.toString();
	}
}

