package genetic;

import genetic.api.*;
import genetic.chromosome.*;
import genetic.selection.*;
import genetic.crossover.*;
import genetic.mutation.*;
import genetic.replacement.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Central GA engine. Configurable; reasonable defaults provided.
 */
public class GeneticAlgorithm {
    // defaults
    private int populationSize = 50;
    private int generations = 200;
    private double crossoverRate = 0.8;
    private double mutationRate = 0.02;
    private SelectionStrategy selection = new TournamentSelection(3);
    private CrossoverOperator crossover = new OnePointCrossover();
    private MutationOperator mutation; // must be set
    private ReplacementStrategy replacement = new GenerationalReplacement();
    private FitnessFunction fitnessFunction;
    private InfeasibleHandler infeasibleHandler; // optional
    private java.util.Random rng = new Random();

    private List<Chromosome> population = new ArrayList<>();

    // setters
    public void setPopulationSize(int s){ this.populationSize = s; }
    public void setGenerations(int g){ this.generations = g; }
    public void setCrossoverRate(double r){ this.crossoverRate = r; }
    public void setMutationRate(double r){ this.mutationRate = r; }
    public void setSelection(SelectionStrategy sel){ this.selection = sel; }
    public void setCrossover(CrossoverOperator co){ this.crossover = co; }
    public void setMutation(MutationOperator mo){ this.mutation = mo; }
    public void setReplacement(ReplacementStrategy rs){ this.replacement = rs; }
    public void setFitnessFunction(FitnessFunction f){ this.fitnessFunction = f; }
    public void setInfeasibleHandler(InfeasibleHandler h){ this.infeasibleHandler = h; }
    public void setRandomSeed(long seed){ this.rng.setSeed(seed); }

    // population initializer must be provided externally (factory lambda) to keep library general
    public interface Initializer { Chromosome create(); }
    private Initializer initializer;

    public void setInitializer(Initializer init){ this.initializer = init; }

    // run GA
    public Chromosome run() {
        if (fitnessFunction == null) throw new IllegalStateException("FitnessFunction not set");
        if (initializer == null) throw new IllegalStateException("Initializer not set");
        if (mutation == null) throw new IllegalStateException("MutationOperator not set");

        // init
        population.clear();
        for(int i=0;i<populationSize;i++){
            Chromosome c = initializer.create();
            population.add(evaluateAndFix(c));
        }

        Chromosome bestEver = population.stream().max(Comparator.comparingDouble(Chromosome::getFitness)).get();

        for (int gen = 0; gen < generations; gen++) {
            List<Chromosome> offspring = new ArrayList<>();
            // produce popSize offspring (could be tuned)
            while (offspring.size() < populationSize) {
                Chromosome parent1 = selection.select(population);
                Chromosome parent2 = selection.select(population);
                Chromosome[] children = crossover.crossover(parent1, parent2, crossoverRate);
                children[0] = mutation.mutate(children[0], mutationRate);
                children[1] = mutation.mutate(children[1], mutationRate);
                offspring.add(evaluateAndFix(children[0]));
                if (offspring.size() < populationSize) offspring.add(evaluateAndFix(children[1]));
            }
            population = replacement.replace(population, offspring, populationSize);
            // update best
            Chromosome genBest = population.stream().max(Comparator.comparingDouble(Chromosome::getFitness)).get();
            if (genBest.getFitness() > bestEver.getFitness()) bestEver = genBest.clone();
        }
        return bestEver;
    }

    private Chromosome evaluateAndFix(Chromosome c){
        // infeasible handling
        if (infeasibleHandler != null && !infeasibleHandler.isFeasible(c)) {
            Chromosome repaired = infeasibleHandler.repair(c);
            if (repaired != null) c = repaired;
        }
        double fit = fitnessFunction.evaluate(c);
        c.setFitness(fit);
        return c;
    }
}
