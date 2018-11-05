/**
 * 
 */
package bloc_fore;

import java.util.ArrayList;

/**
 * @author Borisov Vadim & Dreano Benjamin
 *
 */
public class Bloc {
	private double largeur;
	private double longueur;
	private double hauteur;
	public ArrayList<Face> Faces;
	/**
	 * @param largeur
	 * @param longueur
	 * @param hauteur
	 */
	
	
	public Bloc(double longueur,double largeur, double hauteur) {
		this.largeur = largeur;
		this.longueur = longueur;
		this.hauteur = hauteur;
		Faces = new ArrayList<Face>();
	}
	
	public void remplirFace() {
		for(int i = 0; i<6; i++) {
			this.addFace(i);
		}
		
	}
	
	private void addFace(int index) {
		if (index == 0 || index == 2) {
			Faces.add(index, new Face(longueur, hauteur));
		}
		else if (index == 1 || index == 3) {
			Faces.add(index, new Face(largeur, hauteur));
		}
		else {  // index == 4 && index == 5
			Faces.add(index, new Face(longueur, largeur));
		}
	}

	
	public void calculerIntersections() {
		for (int i = 0; i < Faces.size(); i++) {
			for (int j = i; j < Faces.size(); j++) {        // j = i, car on considere les cas d'intersections d'une face unique aussi
                for (int k = 0; k < Faces.get(i).Trous.size(); k++) {
                    for (int l = k; l < Faces.get(j).Trous.size(); l++) {
                    	if (i == j && l == k){		//cas de meme trou
                    		continue;
						}
						else {
							if (Intersection.isIntersect(Faces.get(i).Trous.get(k), i+1, Faces.get(j).Trous.get(l), j+1)){
								System.out.println("(" + Faces.get(i).Trous.get(k).getX() + ", " + Faces.get(i).Trous.get(k).getY() + ", " + Faces.get(i).Trous.get(k).getZ() + ", " + Faces.get(i).Trous.get(k).getH() + ")" + " FACE " + (i+1) + " - " + "FACE " + (j+1)	 + " (" + Faces.get(j).Trous.get(l).getX() + ", " + Faces.get(j).Trous.get(l).getY() + ", " + Faces.get(j).Trous.get(l).getZ() + ", " + Faces.get(j).Trous.get(l).getH() + ")");
							}
						}
                    }
                }
			}
		}
	}
	
	
	//getters
	
	public double getLargeur() {
		return largeur;
	}

	public double getLongueur() {
		return longueur;
	}

	public double getHauteur() {
		return hauteur;
	}

    public String toString() {
        return "Bloc: longueur: " + longueur + ", largeur:" + largeur + ", hauteur: " + hauteur;
    }
	
}
