package myLib.rungeKutta;

/**
 * 連立微分方程式を定義する関数インターフェイス
 *
 * @author tadaki
 */
@FunctionalInterface
public interface DifferentialEquation {

    /**
     *
     * @param t 独立変数
     * @param y 従属変数
     * @return 微分方程式の右辺の値
     */
    public double[] rhs(double t, double y[]);

}
