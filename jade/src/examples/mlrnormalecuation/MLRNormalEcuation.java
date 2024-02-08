
package mlrnormalecuation;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import java.util.Scanner;

public class MLRNormalEcuation extends Agent implements Regression{
    
    Maths maths = new Maths();
    NormalEcuation normalEcuation = new NormalEcuation();
    Dataset ds = new Dataset();
    Scanner sc = new Scanner(System.in);
    
    public void setup(){
        System.out.println("Agent "+getLocalName()+" started.");
        addBehaviour(new MultipleLinearRegression());
    }

    @Override
    public double b0(double[][] xTxInvxTy) {
        double B0;
        B0 = xTxInvxTy[0][0];
        System.out.println("B0: "+B0);
        return B0;   
    }
    @Override
    public double b1(double[][] xTxInvxTy) {
        double B1;
        B1 = xTxInvxTy[1][0];
        System.out.println("B1: "+B1);
        return B1;
    }

    @Override
    public double b2(double[][] xTxInvxTy) {
        double B2;
        B2 = xTxInvxTy[2][0];  
        System.out.println("B2: "+B2);
        return B2;
    }
    @Override
    public double predictiveEcuation(double B0, double B1, double B2, double x1, double x2) {
        double PR;
        PR = B0+B1*x1+B2*x2;
        System.out.println("x1: "+x1+", x2: "+x2+", then y = "+PR);
        return PR;    }   
    
     private class MultipleLinearRegression extends Behaviour{
        int cont = 0;

        @Override
        public void action() {
            double B0,B1,B2,x1,x2;
            normalEcuation.setxTx(maths.matrixMult(maths.matrixT(ds.getX()), ds.getX()));
            normalEcuation.setxTxInv(normalEcuation.matrixInv(normalEcuation.getxTx()));
            normalEcuation.setxTy(maths.matrixMult(maths.matrixT(ds.getX()), ds.getY()));
            B0 = b0(maths.matrixMult(normalEcuation.getxTxInv(),normalEcuation.getxTy()));
            B1 = b1(maths.matrixMult(normalEcuation.getxTxInv(),normalEcuation.getxTy()));
            B2 = b2(maths.matrixMult(normalEcuation.getxTxInv(),normalEcuation.getxTy()));
            x1 = sc.nextDouble();
            x2 = sc.nextDouble();
            predictiveEcuation(B0, B1, B2, x1, x2);
            cont+=1;         
        }

        @Override
        public boolean done() {
            if(cont == 1){
                return true;
            }else{
                return false;
            }
        }
        @Override
        public int onEnd() {
            //myAgent.doDelete();
            return super.onEnd();
        }
    }

    
}
