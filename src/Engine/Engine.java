package Engine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.swing.ImageIcon;

/**
 * The engine of the Nutrition Application doing most of the dirty work (Import,
 * Store, Search)
 * @author Alex Raita && William Xu
 * @version November 22, 2015
 */
public class Engine
{

	// Declare all the data structures used in the program (The names explain
	// the purpose)
	private static BinaryTree<Keyword> treeOfKeywords;
	private static DoublyLinkedList<Nutrient> scrambledNutrients;
	private static DoublyLinkedList<Nutrient> sortedNutrients;
	private static BinaryTree<Nutrient> listOfNutrients;
	private static DoublyLinkedList<FoodGroup> listOfFd_Groups;
	private static DoublyLinkedList<String> allAddFoodDesLines;
	private static DoublyLinkedList<String> allAddNutDataLines;
	private static DoublyLinkedList<String> allAddWeightLines;
	private static DoublyLinkedList<String> allAddFootnoteLines;

	// Data structures for making sure a certain food item is not added from the
	// ADD_FOOD_DES then FOOD_DES again (for modifying food items) and that the
	// deleted food items
	// are not added at all
	private static DoublyLinkedList<String> listOfUsedNDB_No = new DoublyLinkedList<String>();
	DoublyLinkedList<String> listOfDeletedNDB_No = new DoublyLinkedList<String>();

	// Use an NDB_No counter when adding new food items (Starting from 100000)
	private static int currentNDB_No = 100000;

	// Counters storing the total number of foods and number of nutrients
	private static int nutForFood_No = 0;

	/**
	 * Gets the list of sorted nutrients
	 * @return the list of nutrients
	 */
	public DoublyLinkedList<Nutrient> getSortedNutrients()
	{
		return sortedNutrients;
	}

	/**
	 * Gets the list of food groups
	 * @return the list of food groups
	 */
	public DoublyLinkedList<FoodGroup> getListOfFd_Groups()
	{
		return listOfFd_Groups;
	}

	/**
	 * Get the current NDB_No
	 * @return the current NDB_No
	 */
	public int getCurrentNDB_No()
	{
		return currentNDB_No;
	}

	/**
	 * Increase the current NDB_No
	 */
	public void increaseCurrentNDB_No()
	{
		currentNDB_No++;
	}

