package genetic.examples;

import genetic.api.FitnessFunction;

public class MealPlanFitness implements FitnessFunction {
    @Override
    public double evaluate(Chromosome individual) {
        // Convert chromosome genes to meal items
        // Calculate total calories, cost, and nutrition score
        double calories = individual.getGenes().stream()
                .mapToDouble(Gene::getCalories)
                .sum();
        double cost = individual.getGenes().stream()
                .mapToDouble(Gene::getCost)
                .sum();
        double nutritionScore = individual.getGenes().stream()
                .mapToDouble(Gene::getNutrition)
                .sum();

        // Define target constraints
        double calorieTarget = 2000;
        double costLimit = 50.0;

        // Penalize deviation from ideal targets
        double fitness = (nutritionScore * 10)
                - Math.abs(calories - calorieTarget)
                - (cost > costLimit ? (cost - costLimit) * 5 : 0);

        return Math.max(fitness, 0.0);
    }
}

