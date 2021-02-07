package com.mercadolibre.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.mercadolibre.api.model.Position;

public class TrigonometryUtilsTest {
	
	@Test
	public void distanceBetweenPoints() {
		Position p1 = new Position(-2, 2);
		Position p2 = new Position(0, 2);
		double distance = TrigonometryUtils.distanceBetweenPoints(p1, p2);
		
		Assertions.assertEquals(2, distance);
	}
	
	@Test
	public void getIntersectionsCirclesToFar() {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 5);
		double r1 = 2;
		double r2 = 2;
		
		Position[] result = TrigonometryUtils.getIntersections(p1, p2, r1, r2);
		
		Assertions.assertNull(result[0]);
		Assertions.assertNull(result[1]);
	}
	
	@Test
	public void getIntersectionsOneCircleInsideOther() {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 0);
		double r1 = 2;
		double r2 = 3;
		
		Position[] result = TrigonometryUtils.getIntersections(p1, p2, r1, r2);
		
		Assertions.assertNull(result[0]);
		Assertions.assertNull(result[1]);
	}
	
	@Test
	public void getIntersectionsOnePoint() {
		Position p1 = new Position(-2, 2);
		Position p2 = new Position(0, 2);
		double r1 = 1;
		double r2 = 1;
		
		Position expected = new Position(-1,2);
		Position[] result = TrigonometryUtils.getIntersections(p1, p2, r1, r2);
		
		Assertions.assertEquals(expected, result[0]);
	}
	
	@Test
	public void getIntersectionsTwoPoints() {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(2, 0);
		double r1 = 2;
		double r2 = 2;
		
		Position[] expected = new Position[2];
		expected[0] = new Position(1,1.7321);
		expected[1] = new Position(1,-1.7321);
		
		Position[] result = TrigonometryUtils.getIntersections(p1, p2, r1, r2);
		
		Assertions.assertEquals(expected[0], result[0]);
		Assertions.assertEquals(expected[1], result[1]);
	}
	
	@Test
	public void getIntersectCirclesToFarWithAngles() {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 5);
		double r1 = 2;
		double r2 = 2;
		
		Position[] result = TrigonometryUtils.getIntersect(p1, p2, r1, r2);
		
		Assertions.assertNull(result[0]);
		Assertions.assertNull(result[1]);
	}
	
	@Test
	public void getIntersectOnePointWithAngles() {
		Position p1 = new Position(-2, 2);
		Position p2 = new Position(0, 2);
		double r1 = 1;
		double r2 = 1;
		
		Position expected = new Position(-1,2);
		Position[] result = TrigonometryUtils.getIntersect(p1, p2, r1, r2);
		
		Assertions.assertEquals(expected, result[0]);
	}
	
	@Test
	public void getIntersectTwoPointsWithAngles() {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(2, 0);
		double r1 = 2;
		double r2 = 2;
		
		Position[] expected = new Position[2];
		expected[0] = new Position(1,1.7321);
		expected[1] = new Position(1,-1.7321);
		
		Position[] result = TrigonometryUtils.getIntersect(p1, p2, r1, r2);
		
		Assertions.assertEquals(expected[0], result[0]);
		Assertions.assertEquals(expected[1], result[1]);
	}
	
	@Test
	public void trilateracionSuccess() {
		Position p1 = new Position(0,0);
		Position p2 = new Position(4,0);
		Position p3 = new Position(2,3);
		double r1 = 2;
		double r2 = 2;
		double r3 = 3;
		
		Position expected = new Position(2,0);
		
		Position result = TrigonometryUtils.trilateracion(p1, p2, p3, r1, r2, r3);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void trilateracionCircleDontTouchEachOther() {
		Position p1 = new Position(-500,-200);
		Position p2 = new Position(100,-100);
		Position p3 = new Position(500,100);
		double r1 = 100;
		double r2 = 115.5;
		double r3 = 142.7;
		
		Position result = TrigonometryUtils.trilateracion(p1, p2, p3, r1, r2, r3);
		
		Assertions.assertNull(result);
	}
	
	@Test
	public void trilateracionPointInCommonNotFound() {
		Position p1 = new Position(0,0);
		Position p2 = new Position(0,4);
		Position p3 = new Position(-4,2);
		double r1 = 2;
		double r2 = 2;
		double r3 = 1;
		
		Position result = TrigonometryUtils.trilateracion(p1, p2, p3, r1, r2, r3);
		
		Assertions.assertNull(result);
	}
	
	@Test
	public void cleanDecimalsEquals0() {
		Number zero = 0;
		double result = TrigonometryUtils.cleanDecimals(0);
		
		Assertions.assertEquals(zero.doubleValue(), result);
	}
	
}
