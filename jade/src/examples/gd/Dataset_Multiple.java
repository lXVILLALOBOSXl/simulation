
package gd;

public class Dataset_Multiple {
    
    public Dataset_Multiple() {
    }

    /*
    public double y[] = {251.3,251.3,248.3,267.5,273.0,276.5,270.3,274.9,285.0,290.0,297.0,302.5,304.5,309.3,321.7,330.7,349.0};
    public double x1[] = {41.9,43.4,43.9,44.5,47.3,47.5,47.9,50.2,52.8,53.2,56.7,57.0,63.5,65.3,71.1,77.0,77.8};
    public double x2[] = {29.1,29.3,29.5,29.7,29.9,30.3,30.5,30.7,30.8,30.9,31.5,31.7,31.9,32.0,32.1,32.5,32.9};
    */
    
    /*
    //PRUEBA 2 - guía: https://jretamales.github.io/2018-10-21-introduccion-amable-a-gradiente-descendente/
        double y[] = {-73.43,-72.57,4.77,65.41,-142.68};
        double x1[] = {1.05,-0.36,-0.85,1.33,-0.46};
        double x2[] = {-1.07,-0.63,0.31,0.30,-1.32};
        //double alpha = 0.04;
        //double Precision = 0.01;
    */
        
    //PRUEBA 3 - guía: https://www.chegg.com/homework-help/questions-and-answers/question-1-given-following-data-contain-20-rows-3-columns-xi-x2-y-x1-4-6-8-4-10-1-9-5-2-7--q83333081
    
    
    double x1[] = {1,2,3,4,5,6,7,8,9};//multiple
    double x2[] = {2,4,6,8,10,12,14,16,18};
    double y[] = {101,102,103,104,105,106,107,108,109};
        //double alpha = 0.0005;
        //double Precision = 0.0001;
    
    /*
    //PRUEBA 4 - Mismo error que Hands on 5
    double y[] = {140,155,159,179,192,200,212,215};
    double x1[] = {60,62,67,70,71,72,75,78};
    double x2[] = {22,25,24,20,15,14,14,11};
        //double alpha = 0.0005;
        //double Precision = 0.0001;
    */

    int n = y.length;    

    public int getN() {
        n = y.length;
        return n;
    }


    public double[] getX1() {
        return x1;
    }

    public double[] getX2() {
        return x2;
    }

    public double[] getY() {
        for(int i = 0; i < y.length; i++){
        //System.out.println(y[i]);
        }
        return y;
    }
}
