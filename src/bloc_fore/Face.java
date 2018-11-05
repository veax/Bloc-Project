/**
 * 
 */
package bloc_fore;

import java.util.ArrayList;

/**
 * @author Borisov Vadim & Dreano Benjamin
 *
 */
public class Face {
	private double largeur;
	private double hauteur;
	public ArrayList<Trou> Trous;

	public Face(double largeur, double hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		Trous = new ArrayList<Trou>();
	}
	
	public void addTrou(Trou t) {
		Trous.add(t);
	}

	public double getLargeur() {
		return largeur;
	}

	public double getHauteur() { return hauteur; }
	
	public String toString() {
		return "largeur: " + largeur + " hauteur: " + hauteur;
	}
	
}
