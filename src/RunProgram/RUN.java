package RunProgram;
import java.io.IOException;

import Engine.NutritionGUI;

/**
 * Runs the Nutrition Program
 * @author Alex Raita & William Xu
 * @version 22 November,2015
 */
public class RUN
{
	public static void main(String[] args) throws IOException
	{
		// Create a gui object and start the program
		NutritionGUI gui = new NutritionGUI();
		gui.startProgram();
	}
}
