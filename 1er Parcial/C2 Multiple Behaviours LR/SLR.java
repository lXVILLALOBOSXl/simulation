package examples.behaviours;

public class SLR {
    private double b0;
    private double b1;

    private DataSet dataSet;

    public SLR(DataSet dataSet) {
        this.dataSet = dataSet;
        this.b1 = calculateSlope();
        this.b0 = calculateIntersect();
    }

    private double calculateIntersect(){
        return (DiscreetMath.sum(dataSet.getY())-(b1*DiscreetMath.sum(dataSet.getX()))) / dataSet.getN();
    }

    private double calculateSlope(){
        return ( (dataSet.getN() * DiscreetMath.sumMultiplication(dataSet.getX(), dataSet.getY())) - (DiscreetMath.sum(dataSet.getX()) * DiscreetMath.sum(dataSet.getY())) ) / ( (dataSet.getN() * DiscreetMath.sumPow(dataSet.getX(), 2)) - (Math.pow(DiscreetMath.sum(dataSet.getX()),2)));
    }

    public double getB0() {
        return b0;
    }

    public double getB1() {
        return b1;
    }

    /*public double calculateIntersect(){
        return ( ((DiscreetMath.sum(dataSet.getY()) * DiscreetMath.sumPow(dataSet.getX(),2) * DiscreetMath.sumPow(dataSet.getX(),4)) + (DiscreetMath.sum(dataSet.getX()) * DiscreetMath.sumPow(dataSet.getX(),3) * DiscreetMath.sumMultiplication(dataSet.getY(), dataSet.getX(), 2)) + (DiscreetMath.sumPow(dataSet.getX(),2) * DiscreetMath.sumMultiplication(dataSet.getY(), dataSet.getX()) * DiscreetMath.sumPow(dataSet.getX(), 3))) - ((DiscreetMath.sumMultiplication(dataSet.getY(), dataSet.getX(),2) * Math.pow(DiscreetMath.sumPow(dataSet.getX(), 2),2)) - (DiscreetMath.sum(dataSet.getY()) * Math.pow(DiscreetMath.sumPow(dataSet.getX(), 3),2)) - (DiscreetMath.sum(dataSet.getX()) * DiscreetMath.sumPow(dataSet.getX(), 4) * DiscreetMath.sumMultiplication(dataSet.getX(), dataSet.getY()))) )
                / ( ((dataSet.getN() * DiscreetMath.sumPow(dataSet.getX(), 2) *DiscreetMath.sumPow(dataSet.getX(), 4)) +  (DiscreetMath.sum(dataSet.getX()) * DiscreetMath.sumPow(dataSet.getX(), 3) *  (DiscreetMath.sumPow(dataSet.getX(), 2) ) ) + (DiscreetMath.sumPow(dataSet.getX(), 2) * DiscreetMath.sum(dataSet.getX())) * DiscreetMath.sumPow(dataSet.getX(), 3)) - ((Math.pow(DiscreetMath.sumPow(dataSet.getX(), 2),3)) - (dataSet.getN() * Math.pow(DiscreetMath.sumPow(dataSet.getX(), 3),2)) - (DiscreetMath.sumPow(dataSet.getX(), 4) * Math.pow(DiscreetMath.sum(dataSet.getX()),2)) ) );
    }*/

    /*public double calculateSlope(){
        return ( (() + () + ()) - (() - () - ()) ) / ( (() + () + ()) - (() - () - ()) );
    }*/

    public double predict(double x){
        return b0 + (b1 * x);
    }

    public String getEquation(){
        return "Y = " + b0 + " + " + b1 + "x";
    }



}
