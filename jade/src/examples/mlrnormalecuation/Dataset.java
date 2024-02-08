
package mlrnormalecuation;

public class Dataset {
/*    
            double[][] x = {
            {1,41.9,29.1},
            {1,43.4,29.3},
            {1,43.9,29.5},
            {1,44.5,29.7},
            {1,47.3,29.9},
            {1,47.5,30.3},
            {1,47.9,30.5},
            {1,50.2,30.7},
            {1,52.8,30.8},
            {1,53.2,30.9},
            {1,56.7,31.5},
            {1,57,31.7},
            {1,63.5,31.9},
            {1,65.3,32},
            {1,71.1,32.1},
            {1,77,32.5},
            {1,77.8,32.9}           
        };
*/
    double[][] x = {
            {1,1,2},
            {1,2,4},
            {1,3,6},
            {1,4,8},
            {1,5,10},
            {1,6,12},
            {1,7,14},
            {1,8,16},
            {1,9,18}
        };
        double[][] y = {
            {101},
            {102},
            {103},
            {104},
            {105},
            {106},
            {107},
            {108},
            {109},
        };
    int n = y.length;

    public int getN() {
        n = y.length;
        return n;
    }

    public Dataset(double[][] yi, double[][] xi) {//MLR case
        y = yi;
        x = xi;
    } 
    public Dataset() {
    }

    public double[][] getX() {
        return x;
    }


    public double[][] getY() {
        for(int i = 0; i < y.length; i++){
        //System.out.println(y[i]);
        }
        return y;
    }
}
