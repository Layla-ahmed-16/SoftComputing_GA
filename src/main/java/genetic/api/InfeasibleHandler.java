package genetic.api;

import genetic.chromosome.Chromosome;

public interface InfeasibleHandler {
    boolean isFeasible(Chromosome individual);
    Chromosome repair(Chromosome infeasibleIndividual);
}

