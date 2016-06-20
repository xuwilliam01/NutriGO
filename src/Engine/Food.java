package Engine;
/**
 * Stores information from the FOOD_DES.txt file (food description)
 * @author Alex Raita && William Xu
 * @version November 22, 2015
 */
public class Food implements Comparable<Food>
{

	// Food Description Variables
	private String NDB_No;
	private FoodGroup foodGroup;
	private String Long_Desc;
	private String Shrt_Desc;
	private String ComName;
	private String ManufacName;
	private String Survey;
	private String Ref_desc;
	private double Refuse;
	private String SciName;
	private double N_Factor;
	private double Pro_Factor;
	private double Fat_Factor;
	private double CHO_Factor;

	// Nutrient Data
	private  DoublyLinkedList <NutrientForFood> foodNutrients = new DoublyLinkedList<NutrientForFood>();

	// Weight
	private DoublyLinkedList<Weight> weights = new DoublyLinkedList<Weight>();

	// Footnotes
	private DoublyLinkedList<Footnote> footnotes = new DoublyLinkedList<Footnote>();

	/**
	 * The constructor that initializes the variables
	 * @param nDB_No the NDB_No
	 * @param foodGroup the food group
	 * @param long_Desc the long description
	 * @param shrt_Desc the short the description
	 * @param comName a list of common names
	 * @param manufacName the manufacturer's name
	 * @param survey indicates if the foot item was used in a survey 
	 * @param ref_desc description of what cannot be consumed
	 * @param refuse the percentage of what cannot be consumed
	 * @param sciName the scientific name
	 * @param n_Factor the nitrogen factor
	 * @param pro_Factor the protein factor
	 * @param fat_Factor the fat factor
	 * @param cHO_Factor the cholesterol factor
	 */
	public Food(String nDB_No, FoodGroup foodGroup, String long_Desc,
			String shrt_Desc, String comName, String manufacName,
			String survey, String ref_desc, double refuse, String sciName,
			double n_Factor, double pro_Factor, double fat_Factor,
			double cHO_Factor)
	{
		super();
		NDB_No = nDB_No;
		this.foodGroup = foodGroup;
		Long_Desc = long_Desc;
		Shrt_Desc = shrt_Desc;
		ComName = comName;
		ManufacName = manufacName;
		Survey = survey;
		Ref_desc = ref_desc;
		Refuse = refuse;
		SciName = sciName;
		N_Factor = n_Factor;
		Pro_Factor = pro_Factor;
		Fat_Factor = fat_Factor;
		CHO_Factor = cHO_Factor;
	}

	/**
	 * Gets the food group
	 * @return the food group
	 */
	public FoodGroup getFoodGroup()
	{
		return foodGroup;
	}

	/**
	 * Gets the food nutrients
	 * @return the food nutrients
	 */
	public DoublyLinkedList<NutrientForFood> getFoodNutrients()
	{
		return foodNutrients;
	}

	/**
	 * Adds a nutrient
	 * @param item the nutrient to be added
	 */
	public void addNutrient(NutrientForFood item)
	{
		foodNutrients.add(item);
	}

	/**
	 * Gets the weights
	 * @return the weights
	 */
	public DoublyLinkedList<Weight> getWeights()
	{
		return weights;
	}

	/**
	 * Adds a weight
	 * @param item the weight to be added
	 */
	public void addWeight(Weight item)
	{
		weights.add(item);
	}

	/**
	 * Gets the footnotes
	 * @return the footnotes
	 */
	public DoublyLinkedList<Footnote> getFootnotes()
	{
		return footnotes;
	}

	/**
	 * Adds a footnote
	 * @param item the footnote to be added
	 */
	public void addFootnote(Footnote item)
	{
		footnotes.add(item);
	}

	/**
	 * Gets the NDB_No
	 * @return the NDB_No
	 */
	public String getNDB_No()
	{
		return NDB_No;
	}


	/**
	 * Gets the long description
	 * @return the long description
	 */
	public String getLong_Desc()
	{
		return Long_Desc;
	}

	/**
	 * Gets the description
	 * @return the short description
	 */
	public String getShrt_Desc()
	{
		return Shrt_Desc;
	}

	/**
	 * Gets the common names
	 * @return the common names
	 */
	public String getComName()
	{
		return ComName;
	}

	/**
	 * Gets the manufacturer's name
	 * @return the manufacturer's name
	 */
	public String getManufacName()
	{
		return ManufacName;
	}


	/**
	 * Gets the survey
	 * @return the survey
	 */
	public String getSurvey()
	{
		return Survey;
	}

	/**
	 * Gets the refuse description
	 * @return the refuse description
	 */
	public String getRef_desc()
	{
		return Ref_desc;
	}

	/**
	 * Gets the refuse percent
	 * @return the refuse percent
	 */
	public double getRefuse()
	{
		return Refuse;
	}

	/**
	 * Gets the scientific name
	 * @return the scientific name
	 */
	public String getSciName()
	{
		return SciName;
	}

	/**
	 * Gets the nitrogen factor
	 * @return the nitrogen factor
	 */
	public double getN_Factor()
	{
		return N_Factor;
	}

	/**
	 * Gets the protein factor 
	 * @return the protein factor
	 */
	public double getPro_Factor()
	{
		return Pro_Factor;
	}

	/**
	 * Gets the fat factor
	 * @return the fact factor
	 */
	public double getFat_Factor()
	{
		return Fat_Factor;
	}

	/**
	 * Gets the cholesterol factor
	 * @return the cholesterol factor
	 */
	public double getCHO_Factor()
	{
		return CHO_Factor;
	}

	/**
	 * Gets the nutrients
	 * @return the nutrients
	 */
	public DoublyLinkedList<NutrientForFood> getNutrients()
	{
		return foodNutrients;
	}

	/**
	 * Compares two foods by their long description
	 * @other the food to be compared to
	 * @return the integer difference between the two long descriptions
	 */
	public int compareTo(Food other)
	{
		return Long_Desc.compareTo(other.Long_Desc);
	}






}
