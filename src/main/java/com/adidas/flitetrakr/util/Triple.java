package com.adidas.flitetrakr.util;

/**
 * Utility class used for creating triplets of elements.
 *
 * @param <F> The first element in the triple.
 * @param <S> The second element in the triple.
 * @param <T> The third element in the triple.
 */
public class Triple<F, S, T> {

    private final F first;
    private final S second;
    private final T third;

    /**
     * Creates a new triple from the specified objects.
     *
     * @param first  the first value in the new Triple
     * @param second the second value in the new Triple
     * @param third  the third value in the new Triple
     * @throws IllegalArgumentException Thrown if either argument is null.
     */
    public Triple(F first, S second, T third) {
        if (first == null || second == null || third == null) {
            throw new IllegalArgumentException("Triple cannot contain null values.");
        }
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public T getThird() {
        return third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;

        if (first != null ? !first.equals(triple.first) : triple.first != null) return false;
        if (second != null ? !second.equals(triple.second) : triple.second != null) return false;
        return third != null ? third.equals(triple.third) : triple.third == null;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (third != null ? third.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "<" + first + ", " + second + ", " + third + '>';
    }
}