import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/** 
 * This class is used to create a gui and user interface inside a panel
 * @author Brandon Headrick, Jonathan Cherry
 *
 */
public class UI extends JPanel implements Constants
{
	/** The instance of the Map class.  Used to retrieve tile information*/
	private Map map;
	/** This variable holds the number of the current map that is displayed for the user.*/
	private int mapNum = 0;
	/** flag to allow/disallow the pathfinding to be executed*/
	private boolean startPushed = false;	
	/** This array holds the visual representation of each tiles type by changing color based on
	 * the passed in tile type.*/
	private JButton[][] gridIndex;
	/** This button saves the user's custom map*/
	JButton saveButton;
	/** This button loads the user's custom map*/
	JButton loadButton;
	/** This button resets the entire map to allow the user to create a new map from scratch*/
	JButton resetButton;
	/** This starts the pathfinding algorithm on the map*/
	JButton startButton;
	/** This changes the map to the next pre-made map which is held in the application*/
	JButton nextMap;
	/** an instance of the FindPath class.  This is used to turn the pathfinding on.*/
	FindPath path;
		
	/**
	 * Default constructor for the UI class.  Sets up the panel, layouts, and user interface.
	 */
	public UI()
	{
		this.setLayout(new BorderLayout(10,10));
		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());

		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(COLS,ROWS));
		
		setPreferredSize (new Dimension(500, 500));
		setBackground(Color.WHITE);
		
		//Make an index array that is as big as the gridbutton layout will be.
		gridIndex = new JButton[COLS][ROWS];
		for(int i = 0; i<COLS; i++)
		{
			for(int k = 0; k<ROWS; k++)
			{
				gridIndex[i][k] = new JButton("");
				gridIndex[i][k].addActionListener(new ButtonListener());
				gridPanel.add(gridIndex[i][k]);
			}
		}
		
		JPanel buttonPanel = new JPanel();
		saveButton = new JButton("Save Map");
		loadButton = new JButton("Load Map");
		resetButton = new JButton("Reset");
		startButton = new JButton("Start");
		nextMap = new JButton("Next Map");
		
		saveButton.addActionListener(new ButtonListener());
		loadButton.addActionListener(new ButtonListener());
		resetButton.addActionListener(new ButtonListener());
		startButton.addActionListener(new ButtonListener());
		nextMap.addActionListener(new ButtonListener());
		
		buttonPanel.add(startButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);
		buttonPanel.add(nextMap);
		
		borderPanel.add(gridPanel, BorderLayout.CENTER);
		borderPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.add(gridPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

	}
	/**
	 * Constructor for the UI class that takes in a Map object in order to more easily find information about
	 * the grid
	 * @param m a Map object.
	 */
	public UI(Map m)
	{
		//constructor chain to default constructor
		this();
		map = m;
		path = new FindPath(map);
	}
	/**
	 * The method that is used to paint everything on the panel
	 */
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		//iterate through the entire gridIndex array and change it's color based on the type that it is.
		for(int i = 0; i<(COLS); i++)
		{
			for(int k = 0; k<ROWS; k++)
			{
				//if the tile is a wall, don't ever change it's color
				if(map.getType(i, k) == WALL)
				{
					gridIndex[i][k].setBackground(Color.BLACK);
				}
				else
				{
					if(map.getType(i, k) == FLOOR)
					{
						gridIndex[i][k].setBackground(Color.WHITE);
					}
					
					if(map.getType(i, k) == FINISH)
					{
						gridIndex[i][k].setBackground(Color.RED);
					}

					if(map.getType(i, k) == ADJ_CHECKED)
					{
						gridIndex[i][k].setBackground(Color.CYAN);
					}
					
					if(map.getType(i, k) == WALKED_ON)
					{
						gridIndex[i][k].setBackground(Color.GRAY);
					}
					
					if(map.getType(i, k) == START)
					{
						gridIndex[i][k].setBackground(Color.BLUE);
					}
					
					if(map.getType(i, k) == AVATAR)
					{
						gridIndex[i][k].setBackground(Color.GREEN);
					}
				}
			}
		}
	}
	
	/** 
	 * The private inner class that listens for button clicks
	 */
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			
			String mapName = new String();
			
			//choose one of the pre-made maps
			if(event.getSource() == nextMap)
			{
				startPushed = false;
				
				if(mapNum == 0)
				{
					
					mapName = "DeadEndsMaze.txt";
					mapNum++;
				}
				else if(mapNum == 1)
				{
				
					mapName = "HorizVertMaze.txt";
					mapNum++;
				}
				else if(mapNum == 2)
				{
				
					mapName = "TheTwoLs.txt";
					mapNum++;
				}
				else if(mapNum == 3)
				{
				
					mapName = "PitAvoidance.txt";
					mapNum = 0;
				}
				
				//below actually populates every index of the map with walls or floors
				int[][] types = new int[COLS][ROWS];
				try
				{
					Scanner scan = new Scanner(new File(mapName));
					for(int y = 0;  y < COLS; y++)
					{
						for(int x = 0; x < ROWS; x++)
						{
							map.setType(y, x, scan.nextInt());
						}
					}
					
					//set the avatar's default start position
					map.getAvatar().setXPosition(map.getStartCoordX());
					map.getAvatar().setYPosition(map.getStartCoordY());
					
					map.setType(map.getAvatar().getYPosition(), map.getAvatar().getXPosition(), AVATAR);
					
					//set the finish position
					map.setType(map.getFinishCoordY(), map.getFinishCoordX(), FINISH);
				}
				catch(IOException ioe){}

				//change the color of the newly changed types
				for(int i = 1; i<(COLS-1); i++)
				{
					for(int k = 1; k<ROWS-1; k++)
					{
						//if the tile is a wall, don't ever change it's color
						if(event.getSource() == gridIndex[i][k])
						{
							if(gridIndex[i][k].getBackground() == Color.WHITE)
							{
								gridIndex[i][k].setBackground(Color.BLACK);
								map.setType(i, k, WALL);
							}
							else if(gridIndex[i][k].getBackground() == Color.BLACK)
							{
								map.setType(i, k, FLOOR);
								gridIndex[i][k].setBackground(Color.WHITE);
							}
						}
					}//end inner for
				}//end outer for
				repaint();
				
			}
					
			//when start is pressed, initiate pathfinding
			if(event.getSource() == startButton && !startPushed)
			{
				startPushed = true;
				path.executePathFinding();
			}
			
			//when the reset button is pressed, clear the map
			else if(event.getSource() == resetButton)
			{
				startPushed = false;
				
				for(int i = 1; i<(COLS-1); i++)
				{
					for(int k = 1; k<ROWS-1; k++)
					{
						//make everything a floor
						gridIndex[i][k].setBackground(Color.WHITE);
						map.setType(i, k, FLOOR);

					}//end inner for
				}//end outer for
						
				//at the end, place the avatar at the correct position
				map.getAvatar().setXPosition(map.getStartCoordX());
				map.getAvatar().setYPosition(map.getStartCoordY());
				
				map.setType(map.getAvatar().getYPosition(), map.getAvatar().getXPosition(), AVATAR);
				
				//set the finish position.
				map.setType(map.getFinishCoordY(), map.getFinishCoordX(), FINISH);
				
			}
			//if the save button is pressed, then save the map layout to a file
			else if(event.getSource() == saveButton)
			{
				
				try{
					PrintWriter writer = new PrintWriter(new File("test.txt"));
					
					for(int i = 0; i < COLS; i++)
					{
						for(int j = 0; j < ROWS; j++)
						{	
							if(map.getType(i, j) == WALL)
								writer.print(map.getType(i, j) + " "); //y, x
							else
								writer.print(FLOOR + " "); //y, x
								
						}
						writer.println();
					}
					writer.flush();
					writer.close();
				}
				catch(IOException ioe){}
			}
			//if the load button is pressed, then load the user's custom map
			else if(event.getSource() == loadButton)
			{
				startPushed = false;
				int[][] types = new int[COLS][ROWS];
				try{
					Scanner scan = new Scanner(new File("test.txt"));
					for(int y = 0;  y < COLS; y++)
					{
						for(int x = 0; x < ROWS; x++)
						{
							map.setType(y, x, scan.nextInt());
						}
					}
					
					//at the end, place the avatar at the correct position
					map.getAvatar().setXPosition(map.getStartCoordX());
					map.getAvatar().setYPosition(map.getStartCoordY());
					
					map.setType(map.getAvatar().getYPosition(), map.getAvatar().getXPosition(), AVATAR);
					
					//set the finish position
					map.setType(map.getFinishCoordY(), map.getFinishCoordX(), FINISH);
				}
				catch(IOException ioe){}
			}
			//color the tiles
			for(int i = 1; i<(COLS-1); i++)
			{
				for(int k = 1; k<ROWS-1; k++)
				{
					//if the tile is a wall, don't ever change it's color
					if(event.getSource() == gridIndex[i][k])
					{
						if(gridIndex[i][k].getBackground() == Color.WHITE)
						{
							gridIndex[i][k].setBackground(Color.BLACK);
							map.setType(i, k, WALL);
						}
						else if(gridIndex[i][k].getBackground() == Color.BLACK)
						{
							map.setType(i, k, FLOOR);
							gridIndex[i][k].setBackground(Color.WHITE);
						}
					}
				}//end inner for
			}//end outer for
			repaint();
			
			
		}
	}
	
}
