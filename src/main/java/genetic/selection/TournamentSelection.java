package com.softcomputing.ga.selection;

import com.softcomputing.ga.chromosome.Chromosome;
import java.util.List;
import java.util.Random;

public class TournamentSelection implements SelectionStrategy {
    private final int tournamentSize;
    private final Random rng = new Random();

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = Math.max(2, tournamentSize);
    }

    @Override
    public Chromosome select(List<Chromosome> population) {
        Chromosome best = null;
        for (int i = 0; i < tournamentSize; i++) {
            Chromosome candidate = population.get(rng.nextInt(population.size()));
            if (best == null || candidate.getFitness() > best.getFitness()) best = candidate;
        }
        return best;
    }
}

