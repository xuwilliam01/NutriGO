package Engine;
/**
 * Stores information about a footnote
 * @author Alex Raita & William Xu
 * @version 22 November, 2015
 */
public class Footnote implements Comparable<Footnote>
{
	private String Footnt_No;
	private char Footnt_Typ;
	private String Nutr_No;
	private String Footnt_Txt;

	/**
	 * Constructor for the footnote
	 * @param footnt_No the footnote number
	 * @param footnt_Typ the type of footnote
	 * @param nutr_No the nutrient number it references
	 * @param footnt_Txt the text of the footnote
	 */
	public Footnote(String footnt_No, char footnt_Typ,
			String nutr_No, String footnt_Txt)
	{
		Footnt_No = footnt_No;
		Footnt_Typ = footnt_Typ;
		Nutr_No = nutr_No;
		Footnt_Txt = footnt_Txt;
	}

	/**
	 * Gets the footnote number
	 * @return the footnote number
	 */
	public String getFootnt_No()
	{
		return Footnt_No;
	}

	/**
	 * Gets the footnote type
	 * @return the footnote type
	 */
	public char getFootnt_Typ()
	{
		return Footnt_Typ;
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
	 * Gets the footnote text
	 * @return the footnote text
	 */
	public String getFootnt_Txt()
	{
		return Footnt_Txt;
	}

	/**
	 * Compares two footnote object
	 * @param other the footnote this object is being compared to
	 * @return an integer difference between the two footnotes
	 */
	public int compareTo(Footnote other)
	{
		return Footnt_Txt.compareTo(other.getFootnt_Txt());
	}

}
