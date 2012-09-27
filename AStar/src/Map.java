import java.util.ArrayList;
import java.util.Random;

/**
 * A basic grid, or "map," that will be used by the application to visually represent path finding with the A* Algorithm
 * @author Brandon Headrick, Jonathan Cherry
 *
 */
public class Map implements Constants
{
	/** This is the finish x position*/
	private int finishX;
	/** the finish y position*/
	private int finishY;
	/** the start x position*/
	private int startX;
	/** the start y position*/
	private int startY;

	/** This array represents the "tiles" or area that the avatar will traverse through */
	private Tile[][] tiles;
	
	/** The object that will be traversing the maze.  i.e., this is what will be traversing along the path to reach the destination */
	Avatar avatar;
	
	/**	The array that holds the information about what tiles were visited already */
	private boolean[][] walkedOn;
	
	/** populated with the coordinates of the tiles adjacent to the avatar. */
	private ArrayList<Tile> adjacent;
	
	/**
	 * The default constructor for a new Map.  Sets up the map object to be able to be traversed properly
	 */
	public Map()
	{
		finishX = 0;
		finishY = 0;
		Random rand = new Random();

		tiles = new Tile[COLS][ROWS];
		
		walkedOn = new boolean[COLS][ROWS];
		adjacent = new ArrayList();
		
		//initialize all the tiles array to be full of tile objects
		for(int i = 0; i<COLS; i++)
		{
			for(int k = 0; k<ROWS; k++)
			{
				tiles[i][k] = new Tile(i,k);
			}
		}
		
		startX = 2;
		startY = 2;

		//make the outlying tiles walls
		for(int n = 0; n<COLS; n++)
		{
			setType(n, 0, WALL);
			setType(n, ROWS-1, WALL);
		}
				
		for(int n = 0; n<ROWS; n++)
		{
			setType(0, n, WALL);
			setType(COLS-1, n, WALL);
		}
				
				
		finishX = 17;
		finishY = 17;
				
		tiles[finishY][finishX].setType(FINISH);
		
		//set the begining tile to the start tile
		setType(startY,startX,START);
		//initialize avatar object
		avatar = new Avatar(getStartCoordY(),getStartCoordX());
		
		//set the avatar's position to an avatar type
		setType(avatar.getYPosition(), avatar.getXPosition(), AVATAR);
	}
	
	/** Method to set individual tiles to the desired type **/
	public void setType(int y, int x, int tileType)
	{
		tiles[y][x].setType(tileType);
	}
	
	public void setTile(int y, int x, Tile t)
	{
		tiles[y][x] = t;
	}

	/** retrieve the passed in tile's type */
	public int getType(int y, int x)
	{
		return tiles[y][x].getType();
	}
	
	public Tile getTile(int y, int x)
	{
		return tiles[y][x];
	}
	
	public ArrayList getAdjacent()
	{
		return adjacent;
	}
	
	public Tile[][] getTiles()
	{
		return tiles;
	}
	
	public Avatar getAvatar()
	{
		return avatar;
	}
	
	public int getFinishCoordY()
	{
		return finishY;
	}
	public int getFinishCoordX()
	{
		return finishX;
	}
	
	public int getStartCoordY()
	{
		return startY;
	}
	public int getStartCoordX()
	{
		return startX;
	}
}
