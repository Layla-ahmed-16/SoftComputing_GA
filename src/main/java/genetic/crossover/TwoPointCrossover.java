package genetic.crossover;

import genetic.chromosome.*;
import java.util.Random;

public class TwoPointCrossover implements CrossoverOperator {
    private final Random rng = new Random();

    @Override
    public Chromosome[] crossover(Chromosome p1, Chromosome p2, double rate) {
        if (rng.nextDouble() > rate) return new Chromosome[] {p1.clone(), p2.clone()};
        int n = p1.length();
        int a = rng.nextInt(n), b = rng.nextInt(n);
        int l = Math.min(a,b), r = Math.max(a,b);
        // reuse one-point logic by swapping segment [l,r)
        Chromosome c1 = p1.clone(), c2 = p2.clone();
        if (c1 instanceof BinaryChromosome){
            BinaryChromosome A=(BinaryChromosome)c1, B=(BinaryChromosome)c2;
            for(int i=l;i<r;i++){ boolean ta=A.get(i); A.set(i,B.get(i)); B.set(i,ta); }
            return new Chromosome[]{A,B};
        }
        if (c1 instanceof IntegerChromosome){
            IntegerChromosome A=(IntegerChromosome)c1, B=(IntegerChromosome)c2;
            for(int i=l;i<r;i++){ int ta=A.get(i); A.set(i,B.get(i)); B.set(i,ta); }
            return new Chromosome[]{A,B};
        }
        if (c1 instanceof FloatChromosome){
            FloatChromosome A=(FloatChromosome)c1, B=(FloatChromosome)c2;
            for(int i=l;i<r;i++){ double ta=A.get(i); A.set(i,B.get(i)); B.set(i,ta); }
            return new Chromosome[]{A,B};
        }
        return new Chromosome[]{p1.clone(), p2.clone()};
    }
}

