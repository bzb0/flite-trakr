package com.adidas.flitetrakr.util;

/**
 * A pair consisting of two non-null elements.
 *
 * @param <F> The first element of the pair.
 * @param <S> The second element of the pair.
 */
public final class Pair<F, S> {

    private F first;
    private S second;

    /**
     * Creates a Pair from the specified elements.
     *
     * @param first the first value in the new Pair
     * @param second the second value in the new Pair
     * @throws IllegalArgumentException Thrown if either argument is null.
     */
    public Pair(F first, S second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Pair cannot contain null values.");
        }
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        return second != null ? second.equals(pair.second) : pair.second == null;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "<" + first.toString() + ", " + second.toString() + ">";
    }
}