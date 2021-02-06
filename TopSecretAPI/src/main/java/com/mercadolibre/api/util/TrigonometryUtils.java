package com.mercadolibre.api.util;

import java.text.DecimalFormat;
import java.text.ParseException;

import com.mercadolibre.api.model.Position;

/**
 * Clase utils para el uso de funciones trigonemtrias relacionados al calculo
 * de posiciones de un objeto
 * @author fmediotte
 */
public class TrigonometryUtils {
	
	public static final String MARGEN = "###.####";

	/**
	 * Esta función es utilizada para calcular el punto de intersección 
	 * dados 3 puntos (posición de las naves rebeldes) 
	 * y sus correspondientes radios (distancia hacia la nave)
	 * El procedimiento asumido es buscar la intersección de dos radios de los satelites
	 * y calcular desde dichos puntos si sumandole el radio o la distancia del 3ero damos
	 * con el eje del 3er satelite, si esto ocurre obtendremos el punto donde se encuentra
	 * la nave imperial
	 * @param p1 posición satelite 1 (Kenobi)
	 * @param p2 posición satelite 2 (Skywalker)
	 * @param p3 posición satelite 3 (Sato)
	 * @param r1 distancia desde el satelite 1 hasta la nave imperial
	 * @param r2 distancia desde el satelite 2 hasta la nave imperial
	 * @param r3 distancia desde el satelite 3 hasta la nave imperial
	 * @return
	 */
	public static Position trilateracion(Position p1, Position p2, Position p3, 
			double r1, double r2, double r3) {

		Position[] intersections = getIntersections(p1, p2, r1, r2);
		
		Position result = null;
		
		/*	Calculo si alguno de los puntos obtenidos previamente
			cumple con que la distancia al eje del 3er satelite es igual al radio del mismo
			en caso de ser correcto, dicho punto es la posición de la nave, si no se encuentra
			ninguno que cumpla dicha condición, no se puede obtener la posición de la nave imperial
		*/
		for(Position p : intersections) {
			if(p != null && cleanDecimals(distanceBetweenPoints(p, p3)) == cleanDecimals(r3)) {
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
		
		//Formula de distancia entre 2 puntos
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	/** Método para obtener la intercepción entre dos circuferencias */ 
	public static Position[] getIntersections(Position p1, Position p2, double r1, double r2) {
		//Array que contendra las intersecciones entre el primer satelite y el segundo
		Position[] intersections = new Position[2];

		//Distancia total entre los ejes 
		double pointsDistance = distanceBetweenPoints(p1, p2);

		// Suma de los radios
		double sum_radio = r1 + r2;

		// Si la distancia es mayor a la suma de los radios no se intersectan.
		if (pointsDistance > sum_radio) {
			return intersections;
		}

		// Un circulo dentro de otro no posee intersección
		if (pointsDistance < Math.abs(r1 - r2)) {
			return intersections;
		}

		//Distancia del eje al punto medio de los dos posibles puntos que quiero investigar
		double distance = ((r1*r1) - (r2*r2) + (pointsDistance*pointsDistance)) / (2 * pointsDistance);

		//Distancia entre los puntos separado por coordenadas
		double dx = p2.getX() - p1.getX();
		double dy = p2.getY() -  p1.getY();
		
		// Coordenadas del punto medio entre los ejes
		double px2 = p1.getX() + ((distance * dx) / pointsDistance);
		double py2 = p1.getY() + ((distance * dy) / pointsDistance);
		
		//Si la distancia entre los ejes es igual a la suma de los radios
		//encontramos la intersección unica entre los dos radios de los satelites
		if (pointsDistance == sum_radio) {
			Position uniquePoint = new Position(px2, py2);
			intersections[0] = uniquePoint;
			intersections[1] = null;
		} else {
			
			// Determino la distancia del punto medio entre los ejes hasta las
			// intersecciones que quiero obtener utilizo el teorema de pitagora
			// h ^ 2 = c ^ 2 + c ^ 2
			double h = Math.sqrt((r1*r1) - (distance*distance));

			//Determino el offset de las interseccions desde el punto medio encontrado
			double rx = (h * dy) / pointsDistance;
			double ry = (h * dx) / pointsDistance;

			//Armo los dos puntos del resultado
			double xfinal1 = cleanDecimals(px2 - rx);
			double yfinal1 = cleanDecimals(py2 + ry);
			
			double xfinal2 = cleanDecimals(px2 + rx);
			double yfinal2 = cleanDecimals(py2 - ry);

			Position point = new Position(xfinal1, yfinal1);
			Position point2 = new Position(xfinal2, yfinal2);

			intersections[0] = point;
			intersections[1] = point2;
		}
		
		return intersections;
	}
	
	//Solución con angulos
	public static Position[] getIntersect(Position p1, Position p2, double r1, double r2) {

        double distance = distanceBetweenPoints(p1, p2);
		distance = ((r1*r1) - (r2*r2) + (distance*distance)) / (2 * distance);
        distance /= r1;

        if (distance > 1) {
            return new Position[]{null, null};
        }

        double angulo = Math.acos(distance);

        double px = p2.getX() - p1.getX();
        double py = p2.getY() - p1.getY();
        double atan2 = Math.atan2(py, px); //angulo entre ejes

        if (distance == 1) {
            Position pf = getPosicionWithAngle(p1,r1, atan2 + angulo);
            return new Position[]{pf, null};
        }

        Position pf1 = getPosicionWithAngle(p1,r1, atan2 + angulo);
        Position pf2 = getPosicionWithAngle(p1,r1, atan2 - angulo);
        return new Position[]{pf1, pf2};
    }
	
	public static Position getPosicionWithAngle(Position p, double r, double angle) {
        double x = p.getX() + (r * Math.cos(angle));
        double y = p.getY() + (r * Math.sin(angle));

        x = cleanDecimals(x);
        y = cleanDecimals(y);

        return new Position(x, y);
    }

    public static double cleanDecimals(Number x) {
    	try {
	        DecimalFormat decimalFormat = new DecimalFormat(MARGEN);
	        String format = decimalFormat.format(x);
			x = decimalFormat.parse(format);
			x = x.doubleValue() == 0 ? 0 : x;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return x.doubleValue();
    }
	
}
