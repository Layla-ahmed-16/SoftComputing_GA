package examples;

import genetic.*;
import genetic.selection.*;
import genetic.crossover.*;
import genetic.mutation.*;
import genetic.replacement.*;

import genetic.chromosome.IntegerChromosome;
import genetic.chromosome.Chromosome;

import java.util.Arrays;

public class CaseStudyApplication {
    public static void main(String[] args) {
        // sample food catalogue (small)
        FoodItem[] items = new FoodItem[] {
                new FoodItem("Rice", 200, 4, 45, 1.5, 0.5, false),
                new FoodItem("Chicken", 180, 30, 0, 4, 1.2, false),
                new FoodItem("Salad", 50, 2, 8, 0.5, 0.7, false),
                new FoodItem("Milk", 120, 8, 12, 5, 0.6, false),
                new FoodItem("PeanutSnack", 250, 8, 20, 16, 0.8, true) // allergen
        };

        // decision vars: how many servings of each item per day (0..5)
        int[] lower = new int[items.length];
        int[] upper = new int[items.length];
        Arrays.fill(lower, 0);
        Arrays.fill(upper, 5);

        // configure GA
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setPopulationSize(100);
        ga.setGenerations(300);
        ga.setCrossoverRate(0.9);
        ga.setMutationRate(0.1);
        ga.setSelection(new TournamentSelection(4));
        ga.setCrossover(new TwoPointCrossover());
        ga.setReplacement(new ElitismReplacement(5));
        ga.setMutation(new com.softcomputing.ga.mutation.IntegerSwap(lower, upper));
        ga.setFitnessFunction(new MealPlanFitness(items, 2000, 75));
        ga.setInfeasibleHandler(new MealPlanInfeasibleHandler(items, lower, upper, 2500));
        ga.setInitializer(() -> IntegerChromosome.randomInit(lower, upper, new java.util.Random()));

        Chromosome best = ga.run();
        System.out.println("Best fitness: " + best.getFitness());
        System.out.println("Plan: " + best.asString());
    }
}

