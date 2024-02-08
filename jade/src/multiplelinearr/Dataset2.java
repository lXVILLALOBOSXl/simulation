package multiplelinearr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JimmyLpz1 zz*/
public class Dataset2 {
    private double[][] origin;

    public Dataset2() {

        origin = new double[][]{
            {41.9, 43.4, 43.9, 44.5, 47.3, 47.5, 47.9, 50.2,
                52.8,53.2,56.7,57,63.5,65.3,71.1,77,77.8},//x1i
            {29.1,29.3,29.5,29.7,29.9,30.3,30.5,30.7,30.8,30.9,31.5,31.7,31.9,32,32.1,32.5,32.9},
		{251.3,251.3,248.3,267.5,273,276.5,270.3,274.9,285,290,297,302.5,304.5,309.3,321.7,330.7,349}};//yi

    }

    public double[][] getOrigin() {
        return origin;
    }

    public void setOrigin(double[][] origin) {
        this.origin = origin;
    }

    public void imprimirDataset(double[][] x) {

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {

                System.out.print("[ " + x[i][j] + "]");

            }
            System.out.println("");
        }

    }
    
}

