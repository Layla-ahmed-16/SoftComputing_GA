package com.softcomputing.ga.selection;

import com.softcomputing.ga.chromosome.Chromosome;
import java.util.List;
import java.util.Random;

public class RouletteWheelSelection implements SelectionStrategy {
    private final Random rng = new Random();

    @Override
    public Chromosome select(List<Chromosome> population) {
        double totalFitness = population.stream()
                                        .mapToDouble(c -> Math.max(0, c.getFitness()))
                                        .sum();
        if (totalFitness == 0) return population.get(rng.nextInt(population.size()));

        double r = rng.nextDouble() * totalFitness;
        double cumulative = 0;
        for (Chromosome c : population) {
            cumulative += Math.max(0, c.getFitness());
            if (cumulative >= r) return c;
        }
        return population.get(population.size() - 1); // fallback
    }
}

