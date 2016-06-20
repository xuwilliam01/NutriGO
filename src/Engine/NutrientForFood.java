package Engine;
/**
 * Stores information about specific nutrients for a given food
 * @author Alex Raita && William Xu
 * @version 22 November, 2015
 */
public class NutrientForFood implements Comparable <NutrientForFood>
{
	Nutrient typeOfNutrient;
	private double Nutr_Val;
	private int Num_Data_Pts_Nut;
	private double Std_Error;
	private String Src_Cd;
	private String Derive_Cd;
	private String Ref_NDB_No;
	private String Add_Nutr_Mark;
	private int Num_Studies;
	private double Min;
	private double Max;
	private double DF;
	private double Low_EB;
	private double Up_EB;
	private String Stat_cmt;
	private String AddMod_Date;
	private int identifier;

	/**
	 * The constructor
	 * @param typeOfNutrient the type of nutrient this object is
	 * @param nutr_Val the amount of the nutrient
	 * @param num_Data_Pts_Nut the amount of data points used
	 * @param std_Error the standard error of the mean
	 * @param src_Cd the code indicating type of data
	 * @param derive_Cd the data derivation code
	 * @param ref_NDB_No the number of the item used to calculate a missing value
	 * @param add_Nutr_Mark indicates a vitamin or mineral added
	 * @param num_Studies the number of studies
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param dF the degrees of freedom
	 * @param low_EB the lower 95% error bound
	 * @param up_EB the upper 95% error bound
	 * @param stat_cmt statistical comments
	 * @param addMod_Date indicates when a value was added or modified 
	 * @param identifier an integer that is used to sort the nutrients
	 */
	public NutrientForFood(Nutrient typeOfNutrient, double nutr_Val,
			int num_Data_Pts_Nut, double std_Error, String src_Cd,
			String derive_Cd, String ref_NDB_No, String add_Nutr_Mark,
			int num_Studies, double min, double max, double dF, double low_EB,
			double up_EB, String stat_cmt, String addMod_Date, int identifier)
	{
		this.typeOfNutrient = typeOfNutrient;
		Nutr_Val = nutr_Val;
		Num_Data_Pts_Nut = num_Data_Pts_Nut;
		Std_Error = std_Error;
		Src_Cd = src_Cd;
		Derive_Cd = derive_Cd;
		Ref_NDB_No = ref_NDB_No;
		Add_Nutr_Mark = add_Nutr_Mark;
		Num_Studies = num_Studies;
		Min = min;
		Max = max;
		DF = dF;
		Low_EB = low_EB;
		Up_EB = up_EB;
		Stat_cmt = stat_cmt;
		AddMod_Date = addMod_Date;
		this.identifier = identifier;
	}

	/**
	 * Gets the type of nutrient
	 * @return the type of nutrient
	 */
	public Nutrient getTypeOfNutrient()
	{
		return typeOfNutrient;
	}

	/**
	 * Gets the nutrient value
	 * @return the nutrient value
	 */
	public double getNutr_Val()
	{
		return Nutr_Val;
	}

	/**
	 * Gets the number of data point
	 * @return the number of data points
	 */
	public int getNum_Data_Pts_Nut()
	{
		return Num_Data_Pts_Nut;
	}

	/**
	 * Gets the standard error
	 * @return the standard error
	 */
	public double getStd_Error()
	{
		return Std_Error;
	}

	/**
	 * Gets the src code
	 * @return the src code
	 */
	public String getSrc_Cd()
	{
		return Src_Cd;
	}

	/**
	 * Gets the derivation code
	 * @return the derivation code
	 */
	public String getDerive_Cd()
	{
		return Derive_Cd;
	}

	/**
	 * Gets the reference NDB_No
	 * @return the reference NDB_No
	 */
	public String getRef_NDB_No()
	{
		return Ref_NDB_No;
	}

	/**
	 * Gets the add nutrient mark
	 * @return the add nutrient mark
	 */
	public String getAdd_Nutr_Mark()
	{
		return Add_Nutr_Mark;
	}

	/**
	 * Gets the number of studies
	 * @return the number of studies
	 */
	public int getNum_Studies()
	{
		return Num_Studies;
	}

	/**
	 * Gets the minimum
	 * @return the minimum
	 */
	public double getMin()
	{
		return Min;
	}

	/**
	 * Gets the maximum
	 * @return the maximum
	 */
	public double getMax()
	{
		return Max;
	}

	/**
	 * Gets the degrees of freedom
	 * @return the degrees of freedom
	 */
	public double getDF()
	{
		return DF;
	}

	/**
	 * Gets the lower bound
	 * @return the lower bound
	 */
	public double getLow_EB()
	{
		return Low_EB;
	}

	/**
	 * Gets the upper bound
	 * @return the upper bound
	 */
	public double getUp_EB()
	{
		return Up_EB;
	}

	/**
	 * Gets the statistical comments
	 * @return the statistical comments
	 */
	public String getStat_cmt()
	{
		return Stat_cmt;
	}

	/**
	 * Gets the add/modify date
	 * @return the add/modify date
	 */
	public String getAddMod_Date()
	{
		return AddMod_Date;
	}

	/**
	 * Compares two nutrient for food objects by their idenitifier
	 * @param other the object to be compared to
	 * @return the integer the difference between the two objects
	 */
	public int compareTo(NutrientForFood other) {
		return identifier - other.identifier;
	}


}
