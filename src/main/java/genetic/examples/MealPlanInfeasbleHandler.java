package genetic.examples;
import genetic.api.InfeasibleHandler;
public class MealPlanInfeasibleHandler implements InfeasibleHandler {
    @Override
    public boolean isFeasible(Chromosome individual) {
        double totalCalories = individual.getGenes().stream()
                .mapToDouble(Gene::getCalories)
                .sum();
        double cost = individual.getGenes().stream()
                .mapToDouble(Gene::getCost)
                .sum();

        // Feasible if within calorie and cost constraints
        return totalCalories <= 2200 && cost <= 60.0;
    }

    @Override
    public Chromosome repair(Chromosome infeasibleIndividual) {
        List<Gene> genes = new ArrayList<>(infeasibleIndividual.getGenes());
        // Try to remove or replace high-calorie or expensive genes
        genes.sort(Comparator.comparingDouble(g -> g.getCalories() + g.getCost()));

        while (!isFeasible(new Chromosome(genes))) {
            if (genes.size() > 1) genes.remove(genes.size() - 1);
            else return null; // cannot repair
        }

        return new Chromosome(genes);
    }
}

