package spieler.s04;

import java.util.ArrayList;

import spieler.Zug;


/*
 * Ein ZugMitDrehsteinen besteht aus einem Zug (Feld, auf das man den Stein legen würde) und
 * Einer ArrayList<Zug> das alle Steine speichert die durch das Legen des Steins auf das Feld
 * umgedreht werden würden.
 * 
 * Wir haben diese Datenstruktur gebaut, um beim Berechnen der möglichen Züge direkt mitspeichern zu4
 * können, welchen Nutzen dieser Zug hätte, statt nur zu berechnen welche Züge möglich wären.
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
