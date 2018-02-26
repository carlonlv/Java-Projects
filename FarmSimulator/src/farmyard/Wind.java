package farmyard;

/** If the wind was last blowing up then it is likely to keep blowing up. Same for left/right. */
public class Wind {

    /* 1 is blowing Up, -1 is blowing Down. 0 is no vertical wind. */
    private static int blowingUp = 0;

    /* 1 is blowing Right, -1 is blowing Left. 0 is no horizontal wind. */
    private static int blowingRight = 0;

    /**
     * @return 1 is blowing Up, -1 is blowing Down. 0 is no vertical wind.
     */
    public static int getBlowingUp() {
        return blowingUp;
    }

    /**
     * @return 1 is blowing Right, -1 is blowing Left. 0 is no horizontal wind.
     */
    public static int getBlowingRight() {
        return blowingRight;
    }

    /**
     * @param blowingUp the new vertical direction of the wind.
     */
    private static void setBlowingUp(int blowingUp) {
        Wind.blowingUp = blowingUp;
    }

    /**
     * @param blowingRight the new horizontal direction of the wind.
     */
    private static void setBlowingRight(int blowingRight) {
        Wind.blowingRight = blowingRight;
    }

    /**
     * Generates wind.
     */
    public static void windGenerator(){
        double d = Math.random();
        if (getBlowingUp() != 0) {
            if (d < 0.1) {
                //Wind is changing vertical direction.
               setBlowingUp(-getBlowingUp());
            } else if (d < 0.2){
                //Wind stops vertically.
                setBlowingUp(0);
            }
        } else {
            if (d < 0.1) {
                //Wind starts blowing up.
                setBlowingUp(1);
            } else if (d < 0.2) {
                //Wind starts blowing down.
                setBlowingUp(-1);
            }
        }
        d = Math.random();
        if (getBlowingRight() != 0) {
            if (d < 0.1) {
                //Wind is changing horizontal direction.
                setBlowingRight(-getBlowingRight());
            } else if (d < 0.2){
                //Wind stops horizontally.
                setBlowingRight(0);
            }
        } else {
            if (d < 0.1) {
                //Wind starts blowing right.
                setBlowingRight(1);
            } else if (d < 0.2) {
                //Wind starts blowing left.
                setBlowingRight(-1);
            }
        }
    }
}
