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
public class Matrixs {

    private double[][] Ds, DB0, DB1, DB2;
    private double Resultado;

    public Matrixs() {
    }

    public Matrixs(double n, double Ex1i, double Ex2i, double Eyi, double Ex1iyi, double Ex2iyi, double Ex1i2, double Ex2i2, double Ex1ix2i) {

        this.Ds = new double[][]{
            {n,    Ex1i,    Ex2i,    n,    Ex1i},
            {Ex1i, Ex1i2,   Ex1ix2i, Ex1i, Ex1i2},
            {Ex2i, Ex1ix2i, Ex2i2,   Ex2i, Ex1ix2i}};

        this.DB0 = new double[][]{
            {Eyi,    Ex1i,    Ex2i,    Eyi,    Ex1i},
            {Ex1iyi, Ex1i2,   Ex1ix2i, Ex1iyi, Ex1i2},
            {Ex2iyi, Ex1ix2i, Ex2i2,   Ex2iyi, Ex1ix2i}};

        this.DB1 = new double[][]{
            {n,    Eyi,    Ex2i,    n,    Eyi},
            {Ex1i, Ex1iyi, Ex1ix2i, Ex1i, Ex1iyi},
            {Ex2i, Ex2iyi, Ex2i2,   Ex2i, Ex2iyi}};

        this.DB2 = new double[][]{
            {n,    Ex1i,    Eyi,     n,    Ex1i},
            {Ex1i, Ex1i2,   Ex1iyi,  Ex1i, Ex1i2},
            {Ex2i, Ex1ix2i, Ex2iyi,  Ex2i, Ex1ix2i}};

        this.Resultado = 0;

    }

    public double getResultado() {
        return Resultado;
    }

    public void setResultado(double Resultado) {
        this.Resultado = Resultado;
    }

    public double[][] getDs() {
        return Ds;
    }

    public void setDs(double[][] Ds) {
        this.Ds = Ds;
    }

    public double[][] getDB0() {
        return DB0;
    }

    public void setDB0(double[][] DB0) {
        this.DB0 = DB0;
    }

    public double[][] getDB1() {
        return DB1;
    }

    public void setDB1(double[][] DB1) {
        this.DB1 = DB1;
    }

    public double[][] getDB2() {
        return DB2;
    }

    public void setDB2(double[][] DB2) {
        this.DB2 = DB2;
    }
    

    public double calcularDT(double[][] p) {

        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {

                Resultado = ((p[0][0] * p[1][1] * p[2][2])
                            + (p[0][1] * p[1][2] * p[2][3])
                            + (p[0][2] * p[1][3] * p[2][4])
                            - (p[2][0] * p[1][1] * p[0][2])
                            - (p[2][1] * p[1][2] * p[0][3])
                            - (p[2][2] * p[1][3] * p[0][4]));

            }

        }

        return Resultado;
    }

}
