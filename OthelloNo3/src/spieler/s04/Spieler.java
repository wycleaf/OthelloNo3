package spieler.s04;

import java.util.ArrayList;

import javax.sound.midi.Synthesizer;

import spieler.Farbe;
import spieler.OthelloSpieler;
import spieler.Zug;
import spieler.ZugException;
import spieler.s04.Brett;
import spieler.s04.ZugMitDrehsteinen;

public class Spieler implements OthelloSpieler
{
	private Farbe unsereFarbe;
	private Farbe gegnerFarbe;
	private Brett spielfeld;
	
	Spieler ()
	{
		
	}
	
	Spieler (int suchtiefe)
	{
		
	}
	/* berechnet die Steine, die durch den �bergebenen Zug umgedreht werden. Das Feld auf das der Stein 
	 * (Zug) gelegt wird, wird direkt verarbeitet.
	 */
	public ArrayList<Zug> dreheSteine (Zug zug, Farbe spielerAmZug) throws ZugException
	{
		Farbe gegner = Farbe.SCHWARZ;
		if(spielerAmZug == Farbe.SCHWARZ)
			gegner = Farbe.WEISS;
		
		ArrayList<Zug> steine = new ArrayList<Zug>();
		if(spielfeld.spielfeld[zug.getZeile()][zug.getSpalte()] == Farbe.LEER)
		{
			spielfeld.spielfeld[zug.getZeile()][zug.getSpalte()] = spielerAmZug;
		}
				
		else throw new ZugException ("Feld nicht leer!");
		
		for(int z = -1; z<2; z++)
			for(int s = -1; s<2; s++)
			{
				if(z == 0 && s == 0)
					continue;
				//neuer Zug mit entsprechendem Versatz wird erstellt
				Zug drehstein = new Zug(zug.getZeile()+z, zug.getSpalte()+s);
				
				//�berpr�fung ob der neu erstellte Zug sich im Feld befinden w�rde, sprich
				//zwischen 0 und 7 liegt
				if(imFeld(drehstein.getZeile(), drehstein.getSpalte()))
				{	
					//Eine neue List von Z�gen wird erstellt (diese sollen potenziell umgedrehte Steine
					//darstellen
					
					ArrayList<Zug> m�glicheSteine = new ArrayList<Zug>();
					
					//Wenn auf dem um 1 versetzten Feld ein gegnerischer Stein liegt, wird dieser
					//der eben erzeugten Liste hinzugef�gt.
					
					if(spielfeld.spielfeld[drehstein.getZeile()][drehstein.getSpalte()] == gegner)
					{	
						m�glicheSteine.add(drehstein);
						drehstein = new Zug(drehstein.getZeile()+z, drehstein.getSpalte()+s);
						while(imFeld(drehstein.getZeile(), drehstein.getSpalte()))
						{
							
							//Es wird weiter in die gleiche Richtung geschaut (selber Versatz (z oder s)
							
							if(spielfeld.spielfeld[drehstein.getZeile()][drehstein.getSpalte()] == gegner)
							{
								m�glicheSteine.add(drehstein);
								drehstein = new Zug(drehstein.getZeile()+z, drehstein.getSpalte()+s);
								continue;
							}
							
							// zu guter letzt, wenn sich in dieser Richtung noch ein Stein mit der Farbe
							//des handelnden Spielers befindet werden die Steine der endg�ltigen Liste
							//zugef�gt.
							
							if(spielfeld.spielfeld[drehstein.getZeile()][drehstein.getSpalte()] == spielerAmZug)
							{
								steine.addAll(m�glicheSteine);
							}
							break;
						}
					}
				}
			}
		return steine;
	}
	
	public void neuesSpiel(Farbe meineFarbe,
			int bedenkzeitInSekunden)
	{
		if(meineFarbe == Farbe.WEISS)
		{
			unsereFarbe = Farbe.WEISS;
			gegnerFarbe = Farbe.SCHWARZ;
		}
		else
		{
			gegnerFarbe = Farbe.WEISS;
			unsereFarbe = Farbe.SCHWARZ;
		}
		
		spielfeld = new Brett();
		spielfeld.brettAufbau();
	}
	