	/**
	 * Import everything from the text files
	 * @throws IOException is thrown when certain files are missing
	 */
	public void importData() throws IOException
	{
		// Clear everything
		treeOfKeywords = new BinaryTree<Keyword>();
		scrambledNutrients = new DoublyLinkedList<Nutrient>();
		sortedNutrients = new DoublyLinkedList<Nutrient>();
		listOfNutrients = new BinaryTree<Nutrient>();
		listOfFd_Groups = new DoublyLinkedList<FoodGroup>();
		nutForFood_No = 0;

		allAddFoodDesLines = new DoublyLinkedList<String>();
		allAddNutDataLines = new DoublyLinkedList<String>();
		allAddWeightLines = new DoublyLinkedList<String>();
		allAddFootnoteLines = new DoublyLinkedList<String>();

		listOfUsedNDB_No = new DoublyLinkedList<String>();

		listOfDeletedNDB_No = new DoublyLinkedList<String>();
		currentNDB_No = 100000;
		
		// Use buffered reader to import all the data
		BufferedReader inNutDef = new BufferedReader(new FileReader(new File(
				"Original Data", "NUTR_DEF.txt")));
		BufferedReader inFoodGroup = new BufferedReader(new FileReader(
				new File("Original Data", "FD_GROUP.txt")));
		BufferedReader inFoodDes = new BufferedReader(new FileReader(new File(
				"Original Data", "FOOD_DES.txt")));

		// Use the scanner for parallel imports with the inFoodDes because of
		// the useful hasNext() method
		Scanner inNutData = new Scanner(new FileReader(new File(
				"Original Data", "NUT_Data.txt")));
		Scanner inWeight = new Scanner(new FileReader(new File(
				"Original Data", "WEIGHT.txt")));
		Scanner inFootnote = new Scanner(new FileReader(new File(
				"Original Data", "FOOTNOTE.txt")));

		// Import the NDB_No's of the deleted foods
		BufferedReader inDeletedFoods = new BufferedReader(new FileReader(
				new File(
						"Additional Data", "DELETED_FOODS.txt")));

		// A current line used when splitting up strings and storing in specific
		// variables
		String currentLine = null;

		// Import all the deleted foods as used NDB_Nos
		while ((currentLine = inDeletedFoods.readLine()) != null)
		{
			listOfUsedNDB_No.add(currentLine);
			listOfDeletedNDB_No.add(currentLine);
		}
		inDeletedFoods.close();

		// Read in all the types of food groups and add
		while ((currentLine = inFoodGroup.readLine()) != null)
		{
			String[] tokens = currentLine.split("\\^");

			String FdGrp_Cd = tokens[0].substring(1, tokens[0].length() - 1);
			String FdGrp_Desc = tokens[1].substring(1, tokens[1].length() - 1);

			if (FdGrp_Desc.indexOf('/') >= 0)
			{
				FdGrp_Desc = FdGrp_Desc.substring(0, FdGrp_Desc.indexOf('/'))
						+ " "
						+ FdGrp_Desc.substring(FdGrp_Desc.indexOf('/') + 1,
								FdGrp_Desc.length());
			}
			FoodGroup newFoodGroup = new FoodGroup(FdGrp_Cd);
			newFoodGroup.setFdGrp_Desc(FdGrp_Desc);
			newFoodGroup
					.setPicture(new ImageIcon(FdGrp_Desc + ".png")
							.getImage());
			listOfFd_Groups.add(newFoodGroup);
		}

		// Read in all the types of nutrients
		while ((currentLine = inNutDef.readLine()) != null)
		{
			int nutr_No_End = currentLine.indexOf('~', 1);
			String nutr_No = currentLine.substring(1, nutr_No_End);

			int unitsEnd = currentLine.indexOf('~', nutr_No_End + 3);
			String units = currentLine.substring(nutr_No_End + 3, unitsEnd);

			int tagnameEnd = currentLine.indexOf('~', unitsEnd + 3);
			String tagname = currentLine.substring(unitsEnd + 3, tagnameEnd);

			int nutrDescEnd = currentLine.indexOf('~', tagnameEnd + 3);
			String nutrDesc = currentLine
					.substring(tagnameEnd + 3, nutrDescEnd);

			int num_Desc_End = currentLine.indexOf('~', nutrDescEnd + 3);
			int num_Desc = -1;
			if (num_Desc_End != nutrDescEnd + 3)
			{
				num_Desc = Integer.parseInt(currentLine.substring(
						nutrDescEnd + 3,
						num_Desc_End));
			}

			int sR_Order_End = currentLine.indexOf('~', num_Desc_End + 3);
			int sR_Order = -1;

			if (sR_Order_End != num_Desc_End + 3)
			{
				sR_Order = Integer.parseInt(currentLine.substring(
						num_Desc_End + 3,
						sR_Order_End));
			}

			// To add to a balanced binary tree
			scrambledNutrients.add(new Nutrient(nutr_No, units, tagname,
					nutrDesc,
					num_Desc, sR_Order));

			// For use by the Gui class for a drop down menu of nutrient types
			sortedNutrients.add(new Nutrient(nutr_No, units, tagname, nutrDesc,
					num_Desc, sR_Order));
		}

		// Randomize the order of the nutrients and add to a binary tree by the
		// nutrient number
		int noOfNutrients = scrambledNutrients.size();
		for (int nut = 0; nut < noOfNutrients; nut++)
		{
			int randomIndex = (int) (Math.random() * noOfNutrients);
			Nutrient tempNut = scrambledNutrients.get(nut);
			scrambledNutrients
					.replace(scrambledNutrients.get(randomIndex), nut);
			scrambledNutrients.replace(tempNut, randomIndex);
		}
		TwoWayNode<Nutrient> nutToAdd = scrambledNutrients.getHead();
		listOfNutrients.add(nutToAdd.getItem());
		while (nutToAdd.getNext() != null)
		{
			nutToAdd = nutToAdd.getNext();
			listOfNutrients.add(nutToAdd.getItem());
		}

		// Import all the additional specific food data
		BufferedReader inAddFoodDes = new BufferedReader(new FileReader(
				new File(
						"Additional Data", "ADD_FOOD_DES.txt")));
		Scanner inAddNutData = new Scanner(new FileReader(
				new File("Additional Data", "ADD_NUT_DATA.txt")));
		Scanner inAddWeight = new Scanner(new FileReader(
				new File("Additional Data", "ADD_WEIGHT.txt")));
		Scanner inAddFootnote = new Scanner(new FileReader(
				new File("Additional Data", "ADD_FOOTNOTE.txt")));

		// Current lines for use in the parallel text files to the Food
		// Description file
		String currentInNutDataLine = null;
		String currentInWeightLine = null;
		String currentInFootnoteLine = null;

		// Making sure that the text files are not empty before reading them
		if (inAddNutData.hasNextLine())
		{
			currentInNutDataLine = inAddNutData.nextLine();
			allAddNutDataLines.add(currentInNutDataLine);
		}
		if (inAddWeight.hasNextLine())
		{
			currentInWeightLine = inAddWeight.nextLine();
			allAddWeightLines.add(currentInWeightLine);
		}
		if (inAddFootnote.hasNextLine())
		{
			currentInFootnoteLine = inAddFootnote.nextLine();
			allAddFootnoteLines.add(currentInFootnoteLine);
		}

		// Read in all the foods in the food description file
		while ((currentLine = inAddFoodDes.readLine()) != null)
		{
			allAddFoodDesLines.add(currentLine);

			int NDB_No_End = currentLine.indexOf('~', 1);
			String NDB_No = currentLine.substring(1, NDB_No_End);

			// Adding the current item only if the NDB_No has not already been
			// used
			if (listOfUsedNDB_No.indexOf(NDB_No) < 0)
			{
				listOfUsedNDB_No.add(NDB_No);
				if (Integer.parseInt(NDB_No) >= 100000)
				{
					currentNDB_No = Integer.parseInt(NDB_No);
				}

				int FdGrp_Cd_End = currentLine.indexOf('~', NDB_No_End + 3);
				String FdGrp_Cd = currentLine.substring(NDB_No_End + 3,
						FdGrp_Cd_End);

				FoodGroup foodGroup = new FoodGroup(FdGrp_Cd);
				foodGroup = listOfFd_Groups.get(listOfFd_Groups
						.indexOf(foodGroup));

				int Long_Desc_End = currentLine.indexOf('~', FdGrp_Cd_End + 3);
				String Long_Desc = currentLine.substring(FdGrp_Cd_End + 3,
						Long_Desc_End);

				int Shrt_Desc_End = currentLine.indexOf('~', Long_Desc_End + 3);
				String Shrt_Desc = currentLine.substring(Long_Desc_End + 3,
						Shrt_Desc_End);

				int ComNameEnd = currentLine.indexOf('~', Shrt_Desc_End + 3);
				String ComName = currentLine.substring(Shrt_Desc_End + 3,
						ComNameEnd);

				int ManufacNameEnd = currentLine.indexOf('~', ComNameEnd + 3);
				String ManufacName = currentLine.substring(ComNameEnd + 3,
						ManufacNameEnd);

				int SurveyEnd = currentLine.indexOf('~', ManufacNameEnd + 3);
				String Survey = currentLine
						.substring(ManufacNameEnd + 3, SurveyEnd);

				int Ref_desc_End = currentLine.indexOf('~', SurveyEnd + 3);
				String Ref_desc = currentLine
						.substring(SurveyEnd + 3, Ref_desc_End);

				int RefuseEnd = currentLine.indexOf('^', Ref_desc_End + 2);
				double Refuse = -1;
				if (RefuseEnd != Ref_desc_End + 2)
				{
					Refuse = Double.parseDouble(currentLine.substring(
							Ref_desc_End + 2, RefuseEnd));
				}

				int SciNameEnd = currentLine.indexOf('~', RefuseEnd + 2);
				String SciName = currentLine
						.substring(RefuseEnd + 2, SciNameEnd);

				int N_FactorEnd = currentLine.indexOf('^', SciNameEnd + 2);
				double N_Factor = -1;
				if (N_FactorEnd != SciNameEnd + 2)
				{
					N_Factor = Double.parseDouble(currentLine.substring(
							SciNameEnd + 2, N_FactorEnd));
				}

				int Pro_FactorEnd = currentLine.indexOf('^', N_FactorEnd + 1);
				double Pro_Factor = -1;
				if (Pro_FactorEnd != N_FactorEnd + 1)
				{
					Pro_Factor = Double.parseDouble(currentLine.substring(
							N_FactorEnd + 1, Pro_FactorEnd));
				}

				int Fat_FactorEnd = currentLine.indexOf('^', Pro_FactorEnd + 1);
				double Fat_Factor = -1;
				if (Fat_FactorEnd != Pro_FactorEnd + 1)
				{
					Fat_Factor = Double.parseDouble(currentLine.substring(
							Pro_FactorEnd + 1, Fat_FactorEnd));
				}

				double CHO_Factor = -1;
				if (Fat_FactorEnd != currentLine.length() - 1)
				{
					CHO_Factor = Double.parseDouble(currentLine.substring(
							Fat_FactorEnd + 1));
				}

				// The new food to be added
				Food newFood = new Food(NDB_No, foodGroup, Long_Desc,
						Shrt_Desc,
						ComName, ManufacName, Survey, Ref_desc, Refuse,
						SciName,
						N_Factor, Pro_Factor, Fat_Factor, CHO_Factor);

				// Add all the nutrients for this specific food
				if (currentInNutDataLine != null)
				{
					while (currentInNutDataLine.substring(1, NDB_No_End)
							.equals(
									NDB_No))
					{
						noOfNutrients++;
						String[] tokens = currentInNutDataLine.split("\\^");
						String Nutr_No = tokens[1].substring(1,
								tokens[1].length() - 1);
						double Nutr_Val = Double.parseDouble(tokens[2]);
						int Num_Data_Pts_Nut = Integer.parseInt(tokens[3]);

						double Std_Error = -1;
						if (tokens[4].length() > 0)
						{
							Std_Error = Double.parseDouble(tokens[4]);
						}

						String Src_Cd = tokens[5].substring(1,
								tokens[5].length() - 1);
						String Derive_Cd = tokens[6].substring(1,
								tokens[6].length() - 1);
						String Ref_NDB_No = tokens[7].substring(1,
								tokens[7].length() - 1);
						String Add_Nutr_Mark = tokens[8].substring(1,
								tokens[8].length() - 1);

						int Num_Studies = -1;

						if (tokens[9].length() > 0)
						{
							Num_Studies = Integer.parseInt(tokens[9]);
						}

						double Min = -1;
						if (tokens[10].length() > 0)
						{
							Min = Double.parseDouble(tokens[10]);
						}

						double Max = -1;
						if (tokens[11].length() > 0)
						{
							Max = Double.parseDouble(tokens[11]);
						}

						double DF = -1;
						if (tokens[12].length() > 0)
						{
							DF = Double.parseDouble(tokens[12]);
						}

						double Low_EB = -1;
						if (tokens[13].length() > 0)
						{
							Low_EB = Double.parseDouble(tokens[13]);
						}

						double Up_EB = -1;
						if (tokens[14].length() > 0)
						{
							Up_EB = Double.parseDouble(tokens[14]);
						}

						String Stat_cmt = tokens[15].substring(1,
								tokens[15].length() - 1);
						String AddMod_Date = "";
						if (tokens.length > 16)
						{
							AddMod_Date = tokens[16].substring(0,
									tokens[16].length());
						}

						// Add the nutrient with the index of the type of
						// nutrient as well
						Nutrient checkNut = new Nutrient(Nutr_No);
						Nutrient typeOfNutrient = listOfNutrients
								.getItem(checkNut);

						// Add the nutrient to the current food
						newFood.addNutrient(new NutrientForFood(typeOfNutrient,
								Nutr_Val,
								Num_Data_Pts_Nut, Std_Error, Src_Cd,
								Derive_Cd, Ref_NDB_No, Add_Nutr_Mark,
								Num_Studies, Min, Max, DF, Low_EB,
								Up_EB, Stat_cmt, AddMod_Date, nutForFood_No++));

						// Import the next nut data line
						if (inAddNutData.hasNextLine())
						{
							currentInNutDataLine = inAddNutData.nextLine();
							allAddNutDataLines.add(currentInNutDataLine);
						}
						else
						{
							break;
						}
					}
				}
				// Add all the weights for this specific food
				if (currentInWeightLine != null)
				{
					String checkLine = currentInWeightLine.substring(1,
							NDB_No_End);

					while (checkLine.equals(
							NDB_No))
					{
						String[] tokens = currentInWeightLine.split("\\^");

						String Seq = tokens[1];

						double Amount = Double.parseDouble(tokens[2]);

						String Msre_Desc = tokens[3].substring(1,
								tokens[3].length() - 1);

						double Gm_Wgt = Double.parseDouble(tokens[4]);

						int Num_Data_Pts = -1;
						if (tokens.length > 5 && tokens[5].length() > 0)
						{
							Num_Data_Pts = Integer.parseInt(tokens[5]);
						}

						double Std_Dev = -1;
						if (tokens.length > 6 && tokens[6].length() > 0)
						{
							Std_Dev = Double.parseDouble(tokens[6]);
						}

						newFood.addWeight(new Weight(Seq, Amount,
								Msre_Desc,
								Gm_Wgt, Num_Data_Pts, Std_Dev));

						// Import the next weight line
						if (inAddWeight.hasNextLine())
						{
							currentInWeightLine = inAddWeight.nextLine();
							allAddWeightLines.add(currentInWeightLine);
						}
						else
						{
							break;
						}

					}
				}

				// Add all the footnotes for this specific food
				if (currentInFootnoteLine != null)
				{
					while (currentInFootnoteLine.substring(1, NDB_No_End)
							.equals(NDB_No))
					{
						String[] tokens = currentInFootnoteLine.split("\\^");

						String Footnt_No = tokens[1].substring(1,
								tokens[1].length() - 1);
						char Footnt_Typ = tokens[2].charAt(1);
						String Nutr_No = tokens[3].substring(1,
								tokens[3].length() - 1);
						String Footnt_Txt = tokens[4].substring(1,
								tokens[4].length() - 1);

						newFood.addFootnote(new Footnote(Footnt_No,
								Footnt_Typ,
								Nutr_No, Footnt_Txt));

						// Import the next footnote line
						if (inAddFootnote.hasNextLine())
						{
							currentInFootnoteLine = inAddFootnote.nextLine();
							allAddFootnoteLines.add(currentInFootnoteLine);
						}
						else
						{
							break;
						}

					}
				}

				// Get each of the keywords for the food
				String[] tokens = (Long_Desc + " " + Shrt_Desc + " " + ComName
						+ " " + ManufacName)
						.split(" |\\)|\\(|;|,|/|\\&|!|\\.|-");
				for (int keyword = 0; keyword < tokens.length; keyword++)
				{
					String next = tokens[keyword].toLowerCase().trim();
					if (next.length() > 0)
					{
						// Add only new keys
						Keyword newKey = new Keyword(next);
						if (!treeOfKeywords.contains(newKey))
						{
							treeOfKeywords.add(newKey);
							treeOfKeywords.getItem(newKey).addFood(newFood);
						}

						// Add the food to its keyword in the keyword search
						// tree
						else
						{
							Keyword existingKey = treeOfKeywords
									.getItem(newKey);
							if (existingKey.indexOf(newFood) < 0)
							{
								treeOfKeywords.getItem(newKey).addFood(newFood);
							}
						}
					}
				}
			}
			else
			{
				// Skip all the Nut data, Weight, and Footnote lines for this
				// specific food if it is deleted or already imported
				if (currentInNutDataLine != null)
				{
					while (currentInNutDataLine.substring(1, NDB_No_End)
							.equals(
									NDB_No))
					{
						if (inNutData.hasNextLine())
						{
							currentInNutDataLine = inNutData.nextLine();
						}
						else
						{
							break;
						}
					}
				}
				if (currentInWeightLine != null)
				{
					while (currentInWeightLine.substring(1, NDB_No_End).equals(
							NDB_No))
					{
						if (inWeight.hasNextLine())
						{
							currentInWeightLine = inWeight.nextLine();
						}
						else
						{
							break;
						}
					}
				}
				if (currentInFootnoteLine != null)
				{
					while (currentInFootnoteLine.substring(1, NDB_No_End)
							.equals(NDB_No))
					{
						if (inFootnote.hasNextLine())
						{
							currentInFootnoteLine = inFootnote.nextLine();
						}
						else
						{
							break;
						}
					}
				}
			}
		}
		inAddFoodDes.close();
		inAddNutData.close();
		inAddWeight.close();
		inAddFootnote.close();

		// Import all the original food data the exact same way
		currentLine = null;
		currentInNutDataLine = inNutData.nextLine();
		currentInWeightLine = inWeight.nextLine();
		currentInFootnoteLine = inFootnote.nextLine();

		// Read in all the foods in the food description file
		while ((currentLine = inFoodDes.readLine()) != null)
		{
			int NDB_No_End = currentLine.indexOf('~', 1);
			String NDB_No = currentLine.substring(1, NDB_No_End);

			// Adding the current item only if the NDB_No has not already been
			// used
			if (listOfUsedNDB_No.indexOf(NDB_No) < 0)
			{
				int FdGrp_Cd_End = currentLine.indexOf('~', NDB_No_End + 3);
				String FdGrp_Cd = currentLine.substring(NDB_No_End + 3,
						FdGrp_Cd_End);

				FoodGroup foodGroup = new FoodGroup(FdGrp_Cd);
				foodGroup = listOfFd_Groups.get(listOfFd_Groups
						.indexOf(foodGroup));

				int Long_Desc_End = currentLine.indexOf('~', FdGrp_Cd_End + 3);
				String Long_Desc = currentLine.substring(FdGrp_Cd_End + 3,
						Long_Desc_End);

				int Shrt_Desc_End = currentLine.indexOf('~', Long_Desc_End + 3);
				String Shrt_Desc = currentLine.substring(Long_Desc_End + 3,
						Shrt_Desc_End);

				int ComNameEnd = currentLine.indexOf('~', Shrt_Desc_End + 3);
				String ComName = currentLine.substring(Shrt_Desc_End + 3,
						ComNameEnd);

				int ManufacNameEnd = currentLine.indexOf('~', ComNameEnd + 3);
				String ManufacName = currentLine.substring(ComNameEnd + 3,
						ManufacNameEnd);

				int SurveyEnd = currentLine.indexOf('~', ManufacNameEnd + 3);
				String Survey = currentLine
						.substring(ManufacNameEnd + 3, SurveyEnd);

				int Ref_desc_End = currentLine.indexOf('~', SurveyEnd + 3);
				String Ref_desc = currentLine
						.substring(SurveyEnd + 3, Ref_desc_End);

				int RefuseEnd = currentLine.indexOf('^', Ref_desc_End + 2);
				double Refuse = -1;
				if (RefuseEnd != Ref_desc_End + 2)
				{
					Refuse = Double.parseDouble(currentLine.substring(
							Ref_desc_End + 2, RefuseEnd));
				}

				int SciNameEnd = currentLine.indexOf('~', RefuseEnd + 2);
				String SciName = currentLine
						.substring(RefuseEnd + 2, SciNameEnd);

				int N_FactorEnd = currentLine.indexOf('^', SciNameEnd + 2);
				double N_Factor = -1;
				if (N_FactorEnd != SciNameEnd + 2)
				{
					N_Factor = Double.parseDouble(currentLine.substring(
							SciNameEnd + 2, N_FactorEnd));
				}

				int Pro_FactorEnd = currentLine.indexOf('^', N_FactorEnd + 1);
				double Pro_Factor = -1;
				if (Pro_FactorEnd != N_FactorEnd + 1)
				{
					Pro_Factor = Double.parseDouble(currentLine.substring(
							N_FactorEnd + 1, Pro_FactorEnd));
				}

				int Fat_FactorEnd = currentLine.indexOf('^', Pro_FactorEnd + 1);
				double Fat_Factor = -1;
				if (Fat_FactorEnd != Pro_FactorEnd + 1)
				{
					Fat_Factor = Double.parseDouble(currentLine.substring(
							Pro_FactorEnd + 1, Fat_FactorEnd));
				}

				double CHO_Factor = -1;
				if (Fat_FactorEnd != currentLine.length() - 1)
				{
					CHO_Factor = Double.parseDouble(currentLine.substring(
							Fat_FactorEnd + 1));
				}

				// The new food to be added
				Food newFood = new Food(NDB_No, foodGroup, Long_Desc,
						Shrt_Desc,
						ComName, ManufacName, Survey, Ref_desc, Refuse,
						SciName,
						N_Factor, Pro_Factor, Fat_Factor, CHO_Factor);

				// Add all the nutrients for this specific food
				while (currentInNutDataLine.substring(1, NDB_No_End).equals(
						NDB_No))
				{

					String[] tokens = currentInNutDataLine.split("\\^");
					String Nutr_No = tokens[1].substring(1,
							tokens[1].length() - 1);
					double Nutr_Val = Double.parseDouble(tokens[2]);
					int Num_Data_Pts_Nut = Integer.parseInt(tokens[3]);

					double Std_Error = -1;
					if (tokens[4].length() > 0)
					{
						Std_Error = Double.parseDouble(tokens[4]);
					}

					String Src_Cd = tokens[5].substring(1,
							tokens[5].length() - 1);
					String Derive_Cd = tokens[6].substring(1,
							tokens[6].length() - 1);
					String Ref_NDB_No = tokens[7].substring(1,
							tokens[7].length() - 1);
					String Add_Nutr_Mark = tokens[8].substring(1,
							tokens[8].length() - 1);

					int Num_Studies = -1;

					if (tokens[9].length() > 0)
					{
						Num_Studies = Integer.parseInt(tokens[9]);
					}

					double Min = -1;
					if (tokens[10].length() > 0)
					{
						Min = Double.parseDouble(tokens[10]);
					}

					double Max = -1;
					if (tokens[11].length() > 0)
					{
						Max = Double.parseDouble(tokens[11]);
					}

					double DF = -1;
					if (tokens[12].length() > 0)
					{
						DF = Double.parseDouble(tokens[12]);
					}

					double Low_EB = -1;
					if (tokens[13].length() > 0)
					{
						Low_EB = Double.parseDouble(tokens[13]);
					}

					double Up_EB = -1;
					if (tokens[14].length() > 0)
					{
						Up_EB = Double.parseDouble(tokens[14]);
					}

					String Stat_cmt = tokens[15].substring(1,
							tokens[15].length() - 1);
					String AddMod_Date = "";
					if (tokens.length > 16)
					{
						AddMod_Date = tokens[16].substring(0,
								tokens[16].length());
					}

					// Add the nutrient with the index of the type of nutrient
					// as well
					Nutrient checkNut = new Nutrient(Nutr_No);
					Nutrient typeOfNutrient = listOfNutrients.getItem(checkNut);

					// Add the nutrient to the current food
					newFood.addNutrient(new NutrientForFood(typeOfNutrient,
							Nutr_Val,
							Num_Data_Pts_Nut, Std_Error, Src_Cd,
							Derive_Cd, Ref_NDB_No, Add_Nutr_Mark,
							Num_Studies, Min, Max, DF, Low_EB,
							Up_EB, Stat_cmt, AddMod_Date, nutForFood_No++));
					noOfNutrients++;

					// Import the next nut data line
					if (inNutData.hasNextLine())
					{
						currentInNutDataLine = inNutData.nextLine();
					}
					else
					{
						break;
					}

				}

				// Add all the weights for this specific food
				while (currentInWeightLine.substring(1, NDB_No_End).equals(
						NDB_No))
				{
					String[] tokens = currentInWeightLine.split("\\^");

					String Seq = tokens[1];

					double Amount = Double.parseDouble(tokens[2]);

					String Msre_Desc = tokens[3].substring(1,
							tokens[3].length() - 1);

					double Gm_Wgt = Double.parseDouble(tokens[4]);

					int Num_Data_Pts = -1;
					if (tokens.length > 5 && tokens[5].length() > 0)
					{
						Num_Data_Pts = Integer.parseInt(tokens[5]);
					}

					double Std_Dev = -1;
					if (tokens.length > 6 && tokens[6].length() > 0)
					{
						Std_Dev = Double.parseDouble(tokens[6]);
					}

					newFood.addWeight(new Weight(Seq, Amount, Msre_Desc,
							Gm_Wgt, Num_Data_Pts, Std_Dev));

					// Import the next weight line
					if (inWeight.hasNextLine())
					{
						currentInWeightLine = inWeight.nextLine();
					}
					else
					{
						break;
					}
				}

				// Add all the footnotes for this specific food
				while (currentInFootnoteLine.substring(1, NDB_No_End)
						.equals(NDB_No))
				{
					String[] tokens = currentInFootnoteLine.split("\\^");

					String Footnt_No = tokens[1].substring(1,
							tokens[1].length() - 1);
					char Footnt_Typ = tokens[2].charAt(1);
					String Nutr_No = tokens[3].substring(1,
							tokens[3].length() - 1);
					String Footnt_Txt = tokens[4].substring(1,
							tokens[4].length() - 1);

					newFood.addFootnote(new Footnote(Footnt_No, Footnt_Typ,
							Nutr_No, Footnt_Txt));

					// Import the next weight line
					if (inFootnote.hasNextLine())
					{
						currentInFootnoteLine = inFootnote.nextLine();
					}
					else
					{
						break;
					}

				}

				// Get each of the keywords for the food
				String[] tokens = (Long_Desc + " " + Shrt_Desc + " " + ComName
						+ " " + ManufacName)
						.split(" |\\)|\\(|;|,|/|\\&|!|\\.|-");
				for (int keyword = 0; keyword < tokens.length; keyword++)
				{
					String next = tokens[keyword].toLowerCase().trim();
					if (next.length() > 0)
					{
						// Add only new keys
						Keyword newKey = new Keyword(next);
						if (!treeOfKeywords.contains(newKey))
						{
							treeOfKeywords.add(newKey);
							treeOfKeywords.getItem(newKey).addFood(newFood);
						}
						// Add the food to its keyword in the keyword search
						// tree
						else
						{
							Keyword existingKey = treeOfKeywords
									.getItem(newKey);
							if (existingKey.indexOf(newFood) < 0)
							{
								treeOfKeywords.getItem(newKey).addFood(newFood);
							}
						}
					}
				}
			}
			else
			{
				// Skip all the Nut data, Weight, and Footnote lines for this
				// specific food if it is deleted or already imported
				while (currentInNutDataLine.substring(1, NDB_No_End).equals(
						NDB_No))
				{
					if (inNutData.hasNextLine())
					{
						currentInNutDataLine = inNutData.nextLine();
					}
					else
					{
						break;
					}
				}
				while (currentInWeightLine.substring(1, NDB_No_End).equals(
						NDB_No))
				{
					if (inWeight.hasNextLine())
					{
						currentInWeightLine = inWeight.nextLine();
					}
					else
					{
						break;
					}
				}
				while (currentInFootnoteLine.substring(1, NDB_No_End)
						.equals(NDB_No))
				{
					if (inFootnote.hasNextLine())
					{
						currentInFootnoteLine = inFootnote.nextLine();
					}
					else
					{
						break;
					}
				}
			}
		}
		inNutDef.close();
		inFoodGroup.close();
		inFoodDes.close();
		inNutData.close();
		inWeight.close();
		inFootnote.close();
	}

