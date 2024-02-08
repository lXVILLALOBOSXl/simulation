/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gd;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import java.text.DecimalFormat;
import java.util.Scanner;

public class GD extends Agent {
    static Scanner sc = new Scanner(System.in);
    static GD gd = new GD();
    static Maths maths = new Maths();
    static Dataset_Multiple ds_multiple = new Dataset_Multiple();
    static Dataset_Simple ds_simple = new Dataset_Simple();
    static Model model = new Model();
    DecimalFormat df = new DecimalFormat("#.########");
    boolean flag = false;
    double alpha = 0.0005, precision = 0.0001;


    @Override
    public void setup(){
        System.out.println("Agent "+getLocalName()+" started.");
        addBehaviour(new GradientDescent());

    }
        private class GradientDescent extends Behaviour{
        int cont = 0;

        @Override
        public void action() {
        double Betha[] = new double[100];   
        int iterations = 0;
        String answer;
        System.out.println("Gradient descent. Write 'simple' for Simple Linear Regression or 'multiple' for Multiple Linear Regression");
        answer = sc.next();
        System.out.println(answer);
        if(answer.equals("simple") || answer.equals("Simple") || answer.equals("SIMPLE")){
            Betha[0] = 0;
            Betha[1] = 0;
            model.setB0(Betha[0]);
            model.setB1(Betha[1]);
            double[] y_hat = new double[ds_simple.getN()];
            double[] y_pred = new double[ds_simple.getN()];

	    // Error calculus
	    model.setError(initializeError(ds_simple.getY(), Betha, ds_simple.getX()));
            do{
                   for(int j = 0; j < ds_simple.getY().length; j++){
			   y_hat[j] = Betha[1]*ds_simple.getX()[j]+Betha[0];
			   y_pred[j] = ds_simple.getY()[j] - y_hat[j];
				  
                   }
                double E = maths.sigma(y_pred)/ds_simple.getN();
                model.setError(E*E);
                System.out.println("Error: "+df.format(model.getError()));
               
		// Gradient B0
		model.setDB0((double) -2/ds_simple.getN()*(maths.sigma(y_pred)));

		// Gradient B1
		model.setDB1((double) -2/ds_simple.getN()*(maths.sigma(maths.product(y_pred,ds_simple.getX()))));      
		// Learning Rules
                Betha[0] = Betha[0] - alpha*model.getDB0();
                Betha[1] = Betha[1] - alpha*model.getDB1();
               
		// Set New Model
	       	model.setB0(Betha[0]);
                model.setB1(Betha[1]);
                iterations++;
                
            }while(model.getError() > precision);

            System.out.println("B0 : "+model.getB0());
            System.out.println("B1 : "+model.getB1()); 
            System.out.println("Iterations: "+iterations);
        }
        if(answer.equals("multiple") || answer.equals("Multiple") || answer.equals("MULTIPLE")){
            double[] y_hat = new double[ds_multiple.getN()];
            double[] y_pred = new double[ds_multiple.getN()];
            Betha[0] = 0;
            Betha[1] = 0;
            Betha[2] = 0;
            model.setB0(Betha[0]);
            model.setB1(Betha[1]);
            model.setB2(Betha[2]);
            model.setError(initializeError(ds_multiple.getY(), Betha, ds_multiple.getX1(), ds_multiple.getX2()));
            do{
                for(int j = 0; j < ds_multiple.getY().length; j++){
                    y_hat[j] = Betha[2]*ds_multiple.getX2()[j]+Betha[1]*ds_multiple.getX1()[j]+Betha[0];//Aquí se aplican los valores de entrada a la predicción
                    y_pred[j] = ds_multiple.getY()[j]- y_hat[j];//diferencia_y = (y-ý)(ye menos ye de hat)        
                }
                double E = maths.sigma(y_pred)/ds_simple.getN();
                model.setError(E*E);
                System.out.println("Error: "+df.format(model.getError()));
                model.setDB0((double) -2/ds_multiple.getN()*(maths.sigma(y_pred)));
                model.setDB1((double) -2/ds_multiple.getN()*(maths.sigma(maths.product(y_pred,ds_multiple.getX1()))));
                model.setDB2((double) -2/ds_multiple.getN()*(maths.sigma(maths.product(y_pred,ds_multiple.getX2()))));
                Betha[0] = Betha[0] - alpha*model.getDB0();
                Betha[1] = Betha[1] - alpha*model.getDB1();
                Betha[2] = Betha[2] - alpha*model.getDB2();
                model.setB0(Betha[0]);
                model.setB1(Betha[1]);
                model.setB2(Betha[2]);
                iterations++;
            }while(model.getError() > precision);
            System.out.println("B0 : "+model.getB0());
            System.out.println("B1 : "+model.getB1());
            System.out.println("B2 : "+model.getB2());
            System.out.println("Iterations: "+iterations);
           double prediction;
	   prediction = model.getB0()+model.getB1()*11+model.getB2()*22;
	  System.out.println(prediction); 
	}  
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
        
        public double initializeError(double[] datasetY, double[] betha, double[] x){ //Para caso simple
            int n = datasetY.length;
            double[] y_hat = new double[1000];
            double[] y_pred = new double[n];
                for(int j = 0; j < datasetY.length; j++){
                    y_hat[j] = betha[1]*x[j]+betha[0];//Aquí se aplican los valores de entrada a la predicción
                    y_pred[j] = datasetY[j]-y_hat[j];//diferencia_y = (y-ý)(ye menos ye de hat)        
                }
            
            double E = (maths.sigma(y_pred))/n;
            double Error = E*E;
            return Error;
        }
    public double initializeError(double[] datasetY, double[] betha, double[] x1, double[] x2){ //Para caso multiple
            int n = datasetY.length;
            double[] y_hat = new double[1000];
            double[] y_pred = new double[n];
            for(int j = 0; j < datasetY.length; j++){
                y_hat[j] = betha[2]*x2[j]+betha[1]*x1[j]+betha[0];//Aquí se aplican los valores de entrada a la predicción
                y_pred[j] = datasetY[j]-y_hat[j];//diferencia_y = (y-ý)(ye menos ye de hat)        
            }   
             
            double E = (maths.sigma(y_pred))/n;
            double Error = E*E;
            return Error;
        }

    }
        
}

