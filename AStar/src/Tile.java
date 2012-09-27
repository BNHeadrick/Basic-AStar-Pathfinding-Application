
/**
 * The class that makes Tile objects that will be traversed by the avatar on the map
 * @author Brandon Headrick, Jonathan Cherry
 *
 */
public class Tile 
{

	/** The tile's x position*/
	int x;
	/** The tile's y position*/
	int y;
	/** The tile's type: such as a wall, a floor, or the avatar*/
	int type;
	/**
	 * the value used to determine the next square to traverse in the map.  F = H + G
	 */
	int fCost;
	/**
	 * the estimated movement cost to move from that given square on the grid to the final destination, point B.
	 */
	int hCost;
	/**
	 * the movement cost to move from the starting point A to a given square on the grid, following the path generated to get there.
	 */
	int gCost;
	
	/** Default constructor of Tile.  Used to initialize null Tiles*/
	public Tile()
	{
		
	}
	
	/**
	 * constructor for Tile.  Used to make new tile's at a current position and initialize variables
	 * @param y the current' tile's y position
	 * @param x the current tile's x position
	 */
	public Tile(int y, int x)
	{
		this.y = y;
		this.x = x;
		fCost = 0;
		hCost = 0;
		gCost = 0;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getFCost()
	{
		return fCost;
	}
	
	public int getHCost()
	{
		return hCost;
	}
	public int getGCost()
	{
		return gCost;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}

	public void setFCost(int f)
	{
		fCost = f;
	}
	
	public void setGCost(int g)
	{
		gCost = g;
	}
	
	/**
	 * Compute the F cost by adding G and H costs together.
	 */
	public void computeFCost()
	{
		fCost = gCost + hCost;
	}
	
	public void setHCost(int h)
	{
		hCost =  h;
	}
	public void addGCost(int g)
	{
		
		gCost = gCost + g;
	}
}
