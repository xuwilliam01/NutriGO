package Engine;
import java.awt.Image;

/**
 * Stores information pertaining to a food group
 * @author Alex Raita & William Xu
 * @version 22 November, 2015 
 */
public class FoodGroup implements Comparable<FoodGroup>
{
	private String FdGrp_Cd;
	private String FdGrp_Desc;
	private Image picture;
	
	/**
	 * Constructor that sets the code
	 * @param fdGrp_Cd the food group code
	 */
	public FoodGroup(String fdGrp_Cd)
	{
		FdGrp_Cd = fdGrp_Cd;
	}
	
	/**
	 * Gets the picture
	 * @return the picture
	 */
	public Image getPicture()
	{
		return picture;
	}
	
	/**
	 * Sets the picture
	 * @param picture the picture to be set
	 */
	public void setPicture(Image picture)
	{
		this.picture = picture;
	}
	
	/**
	 * Gets the food group code
	 * @return the food group code
	 */
	public String getFdGrp_Cd()
	{
		return FdGrp_Cd;
	}
	
	/**
	 * Gets the food group description
	 * @return the food group description
	 */
	public String getFdGrp_Desc()
	{
		return FdGrp_Desc;
	}
	
	/**
	 * Sets the good group description
	 * @param fdGrp_Desc the food group description to be set
	 */
	public void setFdGrp_Desc(String fdGrp_Desc)
	{
		FdGrp_Desc = fdGrp_Desc;
	}

	/**
	 * Compares to food group objects
	 * @param item the food group to be compared to
	 * @return the integer difference between the food groups
	 */
	public int compareTo(FoodGroup item)
	{
		return FdGrp_Cd.compareTo(item.getFdGrp_Cd());
	}

	
	
}
