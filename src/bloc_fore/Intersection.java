package bloc_fore;

/**
 * @author Borisov Vadim & Dreano Benjamin
 *
 */

// Static methods to calculate all types of intersections
public class Intersection {
    public static boolean isIntersect(Trou t1, int f1, Trou t2, int f2) {  // Trou t1 de Face f1 et Trou t2 de Face f2
        //***********************************************
        //*** f1 ne sera jamais plus grand que f2 car j au moins ou plus grand que i dans calculerIntersections()

        // 3 cas: faces-paralleles
        // 6 cas: face unique
        // 12 cas: faces-perpendiculaires


        //intersection de 2 circles (x,y,R):
        //SQRT((x0 - x1)^2 + (y0 - y1)^2) <= (R0 + R1)

		// intersection de cote (2 rectangles): RectA.Left < RectB.Right && RectA.Right > RectB.Left

        //***********************************************

        // 1. le cas: faces-parallèles (3 cas)
        double distPoints;
        // faces 1-3: (plan X-Y)
        if (f1 == 1 && f2 == 3) {
            distPoints = Math.sqrt(Math.pow(t1.getXAbs() - t2.getXAbs(), 2) + Math.pow(t1.getYAbs() - t2.getYAbs(), 2));
            if (distPoints <= (t1.getD() / 2 + t2.getD() / 2)) {
                if ((t1.getZAbs() < t2.getZAbs()) && (t1.getZAbs() + t1.getHAbs() > t2.getZAbs() + t2.getHAbs())) {
                    return true;
                }
            }
        }

        // face 2-4: (plan Z-Y)
        if (f1 == 2 && f2 == 4) {
            distPoints = Math.sqrt(Math.pow(t1.getZAbs() - t2.getZAbs(), 2) + Math.pow(t1.getYAbs() - t2.getYAbs(), 2));
            if (distPoints <= (t1.getD() / 2 + t2.getD() / 2)) {
                if ((t2.getXAbs() < t1.getXAbs()) && (t2.getXAbs() + t2.getHAbs() > t1.getXAbs() + t1.getHAbs())) {
                    return true;
                }
            }
        }

        // face 5-6: (plan X-Z)
        if (f1 == 5 && f2 == 6) {
            distPoints = Math.sqrt(Math.pow(t1.getXAbs() - t2.getXAbs(), 2) + Math.pow(t1.getZAbs() - t2.getZAbs(), 2));
            if (distPoints <= (t1.getD() / 2 + t2.getD() / 2)) {
                if ((t1.getYAbs() < t2.getYAbs()) && (t1.getYAbs() + t1.getHAbs() > t2.getYAbs() + t2.getHAbs())) {
                    return true;
                }
            }
        }
        //**********************************************************


        // 2. le cas: face-unique (6 cas)   (f1 == f2)
        if (f1 == f2) {
            distPoints = Math.sqrt(Math.pow(t1.getX() - t2.getX(), 2) + Math.pow(t1.getY() - t2.getY(), 2));
            if (distPoints <= (t1.getD() / 2 + t2.getD() / 2)) {
                if ((t1.getZ() < t2.getZ() + t2.getH()) && (t1.getZ() + t1.getH() > t2.getZ() - t2.getH())) {
                    return true;
                }
            }
        }
        //***********************************************************


        // 3. le cas: faces-perpendiculaires (12 cas)   (f1 == 1 && f2 == 2 || f1 == 1 && f2 == 4 && ....)

        else {

            // only horizontal faces case: (4 cas)
            if (f1 != 5 && f2 != 5 && f1 != 6 && f2 != 6) {
                return horizontal_only_intersection(t1, f1, t2, f2);
            }

            // horizontal-vertical faces cases: (8 cas)
            return vertical_horizontal_intersection(t1, f1, t2, f2);
        }
        return false;
    }

    //*************************************************************************************************

    private static boolean horizontal_only_intersection(Trou t1, int f1, Trou t2, int f2) {
        if (f1 == 2 && f2 == 3) {
            return circle_rect_intersection(t1, t2, false, "YZ") && circle_rect_intersection(t2, t1, false, "XY");
        }
        return circle_rect_intersection(t1, t2, false, "XY") && circle_rect_intersection(t2, t1, false, "YZ");

    }

