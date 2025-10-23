package genetic.crossover;

import genetic.chromosome.*;
import java.util.Random;

public class OnePointCrossover implements CrossoverOperator {
    private final Random rng = new Random();

    @Override
    public Chromosome[] crossover(Chromosome p1, Chromosome p2, double rate) {
        if (rng.nextDouble() > rate) return new Chromosome[] {p1.clone(), p2.clone()};
        if (p1 instanceof BinaryChromosome && p2 instanceof BinaryChromosome) {
            BinaryChromosome a=(BinaryChromosome)p1.clone(), b=(BinaryChromosome)p2.clone();
            int n = a.length();
            int cut = 1 + rng.nextInt(n-1);
            for (int i=cut;i<n;i++){
                boolean ta = a.get(i);
                a.set(i, b.get(i));
                b.set(i, ta);
            }
            a.markUnevaluated(); b.markUnevaluated();
            return new Chromosome[]{a,b};
        }
        if (p1 instanceof IntegerChromosome && p2 instanceof IntegerChromosome){
            IntegerChromosome a=(IntegerChromosome)p1.clone(), b=(IntegerChromosome)p2.clone();
            int n = a.length();
            int cut = 1 + rng.nextInt(n-1);
            for(int i=cut;i<n;i++){
                int ta=a.get(i); a.set(i, b.get(i)); b.set(i, ta);
            }
            return new Chromosome[]{a,b};
        }
        if (p1 instanceof FloatChromosome && p2 instanceof FloatChromosome){
            FloatChromosome a=(FloatChromosome)p1.clone(), b=(FloatChromosome)p2.clone();
            int n = a.length();
            int cut = 1 + rng.nextInt(n-1);
            for(int i=cut;i<n;i++){
                double ta=a.get(i); a.set(i, b.get(i)); b.set(i, ta);
            }
            return new Chromosome[]{a,b};
        }
        return new Chromosome[]{p1.clone(), p2.clone()};
    }
}

