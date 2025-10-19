package com.example.signalviewer;

public class FFT {
    private static final Complex ZERO = new Complex(0, 0);

    /**
     * Do not instantiate.
     */
    private FFT() {
    }
    public static boolean isPowerOfTwo(int n)
    {
        return (int)(Math.ceil((Math.log(n) / Math.log(2))))
                == (int)(Math.floor(((Math.log(n) / Math.log(2)))));
    }

    public static int nextPowerOf2(int a)
    {
        int b = 1;
        while (b < a) {
            b = b << 1;
        }
        return b;
    }

    /**
     * Returns the FFT of the specified complex array.
     *
     * @param x the complex array
     * @return the FFT of the complex array {@code x}
     * @throws IllegalArgumentException if the length of {@code x} is not a power of 2
     */
    public static Complex[] fft(Complex[] x) {
        int n = x.length;

        // base case
        if (n == 1) {
            return new Complex[]{x[0]};
        }

        // radix 2 Cooley-Tukey FFT
        if (!isPowerOfTwo(n)) {
            throw new IllegalArgumentException("n is not a power of 2");
        }

        // fft of even terms
        Complex[] even = new Complex[n / 2];
        for (int k = 0; k < n / 2; k++) {
            even[k] = x[2 * k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd = even;  // reuse the array
        for (int k = 0; k < n / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[n];
        for (int i=0; i<n; i++) { // к сожалению, приходится ещё выделять память под каждый объект
            y[i]=new Complex();
        }
        for (int k = 0; k < n / 2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complex wk = new Complex((float)Math.cos(kth), (float)Math.sin(kth));
            wk.times(r[k]);
            y[k].plus(q[k], wk);
            y[k + n / 2].minus(q[k], wk);
        }
        return y;
    }

}