	/**
	 * Adds a new food to the database
	 * @param newFood the new food
	 * @param foodNutrients the new food's nutrients
	 * @param foodWeights the new food's weights
	 * @param foodFootnotes the new food's footnotes
	 */
	public void addNewFood(Food newFood,
			DoublyLinkedList<NutrientForFood> foodNutrients,
			DoublyLinkedList<Weight> foodWeights,
			DoublyLinkedList<Footnote> foodFootnotes)
	{
		// If the food already exists in the additional text files, then remove
		// it and replace it with the given one
		String NDB_No = newFood.getNDB_No();

		// Un-delete the food
		if (listOfDeletedNDB_No.indexOf(NDB_No) >= 0)
		{
			listOfDeletedNDB_No.remove(NDB_No);
		}

		// Remove any foods in the additional files with the same NDB_No
		// (Replace them)
		for (int lineNo = 0; lineNo < allAddFoodDesLines.size(); lineNo++)
		{
			if (allAddFoodDesLines.get(lineNo).indexOf(NDB_No) >= 0)
			{
				allAddFoodDesLines.remove(lineNo);
				break;
			}
		}
		for (int lineNo = 0; lineNo < allAddNutDataLines.size(); lineNo++)
		{
			if (allAddNutDataLines.get(lineNo).indexOf(NDB_No) >= 0)
			{
				allAddNutDataLines.remove(lineNo);
			}
		}
		for (int lineNo = 0; lineNo < allAddWeightLines.size(); lineNo++)
		{
			if (allAddWeightLines.get(lineNo).indexOf(NDB_No) >= 0)
			{
				allAddWeightLines.remove(lineNo);
			}
		}
		for (int lineNo = 0; lineNo < allAddFootnoteLines.size(); lineNo++)
		{
			if (allAddFootnoteLines.get(lineNo).indexOf(NDB_No) >= 0)
			{
				allAddFootnoteLines.remove(lineNo);
			}
		}

		// Add this food appropriately to the list of Food Descriptions
		String newLine = "~" + newFood.getNDB_No() + "~^~"
				+ newFood.getFoodGroup().getFdGrp_Cd() + "~^~"
				+ newFood.getLong_Desc()
				+ "~^~" + newFood.getShrt_Desc() + "~^~" + newFood.getComName()
				+ "~^~" + newFood.getManufacName() + "~^~"
				+ newFood.getSurvey()
				+ "~^~" + newFood.getRef_desc() + "~^" + newFood.getRefuse()
				+ "^~" + newFood.getSciName()
				+ "~^" + newFood.getN_Factor() + "^" + newFood.getPro_Factor()
				+ "^" + newFood.getFat_Factor()
				+ "^" + newFood.getCHO_Factor();
		allAddFoodDesLines.add(newLine);

		// Add all the nutrients for this food to the list of Nutritional Data
		int noOfFoodNutrients = foodNutrients.size();
		for (int nutrient = 0; nutrient < noOfFoodNutrients; nutrient++)
		{
			NutrientForFood newFoodNutrient = foodNutrients.get(nutrient);
			newFood.addNutrient(newFoodNutrient);
			newLine = "~" + newFood.getNDB_No() + "~^~"
					+ newFoodNutrient.getTypeOfNutrient().getNutr_No() + "~^"
					+ newFoodNutrient.getNutr_Val() + "^"
					+ newFoodNutrient.getNum_Data_Pts_Nut()
					+ "^" + newFoodNutrient.getStd_Error() + "^~"
					+ newFoodNutrient.getSrc_Cd() + "~^~"
					+ newFoodNutrient.getDerive_Cd() + "~^~"
					+ newFoodNutrient.getRef_NDB_No()
					+ "~^~" + newFoodNutrient.getAdd_Nutr_Mark() + "~^"
					+ newFoodNutrient.getNum_Studies() + "^"
					+ newFoodNutrient.getMin() + "^" + newFoodNutrient.getMax()
					+ "^" + newFoodNutrient.getDF() + "^"
					+ newFoodNutrient.getLow_EB() + "^"
					+ newFoodNutrient.getUp_EB() + "^~"
					+ newFoodNutrient.getStat_cmt() + "~^"
					+ newFoodNutrient.getAddMod_Date() + "^";
			allAddNutDataLines.add(newLine);
		}

		// Add all the weights for this food to the list of Weights
		int noOfWeights = foodWeights.size();
		for (int weight = 0; weight < noOfWeights; weight++)
		{
			Weight newWeight = foodWeights.get(weight);
			newFood.addWeight(newWeight);
			newLine = "~" + newFood.getNDB_No() + "~^" + newWeight.getSeq()
					+ "^" + newWeight.getAmount() + "^~"
					+ newWeight.getMsre_Desc() + "~^" + newWeight.getGm_Wgt()
					+ "^" + newWeight.getNum_Data_Pts() + "^"
					+ newWeight.getStd_Dev();
			allAddWeightLines.add(newLine);
		}

		// Add all the footnotes for this food to the list of Footnotes
		int noOfFootnotes = foodFootnotes.size();
		for (int footnote = 0; footnote < noOfFootnotes; footnote++)
		{
			Footnote newFootnote = foodFootnotes.get(footnote);
			newFood.addFootnote(newFootnote);
			newLine = "~" + newFood.getNDB_No() + "~^~"
					+ newFootnote.getFootnt_No() + "~^~"
					+ newFootnote.getFootnt_Typ() + "~^~"
					+ newFootnote.getNutr_No() + "~^~"
					+ newFootnote.getFootnt_Txt() + "~";
			allAddFootnoteLines.add(newLine);
		}

		// Get each of the keywords for the food
		String[] tokens = (newFood.getLong_Desc() + " "
				+ newFood.getShrt_Desc() + " " + newFood.getComName()
				+ " " + newFood.getManufacName())
				.split(" |\\)|\\(|;|,|/|\\&|!|\\.|-");
		for (int keyword = 0; keyword < tokens.length; keyword++)
		{
			String next = tokens[keyword].toLowerCase().trim();
			if (next.length() > 0)
			{
				// Add only new keys
				Keyword newKey = new Keyword(next);
				if (!treeOfKeywords.contains(newKey))
				{
					treeOfKeywords.add(newKey);
					treeOfKeywords.getItem(newKey).addFood(newFood);
				}
				else
				{
					// Add the keyword to its matching key in the search tree of
					// keywords
					Keyword existingKey = treeOfKeywords.getItem(newKey);
					if (existingKey.indexOf(newFood) < 0)
					{
						treeOfKeywords.getItem(newKey).addFood(newFood);
					}
				}
			}
		}
	}

