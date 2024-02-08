package examples.behaviours;

public class SLR {

	private float b0;
	private float b1;

	public SLR() {
		this.b0 = b0;
		this.b1 = b1;
	}

	public float calculateB0() {	

		this.b0 = this.b0 + 10;
		return this.b0;
	}

	public float calculateB1() {	
		this.b1 = this.b1 + 12;
		return this.b1;
	}

	public void printRegressEquation() {
		System.out.println("Regress Eq = " + "sales = " + this.b0 + "+" 
				                  + this.b1 + "advertising");
	}

	public void predict(float advertising) {
		float sales = 0;

		sales = this.b0 + (this.b1 * advertising);
		System.out.println("Predicted value for sales = " + sales); 
	}

}
