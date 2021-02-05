package com.mercadolibre.api.model;

public class Position {
	private double x;
	private double y;

	public Position() {
	}

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getX() == ((Position) obj).getX() 
				&& this.getY() == ((Position) obj).getY();
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	
}
