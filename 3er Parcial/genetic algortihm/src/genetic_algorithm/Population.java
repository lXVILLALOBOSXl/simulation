package genetic_algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Population {
    private List<Chromosome> chromosomes;
    private int size;

    public Population(int size) {
        this.size = size;
    }

    public void initialize() {
        this.chromosomes = new ArrayList<>(this.size);
    }

    public void addChromosome(Chromosome chromosome){
        this.chromosomes.add(chromosome);
    }

    public Chromosome getBest() { return null; }

    public int getSize() {
        return size;
    }

    public Chromosome getChromosome(int index) { return this.chromosomes.get(index); }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    @Override
    public String toString() {
        return chromosomes.stream()
                .map(Chromosome::toString)
                .collect(Collectors.joining(",\n"));
    }
}
