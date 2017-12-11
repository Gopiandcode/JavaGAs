package com.gopiandcode.ga.problems.binpacking;

import com.google.common.collect.ImmutableList;
import com.gopiandcode.ga.GeneticAlgorithmRunner;
import com.gopiandcode.ga.algorithm.interfaces.GeneticAlgorithm;
import com.gopiandcode.ga.list.ListGeneticAlgorithmBuilder;
import com.gopiandcode.ga.list.PermutationListCrossoverStrategy;
import com.gopiandcode.ga.list.PermutationListGeneratorStrategy;
import com.gopiandcode.ga.list.PermutationListMutationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BinPackingProblem {
    private final int bucketCapcacity;
    private final List<Integer> items;

    private static class Solution {
        private final List<Integer> permutation;

        private Solution(List<Integer> permutation) {
            this.permutation = permutation;
        }

        public List<Integer> getPermutation() {
            return permutation;
        }
    }

    public BinPackingProblem(int bucketCapcacity, List<Integer> items) {
        this.bucketCapcacity = bucketCapcacity;
        this.items = items;
    }


    public Optional<Double> score(Solution solution) {
        ArrayList<Integer> bins = new ArrayList<>();
        bins.add(bucketCapcacity);
        int maxSeen = 1;

        for (Integer index : solution.getPermutation()) {
            Integer item = items.get(index);
            boolean inserted = false;

            for (int i = 0; i < maxSeen; i++) {
                Integer remainingCapacity = bins.get(i);
                if (remainingCapacity >= item) {
                    bins.set(i, remainingCapacity - item);
                    inserted = true;
                    break;
                }
            }

            if (!inserted) {
                maxSeen += 1;
                bins.add(bucketCapcacity - item);
            }
        }
        // larger seen get smaller scores
        return Optional.of((double) 1 / (double) maxSeen);
    }


    public static void main(String[] args) {
        BinPackingProblem problem = new BinPackingProblem(13, ImmutableList.of(4, 5, 6, 3, 9, 2));
        GeneticAlgorithm<List<Integer>> algorithm = new ListGeneticAlgorithmBuilder<Integer>()
                .withCrossoverStrategy(new PermutationListCrossoverStrategy<>())
                .withGeneratorStrategy(new PermutationListGeneratorStrategy<>(ImmutableList.of(0, 1, 2, 3, 4, 5)))
                .withMutationStrategy(new PermutationListMutationStrategy<>(0.4, 1))
                .withEvaluationStrategy(individual -> {
                    Solution solution = new Solution(individual);
                    return problem.score(solution).orElse(0.0);
                }).build();

        GeneticAlgorithmRunner<List<Integer>> runner = new GeneticAlgorithmRunner<>(algorithm, (level, message) -> System.out.println(message));

        runner.runTestIteration(100);
        System.out.println(runner.findBestIndividual());
        System.out.println(problem.score(new Solution(runner.findBestIndividual().get())));
    }
}
