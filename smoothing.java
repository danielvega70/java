import java.util.Arrays;

public class Smoother {
    public static double[] smooth(double[] x, int windowLen, String window) {
        double[] y;

        if (x.length < windowLen) {
            throw new IllegalArgumentException("Input signal size must be at least window size");
        }
        if (windowLen < 3) {
            return x;
        }
        if (x.length != windowLen) {
            throw new IllegalArgumentException("Window length must match signal length");
        }
        if (!Arrays.asList("flat", "hanning", "hamming", "bartlett", "blackman").contains(window)) {
            throw new IllegalArgumentException("Invalid window type");
        }

        // Create the window
        double[] w;
        if (window.equals("flat")) {
            w = new double[windowLen];
            Arrays.fill(w, 1.0);
        } else {
            w = getWindow(windowLen, window);
        }

        // Convolve the signal with the window
        y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            double sum = 0;
            for (int j = 0; j < windowLen; j++) {
                int k = i - windowLen / 2 + j;
                if (k >= 0 && k < x.length) {
                    sum += w[j] * x[k];
                }
            }
            y[i] = sum / sum(w);
        }

        return y;
    }

    private static double[] getWindow(int windowLen, String window) {
        double[] w = new double[windowLen];
        for (int i = 0; i < windowLen; i++) {
            w[i] = getSample(i, windowLen, window);
        }
        return w;
    }

    private static double getSample(int i, int windowLen, String window) {
        switch (window) {
            case "hanning":
                return 0.5 * (1 - Math.cos(2 * Math.PI * i / (windowLen - 1)));
            case "hamming":
                return 0.54 - 0.46 * Math.cos(2 * Math.PI * i / (windowLen - 1));
            case "bartlett":
                return 1 - Math.abs((i - (windowLen - 1) / 2.0) / ((windowLen - 1) / 2.0));
            case "blackman":
                return 0.42 - 0.5 * Math.cos(2 * Math.PI * i / (windowLen - 1))
                        + 0.08 * Math.cos(4 * Math.PI * i / (windowLen - 1));
            default:
                throw new IllegalArgumentException("Invalid window type");
        }
    }

    private static double sum(double[] arr) {
        double sum = 0;
        for (double d : arr) {
            sum += d;
        }
        return sum;
    }
}