	/**
	 * Delete a certain food
	 * @param NDB_No the NDB_No of the deleted food
	 */
	public void deleteFood(String NDB_No)
	{
		listOfDeletedNDB_No.add(NDB_No);
	}

	/**
	 * Save all the changes to the external text files
	 * @throws UnsupportedEncodingException if encoding fails
	 * @throws FileNotFoundException if one of the required files is not found
	 */
	public void saveChanges() throws FileNotFoundException
	{
		// Print to the food description file
		PrintWriter outFile = new PrintWriter(new File("Additional Data",
				"ADD_FOOD_DES.txt"));
		int lengthOfFoodDes = allAddFoodDesLines.size();
		for (int lineNo = 0; lineNo < lengthOfFoodDes; lineNo++)
		{
			outFile.println(allAddFoodDesLines.get(lineNo));
		}
		outFile.close();

		// Print to the nut data file
		outFile = new PrintWriter(new File("Additional Data",
				"ADD_NUT_DATA.txt"));
		int lengthOfNutData = allAddNutDataLines.size();
		for (int lineNo = 0; lineNo < lengthOfNutData; lineNo++)
		{
			outFile.println(allAddNutDataLines.get(lineNo));
		}
		outFile.close();

		// Print to the weight file
		outFile = new PrintWriter(new File("Additional Data",
				"ADD_WEIGHT.txt"));
		int lengthOfWeight = allAddWeightLines.size();
		for (int lineNo = 0; lineNo < lengthOfWeight; lineNo++)
		{
			outFile.println(allAddWeightLines.get(lineNo));
		}
		outFile.close();

		// Print the footnote file
		outFile = new PrintWriter(new File("Additional Data",
				"ADD_FOOTNOTE.txt"));

		int lengthOfFootnote = allAddFootnoteLines.size();
		for (int lineNo = 0; lineNo < lengthOfFootnote; lineNo++)
		{
			outFile.println(allAddFootnoteLines.get(lineNo));
		}
		outFile.close();

		// Print to the deleted foods file
		outFile = new PrintWriter(new File("Additional Data",
				"DELETED_FOODS.txt"));
		int lengthOfDeletedFoods = listOfDeletedNDB_No.size();
		for (int lineNo = 0; lineNo < lengthOfDeletedFoods; lineNo++)
		{
			outFile.println(listOfDeletedNDB_No.get(lineNo));
		}
		outFile.close();
	}

