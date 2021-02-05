package com.mercadolibre.api.model;

import java.util.Arrays;

public class Satellite {

	private String name;
	private Position position;
	private Double distanceFromShip;
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

	public Double getDistanceFromShip() {
		return distanceFromShip;
	}

	public void setDistanceFromShip(Double distanceFromShip) {
		this.distanceFromShip = distanceFromShip;
	}

	public String[] getReceiptMessage() {
		return receiptMessage;
	}

	public void setReceiptMessage(String[] receiptMessage) {
		this.receiptMessage = receiptMessage;
	}

	@Override
	public String toString() {
		return "Satellite [name=" + name + ", position=" + position + ", distanceFromShip=" + distanceFromShip
				+ ", receiptMessage=" + Arrays.toString(receiptMessage) + "]";
	}

}
