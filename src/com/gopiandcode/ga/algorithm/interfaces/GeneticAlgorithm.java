package com.gopiandcode.ga.algorithm.interfaces;

import java.util.Map;


public interface GeneticAlgorithm<T> {
    Map<T, Double> calculateFitness();

    void runTrainIteration(Map<T, Double> scores);
}
