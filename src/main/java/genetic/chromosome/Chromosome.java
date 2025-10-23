package genetic.chromosome;

import java.util.List;


public abstract class Chromosome implements Cloneable {
	protected double fitness = Double.NEGATIVE_INFINITY;
	protected boolean evaluated = false;

	// number of genes
	public abstract int length();

	public List<Object> getGenes() { throw new UnsupportedOperationException("getGenes not supported"); }

	public void markUnevaluated(){ this.evaluated = false; this.fitness = Double.NEGATIVE_INFINITY; }

	public double getFitness(){ return fitness; }
	public void setFitness(double fitness){ this.fitness = fitness; this.evaluated = true; }

	public String asString(){ return toString(); }

	@Override
	public abstract Chromosome clone();
}

