package com.mercadolibre.app.util;

import com.mercadolibre.app.model.Position;

/**
 * Clase utils para el uso de funciones trigonemtrias relacionados al calculo
 * de posiciones de un objeto
 * @author fmediotte
 */
public class TrigonometryUtils {

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

		Position result = null;
		
		//Array que contendra las intersecciones entre el primer satelite y el segundo
		Position[] intersectionsS1S2 = new Position[2];

		//Distancia total entre los ejes 
		double distS1S2 = TrigonometryUtils.distanceBetweenPoints(p1, p2);

		// Suma de los radios
		double radSumR1R2 = r1 + r2;

		// Chequeo si se intersectan los circulos
		// Si la distancia es mayor a la suma de los radios no se intersectan.
		if (distS1S2 > radSumR1R2) {
			// No se intersectan
			return result;
		}

		if (distS1S2 < Math.abs(r1 - r2)) {
			// Un circulo dentro de otro
			return result;
		}

		//Distancia del eje al punto medio de los dos ejes que quiero investigar
		double a = ((r1*r1) - (r2*r2) + (distS1S2*distS1S2)) / (2 * distS1S2);
		
		//Para hacer más facil las cuentas me guardo el x e y de los primeros dos puntos
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		
		//Distancia entre los ejes de los satelites
		double dx = x2 - x1;
		double dy = y2 - y1;
		
		// Coordenadas del punto medio entre los ejes
		double px2 = x1 + (dx * a / distS1S2);
		double py2 = y1 + (dy * a / distS1S2);
		
		//Si la distancia entre los ejes es igual a la suma de los radios
		//encontramos la intersección unica entre los dos radios de los satelites
		if (distS1S2 == radSumR1R2) {
			Position uniquePoint = new Position(px2, py2);
			intersectionsS1S2[0] = uniquePoint;
		} else {
			
			// Determino la distancia del punto medio entre los ejes hasta las
			// intersecciones que quiero obtener utilizo el teorema de pitagora
			// h ^ 2 = c ^ 2 + c ^ 2
			double h = Math.sqrt((r1*r1) - (a*a));

			double rx = -dy * (h / distS1S2);
			double ry = dx * (h / distS1S2);

			//Armo los dos puntos del resultado
			double xfinal1 = Math.round(px2 + rx);
			double xfinal2 = Math.round(px2 - rx);

			double yfinal1 = Math.round(py2 + ry);
			double yfinal2 = Math.round(py2 - ry);

			Position point = new Position(xfinal1, yfinal1);
			Position point2 = new Position(xfinal2, yfinal2);

			intersectionsS1S2[0] = point;
			intersectionsS1S2[1] = point2;
		}
		
		/*	Calculo si alguno de los puntos obtenidos lentamente
			cumple con que la distancia al eje del 3er satelite es igual al radio del mismo
			en caso de ser correcto, dicho punto es la posición de la nave, si no se encuentra
			ninguno que cumpla dicha condición, no se puede obtener la posición de la nave imperial
		*/
		for(Position p : intersectionsS1S2) {
			if(p != null && distanceBetweenPoints(p, p3) == r3) {
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
	
}
