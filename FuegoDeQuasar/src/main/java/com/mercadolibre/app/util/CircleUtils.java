package com.mercadolibre.app.util;

import com.mercadolibre.app.model.Position;
import com.mercadolibre.app.model.Satellite;

public class CircleUtils {

	public static Position trilateracion(Satellite s1, Satellite s2, Satellite s3, 
			double r1, double r2, double r3) {

		Position result = null;
		
		Position[] intersectionsS1S2 = new Position[2];

		/* Distancia total entre los ejes */
		double distS1S2 = CircleUtils.distanceBetweenPoints(s1.getPosition(), s2.getPosition());

		/* Suma de los radios */
		double radSumR1R2 = r1 + r2;

		// Chequeo si se intersectan los circulos
		if (distS1S2 > radSumR1R2) {
			// No se intersectan
			return result;
		}

		if (distS1S2 < Math.abs(r1 - r2)) {
			// Un circulo dentro de otro
			return result;
		}

		//Distancia del eje al punto medio de los dos ejes
		double a = ((r1*r1) - (r2*r2) + (distS1S2*distS1S2)) / (2 * distS1S2);
		
		//Para hacer más facil las cuentas
		double x1 = s1.getPosition().getX();
		double y1 = s1.getPosition().getY();
		double x2 = s2.getPosition().getX();
		double y2 = s2.getPosition().getY();
		
		//Distancia entre los ejes de los satelites
		double dx = x2 - x1;
		double dy = y2 - y1;
		
		// Coordenadas del punto medio entre los ejes, si se estan tocando en un solo
		// punto esta es la única intersección
		double px2 = x1 + (dx * a / distS1S2);
		double py2 = y1 + (dy * a / distS1S2);
		
		if (distS1S2 == radSumR1R2) {
			Position uniquePoint = new Position(px2, py2);
			intersectionsS1S2[0] = uniquePoint;
		} else {
			// Determino la distancia del punto medio entre los ejes hasta las
			// intersecciones que quiero obtener
			double h = Math.sqrt((r1*r1) - (a*a));

			double rx = -dy * (h / distS1S2);
			double ry = dx * (h / distS1S2);

			/* armado de puntos de resultado */
			double xfinal1 = Math.round(px2 + rx);
			double xfinal2 = Math.round(px2 - rx);

			double yfinal1 = Math.round(py2 + ry);
			double yfinal2 = Math.round(py2 - ry);

			Position point = new Position(xfinal1, yfinal1);
			Position point2 = new Position(xfinal2, yfinal2);

			intersectionsS1S2[0] = point;
			intersectionsS1S2[1] = point2;
		}
		
		for(Position p : intersectionsS1S2) {
			if(p != null && distanceBetweenPoints(p, s3.getPosition()) == r3) {
				result = p;
				break;
			}
		}

		return result;

	}
	
	public static double distanceBetweenPoints(Position p1, Position p2) {
		/* Distancia horizontal y vertical entre los dos ejes */
		double dx = p2.getX() - p1.getX();
		double dy = p2.getY() - p1.getY();
		
		return Math.sqrt(dx*dx + dy*dy);
	}

}
