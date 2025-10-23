package genetic.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntegerChromosome extends Chromosome {
	private final int[] genes;

	public IntegerChromosome(int length){ this.genes = new int[length]; }

	public static IntegerChromosome randomInit(int[] lower, int[] upper, Random rng){
		if (lower.length != upper.length) throw new IllegalArgumentException("bounds length mismatch");
		IntegerChromosome c = new IntegerChromosome(lower.length);
		for (int i = 0; i < lower.length; i++){
			int lo = lower[i], hi = upper[i];
			if (hi < lo) { int t = lo; lo = hi; hi = t; }
			c.genes[i] = lo + (rng.nextInt(hi - lo + 1));
		}
		return c;
	}

	public int get(int idx){ return genes[idx]; }
	public void set(int idx, int val){ genes[idx] = val; markUnevaluated(); }
	@Override public int length(){ return genes.length; }

	@Override
	public List<Object> getGenes(){
		List<Object> out = new ArrayList<>(genes.length);
		for(int v:genes) out.add(v);
		return out;
	}

	@Override
	public IntegerChromosome clone(){
		IntegerChromosome c = new IntegerChromosome(genes.length);
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
			sb.append(genes[i]);
		}
		sb.append(']');
		return sb.toString();
	}
}


