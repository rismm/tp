package supertracker.util;

/**
 * A generic class representing a triple of three elements.
 */
public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;

    /**
     * Constructs a new Triple object with the specified elements.
     *
     * @param first  First element.
     * @param second Second element.
     * @param third  Third element.
     */
    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Gets the first element of the triple.
     *
     * @return First element.
     */
    public A getFirst() {
        return first;
    }

    /**
     * Gets the second element of the triple.
     *
     * @return Second element.
     */
    public B getSecond() {
        return second;
    }

    /**
     * Gets the third element of the triple.
     *
     * @return Third element.
     */
    public C getThird() {
        return third;
    }
}