	public Zug berechneZug(Zug vorherigerZug, long zeitWeiss, long zeitSchwarz) throws ZugException
	{
		ArrayList<Zug> zuDrehendeSteine = new ArrayList<Zug>();
		if(!(vorherigerZug==null))
		{	
			zuDrehendeSteine = dreheSteine(vorherigerZug, gegnerFarbe);
		
					for(int i=0; i<zuDrehendeSteine.size(); i++)
						spielfeld.spielfeld[zuDrehendeSteine.get(i).getZeile()][zuDrehendeSteine.get(i).getSpalte()] = gegnerFarbe;
			
		}
		Zug unserZug = new Zug(-1,-1);
		ArrayList<ZugMitDrehsteinen> m�glicheZ�ge= m�glicheZ�ge();
		if(!(m�glicheZ�ge.isEmpty()))
				unserZug = findeBestenZug(m�glicheZ�ge);
				
		zuDrehendeSteine = dreheSteine(unserZug, unsereFarbe);
		for(int i=0; i<zuDrehendeSteine.size(); i++)
			spielfeld.spielfeld[zuDrehendeSteine.get(i).getZeile()][zuDrehendeSteine.get(i).getSpalte()] = unsereFarbe;
			
		return unserZug;
	}
	
	public Zug findeBestenZug (ArrayList<ZugMitDrehsteinen> z�ge)
	{
		int bestesErgebnis=0; // das bisher beste Ergebnis eines einzelnen Zugs
		int bewertung = 0;	//die bewertung des aktuellen Zugs
		Zug besterZug = z�ge.get(0); //der intial beste Zug ist der am Anfang der �bergebenen Liste
		
		for(int i=0; i<z�ge.size(); i++)
		{	
			//Das zu belegende Feld (der aktuelle Zug an sich) wird mit der Bewertungsmatrix verglichen
			//und der Wert der Bewertung zuaddiert
			
			bewertung += Brett.getBewertung()[z�ge.get(i).getZeile()][z�ge.get(i).getSpalte()];

			for(int k=0; k<z�ge.get(i).getDrehsteine().size(); k++)
				//Ein ZugMitDrehsteinen besteht aus 1 Zug und einer ArrayList<Zug> die alle Steine
				//repr�sentiert die dieser Zug umdrehen w�rde
			{
				bewertung += Brett.getBewertung()[z�ge.get(i).getDrehsteine().get(k).getZeile()][z�ge.get(i).getDrehsteine().get(k).getSpalte()];
				//Die Drehsteine die zu dem Zug mitgespeichert wurden werden ebenfalls mit der 
				//Bewertungsmatrix verglichen
			}
			
			
			if(bestesErgebnis<bewertung)
			{	
				bestesErgebnis=bewertung;
				besterZug=z�ge.get(i);
				
			}
			bewertung=0;
		}
		
		return besterZug;
	}
	
	public String meinName()
	
	{
		return "S04";
	}
	
	/*Wir bewegen uns durch das komplette Spielfeld und suchen nach leeren Feldern
	 * und schauen uns jede Richtung separat an. F�r jedes leere Feld wird ein 
	 * ZugMitDrehsteinen erstellt. Wenn entsprechende benachbarte gegnerische Steine gefunden werden
	 * werden einfache Z�ge erstellt und der ArrayList des ZugMitDrehsteinen hinzugef�gt.
	 * Der ZugMitDrehsteinen wiederrum wird der eingangs erstellen ArrayList<ZugMitDrehsteinen> hinzugef�gt.
	 */
	
