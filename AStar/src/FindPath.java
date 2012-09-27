import java.util.ArrayList;
import javax.swing.JButton;

/**
 * this class uses the map object and A* algorithm to find the best path to the map's finishing node.
 * @author Brandon Headrick, Jonathan Cherry
 *
 */
public class FindPath implements Constants
{

	/** an instance of the Map object.  Used to traverse the actual map */
	private Map map;
	/** holds every tile on the map */
	private Tile[][] tiles;
	/** represents the current position of the "person" traversing the maze.  This holds the avatar's current position*/
	private Avatar avatar;
	/** holds the 8 tiles adjacent to the avatar's current position*/
	private ArrayList<Tile> adjacency;
	/** holds the tile that has the current low F cost.  It will be one of the tiles in the current adjacency list*/
	private Tile lowFTile;
	/**the variable that is toggled based on if an exception is thrown during the application's execution or not*/
	private boolean exceptionFound = false;
	
	/** The constructor for the FindPath Class
	 * Sets up the instance variables.
	 */
	public FindPath(Map m)
	{
		map = m;
		tiles = map.getTiles();
		avatar = map.getAvatar();
		adjacency = map.getAdjacent();
	}
	
	/**
	 * Basic logic of A star.  Also includes a loop that continues until either a dead end is found or
	 * the avatar makes it to the correct final destination
	 */
	public void executePathFinding()
	{
		//loop logic until the avatar reaches the finishing tile
			while(true)
			{
				
			//populate the adjacency arrayList with the current tiles adjacent to the avatars current posision
			//also, the GCost is calculated here
			findAdjacentTiles();
			
			calculateHCost();
			calculateFCost();
			
			findLowestFCost();
			
			//if the lowFTile isn't given a value, then the pathfinding must have failed.  The only reason
			//it could fail is if the final destination can not be traversed to.  Therefore, tell the user
			//that this path cannot be found.
			if(lowFTile == null)
			{
				System.out.println("CAN'T SOLVE");
				break;
			}
			
			//update the visited tiles.
			addToWalkedOn();
			//move the avatar to the new position of the lowest F tile on the map.
			moveAvatar();
			
			//This breaks the loop if the avatar has successfully made it to the finish of the map
			if((avatar.getXPosition() == map.getFinishCoordX()) && (avatar.getYPosition() == map.getFinishCoordY()) || exceptionFound)
			{
				break;
			}
		}
	}
	
