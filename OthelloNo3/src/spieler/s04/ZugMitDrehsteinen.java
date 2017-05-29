package spieler.s04;

import java.util.ArrayList;

import spieler.Zug;


/*
 * Ein ZugMitDrehsteinen besteht aus einem Zug (Feld, auf das man den Stein legen w�rde) und
 * Einer ArrayList<Zug> das alle Steine speichert die durch das Legen des Steins auf das Feld
 * umgedreht werden w�rden.
 * 
 * Wir haben diese Datenstruktur gebaut, um beim Berechnen der m�glichen Z�ge direkt mitspeichern zu4
 * k�nnen, welchen Nutzen dieser Zug h�tte, statt nur zu berechnen welche Z�ge m�glich w�ren.
 */
public class ZugMitDrehsteinen extends Zug
{
	private ArrayList<Zug> drehsteine;
	
	public ZugMitDrehsteinen(int zeile, int spalte) 
	{
		super(zeile, spalte);
		this.drehsteine = new ArrayList<Zug>();
	}

	public ArrayList<Zug> getDrehsteine() {
		return drehsteine;
	}

	public void setDrehsteine(ArrayList<Zug> drehsteine) {
		this.drehsteine = drehsteine;
	}

	
}
