
package gd;

public class Maths {
    public double[] sqr(double[] dataset){//X1^2i 
        int n = dataset.length;
        double dataSetSqr[] = new double[n];
        for(int i = 0; i < n; i++){
            dataSetSqr[i] = Math.pow(dataset[i], 2);
            //System.out.println(dataSetSqr[i]);
        }
        return dataSetSqr;
    }

    public double[] product(double[] dataset1, double[] dataset2){
        int n = dataset1.length;
        double[] prod = new double[n];
        for(int i = 0; i < n; i++){
            prod[i] = dataset1[i]*dataset2[i];
            //System.out.println(prod[i]);
        }
        return prod;
    }
    /*
    public double[] product(double[] dataset1, double[][] dataset2){
        int n = dataset1.length;
        double[] prod = new double[n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                prod[i] = dataset1[i]*dataset2[i][j];
            }
            
            //System.out.println(prod[i]);
        }
        return prod;
    }*/

    public double sigma(double[] dataset){//Sumatoria
        int n = dataset.length;
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
        //System.out.println(col_m1);
        //System.out.println(fil_m1);
        //System.out.println(col_m2);
        //System.out.println(fil_m2);
        double[][] matrixResult = new double[fil_m1][col_m2];
        if(col_m1 == fil_m2){
            for(int i = 0; i < fil_m1; i++){
                for(int j = 0; j < col_m2; j++){
                    for(int k = 0; k < col_m1; k++){
                        matrixResult[i][j] += m1[i][k]*m2[k][j];            
                    }
                    System.out.print(matrixResult[i][j]+"\t");
                }
            System.out.println();
            }
            
        }
        return matrixResult;
    }
}
