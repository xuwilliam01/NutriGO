package Engine;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The GUI for the nutrition program
 * @author Alex Raita && William Xu
 * @version 22 November, 2015
 */
public class NutritionGUI {

	//Frame and panels for program
	private static JFrame frame = new JFrame();
	private static MenuPanel menuPanel = new MenuPanel();
	private static SearchPanel searchPanel;
	private static FoodPanel foodPanel;
	private static NewItemPanel newItemPanel;
	private static LoadingPanel loadingPanel;
	private final static int WIDTH_SCREEN = 650;
	private final static int HEIGHT_SCREEN = 800;

	//Engine
	private static Engine engine = new Engine();

	/**
	 * Reacts to the GO button on the menu panel
	 * @author Alex Raita && William Xu
	 * @version 22 November, 2015
	 */
	private static class GoButtonPressed implements ActionListener
	{
		/**
		 * Switch to the loading panel
		 */
		public void actionPerformed(ActionEvent e) {
			frame.remove(menuPanel);
			menuPanel = null;
			loadingPanel = new LoadingPanel();
			frame.add(loadingPanel);
			frame.setVisible(true);
		}
	}

	/**
	 * When loading is finished, switch to the search panel
	 * @param timer the timer that needs to be stopped
	 */
	public static void loadingFinished (Timer timer)
	{
		//Stop the timer and import data from files
		timer.stop();
		try {
			engine.importData();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//Switch to the search panel
		frame.remove(loadingPanel);
		loadingPanel = null;
		searchPanel = new SearchPanel();
		frame.add(searchPanel);
		frame.setVisible(true);
	}

	/**
	 * Reacts to the search button on the food panel
	 * @author Alex Raita && William Xu
	 * @version 22 November, 2015
	 */
	private static class SearchButtonPressed implements ActionListener 
	{
		/**
		 * Switch to the search panel
		 */
		public void actionPerformed(ActionEvent e) {
			//Switch to the search Panel
			frame.remove(foodPanel);
			foodPanel = null;
			searchPanel = new SearchPanel();
			frame.add(searchPanel);
			frame.setVisible(true);

		}

	}

	/**
	 * Reacts to the new item button on the search panel
	 * @author Alex Raita && William Xu
	 * @version 22 November, 2015
	 */
	public static class ItemButtonListener implements ActionListener
	{
		Timer timer;

		/**
		 * Constructor sets the timer
		 * @param timer the timer to be set
		 */
		public ItemButtonListener(Timer timer)
		{
			this.timer = timer;
		}

		/**
		 * Switch to the new item panel
		 */
		public void actionPerformed(ActionEvent e) {
			timer.stop();
			frame.remove(searchPanel);
			searchPanel = null;
			newItemPanel = new NewItemPanel(false,null);
			frame.add(newItemPanel);
			frame.setVisible(true);
		}		
	}

	/**
	 * Reacts to the back button on the new item panel
	 * @author Alex Raita && William Xu
	 * @version 22 November, 2015
	 */
	public static class BackButtonPressed implements ActionListener
	{
		boolean isFoodPanel = false;
		Food food;

		/**
		 * Constructor. Note that the new item panel also acts as the modify panel, and the boolean 
		 * isFoodPanel tells us whether we were adding a new panel or modifying a food
		 * @param isFoodPanel whether the program is trying to go back to the food panel or the search panel
		 * @param food the food that was on the food panel
		 */
		public BackButtonPressed(boolean isFoodPanel,Food food)
		{
			if(isFoodPanel)
			{
				this.isFoodPanel = true;
				this.food = food;
			}
		}

		/**
		 * Switches to the food panel or search panel, depending on the isFoodPanel variable
		 */
		public void actionPerformed(ActionEvent e) {
			//Switch back to the appropriate panel
			frame.remove(newItemPanel);
			newItemPanel = null;
			if(isFoodPanel)
			{
				foodPanel = new FoodPanel(food);
				frame.add(foodPanel);
			}
			else
			{
				searchPanel = new SearchPanel();
				frame.add(searchPanel);
			}
			frame.setVisible(true);
		}

	}

	/**
	 * Opens the food description given a food
	 * @param food the food which the food panel will reference
	 */
	public static void openFoodDescription(Food food)
	{
		//Switch to the food panel
		frame.remove(searchPanel);
		searchPanel = null;
		foodPanel = new FoodPanel(food);
		frame.add(foodPanel);
		frame.setVisible(true);
	}

	/**
	 * Reacts to the modify button on the food panel
	 * @author Alex Raita && William Xu
	 * @version 22 November, 2015
	 */
	public static class ModifyButtonPressed implements ActionListener
	{
		Food food;

		/**
		 * Constructor that stores the food to be modified
		 * @param food the food to be modified
		 */
		public ModifyButtonPressed(Food food)
		{
			this.food = food;
		}

		/**
		 * Switch to the new item panel
		 */
		public void actionPerformed(ActionEvent e) {
			frame.remove(foodPanel);
			foodPanel = null;
			//Tells the constructor of the new item panel, that a food is being modified, not added
			newItemPanel = new NewItemPanel(true, food);
			frame.add(newItemPanel);
			frame.setVisible(true);		
		}

	}

	/**
	 * Creates the loading panel
	 * @author Alex Raita && William Xu
	 * @version 22 November, 2015
	 */
	private static class LoadingPanel extends JPanel implements ActionListener
	{
		Timer loadingTimer = new Timer(100,this);
		/**
		 * Constructor. The timer doesn't seem to have a point, but we needed it
		 * to prevent some weird bugs with switching screens
		 */
		public LoadingPanel()
		{
			setLayout(null);
			setSize(HEIGHT_SCREEN,WIDTH_SCREEN);
			loadingTimer.start();
		}

		/**
		 * Draw the images on the screen
		 */
		public void paintComponent(Graphics g)
		{
			Image loading = new ImageIcon("Loading.png").getImage();
			Image nutriGo = new ImageIcon("LargeNutriGo.png").getImage();
			g.setColor(Color.WHITE);
			g.fillRect(0,0,WIDTH_SCREEN, HEIGHT_SCREEN);
			g.drawImage(nutriGo, 0, 0,this);
			g.drawImage(loading,-20,285,this);
		}

		/**
		 * When the timer goes off, call another method to switch screens
		 */
		public void actionPerformed(ActionEvent e) {
			loadingFinished(loadingTimer);

		}
	}

	/**
	 * Reacts to the refresh button on the search panel
	 * @author Alex Raita && William Xu
	 * @version 22 November, 2015
	 */
	private static class RefreshButtonPressed implements ActionListener
	{
		Timer timer;
		/**
		 * Constructor
		 * @param timer the timer in the search panel that will be stopped
		 */
		public RefreshButtonPressed(Timer timer)
		{
			this.timer = timer;
		}

		/**
		 * Stop the timer and switch to the loading screen
		 */
		public void actionPerformed(ActionEvent e) {
			timer.stop();
			frame.remove(searchPanel);
			searchPanel = null;
			loadingPanel = new LoadingPanel();
			frame.add(loadingPanel);
			frame.setVisible(true);		
		}

	}

	/**
	 * Reacts to the delete button on the food panel
	 * @author Alex Raita && William Xu
	 * @version 22 November, 2015
	 */
	private static class DeleteButtonPressed extends JPanel implements ActionListener
	{
		Food food;
		/**
		 * Constructor
		 * @param food the food to be deleted
		 */
		public DeleteButtonPressed(Food food)
		{
			this.food = food;
		}

		/**
		 * Delete the food if the user confirms it 
		 */
		public void actionPerformed(ActionEvent e) {

			int answer = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete this food?\nChanges will take place when the databse is refreshed or restarted","Warning",JOptionPane.YES_NO_OPTION);
			if(answer == JOptionPane.YES_OPTION)
			{

				try {
					engine.deleteFood(food.getNDB_No());
					engine.saveChanges();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				frame.remove(foodPanel);
				foodPanel = null;
				searchPanel = new SearchPanel();
				frame.add(searchPanel);
				frame.setVisible(true);
			}
		}

	}

	/**
	 * Panel for adding new items
	 * @author Alex Raita & William Xu
	 * @version 22 Novemeber, 2015
	 *
	 */
	private static class NewItemPanel extends JPanel implements ActionListener
	{
		//Create food labels and text fields
		JLabel foodLabel = new JLabel("Food Info");
		JLabel foodDesc = new JLabel("Food Description");
		JLabel commName = new JLabel("Common Names");
		JLabel foodGroup = new JLabel ("Food Group");
		JLabel manufName = new JLabel ("Manufacturer's Name");
		JLabel sciName = new JLabel ("Scientific Name");
		JLabel inedParts = new JLabel("Inedible Parts");

		JTextField foodDescIn = new JTextField();
		JTextField commNameIn= new JTextField();
		JComboBox<String> foodGroupIn;
		JTextField manufNameIn= new JTextField();
		JTextField sciNameIn= new JTextField();
		JTextField inedPartsIn= new JTextField();

		//Add food button
		JButton addFoodButton = new JButton("Add New Food");

		//Create nutrient labels and text fields
		JLabel nutrientLabel = new JLabel("Nutrient Info");
		JLabel nutName = new JLabel("Nutrient Name");
		JLabel nutValue = new JLabel("Amount in 100g");

		JComboBox<String> nutNameIn;
		JTextField nutValueIn = new JTextField();

		JButton addNutrient = new JButton("Add Nutrient");
		DoublyLinkedList<NutrientForFood> listOfNutrients = new DoublyLinkedList<NutrientForFood>();
		JLabel numNutrients = new JLabel("Number of Nutrients Added: "+ listOfNutrients.size());

		//Create weight labels and text fields
		JLabel weightLabel = new JLabel("Weight Info");
		JLabel weight = new JLabel("Weight in grams per unit of measure");
		JLabel measurement = new JLabel("Measurement Unit i.e slice,cup,oz");
		JLabel amount = new JLabel("Amount(only numbers)i.e 2 slices,4 cups");

		JTextField weightIn = new JTextField();
		JTextField measurementIn = new JTextField();
		JTextField amountIn = new JTextField();

		JButton addWeightButton = new JButton("Add Weight");
		DoublyLinkedList<Weight> listOfWeights = new DoublyLinkedList<Weight>();
		JLabel numWeights = new JLabel("Number of Weights Added: "+ listOfWeights.size());

		//Create footnote labels and text fields
		JLabel footnoteLabel = new JLabel("Footnote Info");
		JLabel text = new JLabel("Footnote");

		JTextField textIn = new JTextField();

		JButton addFootnoteButton = new JButton("Add FootNote");
		DoublyLinkedList<Footnote> listOfFootnotes = new DoublyLinkedList<Footnote>();
		JLabel numFootnotes = new JLabel("Number of Footnotes Added: "+ listOfFootnotes.size());

		//Back button
		JButton backButton = new JButton("Back");

		public boolean isModifying;
		public Food food;
		public NewItemPanel(boolean isModifying, Food food)
		{
			setLayout(null);

			//Create combo boxes for nutrients and food groups
			DoublyLinkedList<Nutrient> allNutrients = engine.getSortedNutrients();
			TwoWayNode<Nutrient> nutrientNode = allNutrients.getHead(); 
			String[] stringOfAllNutrients = new String[allNutrients.size()];
			for(int i = 0; i < stringOfAllNutrients.length;i++)
			{
				stringOfAllNutrients[i] = nutrientNode.getItem().getNutrDesc();
				nutrientNode = nutrientNode.getNext();
			}
			nutNameIn = new JComboBox<String>(stringOfAllNutrients);

			DoublyLinkedList<FoodGroup> allFoodGroups = engine.getListOfFd_Groups();
			TwoWayNode<FoodGroup> foodGroupNode = allFoodGroups.getHead();
			String[] stringOfAllFoodGroups = new String[allFoodGroups.size()];
			for(int i = 0; i < stringOfAllFoodGroups.length;i++)
			{
				stringOfAllFoodGroups[i] = foodGroupNode.getItem().getFdGrp_Desc();
				foodGroupNode =foodGroupNode.getNext();
			}
			foodGroupIn = new JComboBox<String>(stringOfAllFoodGroups);

			//If modifying a food, set the text fields with the given food info
			this.isModifying = isModifying;
			if(isModifying)
			{
				addFoodButton.setText("Save");
				this.food = food;
				foodDescIn = new JTextField(food.getLong_Desc());
				commNameIn= new JTextField(food.getComName());
				foodGroupIn.setSelectedItem(food.getFoodGroup().getFdGrp_Desc());
				manufNameIn= new JTextField(food.getManufacName());
				sciNameIn= new JTextField(food.getSciName());
				inedPartsIn= new JTextField(food.getRef_desc());

				listOfNutrients = food.getFoodNutrients().copy();
				numNutrients = new JLabel("Number of Nutrients Added: "+ listOfNutrients.size());

				listOfWeights = food.getWeights().copy();
				numWeights = new JLabel("Number of Weights Added: "+ listOfWeights.size());

				listOfFootnotes = food.getFootnotes().copy();
				numFootnotes = new JLabel("Number of Footnotes Added: "+ listOfFootnotes.size());
			}


			//Add food labels and text fields				
			int space = 26;
			int start = 150;
			foodLabel.setFont(new Font("Verdana",Font.BOLD,20));

			foodLabel.setSize(150, 20);
			foodDesc.setSize(150, 20);
			commName.setSize(150, 20);
			foodGroup.setSize(150, 20);
			manufName.setSize(150, 20);
			sciName.setSize(150, 20);
			inedParts.setSize(150, 20);

			foodLabel.setLocation(8,120);
			foodDesc.setLocation(10,start);
			commName.setLocation(10,start+space);
			foodGroup.setLocation(10,start+2*space);
			manufName.setLocation(10,start+3*space);
			sciName.setLocation(10,start+4*space);
			inedParts.setLocation(10,start+5*space);

			foodDescIn.setSize(400,space-2);
			commNameIn.setSize(400,space-2);
			foodGroupIn.setSize(400,space-2);
			manufNameIn.setSize(400,space-2);
			sciNameIn.setSize(400,space-2);
			inedPartsIn.setSize(400,space-2);

			start++;
			foodDescIn.setLocation(170,start);
			commNameIn.setLocation(170,start+space);
			foodGroupIn.setLocation(170,start+2*space);
			manufNameIn.setLocation(170,start+3*space);
			sciNameIn.setLocation(170, start+4*space);
			inedPartsIn.setLocation(170,start+5*space);

			start += 7*space-1;

			add(foodLabel);
			add(foodDesc);
			add(commName);
			add(foodGroup);
			add(manufName);
			add(sciName);
			add(inedParts);

			add(foodDescIn);
			add(commNameIn);
			add(foodGroupIn);
			add(manufNameIn);
			add(sciNameIn);
			add(inedPartsIn);

			//Add button
			addFoodButton.setSize(200,50);
			addFoodButton.setLocation(390,12);
			addFoodButton.addActionListener(this);
			add(addFoodButton);

			//Add nutrient labels and text fields
			nutrientLabel.setFont(new Font("Verdana",Font.BOLD,20));
			nutrientLabel.setSize(150,20);
			nutName.setSize(150,20);
			nutValue.setSize(150,20);

			nutrientLabel.setLocation(8,start);
			nutName.setLocation(10,start+space);
			nutValue.setLocation(10,start+2*space);

			start++;

			nutNameIn.setLocation(170,start + space);
			nutValueIn.setLocation(170,start+2*space);

			start += 3*space;

			nutNameIn.setSize(400,space-2);
			nutValueIn.setSize(400,space-2);

			add(nutrientLabel);
			add(nutName);
			add(nutValue);

			add(nutNameIn);
			add(nutValueIn);

			//Add Nutrient Button		
			addNutrient.setSize(100,40);
			addNutrient.setLocation(8,start);
			addNutrient.addActionListener(this);
			add(addNutrient);

			//Add counter
			numNutrients.setSize(300,20);
			numNutrients.setLocation(170,start);
			add(numNutrients);
			start += 3*space-5;

			//Add weight labels and text fields
			weightLabel.setFont(new Font("Verdana",Font.BOLD,20));
			weightLabel.setSize(275,20);
			weight.setSize(275,20);
			measurement.setSize(275,20);
			amount.setSize(275,20);

			weightLabel.setLocation(10,start);
			weight.setLocation(10,start+space);
			measurement.setLocation(10,start+2*space);
			amount.setLocation(10,start+3*space);

			weightIn.setSize(275,space-2);
			measurementIn.setSize(275,space-2);
			amountIn.setSize(275,space-2);

			start++;
			weightIn.setLocation(275,start+space);
			measurementIn.setLocation(275,start+2*space);
			amountIn.setLocation(275,start+3*space);

			add(weightLabel);
			add(measurement);
			add(weight);
			add(amount);

			add(measurementIn);
			add(weightIn);
			add(amountIn);

			//Add weight button
			start += 4*space;
			addWeightButton.setSize(100,40);
			addWeightButton.setLocation(10,start);
			addWeightButton.addActionListener(this);
			add(addWeightButton);

			//Add counter
			numWeights.setSize(300,20);
			numWeights.setLocation(275,start);
			add(numWeights);
			start+=3*space-10;

			//Add footnote labels and text fields
			footnoteLabel.setFont(new Font("Verdana",Font.BOLD,20));
			footnoteLabel.setSize(200,20);
			text.setSize(150,20);

			footnoteLabel.setLocation(10,start);
			text.setLocation(10,start+space);

			textIn.setSize(400,space-2);
			textIn.setLocation(170,start+space+1);

			add(footnoteLabel);
			add(text);
			add(textIn);		

			//Add button
			start+= 2*space;
			addFootnoteButton.setSize(100,40);
			addFootnoteButton.setLocation(10,start);
			addFootnoteButton.addActionListener(this);
			add(addFootnoteButton);

			numFootnotes.setSize(300,20);
			numFootnotes.setLocation(170,start);
			add(numFootnotes);

			//Add Back button
			backButton.setSize(100,50);
			backButton.setLocation(390,75);
			if(isModifying)
				backButton.addActionListener(new BackButtonPressed(true,food));
			else 
				backButton.addActionListener(new BackButtonPressed(false, null));
			add(backButton);

		}

		/**
		 * Draw the logo
		 */
		public void paintComponent(Graphics g)
		{
			Image nutriGo = new ImageIcon("NutriGo.jpg").getImage();
			g.setColor(Color.WHITE);
			g.fillRect(0,0,WIDTH_SCREEN, HEIGHT_SCREEN);
			g.drawImage(nutriGo,-200,-40,this);
		}

		/**
		 * A button was pressed
		 */
		public void actionPerformed(ActionEvent e) {
			//If add nutrient was pressed
			if(e.getSource() == addNutrient)
			{
				//Figure out which nutrient was pressed
				Nutrient nutrient = null;
				TwoWayNode<Nutrient> nut = engine.getSortedNutrients().getHead();
				if(nut.getItem().getNutrDesc().equalsIgnoreCase((String) nutNameIn.getSelectedItem()))
				{
					nutrient = nut.getItem();
				}
				else
					while(nut.getNext() != null)
					{
						nut = nut.getNext();
						if(nut.getItem().getNutrDesc().equalsIgnoreCase((String) nutNameIn.getSelectedItem()))
						{
							nutrient = nut.getItem();
							break;
						}
					}

				double nutr_Val = 0;

				//Determine whether the nutrient is already in the food and needs to be updated
				//or if it is a new nutrient
				try
				{
					nutr_Val = Double.parseDouble(nutValueIn.getText());
					TwoWayNode<NutrientForFood> nutOfFood = listOfNutrients.getHead();
					boolean isThere = false;
					int index = 0;
					if(nutOfFood != null)
					{
						if(nutOfFood.getItem().getTypeOfNutrient().compareTo(nutrient) == 0)
						{
							isThere = true;
						}
						else
							while(nutOfFood.getNext() != null)
							{
								nutOfFood = nutOfFood.getNext();
								index++;
								if(nutOfFood.getItem().getTypeOfNutrient().compareTo(nutrient) == 0)
								{
									isThere = true;
									break;
								}
							}
					}

					//If the nutrient isn't already there, add it
					if(!isThere)
					{
						listOfNutrients.add(new NutrientForFood(nutrient, nutr_Val ,0,0,"","","","",0,0,0,0,0,0,"","",0));
					}
					//Otherwise update it and tell the user it was updated
					else
					{
						listOfNutrients.replace(new NutrientForFood(nutrient, nutr_Val ,0,0,"","","","",0,0,0,0,0,0,"","",0), index);
						JOptionPane.showMessageDialog(frame,
								"Nutrient already exists, so the amount of it was updated",
								"Nutrient Updated",
								JOptionPane.ERROR_MESSAGE);
					}

					//Clear the nutrient information and refresh
					nutValueIn.setText("");
					numNutrients.setText("Number of Nutrients Added: "+ listOfNutrients.size());
					repaint();


				}
				//If the user input is not a double for the amount of the nutrient, tell them
				catch(NumberFormatException E)
				{
					JOptionPane.showMessageDialog(frame,
							"Nutrient amount must be a number only",
							"Number Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			//If the add weight button was pressed
			else if(e.getSource() == addWeightButton)
			{
				int amountOfFood;
				double weightGrams;
				String measurementText = measurementIn.getText();

				try
				{
					//Get the amount
					amountOfFood = Integer.parseInt(amountIn.getText());
					if(amountOfFood <= 0)
						throw new NumberFormatException();

					try{
						weightGrams = Double.parseDouble(weightIn.getText());
						if(weightGrams <= 0)
							throw new NumberFormatException();
						if(measurementText.length() > 0) //The measurement unit cannot be empty
						{
							//The input is valid, so add the new weight 
							listOfWeights.add(new Weight("",amountOfFood,measurementText,weightGrams,0,0));

							//Clear the input boxes and refresh
							measurementIn.setText("");
							weightIn.setText("");
							amountIn.setText("");
							numWeights.setText("Number of Weights Added: "+ listOfWeights.size());
							repaint();
						}
						else //If the measurement unit is empty, tell the user
							JOptionPane.showMessageDialog(frame,
									"Must give a measurment unit",
									"Number Error",
									JOptionPane.ERROR_MESSAGE);
					}
					//If the weight is invalid, tell the user
					catch(NumberFormatException E)
					{
						JOptionPane.showMessageDialog(frame,
								"Weight must be a positive number only",
								"Number Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
				//If the amount if invalid tell the user
				catch(NumberFormatException E)
				{
					JOptionPane.showMessageDialog(frame,
							"Amount must be a positive whole number only",
							"Number Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			//If adding a footnote
			else if(e.getSource() == addFootnoteButton)
			{
				String footnote = textIn.getText();
				if(footnote.length() == 0) //Footnote must have text, so tell the user
				{
					JOptionPane.showMessageDialog(frame,
							"Footnote must have text",
							"Footnote error",
							JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					//Add the footnote and refresh
					listOfFootnotes.add(new Footnote("",'D',"",footnote));
					textIn.setText("");
					numFootnotes.setText("Number of Footnotes Added: "+ listOfFootnotes.size());
					repaint();
				}
			}
			//If adding a new food
			else if(e.getSource() == addFoodButton)
			{
				String foodDescription = foodDescIn.getText();
				String foodGroupText = (String) foodGroupIn.getSelectedItem();

				//If the food description is empty, tell the user
				if(foodDescription.length() == 0)
				{
					JOptionPane.showMessageDialog(frame,
							"The food description can't be empty",
							"Food Definition Error",
							JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					//Figure out which food group was chosen
					TwoWayNode<FoodGroup> fd_groups = engine.getListOfFd_Groups().getHead();
					FoodGroup fd_group = null;
					if(fd_groups.getItem().getFdGrp_Desc().equals(foodGroupText))
					{
						fd_group = fd_groups.getItem();
					}
					else
					{
						while(fd_groups.getNext() != null)
						{
							fd_groups = fd_groups.getNext();
							if(fd_groups.getItem().getFdGrp_Desc().equals(foodGroupText))
							{
								fd_group = fd_groups.getItem();
								break;
							}
						}
					}


					Food newFood = null;

					//If the food is being modified, keep the same NDB_No
					if(isModifying)
					{
						newFood = new Food(food.getNDB_No(),fd_group,foodDescription, "",commNameIn.getText(),manufNameIn.getText(),"",inedPartsIn.getText(),0,sciNameIn.getText(),0,0,0,0);
						JOptionPane.showMessageDialog(frame,
								"Modifications will take place upon restart or refresh",
								"Note",
								JOptionPane.ERROR_MESSAGE);
					}
					//A new food needs a new NDB_No
					else
					{
						engine.increaseCurrentNDB_No();
						newFood = new Food(engine.getCurrentNDB_No()+"",fd_group,foodDescription, "",commNameIn.getText(),manufNameIn.getText(),"",inedPartsIn.getText(),0,sciNameIn.getText(),0,0,0,0);	
					}
					engine.addNewFood(newFood, listOfNutrients, listOfWeights, listOfFootnotes);

					//Only if adding a new food, should the textfields be reset
					if(!isModifying)
					{
						foodDescIn.setText("");
						commNameIn.setText("");
						manufNameIn.setText("");
						inedPartsIn.setText("");
						sciNameIn.setText("");
						listOfFootnotes = new DoublyLinkedList<Footnote>();
						listOfWeights = new DoublyLinkedList<Weight>();
						listOfNutrients = new DoublyLinkedList<NutrientForFood>();
						numWeights.setText("Number of Weights Added: 0");
						numFootnotes.setText("Number of Footnotes Added: 0");
						numNutrients.setText("Numbes of Nutrients Added: 0");
						
						JOptionPane.showMessageDialog(frame,
								"The new food was added!",
								"Food Added",
								JOptionPane.ERROR_MESSAGE);
					}

					//Refresh and save changes
					repaint();
					try {
						engine.saveChanges();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}

				}
			}
		}

	}

	/**
	 * Displays information about a given food
	 * @author Alex Raita & William Xu
	 * @version 22 Novemeber, 2015
	 */
	private static class FoodPanel extends JPanel
	{
		Food food;
		int lastLocation = 110;

		/**
		 * Constructor
		 * @param food the food that is being displayed
		 */
		public FoodPanel(Food food)
		{
			this.food = food;
			setLayout(null);
			setSize(HEIGHT_SCREEN,WIDTH_SCREEN);

			//Button to go back to the search
			JButton search = new JButton("Back...");
			search.addActionListener(new SearchButtonPressed());
			search.setForeground(Color.BLACK);
			search.setSize(100,40);
			search.setLocation(WIDTH_SCREEN-125,HEIGHT_SCREEN-90);
			add(search);

			//Show the food descriptions
			addLabels("Food Description",food.getLong_Desc(),true);
			addLabels("Common Names",food.getComName(),true);
			addLabels("Food Group",food.getFoodGroup().getFdGrp_Desc(),false);
			addLabels("Manufacturer's Name",food.getManufacName(),true);
			addLabels("Scientific Name",food.getSciName(),true);
			addLabels("Inedible Parts",food.getRef_desc(),true);
		
			//Add a table of nutrients		
			int numNutrientResults = food.getNutrients().size();
			lastLocation+=50;
			if( numNutrientResults > 0)
			{
				String[] headers = {"Nutrient Name", "Amount in 100g"};
				String[][] nutrients = new String[numNutrientResults][2]; 
				TwoWayNode<NutrientForFood> listOfNutrients = food.getNutrients().getHead();
				for(int i = 0; i < nutrients.length;i++)
				{
					nutrients[i][0] = listOfNutrients.getItem().getTypeOfNutrient().getNutrDesc();
					nutrients[i][1] = Double.toString(listOfNutrients.getItem().getNutr_Val()) + listOfNutrients.getItem().getTypeOfNutrient().getUnits();
					listOfNutrients = listOfNutrients.getNext();
				}
				JTable nutrientTable = new JTable(nutrients, headers);
				nutrientTable.setEnabled(false);
				JScrollPane table = new JScrollPane(nutrientTable);
				table.setLocation(10,lastLocation);
				int heightTable = Math.min((int)((numNutrientResults+1)*15.5+10),200);
				lastLocation += heightTable;
				table.setSize(WIDTH_SCREEN-20,heightTable);
				add(table);
			}

			//Add Table for Weights
			int numWeightResults = food.getWeights().size();
			if( numWeightResults > 0)
			{
				String[] header = {"Weights"};
				String[][] weights = new String[numWeightResults][1];
				TwoWayNode<Weight> listOfWeights = food.getWeights().getHead();
				for(int i = 0; i < weights.length;i++)
				{
					weights[i][0] = listOfWeights.getItem().getGm_Wgt() +"g"+" in "+ listOfWeights.getItem().getAmount()+" "+listOfWeights.getItem().getMsre_Desc();
					listOfWeights = listOfWeights.getNext();
				}
				JTable weightTable = new JTable(weights,header);
				weightTable.setEnabled(false);
				JScrollPane table = new JScrollPane(weightTable);
				table.setLocation(10,lastLocation + 20);
				int heightTable = Math.min((int)((numWeightResults+1)*15.5+10),100);
				lastLocation += 20 + heightTable;
				table.setSize(WIDTH_SCREEN-20, heightTable);
				add(table);
			}
			
			//Add Footnotes
			int numFootnoteResults = food.getFootnotes().size();
			TwoWayNode<Footnote> listOfFootnotes = food.getFootnotes().getHead();
			for(int i = 1; i <= numFootnoteResults;i++)
			{
				addLabels("Footnote #"+i,listOfFootnotes.getItem().getFootnt_Txt(),true);
				listOfFootnotes = listOfFootnotes.getNext();
			}

			//Add a button to modify a food
			JButton modifyButton = new JButton("Modify Data");
			modifyButton.setSize(100,30);
			modifyButton.setLocation(10,90);
			modifyButton.addActionListener(new ModifyButtonPressed(food));
			add(modifyButton);

			//Add a delete button
			JButton deleteButton = new JButton("Delete");
			deleteButton.setSize(100,30);
			deleteButton.setLocation(130,90);
			deleteButton.addActionListener(new DeleteButtonPressed(food));
			add(deleteButton);
		}

		/**
		 * Adds a nicely formatted label, given the strings it should display
		 * @param label the label
		 * @param info the info to be shown
		 * @param hasToolTip whether or not the info should have a tool tip
		 */
		public void addLabels(String label, String info, boolean hasToolTip)
		{
			JLabel itemLabel = new JLabel(label+": ");
			JLabel infoLabel = new JLabel(info.length() == 0 ? "N/A":info);
			itemLabel.setSize(150,20);
			itemLabel.setLocation(10,lastLocation+20);
			infoLabel.setSize(480,20);
			infoLabel.setLocation(160,lastLocation+20);
			if(hasToolTip)
				infoLabel.setToolTipText(info);
			lastLocation += 20;
			add(itemLabel);
			add(infoLabel);

		}

		/**
		 * Draws the logo and the image of the the foodgroup 
		 */
		public void paintComponent(Graphics g)
		{
			Image nutriGo = new ImageIcon("NutriGo.jpg").getImage();
			g.setColor(Color.WHITE);
			g.fillRect(0,0,WIDTH_SCREEN, HEIGHT_SCREEN);
			g.drawImage(nutriGo,-200,-40,this);

			Image foodGroup = food.getFoodGroup().getPicture();

			double ratio = 1.0*foodGroup.getWidth(this)/(WIDTH_SCREEN-310);
			int picWidth = WIDTH_SCREEN-310;
			int picHeight = (int)(foodGroup.getHeight(this)/ratio);

			g.drawImage(foodGroup,320,10,picWidth,picHeight,this);

		}
	}

	/**
	 * The menu panel
	 * @author Alex Raita & William Xu
	 * @version 22 November, 2015
	 */
	private static class MenuPanel extends JPanel
	{
		/**
		 * Constructor
		 */
		public MenuPanel()
		{
			setLayout(null);
			setSize(HEIGHT_SCREEN,WIDTH_SCREEN);

			//Add a go button
			JButton start = new JButton("GO!");
			start.setSize(200,100);
			start.setLocation(225,275);
			start.addActionListener(new GoButtonPressed());
			add(start);	
		}

		/**
		 * Draw the logo
		 */
		public void paintComponent(Graphics g)
		{
			Image nutriGo = new ImageIcon("LargeNutriGo.png").getImage();
			g.setColor(Color.WHITE);
			g.fillRect(0,0,WIDTH_SCREEN,HEIGHT_SCREEN);
			g.drawImage(nutriGo,0,0,this);
		}
	}

	/**
	 * Lets the user search for food
	 * @author Alex Raita & William Xu
	 * @version 22 November, 2015
	 */
	private static class SearchPanel extends JPanel implements ActionListener, MouseListener
	{
		PriorityQueue<Food> searchQueue = new PriorityQueue<Food>();
		TextField searchBar;
		Timer searchBarTimer;
		JTable searchResults = new JTable(new String[][] {{""}},new String[]{""});
		JScrollPane scrollPane;
		int paneSize;
		static String searchText = "Search for food here!";
		String[][] results;
		int numTimerEvents = 0;
		Button addItemButton = new Button("Add Item");
		Button updateDataButton = new Button("Refresh Data");

		/**
		 * Constructor
		 */
		public SearchPanel()
		{		
			setLayout(null);
			searchBar = new TextField(searchText);	
			searchBar.setSize(600,25);
			searchBar.setLocation(10,150);
			searchBar.addMouseListener(this);
			add(searchBar);
			searchBarTimer = new Timer(200,this);
			searchBarTimer.start();
			scrollPane = new JScrollPane(searchResults);
			scrollPane.setSize(600,paneSize);
			scrollPane.setLocation(10,175);

			add(scrollPane);

			addItemButton.setSize(200,50);
			addItemButton.setLocation(200,700);
			addItemButton.addActionListener(new ItemButtonListener(searchBarTimer));
			add(addItemButton);

			updateDataButton.setSize(200,50);
			updateDataButton.setLocation(400,700);
			updateDataButton.addActionListener(new RefreshButtonPressed(searchBarTimer));
			add(updateDataButton);
		}

		/**
		 * Draw the logo
		 */
		public void paintComponent(Graphics g)
		{
			Image nutriGo = new ImageIcon("NutriGo.jpg").getImage();
			g.setColor(Color.WHITE);
			g.fillRect(0,0,WIDTH_SCREEN,HEIGHT_SCREEN);
			g.drawImage(nutriGo,-58,0,this);
		}

		/**
		 * When the search timer goes off, update the search
		 */
		public void actionPerformed(ActionEvent e) {
			numTimerEvents++;

			//If search bar is empty, make sure the scroll pane is also empty
			if(searchBar.getText().trim().isEmpty())
			{
				searchResults = new JTable(new String[][] {{""}},new String[]{""});
				paneSize = 0;
				searchResults.setCellSelectionEnabled(true);
				searchResults.addMouseListener(this);
				remove(scrollPane);
				scrollPane = new JScrollPane(searchResults);
				scrollPane.setSize(600,paneSize);
				scrollPane.setLocation(10,175);
				add(scrollPane);		
				repaint();
				setVisible(true);
				
			}
			//Only update if the text changes
			else if((!searchBar.getText().equals(searchText) || numTimerEvents == 1) && !searchBar.getText().equals("Search for food here!"))
			{
				searchText = searchBar.getText();
				searchQueue = engine.search(searchText);

				//If search queue has more than 1000 results, the query was very big, so only display the first 1000
				int numResults = Math.min(searchQueue.size(),1000);
				if(numResults > 0)
				{
					results = new String[numResults][1];
					for(int result = 0; result < results.length; result++)
					{
						results[result][0] = searchQueue.get(result).getLong_Desc();
					}

					searchResults = new JTable(results,new String[]{""});
					paneSize = Math.min((int)((numResults)*15.5+10),400);
				}
				else
				{
					searchResults = new JTable(new String[][] {{""}},new String[]{""});
					paneSize = 0;
				}
				searchResults.setCellSelectionEnabled(true);
				searchResults.addMouseListener(this);
				remove(scrollPane);
				scrollPane = new JScrollPane(searchResults);
				scrollPane.setSize(600,paneSize);
				scrollPane.setLocation(10,175);
				add(scrollPane);		
				repaint();
				setVisible(true);
			}
			
			searchText = searchBar.getText();
			searchBarTimer.restart();
		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		/**
		 * React to mouse selection
		 */
		public void mouseReleased(MouseEvent e) {	

			//If a food item is chosen, go to the result on the food panel
			if(e.getSource() == searchResults)
			{
				searchBarTimer.stop();

				//Delay because it makes the transition look better
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				openFoodDescription(searchQueue.get(searchResults.getSelectedRow()));
			}
			//If the search bar is pressed and the text is "search for food here", it can be deleted so the user can type in a search
			else if(e.getSource() == searchBar)
			{
				if(searchBar.getText().equals("Search for food here!"))
				{
					searchBar.setText("");
					repaint();
				}
			}
		}
		public void mouseEntered(MouseEvent e) {			
		}

		public void mouseExited(MouseEvent e) {
		}

	}

	/**
	 * Start the program
	 */
	public void startProgram() {

		frame.setSize(WIDTH_SCREEN,HEIGHT_SCREEN);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(menuPanel);
		frame.setVisible(true);



	}

}
