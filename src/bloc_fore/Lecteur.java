/**
 * 
 */
package bloc_fore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

/**
 * @author Borisov Vadim & Dreano Benjamin
 *
 */
public class Lecteur {
	
	public Bloc lecture() {
		Bloc b = null;
		Bloc sortie = null;	// bloc pour sortir d'une fonction dans le cas d'invalidation d'un fichier
		// lire le fichier
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez le nom du fichier avec l'extension (il doit etre situe dans le racine du projet): ");
		String fileName = sc.nextLine();
		fileName = System.getProperty("user.dir") + "/" + fileName;


		try(BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
		    String line = br.readLine();
		    // dimensions de bloc
		    String[] dimensions=null;
		    try {
		    	dimensions = line.split(" ; ");
		    }catch(Exception e) {
		    	System.out.println("Dimensions incorrectes du bloc");
		    	return sortie;
		    }
		    if (dimensions.length != 3) {
		    	System.out.println("Nombre de parametre incorrecte pour dimensions du bloc");
		    	return sortie;
		    }
		    try {
		    	 b = new Bloc(parseDouble(dimensions[0]), parseDouble(dimensions[1]), parseDouble(dimensions[2]));
		    	 b.remplirFace();
		    } catch (Exception e){
		    	System.out.println("syntax incorrecte pour bloc");
		    	return sortie;
		    }
		    
	

		    line = br.readLine();   
	        int current = 0;	// une face courante
	        
	        // verifier que la ligne apres dimension de bloc est forcement FACE[1-6]
	        if (line.matches("^FACE[1-6]$")) {
        		try {
        			if (current < Integer.parseInt(line.replaceAll("FACE", ""))) {
        				current = Integer.parseInt(line.replaceAll("FACE", ""));
        			}
        			else {
						System.out.println("Faces doivent etre ecrites dans l'ordre");
						return sortie;
        			}
        		} 
        		catch (Exception e) {
						System.out.println("Syntaxe Face incorrecte");
        				System.out.println(e);	//Autre chose que juste int dans la ligne censé être FACEx
						return sortie;
        		}
	        }
    		else {
    			//pas de face en 2eme ligne - la sortie du programme
    				System.out.println("Syntaxe Face incorrecte");
    				return sortie;
    		}
	        
	        
	        //vérifier le reste du fichier

			while ((line = br.readLine()) != null && line.length() > 0){
				if (line.matches("^FACE[1-6]$")) {
					try {
						if (current < Integer.parseInt(line.replaceAll("FACE", ""))) {
							current = Integer.parseInt(line.replaceAll("FACE", ""));
						}
						else {
							System.out.println("Faces doivent etre ecrites dans l'ordre");
							return sortie;
						}
					}
					catch (Exception e) {
						System.out.println("Syntaxe Face incorrecte");
						System.out.println(e);	//Autre chose que juste int dans la ligne censé être FACEx
						return sortie;
					}
				}
				else {
					//pattern pour les trous

					try {
						String[] trou = line.split(",");	// parse la ligne avec les dimensions d'un trou

						double x = Double.parseDouble(trou[0]);
						double y = Double.parseDouble(trou[1]);
						double z = Double.parseDouble(trou[2]);
						double h = Double.parseDouble(trou[3]);
						double d = Double.parseDouble(trou[4]);

						// validation des donnees (coordonnees par rapport au bloc)
						if (!validation(x, y, z, h, d, current, b)){
							System.out.println("Coordonnees d'un trou ne sont pas valides");
							return sortie;
						}

						if (trou.length != 5) {
							System.out.println("Nombre de parametres incorrecte pour le trou");
							return sortie;
						}
						switch(current) {
							// on cree le trou avec les coordonnees relatives et absolues:
							// (x,y,z,h,d,xAbs,yAbs,zAbs,hAbs)
							case 1:
								b.Faces.get(current-1).addTrou(new Trou(x, y, z, h, d, x, y, z, h));

								break; //par default face 1 est en absolu
							case 2:
								b.Faces.get(current-1).addTrou(new Trou(x, y, z, h, d, b.getLongueur()-z, y, x, -h));
								break;

							case 3:
								b.Faces.get(current-1).addTrou(new Trou(x, y, z, h, d, b.getLongueur()-x, y, b.getLargeur()-z, -h));
								break;

							case 4:
								b.Faces.get(current-1).addTrou(new Trou(x, y, z, h, d, z, y, b.getLargeur()-x, h));
								break;

							case 5:
								b.Faces.get(current-1).addTrou(new Trou(x, y, z, h, d, x, z,  b.getLargeur() - y, h));
								break;

							case 6: b.Faces.get(current-1).addTrou(new Trou(x, y, z, h, d, x, b.getHauteur() - z, y, -h));
								break;

							default:
								break;
						}

					}
					catch (Exception e) {
						//exit - on a l'autre chose que des chiffres dans le constructeur de trou
						System.out.print(e);
						System.out.println("syntax incorrecte le constructeur de trou");
						return sortie;
					}
				}
			}
	    } catch (FileNotFoundException e) {
			System.out.println("Le nom du fichier n'est pas correcte ou ce fichier n'existe pas");
			System.out.println("Le chemin saisi: " + fileName);
	    } catch (IOException e) {
			e.printStackTrace();
		}
		return b;
 }

 private boolean validation(double x, double y, double z, double h, double d, int current, Bloc b){
	 // helpers for validation:
	 double r = d/2;	// rayon d'un cylindre
	 switch(current){
		 case 1:
		 case 3:
		 	if ( x-r >=0 && x+r <= b.getLongueur() && y-r >= 0 && y+r <= b.getHauteur() && z-r >= 0 && z+r <= b.getLargeur()){
		 		return true;
			}
			return false;
		 case 2:
		 case 4:
			if ( x-r >= 0 && x+r <= b.getLargeur() && y-r >= 0 && y+r <= b.getHauteur() && z-r >= 0 && z+r <= b.getLongueur()){
				return true;
			}
			return false;
		 case 5:
		 case 6:
		 	if (x-r >=0 && x+r <= b.getLongueur() && y-r >= 0 && y+r <= b.getLargeur() && z-r >= 0 && z+r <= b.getHauteur()){
		 		return true;
			}
			return false;
		 default:
		 	return false;
	 }
 }

}
