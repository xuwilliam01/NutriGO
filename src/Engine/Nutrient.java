package Engine;
/**
 * Stores information about a specific nutrient
 * @author Alex Raita & William Xu
 * @version 22 November, 2015
 */
public class Nutrient implements Comparable<Nutrient>
{
	private String Nutr_No;
	private String Units;
	private String Tagname;
	private String NutrDesc;
	private int Num_Desc;
	private int SR_Order;
	
	/**
	 * Constructor only for the nutrient number
	 * @param nutr_No the nutrient number
	 */
	public Nutrient(String nutr_No)
	{
		Nutr_No = nutr_No;
	}
	
	/**
	 * Constructor for all variables
	 * @param nutr_No the nutrient number
	 * @param units the units used to measure this nutrient
	 * @param tagname the tagname of the nutrient
	 * @param nutrDesc the description of the nutrient
	 * @param num_Desc the number of decimals for rounding
	 * @param sR_Order a number used for ordering nutrients
	 */
	public Nutrient(String nutr_No, String units, String tagname,
			String nutrDesc, int num_Desc, int sR_Order)
	{
		Nutr_No = nutr_No;
		Units = units;
		Tagname = tagname;
		NutrDesc = nutrDesc;
		Num_Desc = num_Desc;
		SR_Order = sR_Order;
	}
	
	/**
	 * Gets the nutrient number
	 * @return the nutrient number
	 */
	public String getNutr_No()
	{
		return Nutr_No;
	}
	
	/**
	 * Gets the units
	 * @return the units
	 */
	public String getUnits()
	{
		return Units;
	}
	
	/**
	 * Gets the tagname
	 * @return the tagname
	 */
	public String getTagname()
	{
		return Tagname;
	}
	
	/**
	 * Gets the nutrient description
	 * @return the nutrient description
	 */
	public String getNutrDesc()
	{
		return NutrDesc;
	}
	
	/**
	 * Gets the number description
	 * @return the number description
	 */
	public int getNum_Desc()
	{
		return Num_Desc;
	}
	
	/**
	 * Gets the sr order
	 * @return the sr order
	 */
	public int getSR_Order()
	{
		return SR_Order;
	}
	
	/**
	 * Compares two nutrient objects by their nutrient number
	 * @param item the nutrient that is being compared
	 * @return the integer difference between the nutrient numbers
	 */
	public int compareTo(Nutrient item)
	{
		return Nutr_No.compareTo(item.getNutr_No());
	}
	
	
}
