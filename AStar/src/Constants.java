
/**
 * This class is used to easily manage various values and attributes of the A star algorithm.
 * @author Brandon Headrick, Jonathan Cherry
 *
 */
public interface Constants 
{
	/**This variable holds the value that signifies a floor tile  */
	public static final int FLOOR = 0;
	/**This variable holds the value that signifies a wall tile  */
	public static final int WALL = 1;
	/**This variable holds the value that signifies a walked on tile  */
	public static final int WALKED_ON = 2;
	/**This variable holds the value that signifies an adjacent tile  */
	public static final int ADJ_CHECKED = 3;
	/**This variable holds the value that signifies an avatar tile  */
	public static final int AVATAR = 4;
	/**This variable holds the value that signifies the start tile  */
	public static final int START = 5;
	/**This variable holds the value that signifies the finish tile  */
	public static final int FINISH = 6;
	
	/** number of X position tiles */
	public static final int COLS = 20;
	/** number of Y position tiles */
	public static final int ROWS = 20;
	
	/** the value of the horizontal or vertical movement for the Heuristic */
	public static final int DIRECT_MOVE = 5;
	/** The value of diagonal movement in any direction for the Heuristic**/
	public static final int DIAG_MOVE = 7;
}
