package genetic.crossover;

import genetic.chromosome.*;
import java.util.Random;

public class UniformCrossover implements CrossoverOperator {
    private final Random rng = new Random();
    private final double swapProb;

    public UniformCrossover(double swapProb) { this.swapProb = swapProb; }

    @Override
    public Chromosome[] crossover(Chromosome p1, Chromosome p2, double rate) {
        if (rng.nextDouble() > rate) return new Chromosome[] {p1.clone(), p2.clone()};
        Chromosome A = p1.clone(), B = p2.clone();
        int n = A.length();
        if (A instanceof BinaryChromosome) {
            BinaryChromosome a=(BinaryChromosome)A, b=(BinaryChromosome)B;
            for(int i=0;i<n;i++) if (rng.nextDouble()<swapProb) {
                boolean ta=a.get(i); a.set(i,b.get(i)); b.set(i,ta);
            }
            return new Chromosome[]{a,b};
        }
        if (A instanceof IntegerChromosome){
            IntegerChromosome a=(IntegerChromosome)A, b=(IntegerChromosome)B;
            for(int i=0;i<n;i++) if (rng.nextDouble()<swapProb){
                int ta=a.get(i); a.set(i,b.get(i)); b.set(i,ta);
            }
            return new Chromosome[]{a,b};
        }
        if (A instanceof FloatChromosome){
            FloatChromosome a=(FloatChromosome)A, b=(FloatChromosome)B;
            for(int i=0;i<n;i++) if (rng.nextDouble()<swapProb){
                double ta=a.get(i); a.set(i,b.get(i)); b.set(i,ta);
            }
            return new Chromosome[]{a,b};
        }
        return new Chromosome[]{p1.clone(), p2.clone()};
    }
}

