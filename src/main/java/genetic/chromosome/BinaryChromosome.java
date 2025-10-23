package genetic.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryChromosome extends Chromosome {
	private final boolean[] genes;

	public BinaryChromosome(int length){ this.genes = new boolean[length]; }

	public static BinaryChromosome randomInit(int length, Random rng){
		BinaryChromosome c = new BinaryChromosome(length);
		for(int i=0;i<length;i++) c.genes[i] = rng.nextBoolean();
		return c;
	}

	public boolean get(int idx){ return genes[idx]; }
	public void set(int idx, boolean val){ genes[idx] = val; markUnevaluated(); }
	@Override public int length(){ return genes.length; }

	@Override
	public List<Object> getGenes(){
		List<Object> out = new ArrayList<>(genes.length);
		for(boolean b:genes) out.add(b);
		return out;
	}

	@Override
	public BinaryChromosome clone(){
		BinaryChromosome c = new BinaryChromosome(genes.length);
		System.arraycopy(this.genes, 0, c.genes, 0, genes.length);
		c.fitness = this.fitness; c.evaluated = this.evaluated;
		return c;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(boolean b:genes) sb.append(b ? '1' : '0');
		return sb.toString();
	}
}

