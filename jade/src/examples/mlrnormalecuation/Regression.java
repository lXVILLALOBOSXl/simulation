
package mlrnormalecuation;

public interface Regression {
    public double b0(double[][] xTxInvxTy);
    public double b1(double[][] xTxInvxTy);
    public double b2(double[][] xTxInvxTy);
    public double predictiveEcuation(double B0, double B1, double B2, double x1, double x2);
}
