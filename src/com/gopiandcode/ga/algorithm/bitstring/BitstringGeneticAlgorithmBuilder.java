package com.gopiandcode.ga.algorithm.bitstring;

import com.gopiandcode.ga.algorithm.BasicGeneticAlgorithm;
import com.gopiandcode.ga.algorithm.RouletteWheelSelectionStrategy;
import com.gopiandcode.ga.algorithm.interfaces.*;

import java.util.Optional;

public class BitstringGeneticAlgorithmBuilder {
    private int size;
    private MutationStrategy<Bitstring> mutationStrategy = new FixedProbabilityBitstringMutationStrategy(mutationProbability);
    private SelectionStrategy<Bitstring> selectionStrategy = new RouletteWheelSelectionStrategy<>();
    private CrossoverStrategy<Bitstring> crossoverStrategy = new SinglePointBitstringCrossoverStrategy();

    // must be provided
    private Optional<GeneratorStrategy<Bitstring>> generatorStrategy = Optional.empty();
    private Optional<EvaluationStrategy<Bitstring>> evaluationStrategy = Optional.empty();

    public BitstringGeneticAlgorithmBuilder(){
    }


    public BitstringGeneticAlgorithmBuilder withMutationStrategy(MutationStrategy<Bitstring> mutationStrategy){
        this.mutationStrategy = mutationStrategy;
        return this;
    }


    public BitstringGeneticAlgorithmBuilder withCrossoverStrategy(CrossoverStrategy<Bitstring> crossoverStrategy){
        this.crossoverStrategy = crossoverStrategy;
        return this;
    }

    public BitstringGeneticAlgorithmBuilder withSelectionStrategy(SelectionStrategy<Bitstring> selectionStrategy) {
        this.selectionStrategy = selectionStrategy;
        return this;
    }

    public BitstringGeneticAlgorithmBuilder withPopulationSize(int size) {
        this.size = size;
        return this;
    }

    public BitstringGeneticAlgorithmBuilder withEvaluator(EvaluationStrategy<Bitstring> evaluationStrategy){
        this.evaluationStrategy = Optional.of(evaluationStrategy);
        return this;
    }

    public BitstringGeneticAlgorithmBuilder withInputSize(int size) {
       this.generatorStrategy = Optional.of(new BitstringGeneratorStrategy(size));
       return this;
    }


    public GeneticAlgorithm<Bitstring> build(){
        if(!this.evaluationStrategy.isPresent()) {
            throw new RuntimeException("No Evaluation Strategy provided.");
        }
        else if(!this.generatorStrategy.isPresent()) {
            throw new RuntimeException("No Generator Strategy provided.");
        }

        return new BasicGeneticAlgorithm<Bitstring>(size, evaluationStrategy.get(), crossoverStrategy, mutationStrategy, generatorStrategy.get(), selectionStrategy);
    }

}
