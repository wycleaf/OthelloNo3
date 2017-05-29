package spieler.s04;

import java.util.ArrayList;

import spieler.Farbe;
import spieler.Zug;
import spieler.ZugException;

public class Test 
{
	/*
	 * Einfache Testklasse um einzelne Funktionen außerhalb des Wettkampfrahmens einfacher testen zu  können
	 */

	public static void main(String[] args) 
	{
		Spieler s04 = new Spieler();
		s04.neuesSpiel(Farbe.WEISS, 150);
		
		ArrayList<ZugMitDrehsteinen> züge = s04.möglicheZüge();
//		System.out.println("Liste der möglichen Züge und deren umgedrehten Steinen:");
//		for(int i = 0; i<züge.size(); i++)
//		{	
//			
//			System.out.println();
//			System.out.print("Zug " + (i+1) + ": Zeile:");
//			System.out.println(züge.get(i).getZeile() + " Spalte:" + züge.get(i).getSpalte());
//			System.out.println("Umgedrehte Steine:");
//			for(int k = 0; k<züge.get(i).getDrehsteine().size(); k++)
//			{	System.out.print("Zeile: ");
//				System.out.println(züge.get(i).getDrehsteine().get(k).getZeile() + " Spalte:" +
//						züge.get(i).getDrehsteine().get(k).getSpalte());
//			}
//		}
		s04.findeBestenZug(züge);
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