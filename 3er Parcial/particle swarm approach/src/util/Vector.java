package util;

public class Vector {
    public int length;
    private double[] coordinates;

    public Vector(double[] coordinates) {
        this.coordinates = coordinates;
        this.length = coordinates.length;
    }

    public Vector(int length) {
        this.length = length;
        this.coordinates = new double[length];
    }

    public double getCoordinate(int index) {
        return coordinates[index];
    }

    public int getDimension() {
        return coordinates.length;
    }

    public static Vector sum(Vector vectorA, Vector vectorB) {
        if (vectorA.getDimension() != vectorB.getDimension()) {
            throw new IllegalArgumentException("Vectors must be of the same dimension.");
        }

        double[] resultCoordinates = new double[vectorA.getDimension()];

        for (int i = 0; i < vectorA.getDimension(); i++) {
            resultCoordinates[i] = vectorA.getCoordinate(i) + vectorB.getCoordinate(i);
        }

        return new Vector(resultCoordinates);
    }

    public static Vector sub(Vector vectorA, Vector vectorB) {
        if (vectorA.getDimension() != vectorB.getDimension()) {
            throw new IllegalArgumentException("Vectors must be of the same dimension.");
        }

        double[] resultCoordinates = new double[vectorA.getDimension()];

        for (int i = 0; i < vectorA.getDimension(); i++) {
            resultCoordinates[i] = vectorA.getCoordinate(i) - vectorB.getCoordinate(i);
        }

        return new Vector(resultCoordinates);
    }

    public static double dotProduct(Vector vectorA, Vector vectorB) {
        if (vectorA.getDimension() != vectorB.getDimension()) {
            throw new IllegalArgumentException("Vectors must be of the same dimension.");
        }

        double result = 0.0;

        for (int i = 0; i < vectorA.getDimension(); i++) {
            result += vectorA.getCoordinate(i) * vectorB.getCoordinate(i);
        }

        return result;
    }

    public static Vector crossProduct(Vector vectorA, Vector vectorB) {
        if (vectorA.getDimension() != 3 || vectorB.getDimension() != 3) {
            throw new UnsupportedOperationException("Cross product is only defined for 3-dimensional vectors.");
        }

        double[] resultCoordinates = new double[3];
        resultCoordinates[0] = vectorA.getCoordinate(1) * vectorB.getCoordinate(2) - vectorA.getCoordinate(2) * vectorB.getCoordinate(1);
        resultCoordinates[1] = vectorA.getCoordinate(2) * vectorB.getCoordinate(0) - vectorA.getCoordinate(0) * vectorB.getCoordinate(2);
        resultCoordinates[2] = vectorA.getCoordinate(0) * vectorB.getCoordinate(1) - vectorA.getCoordinate(1) * vectorB.getCoordinate(0);

        return new Vector(resultCoordinates);

    }

    public static Vector multiply(double scalar, Vector vector) {
        double[] resultCoordinates = new double[vector.getDimension()];

        for (int i = 0; i < vector.getDimension(); i++) {
            resultCoordinates[i] = scalar * vector.getCoordinate(i);
        }

        return new Vector(resultCoordinates);
    }

    public static double magnitude(Vector vector) {
        return Math.sqrt(Vector.dotProduct(vector, vector));
    }

    public static Vector normalize(Vector vector) {
        double magnitude = Vector.magnitude(vector);

        if (magnitude == 0.0) {
            throw new ArithmeticException("Cannot normalize a zero-length vector.");
        }

        return Vector.multiply((1.0 / magnitude), vector);
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");

        for (int i = 0; i < this.getDimension(); i++) {
            sb.append(this.getCoordinate(i));

            if (i != this.getDimension() - 1) {
                sb.append(", ");
            }
        }

        sb.append(">");
        return sb.toString();
    }
}
