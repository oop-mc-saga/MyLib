package myLib.rungeKutta;

import java.awt.geom.Point2D;
import java.util.List;
import myLib.utils.Utils;

/**
 * Runge-Kutta法で連立微分方程式を解く場合の、力学系の抽象クラス
 *
 * @author tadaki
 */
public class Dynamics {

    protected double t;//独立変数
    protected double y[];//従属変数
    protected double yInit[];//従属変数の初期値
    protected DifferentialEquation equation;//連立微分方程式
    protected final int numVar;//従属変数の数

    /**
     * コンストラクタ
     *
     * @param initials 従属変数の初期値
     */
    public Dynamics(double... initials) {
        this.numVar = initials.length;
        y = new double[numVar];
        yInit = new double[numVar];
        for (int i = 0; i < initials.length; i++) {
            y[i] = initials[i];
            yInit[i] = initials[i];
        }
        t = 0.;
    }

    /**
     * 連立微分方程式の定義
     *
     * @param equation
     */
    public void setEquation(DifferentialEquation equation) {
        this.equation = equation;
    }

    /**
     * 独立変数の設定
     *
     * @param t
     */
    public void setT(double t) {
        this.t = t;
    }

    /**
     * 全ての変数を再初期化する
     *
     * @return 初期化された従属変数の値
     */
    public double[] initialize() {
        for (int i = 0; i < numVar; i++) {
            y[i] = yInit[i];
        }
        t = 0;
        return y;
    }

    /**
     * 時間発展：1ステップ
     *
     * @param dt 時間
     * @return 新しい状態での従属変数の値
     */
    public double[] update(double dt) {
        double yy[] = RungeKutta.rk4(t, y, dt, equation);
        for (int i = 0; i < numVar; i++) {
            y[i] = yy[i];
        }
        t += dt;
        return yy;
    }

    /**
     * 時間発展
     *
     * @param h 時間
     * @param nstep 時間ttをnstepに区切って時間発展
     * @return nstepの間の時間発展 (t, y[0])のリスト
     */
    public List<Point2D.Double> evolution(double h, int nstep) {
        return evolution(h, nstep, 0);
    }

    /**
     * 時間発展
     *
     * @param h 時間
     * @param nstep 時間ttをnstepに区切って時間発展
     * @param vNum 表示する変数のインデクス
     * @return nstepの間の時間発展 (t, y[0])のリスト
     */
    public List<Point2D.Double> evolution(double h, int nstep, int vNum) {
        double yy[][] = RungeKutta.rkdumb(y, t, h, nstep, equation);
        List<Point2D.Double> points = Utils.createList();
        double dt = h / nstep;
        for (int i = 0; i < nstep; i++) {
            double tt = i * dt;
            points.add(new Point2D.Double(tt, yy[vNum][i]));
        }
        for (int i = 0; i < numVar; i++) {
            y[i] = yy[i][nstep - 1];
        }
        t += h;
        return points;
    }

    /**
     * 二つの変数の時間発展
     *
     * @param h 時間
     * @param nstep 時間ttをnstepに区切って時間発展
     * @param xNum x軸に相当する変数のインデクス
     * @param yNum y軸に相当する変数のインデクス
     * @return nstepの間の時間発展 (t, y[0])のリスト
     */
    public List<Point2D.Double> evolution(double h, int nstep, int xNum, int yNum) {
        double yy[][] = RungeKutta.rkdumb(y, t, h, nstep, equation);
        List<Point2D.Double> points = Utils.createList();
        double dt = h / nstep;
        for (int i = 0; i < nstep; i++) {
            double tt = i * dt;
            points.add(new Point2D.Double(yy[xNum][i], yy[yNum][i]));
        }
        for (int i = 0; i < numVar; i++) {
            y[i] = yy[i][nstep - 1];
        }
        t += h;
        return points;
    }
}
