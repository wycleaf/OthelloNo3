package spieler.s04;
import spieler.Farbe;
public class Brett implements Cloneable
{
	static int spielfeldgr��e = 8;
	public Farbe spielfeld[][];
	private static int bewertung[][];

	public static int getSpielfeldgr��e() {
		return spielfeldgr��e;
	}

	public static void setSpielfeldgr��e(int spielfeldgr��e) {
		Brett.spielfeldgr��e = spielfeldgr��e;
	}

	public Farbe[][] getSpielfeld() {
		return spielfeld;
	}

	public void setSpielfeld(Farbe[][] spielfeld) {
		this.spielfeld = spielfeld;
	}

	public static int[][] getBewertung() {
		return bewertung;
	}

	public static void setBewertung(int[][] bewertung) {
		Brett.bewertung = bewertung;
	}

	Brett()
	{
		spielfeld = new Farbe [spielfeldgr��e][spielfeldgr��e];
		
		bewertung = new int[][] {
			{50, -1,  5, 2, 2, 5,  -1, 50},
			{-1, -10, 1, 1, 1, 1, -10, -1},
			{5,    1, 1, 1, 1, 1,   1,  5},
			{2,    1, 1, 1, 1, 1,   1,  2},
			{2,    1, 1, 1, 1, 1,   1,  2},
			{5,    1, 1, 1, 1, 1,   1,  5},
			{-1, -10, 1, 1, 1, 1, -10, -1},
			{50,  -1, 5, 2, 2, 5,  -1, 50}
			};
	
	}
	
	public void brettAufbau()
	{
		/*komplettes Feld mit Farbe.Leer bef�llen. Die Ausgangsposition danach korrigieren*/
		
		for(int zeile = 0; zeile<=7; zeile++)
			for(int spalte=0; spalte<=7; spalte++)
			{
				spielfeld[zeile][spalte] = Farbe.LEER;
			} 
		
		spielfeld[3][3] = Farbe.WEISS;
		spielfeld[3][4] = Farbe.SCHWARZ;
		spielfeld[4][3] = Farbe.SCHWARZ;
		spielfeld[4][4] = Farbe.WEISS;
		
	}
	//Bisher nicht genutzt, wir denken n�tig f�r min/max sp�ters
	public Brett brettKopieren(Brett original)
	{
		Brett kopie = new Brett ();
		for(int zeile = 0; zeile<spielfeldgr��e; zeile++)
			for(int spalte=0; spalte<spielfeldgr��e; spalte++)
				kopie.spielfeld[zeile][spalte] = original.spielfeld[zeile][spalte];
			
		return kopie;
	}
	
}
