package spieler.s04;

import java.util.ArrayList;

import spieler.Farbe;
import spieler.Zug;
import spieler.ZugException;

public class Test 
{
	/*
	 * Einfache Testklasse um einzelne Funktionen au�erhalb des Wettkampfrahmens einfacher testen zu  k�nnen
	 */

	public static void main(String[] args) 
	{
		Spieler s04 = new Spieler();
		s04.neuesSpiel(Farbe.WEISS, 150);
		
		ArrayList<ZugMitDrehsteinen> z�ge = s04.m�glicheZ�ge();
//		System.out.println("Liste der m�glichen Z�ge und deren umgedrehten Steinen:");
//		for(int i = 0; i<z�ge.size(); i++)
//		{	
//			
//			System.out.println();
//			System.out.print("Zug " + (i+1) + ": Zeile:");
//			System.out.println(z�ge.get(i).getZeile() + " Spalte:" + z�ge.get(i).getSpalte());
//			System.out.println("Umgedrehte Steine:");
//			for(int k = 0; k<z�ge.get(i).getDrehsteine().size(); k++)
//			{	System.out.print("Zeile: ");
//				System.out.println(z�ge.get(i).getDrehsteine().get(k).getZeile() + " Spalte:" +
//						z�ge.get(i).getDrehsteine().get(k).getSpalte());
//			}
//		}
		s04.findeBestenZug(z�ge);
		Zug derZug = new Zug(2,2);
		Zug vorherigerZug = new Zug(2,3);
		try {
			s04.berechneZug(vorherigerZug, 150, 150);
			
		} catch (ZugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}