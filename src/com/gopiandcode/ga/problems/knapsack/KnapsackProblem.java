package com.gopiandcode.ga.problems.knapsack;

import com.gopiandcode.ga.GeneticAlgorithmRunner;
import com.gopiandcode.ga.algorithm.interfaces.GeneticAlgorithm;
import com.gopiandcode.ga.list.GaussianConvolutionListMutationStrategy;
import com.gopiandcode.ga.list.ListGeneratorStrategy;
import com.gopiandcode.ga.list.ListGeneticAlgorithmBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class KnapsackProblem {
    public final int maximumCapacity;
    public final ArrayList<Item> items;

    private static class Item {
        private final int benefit;
        private final int volume;

        private Item(int benefit, int volume) {
            this.benefit = benefit;
            this.volume = volume;
        }
    }

    public static class Solution {

        private final ArrayList<Integer> count;

        public Solution(ArrayList<Integer> count) {
            this.count = count;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Solution{" +
                    "count=" + count +
                    '}');
            for (Integer i : count) {
                builder.append(i + ",");
            }
            return builder.toString();
        }

    }


    public KnapsackProblem(int maximumCapacity, ArrayList<Item> items) {
        this.maximumCapacity = maximumCapacity;
        this.items = items;
    }


    public Optional<Integer> score(Solution solution) {
        if (solution.count.size() != items.size()) throw new RuntimeException("Solution has more entries than items in this problem instance");

        int volume = 0;
        int benefit = 0;

        for (int i = 0; i < items.size(); i++) {
            volume += solution.count.get(i) * items.get(i).volume;
            benefit += solution.count.get(i) * items.get(i).benefit;
            if (volume >= maximumCapacity) return Optional.empty();
        }

        return Optional.of(benefit);
    }


    public static void main(String[] args) {
        // Example instance of problem
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(4, 6));
        items.add(new Item(3, 7));
        items.add(new Item(5, 8));
        KnapsackProblem problem = new KnapsackProblem(13, items);


        GaussianConvolutionListMutationStrategy<Integer> mutationStrategy = new GaussianConvolutionListMutationStrategy<>(0.1, 0, 50, 2.5, Double::intValue);
        ListGeneratorStrategy<Integer> generatorStrategy = new ListGeneratorStrategy<>(3, () -> Math.max(Math.min(ThreadLocalRandom.current().nextInt(0, 20), 20), 0));

        GeneticAlgorithm<List<Integer>> geneticAlgorithm = new ListGeneticAlgorithmBuilder<Integer>()
                .withMutationStrategy(mutationStrategy)
                .withGeneratorStrategy(generatorStrategy)
                .withEvaluationStrategy(individual -> {
                    Solution solution = new Solution(new ArrayList<>(individual));
                    return problem.score(solution).orElse(0).doubleValue();
                })
                .build();

        GeneticAlgorithmRunner<List<Integer>> geneticAlgorithmRunner = new GeneticAlgorithmRunner<>(geneticAlgorithm, (level, message) -> System.out.println(message));

        geneticAlgorithmRunner.runTestIteration(1000);
        System.out.println(geneticAlgorithmRunner.findBestIndividual());
    }
}
