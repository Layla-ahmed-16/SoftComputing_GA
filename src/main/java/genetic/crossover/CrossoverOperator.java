
package genetic.crossover;

import genetic.chromosome.Chromosome;

public interface CrossoverOperator {
    Chromosome[] crossover(Chromosome parent1, Chromosome parent2, double rate);
}
