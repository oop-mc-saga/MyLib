package myLib.rungeKutta;

/**
 * 4次のRunge-Kutta法
 *
 * @author tadaki
 */
public class RungeKutta {

    /**
     * One step from x to x + h
     *
     * @param x initial value of independent valiable
     * @param y initial values of dependent valiables
     * @param h step
     * @param eq class contains differential equations
     * @return next values of dependent valiables
     */
    public static double[] rk4(
            double x, double y[], double h, DifferentialEquation eq) {
        int n = y.length;
        double hh = h / 2.;
        double h6 = h / 6.;
        double k1[] = eq.rhs(x, y);
        double xh = x + hh;
        double yt[] = new double[n];
        for (int i = 0; i < n; i++) {
            yt[i] = y[i] + hh * k1[i];
        }
        double k2[] = eq.rhs(xh, yt);
        for (int i = 0; i < n; i++) {
            yt[i] = y[i] + hh * k2[i];
        }
        double k3[] = eq.rhs(xh, yt);
        for (int i = 0; i < n; i++) {
            yt[i] = y[i] + h * k3[i];
        }
        double k4[] = eq.rhs(x + h, yt);
        double yy[] = new double[n];
        for (int i = 0; i < n; i++) {
            yy[i] = y[i] + h6 * (k1[i] + 2. * k2[i] + 2. * k3[i] + k4[i]);
        }
        return yy;
    }

    /**
     * solve by rk4 from x1 to x2 with nstep
     *
     * @param vstart start values of dependent valiables
     * @param x1 initial value of independent valiable
     * @param x2 final value of independent valiable
     * @param nstep the number of steps between x1 and x3
     * @param eq class contains differential equations
     * @return sequence of values of dependent valiables
     */
    public static double[][] rkdumb(
            double vstart[], double x1, double x2, int nstep,
            DifferentialEquation eq) {
        int n = vstart.length;
        double y[][] = new double[n][nstep];
        double v[] = new double[n];
        for (int i = 0; i < n; i++) {
            v[i] = vstart[i];
            y[i][0] = v[i];
        }

        double x = x1;
        double h = (x2 - x1) / nstep;
        for (int t = 1; t < nstep; t++) {
            double vout[] = rk4(x, v, h, eq);
            if ((double) (x + h) == x) {
                System.err.println("too small step");
            }
            x += h;
            for (int i = 0; i < n; i++) {
                v[i] = vout[i];
                y[i][t] = v[i];
            }
        }
        return y;
    }
}
