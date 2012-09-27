/**
 * The avatar class is used to make one avatar Object.  The avatar object is used to keep track of the 
 * path's current position as it traverses a map to find it's destination.
 * @author Brandon Headrick, Jonathan Cherry
 *
 */
public class Avatar 
{
	/** Holds the avatars' current x position*/
	int xPosition = 0;
	/** Holds the avatar's current y position*/
	int yPosition = 0;
	/** Holds the avatar's g value*/
	int gVal = 0;
	
	/**
	 * The Avatar's constructor that is made with the current avatar's x and y positions set.
	 * @param yPos the avatar's current y position
	 * @param xPos the avatar's current x position
	 */
	public Avatar(int yPos, int xPos)
	{
		setXPosition(xPos);
		setYPosition(yPos);
	}
	
	public int getXPosition()
	{
		return xPosition;
	}
	
	public int getYPosition()
	{
		return yPosition;
	}
	
	public int getGVal()
	{
		return gVal;
	}
	
	public void setXPosition(int x)
	{
		xPosition = x;
	}
	
	public void setYPosition(int y)
	{
		yPosition = y;
	}
	
	public void setGVal(int g)
	{
		gVal = g;
	}
}
