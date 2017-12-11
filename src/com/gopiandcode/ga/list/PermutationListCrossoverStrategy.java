package com.gopiandcode.ga.list;

import com.google.common.collect.ImmutableList;
import com.gopiandcode.ga.algorithm.interfaces.CrossoverStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermutationListCrossoverStrategy<T> implements CrossoverStrategy<List<T>> {
    @Override
    public ImmutableList<List<T>> crossover(List<T> parentA, List<T> parentB) {
        Set<T> seenA = new HashSet<>();
        Set<T> seenB = new HashSet<>();

        List<T> childA = new ArrayList<>();
        List<T> childB = new ArrayList<>();

        for (int i = 0; i < parentA.size(); i++) {
            T elementA = parentA.get(i);
            T elementB = parentB.get(i);

            if (!seenA.contains(elementA)) {
                childA.add(elementA);
                seenA.add(elementA);
            }
            if (!seenA.contains(elementB)) {
                childA.add(elementB);
                seenA.add(elementB);
            }


            if (!seenB.contains(elementB)) {
                childB.add(elementB);
                seenB.add(elementB);
            }
            if (!seenB.contains(elementA)) {
                childB.add(elementA);
                seenB.add(elementA);
            }
        }


        return ImmutableList.of(
                childA,
                childB
        );
    }
}
