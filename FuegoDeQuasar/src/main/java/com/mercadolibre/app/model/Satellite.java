package com.mercadolibre.app.model;

public class Satellite {

	private String name;
	private Position position;
	private double distanceFromShip;
	private String[] receiptMessage;

	public Satellite() {
	}

	public Satellite(String name, Position position) {
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double getDistanceFromShip() {
		return distanceFromShip;
	}

	public void setDistanceFromShip(double distanceFromShip) {
		this.distanceFromShip = distanceFromShip;
	}

	public String[] getReceiptMessage() {
		return receiptMessage;
	}

	public void setReceiptMessage(String[] receiptMessage) {
		this.receiptMessage = receiptMessage;
	}

}