	/**
	 * Searches the database for all items that match the user's search results
	 * and returns a priority queue sorted by the number of matching keywords
	 * @param entry what the user typed in
	 * @return a priority queue containing all the search results
	 */
	public PriorityQueue<Food> search(String entry)
	{
		// Store the results in parallel lists of the results and their priority
		DoublyLinkedList<Food> listOfResults = new DoublyLinkedList<Food>();
		DoublyLinkedList<Integer> noOfMatches = new DoublyLinkedList<Integer>();

		// Get all the keywords of the user's entry
		String[] keywords = entry.split(" ");
		for (int word = 0; word < keywords.length; word++)
		{
			// Return all the results related to each of the user's keywords
			Keyword newKey = new Keyword(keywords[word].toLowerCase().trim());
			if (treeOfKeywords.contains(newKey))
			{
				DoublyLinkedList<Food> newKeyResults = treeOfKeywords.getItem(
						newKey).getListOfFoods();

				int noOfResults = newKeyResults.size();
				for (int result = 0; result < noOfResults; result++)
				{
					Food newFood = newKeyResults.get(result);
					int newFoodIndex = listOfResults.indexOf(newFood);

					if (newFoodIndex >= 0)
					{
						noOfMatches.replace(noOfMatches.get(newFoodIndex) + 1,
								newFoodIndex);
					}
					else
					{
						listOfResults.add(newFood);
						noOfMatches.add(1);
					}
				}
			}

			// Return all plural results as well even if the user types singular
			newKey = new Keyword(keywords[word].toLowerCase().trim() + "s");

			if (treeOfKeywords.contains(newKey))
			{
				DoublyLinkedList<Food> newKeyResults = treeOfKeywords.getItem(
						newKey).getListOfFoods();

				int noOfResults = newKeyResults.size();
				for (int result = 0; result < noOfResults; result++)
				{
					Food newFood = newKeyResults.get(result);
					int newFoodIndex = listOfResults.indexOf(newFood);

					if (newFoodIndex >= 0)
					{
						noOfMatches.replace(noOfMatches.get(newFoodIndex) + 1,
								newFoodIndex);
					}
					else
					{
						listOfResults.add(newFood);
						noOfMatches.add(1);
					}
				}
			}

			// Return all results with a 'y' at the end
			newKey = new Keyword(keywords[word].toLowerCase().trim() + "y");

			if (treeOfKeywords.contains(newKey))
			{
				DoublyLinkedList<Food> newKeyResults = treeOfKeywords.getItem(
						newKey).getListOfFoods();

				int noOfResults = newKeyResults.size();
				for (int result = 0; result < noOfResults; result++)
				{
					Food newFood = newKeyResults.get(result);
					int newFoodIndex = listOfResults.indexOf(newFood);

					if (newFoodIndex >= 0)
					{
						noOfMatches.replace(noOfMatches.get(newFoodIndex) + 1,
								newFoodIndex);
					}
					else
					{
						listOfResults.add(newFood);
						noOfMatches.add(1);
					}
				}
			}
		}

		// Sort the results by number of matching keywords to the user's input
		PriorityQueue<Food> sortedResults = new PriorityQueue<Food>();
		int noOfResults = listOfResults.size();
		for (int result = 0; result < noOfResults; result++)
		{
			sortedResults.enqueue(listOfResults.get(result),
					noOfMatches.get(result));
		}
		return sortedResults;
	}
}
