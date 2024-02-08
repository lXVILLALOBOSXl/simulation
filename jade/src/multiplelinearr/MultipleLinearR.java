package multiplelinearr;


import java.util.Scanner;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author JimmyLpz
 */
public class MultipleLinearR extends Agent implements Regresion2 {

    public void setup() {
        System.out.println("Hola Mundo Soy el Agente" + getLocalName()+"started");
        addBehaviour(new MyGenericBehaviour());
    }

    private class MyGenericBehaviour extends Behaviour {

        private int cont = 0;

        @Override
        public void action() {
            
           
              

                   
                    
                    
                     Scanner almacen = new Scanner(System.in);

            double[][] x, DS, DB0, DB1, DB2;
            double v1, v2, v3, v4, v5, v6, v7, v8, v9;
            double n1 = 0, n2 = 0;

            Regresion2 aa;
            aa = new MultipleLinearR();
            Dataset2 objData = new Dataset2();
            x = objData.getOrigin();
            Arithmetic objOperation = new Arithmetic();

            v1 = objOperation.zigma(x, 9);
            v2 = objOperation.zigma(x, 1);
            v3 = objOperation.zigma(x, 2);
            v4 = objOperation.zigma(x, 3);
            v5 = objOperation.zigma(x, 4);
            v6 = objOperation.zigma(x, 5);
            v7 = objOperation.zigma(x, 6);
            v8 = objOperation.zigma(x, 7);
            v9 = objOperation.zigma(x, 8);

            Matrixs objMatrix = new Matrixs(v1, v2, v3, v4, v5, v6, v7, v8, v9);

            DS = objMatrix.getDs();
            DB0 = objMatrix.getDB0();
            DB1 = objMatrix.getDB1();
            DB2 = objMatrix.getDB2();

            aa.Results(objMatrix.calcularDT(DS), objMatrix.calcularDT(DB0),
                    objMatrix.calcularDT(DB1), objMatrix.calcularDT(DB2));

            System.out.println("Prediccion");
            System.out.println("inserte el primer valor para predecir");
            n1 = almacen.nextDouble();
            System.out.println("inserte el segundo valor para predecir");
            n2 = almacen.nextDouble();

            System.out.println("Resultado: "
                    + aa.Prediction(objMatrix.calcularDT(DS),
                            objMatrix.calcularDT(DB0), objMatrix.calcularDT(DB1),
                            objMatrix.calcularDT(DB2), n1, n2));

            cont ++;
                   // fin = true;
                
            
           
        }

        @Override
        public boolean done() {
           return cont==1;
            
        }

        @Override
        public int onEnd() {
            myAgent.doDelete();
            System.out.println("agent dead");
            return super.onEnd();
        }

    }

    @Override
    public double Prediction(double RDS, double RB0, double RB1, double RB2, double n1, double n2) {
        double B0 = RB0 / RDS;
        double B1 = RB1 / RDS;
        double B2 = RB2 / RDS;
        System.out.println("B0: " + B0);
        System.out.println("B1: " + B1);
        System.out.println("B2: " + B2);
        double prediction = B0 + (B1 * n1) + (B2 * n2);

        return prediction;
    }

    @Override
    public void Results(double RDS, double RB0, double RB1, double RB2) {
        System.out.println("Determinante del sistema: " + RDS);
        System.out.println("Determinante de B0: " + RB0);
        System.out.println("Determinante de B1: " + RB1);
        System.out.println("Determinante de B2: " + RB2);
    }

}
