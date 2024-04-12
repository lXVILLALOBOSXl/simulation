import java.util.Arrays;
import java.util.stream.Collectors;

public class Chromosome {
    private double[] genes;
    private double fitness;

    public Chromosome(double[] genes) {
        this.genes = genes;
    }

    public double calculateFitness() { return 0.0; }

    public void mutate() {}

    public Chromosome clone() { return null; }

    @Override
    public String toString() {
        return Arrays.stream(genes)
                .mapToObj(Double::toString)
                .collect(Collectors.joining(",", "[", "]"));
    }
}
