
package mlrnormalecuation;

import java.text.DecimalFormat;

public class Maths {
    DecimalFormat df = new DecimalFormat("####0.00");
    public double[] sqr(double[] dataset, int n){//X1^2i 
        double dataSetSqr[] = new double[n];
        for(int i = 0; i < n; i++){
            dataSetSqr[i] = Math.pow(dataset[i], 2);
            //System.out.println(dataSetSqr[i]);
        }
        return dataSetSqr;
    }

    public double[] product(double[] dataset1, double[] dataset2, int n){
        double[] prod = new double[n];
        for(int i = 0; i < n; i++){
            prod[i] = dataset1[i]*dataset2[i];
            //System.out.println(prod[i]);
        }
        return prod;
    }

    public double sigma(double[] dataset, int n){//Sumatoria
        double zigma = 0;
        for(int i = 0; i < n; i++){
            zigma += dataset[i]; 
        }
        return zigma;
    }
    
    public double[][] matrixMult(double[][] m1, double[][] m2){
        
        //Comprobar que sean multiplicables
        /*
        System.out.println(m1[0].length);//cantidad de datos por renglon
        System.out.println(m1.length);//cantidad de datos por columna
        System.out.println(m2[0].length);//cantidad de datos por renglon
        System.out.println(m2.length);//cantidad de datos por columna
        */
        int fil_m1 = m1.length;//columnas
        int col_m1 = m1[0].length;//renglones
        
        int fil_m2 = m2.length;
        int col_m2 = m2[0].length;
        double[][] matrixResult = new double[fil_m1][col_m2];
        if(col_m1 == fil_m2){
            for(int i = 0; i < fil_m1; i++){
                for(int j = 0; j < col_m2; j++){
                    for(int k = 0; k < col_m1; k++){
                        matrixResult[i][j] += m1[i][k]*m2[k][j];            
                    }
                    //System.out.print((decimalFormat(matrixResult[i][j], 2))+"\t");
                }
            System.out.println();
            }
            
        }
        return matrixResult;
    }
    public double[][] matrixT(double[][] m){   
        double[][] trans = new double[m[0].length][m.length];
        for(int i = 0; i < m[i].length; i++){ //m.length = renglones, m[i].length = columnas
            for(int j = 0; j < m.length; j++){
                //System.out.print(formatearDecimales(m[j][i],2)+" \t");
                trans[i][j]= m[j][i];
            }
            System.out.println();
        } 
        return trans;
    }
       
    public void readMatrixMult(double[][] m1,double[][] m2){
        double[][] resultado = matrixMult(m1,m2);
        for(double[] fila: resultado){
            for(double columna: fila){
                System.out.print(columna + " ");
            }
            System.out.println("");
        }
    }
    public void readMatrix(double[][] m){
        //System.out.println(m.length);
        //System.out.println(m[0].length);
        for(int i = 0; i < m.length; i++){ //m.length = renglones, m[i].length = columnas
            for(int j = 0; j < m[i].length; j++){
                System.out.print(m[i][j]+" \t");
            }
            System.out.println();
        }     
    }
    public static Double decimalFormat(Double num, Integer numDec) {
    return Math.round(num * Math.pow(10, numDec)) / Math.pow(10, numDec);
}

}