    private static boolean vertical_horizontal_intersection(Trou t1, int f1, Trou t2, int f2) {
        if ((f1 == 1 && f2 == 5) || (f1 == 1 && f2 == 6) || (f1 == 3 && f2 == 5) || (f1 == 3 && f2 == 6)) {
            return circle_rect_intersection(t1, t2, true, "XY") && rect_rect_intersection(t2, t1, "YZ");
        }

        // 2-5, 2-6, 4-5, 4-6:
        return circle_rect_intersection(t1, t2, true, "YZ") && rect_rect_intersection(t2, t1, "XY");
    }


    // vert_dir is true if the rectangle has a vertical direction
    // plan defines which plan is current for intersection: X-Y or Y-Z
    private static boolean circle_rect_intersection(Trou t1, Trou t2, boolean vert_dir, String plan) {
        // t1 is always circle
        double leftSide, rightSide, topSide, bottomSide, rectLeft, rectRight, rectTop, rectBottom, circleRight, circleLeft, circleTop, circleBottom;
        topSide = t2.getD() / 2;
        bottomSide = -t2.getD() / 2;
        // check direction of rectangle
        if (t2.getHAbs() < 0 && !vert_dir) {
            leftSide = t2.getHAbs();
            rightSide = 0;
        } else if (t2.getHAbs() >= 0 && !vert_dir) {
            leftSide = 0;
            rightSide = t2.getHAbs();
        } else if (t2.getHAbs() < 0 && vert_dir) {
            leftSide = -t2.getD() / 2;
            rightSide = t2.getD() / 2;
            topSide = 0;
            bottomSide = t2.getHAbs();
        } else {                            // t2.getHAbs() >= 0 && vert_dir
            leftSide = -t2.getD() / 2;
            rightSide = t2.getD() / 2;
            topSide = t2.getHAbs();
            bottomSide = 0;
        }

        // check plan
        if (plan == "XY") {
            rectLeft = t2.getXAbs() + leftSide;
            rectRight = t2.getXAbs() + rightSide;
            rectTop = t2.getYAbs() + topSide;
            rectBottom = t2.getYAbs() + bottomSide;
            circleRight = t1.getXAbs() + t1.getD() / 2;
            circleLeft = t1.getXAbs() - t1.getD() / 2;
            circleTop = t1.getYAbs() + t1.getD() / 2;
            circleBottom = t1.getYAbs() - t1.getD() / 2;
        } else if (plan == "YZ") {            // plan YZ
            rectLeft = t2.getZAbs() + leftSide;
            rectRight = t2.getZAbs() + rightSide;
            rectTop = t2.getYAbs() + topSide;
            rectBottom = t2.getYAbs() + bottomSide;
            circleRight = t1.getZAbs() + t1.getD() / 2;
            circleLeft = t1.getZAbs() - t1.getD() / 2;
            circleTop = t1.getYAbs() + t1.getD() / 2;
            circleBottom = t1.getYAbs() - t1.getD() / 2;
        } else {
            return false;    // error in plan name (not XY and not YZ)
        }

        return (rectLeft < circleRight) && (rectRight > circleLeft) && (rectTop > circleBottom) && (rectBottom < circleTop);

    }

    // intersection de cote: RectA.Left < RectB.Right && RectA.Right > RectB.Left  (check horizontal overlap)
    // not necessary to check vertical overlap because of intersection check in another plan
    // rect_rect_intersection used only for intersection with faces 5 and 6 (vert_dir by default)
    private static boolean rect_rect_intersection(Trou t1, Trou t2, String plan) {
        // vertical rectangle is always t1
        double leftSide, rightSide, rect1Left, rect1Right, rect2Left, rect2Right;
        if (t2.getHAbs() < 0) {
            leftSide = t2.getHAbs();
            rightSide = 0;
        } else {
            leftSide = 0;
            rightSide = t2.getHAbs();
        }

        // check plan
        if (plan == "XY") {
            rect2Left = t2.getXAbs() + leftSide;
            rect2Right = t2.getXAbs() + rightSide;
            rect1Left = t1.getXAbs();
            rect1Right = t1.getXAbs();
        } else if (plan == "YZ") {            // plan YZ
            rect2Left = t2.getZAbs() + leftSide;
            rect2Right = t2.getZAbs() + rightSide;
            rect1Left = t1.getZAbs();
            rect1Right = t1.getZAbs();
        } else {
            return false; // errors in plan name
        }

        return rect2Left < rect1Right + t1.getD() / 2 && rect2Right > rect1Left - t1.getD() / 2;
    }

}


