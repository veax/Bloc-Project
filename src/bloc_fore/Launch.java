/**
 * 
 */
package bloc_fore;

/**
 * @author Borisov Vadim & Dreano Benjamin
 *
 */
public class Launch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lecteur l1 = new Lecteur();	// creation d'un objet Lecteur
		Bloc b =l1.lecture();		// lecture d'un fichier
		if (b != null){		// on evite nullPointerException si on retourne Bloc sortie et pas Bloc b
			b.calculerIntersections();
		}
		else {
			System.out.println("fichier incorrecte");
		}
	}

}
