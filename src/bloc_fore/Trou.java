/**
 * 
 */
package bloc_fore;

import java.util.ArrayList;
import bloc_fore.Bloc;
/**
 * @author Borisov Vadim & Dreano Benjamin
 *
 */
public class Trou {
	private double x;
	private double y;
	private double z;
	private double h;	// profondeur
	private double d;	// diametre
	
	// coordonées absolues
	private double xAbs;
	private double yAbs;
	private double zAbs;
	private double hAbs;
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param h
	 * @param d
	 * @param xAbs
	 * @param yAbs
	 * @param zAbs
	 * @param hAbs
	 */
	public Trou(double x, double y, double z, double h, double d, double xAbs, double yAbs, double zAbs, double hAbs) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = h;
		this.d = d;
		this.xAbs=xAbs;
		this.yAbs=yAbs;
		this.zAbs=zAbs;
		this.hAbs=hAbs;
	}

	
	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}



	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}


	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}


    /**
     * @return the d
     */
    public double getD() {
        return d;
    }


    /**
     * @return the h
     */
    public double getH() {
        return h;
    }




	/**
	 * @return the xAbs
	 */
	public double getXAbs() {
		return xAbs;
	}



	/**
	 * @return the yAbs
	 */
	public double getYAbs() {
		return yAbs;
	}



	/**
	 * @return the zAbs
	 */
	public double getZAbs() {
		return zAbs;
	}



    /**
     * @return the hAbs
     */
	public double getHAbs() {
		return hAbs;
	}



	public String toString() {
		return "x: " + x+ " y: " + y + " z: " + z + " h:" + h + " d:" + d + "\n" + "            xAbs: " + xAbs + " yAbs: " + yAbs + " zAbs: " + zAbs;
	}
}