	public ArrayList<ZugMitDrehsteinen> m�glicheZ�ge ()
	{		
		ArrayList<ZugMitDrehsteinen> z�ge = new ArrayList<ZugMitDrehsteinen>();
		
		for(int i = 0; i<spielfeld.spielfeld.length; i++)
			for(int k = 0; k<spielfeld.spielfeld.length; k++)
			{	
				//f�r jede Richtung und jedes leere Feld wird ein separater ZugMitDrehsteinen gebaut
				//um "Drehsteinfragmente" aus anderen Richtungen zu vermeiden.
				
				ZugMitDrehsteinen ersterZug = new ZugMitDrehsteinen(i,k);
				ZugMitDrehsteinen zweiterZug = new ZugMitDrehsteinen(i,k);
				ZugMitDrehsteinen dritterZug = new ZugMitDrehsteinen(i,k);
				ZugMitDrehsteinen vierterZug = new ZugMitDrehsteinen(i,k);
				ZugMitDrehsteinen f�nfterZug = new ZugMitDrehsteinen(i,k);
				ZugMitDrehsteinen sechsterZug = new ZugMitDrehsteinen(i,k);
				ZugMitDrehsteinen siebterZug = new ZugMitDrehsteinen(i,k);
				ZugMitDrehsteinen achterZug = new ZugMitDrehsteinen(i,k);
				
				if(spielfeld.spielfeld[i][k] == Farbe.LEER)
				{	
					boolean korrekt = false;
					
					//Wir schauen zuerst auf das Feld direkt rechts dem leeren Feld, auf das wir einen
					//Stein legen wollen
					
					for(int spalte = 1; spalte<8; spalte++)/*rechts*/
					{	if(imFeld(i, k+spalte))
						{
							
							if(spielfeld.spielfeld[i][k+spalte] == Farbe.LEER)
							{
								korrekt = false;
								break;
							}
							
							if(spielfeld.spielfeld[i][k+spalte] == gegnerFarbe)
							{	Zug drehstein = new Zug(i, k+spalte);
								ersterZug.getDrehsteine().add(drehstein);
								korrekt = true;
								continue;
							}
							
							if(spielfeld.spielfeld[i][k+spalte] == unsereFarbe && spielfeld.spielfeld[i][k+spalte-1] == gegnerFarbe)
							{	
								korrekt = false;				
								z�ge.add(ersterZug);
								break;
							}
						}
					}
				
					korrekt = false;
					
					for(int spalte = -1; spalte>-8; spalte--)/*links*/
					{	if(imFeld(i, k+spalte))
						{
					
							if(spielfeld.spielfeld[i][k+spalte] == Farbe.LEER)
							{
								korrekt = false;
								break;
							}
							
							if(spielfeld.spielfeld[i][k+spalte] == gegnerFarbe)
							{	
								Zug drehstein = new Zug(i, k+spalte);
								zweiterZug.getDrehsteine().add(drehstein);
								korrekt = true;
								continue;
							}
							
							if(spielfeld.spielfeld[i][k+spalte] == unsereFarbe && spielfeld.spielfeld[i][k+spalte+1] == gegnerFarbe)
							{	
								korrekt = false;
								z�ge.add(zweiterZug);
								break;
							}
						}
					}
					
					korrekt = false;
					
					for(int zeile = 1; zeile<8; zeile++)/*unten*/
					{	if(imFeld(i+zeile, k))
						{	
							if(spielfeld.spielfeld[i+zeile][k] == Farbe.LEER)
							{
								korrekt = false;
								break;
							}
							
							if(spielfeld.spielfeld[i+zeile][k] == gegnerFarbe)
							{	Zug drehstein = new Zug(i+zeile, k);
								dritterZug.getDrehsteine().add(drehstein);
								korrekt = true;
								continue;
							}
							
							if(spielfeld.spielfeld[i+zeile][k] == unsereFarbe && spielfeld.spielfeld[i+zeile-1][k] == gegnerFarbe)
							{	
								korrekt = false;
							
								z�ge.add(dritterZug);
								break;
							}
						}
					}
					
					korrekt = false;
					
					for(int zeile = -1; zeile>-8; zeile--)/*oben*/
					{	if(imFeld(i+zeile, k))
						{
							if(spielfeld.spielfeld[i+zeile][k] == Farbe.LEER)
							{
								korrekt = false;
								break;
							}
							
							if(spielfeld.spielfeld[i+zeile][k] == gegnerFarbe)
							{	Zug drehstein = new Zug(i+zeile, k);
								vierterZug.getDrehsteine().add(drehstein);
								korrekt = true;
								continue;
							}
							
							if(spielfeld.spielfeld[i+zeile][k] == unsereFarbe && spielfeld.spielfeld[i+zeile+1][k] == gegnerFarbe)
							{	
								korrekt = false;
								z�ge.add(vierterZug);
								break;
							}
						}
					}
					
					korrekt = false;
					
					for(int zalte = -1; zalte>-8; zalte--)/*oben links*/
					{	if(imFeld(i+zalte, k+zalte))
						{
							if(spielfeld.spielfeld[i+zalte][k+zalte] == Farbe.LEER)
							{
								korrekt = false;
								break;
							}
							
							if(spielfeld.spielfeld[i+zalte][k+zalte] == gegnerFarbe)
							{	Zug drehstein = new Zug(i+zalte, k+zalte);
								f�nfterZug.getDrehsteine().add(drehstein);
								korrekt = true;
								continue;
							}
							
							if(spielfeld.spielfeld[i+zalte][k+zalte] == unsereFarbe && spielfeld.spielfeld[i+zalte+1][k+zalte+1] == gegnerFarbe)
							{	
								korrekt = false;
								z�ge.add(f�nfterZug);
								break;
							}
						}
					}
					
					korrekt = false;
					
					for(int zalte = 1; zalte<8; zalte++)/*unten rechts*/
					{	if(imFeld(i+zalte, k+zalte))
						{
							if(spielfeld.spielfeld[i+zalte][k+zalte] == Farbe.LEER)
							{
								korrekt = false;
								break;
							}
							
							if(spielfeld.spielfeld[i+zalte][k+zalte] == gegnerFarbe)
							{	Zug drehstein = new Zug(i+zalte, k+zalte);
								sechsterZug.getDrehsteine().add(drehstein);
								korrekt = true;
								continue;
							}
							
							if(spielfeld.spielfeld[i+zalte][k+zalte] == unsereFarbe && spielfeld.spielfeld[i+zalte-1][k+zalte-1] == gegnerFarbe)
							{	
								korrekt = false;
								z�ge.add(sechsterZug);
								break;
							}
						}
					}
					
					korrekt = false;
					
					for(int zalte = 1; zalte<8; zalte++)/*oben rechts*/
					{	if(imFeld(i+(zalte*-1), k+zalte))
						{	
							if(spielfeld.spielfeld[i+(zalte*-1)][k+zalte] == Farbe.LEER)
							{
								korrekt = false;
								break;
							}
							
							if(spielfeld.spielfeld[i+(zalte*-1)][k+zalte] == gegnerFarbe)
							{	Zug drehstein = new Zug(i+(zalte*-1), k+zalte);
								siebterZug.getDrehsteine().add(drehstein);
								korrekt = true;
								continue;
							}
							
							if(spielfeld.spielfeld[i+(zalte*-1)][k+zalte] == unsereFarbe && spielfeld.spielfeld[i+(zalte*-1)+1][k+zalte-1] == gegnerFarbe)
							{	
								korrekt = false;
								z�ge.add(siebterZug);
								break;
							}
						}
					}
					
					korrekt = false;
					
					for(int zalte = 1; zalte<8; zalte++)/*unten links*/
					{	if(imFeld(i+zalte, k+(zalte*-1)))
						{
							if(spielfeld.spielfeld[i+zalte][k+(zalte*-1)] == Farbe.LEER)
							{
								korrekt = false;
								break;
							}
							
							if(spielfeld.spielfeld[i+zalte][k+(zalte*-1)] == gegnerFarbe)
							{	Zug drehstein = new Zug(i+zalte, k+(zalte*-1));
								achterZug.getDrehsteine().add(drehstein);
								korrekt = true;
								continue;
							}
							
							if(spielfeld.spielfeld[i+zalte][k+(zalte*-1)] == unsereFarbe && spielfeld.spielfeld[i+zalte-1][k+(zalte*-1)+1] == gegnerFarbe)
							{	
								korrekt = false;
								z�ge.add(achterZug);
								break;
							}
						}
					}

				}
			}
		return z�ge;
	}

	public boolean imFeld(int zeile, int spalte)
	{
			if(zeile > 7 ||  zeile <0 || spalte>7 || spalte <0)
			return false;
			else return true;
	}

}