	/**
	 * used to find the adjacent tiles to the current tile in order to add them to the adjacency list.
	 */
	public void findAdjacentTiles()
	{
		int currXPos = avatar.getXPosition();
		int currYPos = avatar.getYPosition();
		adjacency.clear();

		//now, get the (up to) 8 tiles adjacent to the avatar's current position.  Only adding to the adjacency list the tile
		//if it is in the bounds of the map AND it is a floor tile.  If neither of these are true, they are assigned the value
		//of null to signify that it will not be taken into account when plotting the path.
		//every if statement will also assign each tile a G cost.  14 for diagonal movements, 10 for direct movements.
		//every if statement is also given an H cost using the Manhattan method.
		
		/* upper left tile, tile directly above, and upper right tile tests */
		if(currXPos - 1 >= 0 && currYPos - 1 >= 0 && (map.getType(currYPos-1, currXPos-1) == FLOOR || map.getType(currYPos-1, currXPos-1) == ADJ_CHECKED || 
				map.getType(currYPos-1, currXPos-1) == FINISH))
		{
			//add tiles to the adjacency list.
			adjacency.add(map.getTile(currYPos-1, currXPos-1));
			map.getTile(currYPos-1, currXPos-1).setGCost(avatar.getGVal() + DIAG_MOVE);
		}
		else
		{
			adjacency.add(null);
		}
		if(currYPos -1 >= 0 && (map.getType(currYPos-1, currXPos) == FLOOR || map.getType(currYPos-1, currXPos) == ADJ_CHECKED ||
				map.getType(currYPos-1, currXPos) == FINISH))
		{
			adjacency.add(map.getTile(currYPos-1, currXPos));
			map.getTile(currYPos-1, currXPos).setGCost(avatar.getGVal() + DIRECT_MOVE);
		}
		else
		{
			adjacency.add(null);
		}
		if((currXPos + 1) <= (ROWS-1) && currYPos -1 >= 0 && (map.getType(currYPos-1, currXPos+1) == FLOOR || map.getType(currYPos-1, currXPos+1) == ADJ_CHECKED ||
				map.getType(currYPos-1, currXPos+1) == FINISH))
		{
			adjacency.add(map.getTile(currYPos-1, currXPos+1));		
			map.getTile(currYPos-1, currXPos+1).setGCost(avatar.getGVal() + DIAG_MOVE);			
		}
		else
		{
			adjacency.add(null);
		}
		
		/* left of tile, current tile, right of tile */
		if(currXPos -1 >= 0 && (map.getType(currYPos, currXPos-1) == FLOOR || map.getType(currYPos, currXPos-1) == ADJ_CHECKED ||
				map.getType(currYPos, currXPos-1) == FINISH))
		{
			adjacency.add(map.getTile(currYPos, currXPos-1));
			map.getTile(currYPos, currXPos-1).setGCost(avatar.getGVal() + DIRECT_MOVE);
		}
		else
		{
			adjacency.add(null);
		}

		//Add the middle tile just for housekeeping.
		adjacency.add(null);

		if(currXPos + 1 <= (ROWS-1)&& (map.getType(currYPos, currXPos+1) == FLOOR || map.getType(currYPos, currXPos+1) == ADJ_CHECKED ||
				map.getType(currYPos, currXPos+1) == FINISH))
		{
			adjacency.add(map.getTile(currYPos, currXPos+1));
			map.getTile(currYPos, currXPos+1).setGCost(avatar.getGVal() + DIRECT_MOVE);
		}
		else
		{
			adjacency.add(null);
		}
		
		/* lower left tile, tile directly below, and lower right tile test */
		if(currXPos - 1 >= 0 && currYPos + 1 <= COLS-1 && (map.getType(currYPos+1, currXPos-1) == FLOOR || map.getType(currYPos+1, currXPos-1) == ADJ_CHECKED ||
				map.getType(currYPos+1, currXPos-1) == FINISH))
		{
			adjacency.add(map.getTile(currYPos+1, currXPos-1));
			map.getTile(currYPos+1, currXPos-1).setGCost(avatar.getGVal() + DIAG_MOVE);
		}
		else
		{
			adjacency.add(null);
		}
		if(currYPos + 1 <= COLS-1 && (map.getType(currYPos+1, currXPos) == FLOOR || map.getType(currYPos+1, currXPos) == ADJ_CHECKED ||
				map.getType(currYPos+1, currXPos) == FINISH))
		{
			adjacency.add(map.getTile(currYPos+1, currXPos));
			map.getTile(currYPos+1, currXPos).setGCost(avatar.getGVal() + DIRECT_MOVE);
		}
		else
		{
			adjacency.add(null);
		}
		if(currXPos + 1 <= (ROWS-1) && currYPos + 1 <= COLS-1 && (map.getType(currYPos+1, currXPos+1) == FLOOR || map.getType(currYPos+1, currXPos+1) == ADJ_CHECKED ||
				map.getType(currYPos+1, currXPos+1) == FINISH))
		{
			adjacency.add(map.getTile(currYPos+1, currXPos+1));		
			map.getTile(currYPos+1, currXPos+1).setGCost(avatar.getGVal() + DIAG_MOVE);
		}
		else
		{
			adjacency.add(null);
		}
		
		//check to make sure that the avatar goes all the way around a wall's edge
		//if the top middle tile is a wall, set the top left and top right tiles to something that can't be traversed
		if(map.getType(currYPos-1, currXPos) == WALL)
		{
			adjacency.set(0, null);
			adjacency.set(2, null);
		}
		
		//if the middle left tile is a wall, set the top left and bottom left tiles to something that can't be traversed
		if(map.getType(currYPos, currXPos-1) == WALL)
		{
			adjacency.set(0, null);
			adjacency.set(6, null);
		}
		
		//if the middle right tile is a wall, set the top right and bottom right tiles to something that can't be traversed
		if(map.getType(currYPos, currXPos+1) == WALL)
		{
			adjacency.set(2, null);
			adjacency.set(8, null);
		}
		
		//if the bottom middle tile is a wall, set the bottom left and bottom right tiles to something that can't be traversed
		if(map.getType(currYPos+1, currXPos) == WALL)
		{
			adjacency.set(6, null);
			adjacency.set(8, null);
		}
		
		
		//set tiles that were found to be adjacent to the correct type.
		for(int i = 0; i<adjacency.size(); i++)
		{
			if(adjacency.get(i) != null)
			{
				map.setType(adjacency.get(i).getY(), adjacency.get(i).getX(), ADJ_CHECKED);				
			}
		}
		
	}//end method
	
