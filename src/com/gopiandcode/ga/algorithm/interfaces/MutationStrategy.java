package com.gopiandcode.ga.algorithm.interfaces;

public interface MutationStrategy<T> {
    public T mutate(T individual);
}
