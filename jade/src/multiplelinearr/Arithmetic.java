package multiplelinearr;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JimmyLpz
 */
public class Arithmetic {
    
     private double Ex1i,Ex2i,Eyi,Ex1iyi,Ex2iyi, Ex1i2,Ex2i2,Ex1ix2i, n,resultado;

    public Arithmetic() {
        this.resultado=0;
        this.n = 0;
        this.Ex1i = 0;
        this.Ex2i=0;
        this.Eyi = 0;
        this.Ex1iyi = 0;
        this.Ex2iyi=0;
        this.Ex1i2 = 0;
        this.Ex2i2 = 0;
        this.Ex1ix2i = 0;
        
    }

    public double getEx1i() {
        return Ex1i;
    }

    public void setEx1i(double Ex1i) {
        this.Ex1i = Ex1i;
    }

    public double getEx2i() {
        return Ex2i;
    }

    public void setEx2i(double Ex2i) {
        this.Ex2i = Ex2i;
    }

    public double getEyi() {
        return Eyi;
    }

    public void setEyi(double Eyi) {
        this.Eyi = Eyi;
    }

    public double getEx1iyi() {
        return Ex1iyi;
    }

    public void setEx1iyi(double Ex1iyi) {
        this.Ex1iyi = Ex1iyi;
    }

    public double getEx2iyi() {
        return Ex2iyi;
    }

    public void setEx2iyi(double Ex2iyi) {
        this.Ex2iyi = Ex2iyi;
    }

    public double getEx1i2() {
        return Ex1i2;
    }

    public void setEx1i2(double Ex1i2) {
        this.Ex1i2 = Ex1i2;
    }

    public double getEx2i2() {
        return Ex2i2;
    }

    public void setEx2i2(double Ex2i2) {
        this.Ex2i2 = Ex2i2;
    }

    public double getEx1ix2i() {
        return Ex1ix2i;
    }

    public void setEx1ix2i(double Ex1ix2i) {
        this.Ex1ix2i = Ex1ix2i;
    }

    public double getN() {
        
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }
    
    public double zigma(double[][] x, int p) {
        double l, punto;
        resultado = 0;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {

                switch (p) {
                    case 1: //x1i
                        if (i == 0) {
                            l = x[i][j];
                            resultado = resultado + l;
                        }
                        break;
                    case 2://x2i
                        if (i == 1) { //x2i
                            l = x[i][j];
                            resultado = resultado + l;
                        }
                        break;
                    case 3://yi
                        if (i == 2) {
                            l = x[i][j];
                            resultado = resultado + l;
                        }
                        break;
                    case 4://x1iyi
                        if (i == 0) {
                            punto = x[0][j] * x[2][j];
                            l = punto;
                            resultado = resultado + l;

                        }
                        break;
                    case 5://x2iyi

                        if (i == 1) {
                            punto = x[1][j] * x[2][j];
                            l = punto;
                            resultado = resultado + l;

                        }
                        break;
                    case 6://x1i2
                        if (i == 0) {
                            punto = x[i][j] * x[i][j];
                            l = punto;
                            resultado = resultado + l;

                        }
                        break;
                    case 7://x2i2
                        if (i == 1) {
                            punto = x[i][j] * x[i][j];
                            l = punto;
                            resultado = resultado + l;

                        }
                        break;
                    case 8://x1ix2i

                        if (i == 0) {
                            punto = x[0][j] * x[1][j];
                            l = punto;
                            resultado = resultado + l;

                        }
                        break;
                    case 9:
                        resultado = j + 1;
                        break;

                }

            }

        }
        return resultado;
    }

    
    
}