	/** calculates the HCost based on the Manhattan process (get the estimated distance to the finish line
	 * based on only vertical and horizontal movement). */
	private void calculateHCost()
	{
		for(int i = 0; i<adjacency.size(); i++)
		{
			if(adjacency.get(i) != null)
			{
				int tempX = adjacency.get(i).getX();
				int tempY = adjacency.get(i).getY();
				
				tempX = Math.abs((map.getFinishCoordX()) - tempX);
				tempY = Math.abs((map.getFinishCoordY()) - tempY);
				
				adjacency.get(i).setHCost((tempX + tempY)*DIRECT_MOVE);
			}
		}
	}
	/** Calculate the F cost by adding the G and H costs together.*/
	private void calculateFCost()
	{
		for(int i = 0; i<adjacency.size(); i++)
		{
			if(adjacency.get(i) != null)
			{
				int tempX = adjacency.get(i).getX();
				int tempY = adjacency.get(i).getY();
				
				adjacency.get(i).computeFCost();
			}
		}
	}
	/** This finds the tile with the lowest F cost of all of the current adjacent's F costs*/
	private void findLowestFCost() 
	{
		lowFTile = null;
		
		for(int i = 0; i<adjacency.size(); i++)
		{
			if(adjacency.get(i) != null)
			{

				if(lowFTile == null)
					lowFTile = adjacency.get(i);
				
				if(adjacency.get(i).getFCost() < lowFTile.getFCost())
					lowFTile = adjacency.get(i);

			}
			
		}
		//HERE, NEED TO CHECK FOR LOWFTILE TO BE NOT NULL.  COULD BE NULL IT HIT DEAD END.
		if(lowFTile == null)
		{
			findNewLowFCost();
			System.out.println("DEAD END HIT");
		}
	}
	
	private void addToWalkedOn()
	{
		map.setType(avatar.getYPosition(), avatar.getXPosition(), WALKED_ON);
	}
	
	/** move the avatar to the new position which was calculated based on the F cost. */
	private void moveAvatar()
	{
		avatar.setXPosition(lowFTile.getX());
		avatar.setYPosition(lowFTile.getY());	
		avatar.setGVal(lowFTile.getGCost());
			
		map.setType(avatar.getYPosition(), avatar.getXPosition(), AVATAR);
		
	}
	/** if the avatar hits a dead end, it needs to find a new tile to begin searching from again.*/
	void findNewLowFCost()
	{
		//set new lowTile to one of the adjacents in the tile list.  
		Tile tempLowest = null;
		for(int i = 0; i<COLS; i++)
		{
			for(int k = 0; k<ROWS; k++)
			{
				if(map.getType(i,k) == ADJ_CHECKED)
				{
					if(tempLowest == null)
					{
						tempLowest = map.getTile(i, k);
					}
					else if(tempLowest.getFCost() > map.getTile(i, k).getFCost() )
					{
						tempLowest = map.getTile(i, k);
					}
				}
			}
			
			lowFTile = tempLowest;
		}
		
	}
	
}
