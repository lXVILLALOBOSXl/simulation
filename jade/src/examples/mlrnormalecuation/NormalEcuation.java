
package mlrnormalecuation;

import java.text.DecimalFormat;


public class NormalEcuation {
    public double[][] xTx, xTxInv, xTy;
    
    DecimalFormat df = new DecimalFormat("####0.00");

    public double[][] getxTx() {
        return xTx;
    }

    public void setxTx(double[][] xTx) {
        this.xTx = xTx;
    }

    public double[][] getxTxInv() {
        return xTxInv;
    }

    public void setxTxInv(double[][] xTxInv) {
        this.xTxInv = xTxInv;
    }

    public double[][] getxTy() {
        return xTy;
    }

    public void setxTy(double[][] xTy) {
        this.xTy = xTy;
    }
    
    public double[][] matrixInv(double[][] m) {//Inversa de una matriz
    double det=1/determinant(m);
    double[][] nmatrix=matrixAdj(m);
    multMatrix(det,nmatrix);
    for(int i = 0; i < nmatrix[0].length; i++){
        for(int j =0; j < nmatrix.length; j++){
            //System.out.print(decimalFormat((nmatrix[i][j]),2)+"\t");
        }
            System.out.println();
    }
    
    return nmatrix;
}
 
public void multMatrix(double n, double[][] m) {//Multiplicacion de una matriz
    for(int i=0;i<m.length;i++)
        for(int j=0;j<m.length;j++)
            m[i][j]*=n;
}
 
public  double[][] matrixAdj(double [][] m){//Adjunta
    return matrixTr(matrixCof(m));
}
 
public double[][] matrixCof(double[][] m){//Cofactores
    double[][] nm = new double[m.length][m.length];
    for(int i = 0; i < m.length;i++) {
        for(int j = 0; j < m.length;j++) {
            double[][] det = new double[m.length-1][m.length-1];
            double detValue;
            for(int k = 0; k < m.length;k++) {
                if(k!=i) {
                    for(int l = 0; l < m.length;l++) {
                        if(l!=j) {
                        int ind1=k<i ? k : k-1 ;
                        int ind2=l<j ? l : l-1 ;
                        det[ind1][ind2]=m[k][l];
                        }
                    }
                }
            }
            detValue=determinant(det);
            nm[i][j]=detValue * (double)Math.pow(-1, i+j+2);
        }
    }
    return nm;
}
 
public double[][] matrixTr(double [][] m){//Transpuesta
    double[][]newm = new double[m[0].length][m.length];
    for(int i = 0; i < m.length; i++)
    {
        for(int j = 0; j < m.length; j++)
            newm[i][j]=m[j][i];
    }
    return newm;
}
 
public double determinant(double[][] m)
{
    double det;
    if(m.length == 2){
        det=(m[0][0]*m[1][1])-(m[1][0]*m[0][1]);
        return det;
    }
    double addition=0;
    for(int i = 0; i < m.length; i++){
    double[][] nm = new double[m.length-1][m.length-1];
        for(int j = 0; j < m.length; j++){
            if(j != i){
                for(int k = 1; k < m.length; k++){
                int ind =-1;
                if(j < i)
                ind = j;
                else if(j > i)
                ind = j-1;
                nm[ind][k-1] = m[j][k];
                }
            }
        }
        if(i%2==0)
        addition+=m[i][0] * determinant(nm);
        else
        addition-=m[i][0] * determinant(nm);
    }
    return addition;
}
public static Double decimalFormat(Double num, Integer numDec) {
    return Math.round(num * Math.pow(10, numDec)) / Math.pow(10, numDec);
}

}
