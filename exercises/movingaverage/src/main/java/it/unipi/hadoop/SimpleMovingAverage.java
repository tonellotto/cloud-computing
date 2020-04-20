package it.unipi.hadoop;

/**
 * Simple moving average by using an array data structure.
 */
public class SimpleMovingAverage
{
    private double sum;
    private final int N;
    private double[] window;
    private int pos;
    private int size;

    public SimpleMovingAverage(final int N)
    {
        if (N < 1)
           throw new IllegalArgumentException("N must be > 0");
        this.N = N;
        this.window = new double[N];

        this.pos  = 0;
        this.size = 0;
        this.sum  = 0.0d;
    }

    public void add(final double a)
    {
        sum += a;
        if (size < N) {
            size++;
        } else {
            pos = pos % N;
            sum -= window[pos];
        }
        window[pos++] = a;
    }

    public double get()
    {
        if (size == 0)
            throw new IllegalArgumentException("average is undefined");
        return sum / size;
    }

    public static void main(String[] args)
    {
        // time series        1   2   3  4   5   6   7
        double[] testData = {10, 18, 20, 30, 24, 33, 27};
        int[] allWindowSizes = {3, 4};
        for (int windowSize : allWindowSizes) {
            SimpleMovingAverage sma = new SimpleMovingAverage(windowSize);
            System.out.println("windowSize = " + windowSize);
            for (double x : testData) {
                sma.add(x);
                System.out.println("Next number = " + x + ", SMA = " + sma.get());
            }
        }
    }
}