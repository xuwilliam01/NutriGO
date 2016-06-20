package Engine;
/**
 * 
 * @author William Xu && Alex Raita
 * @version 22 November, 2015
 * @param Weight Can only compare Weights with other Weights
 *
 */
public class Weight implements Comparable<Weight>
{
	private String Seq;
	private double Amount;
	private String Msre_Desc;
	private double Gm_Wgt;
	private int Num_Data_Pts;
	private double Std_Dev;
	
	/**
	 * Constructor
	 * @param seq the sequence number of the weight
	 * @param amount the specified amount of the food
	 * @param msre_Desc the type of pieces (ex. slices)
	 * @param gm_Wgt the weight per specified amount
	 * @param num_Data_Pts the number of data points
	 * @param std_Dev the standard deviation of the data
	 */
	public Weight(String seq, double amount, String msre_Desc,
			double gm_Wgt, int num_Data_Pts, double std_Dev)
	{
		Seq = seq;
		Amount = amount;
		Msre_Desc = msre_Desc;
		Gm_Wgt = gm_Wgt;
		Num_Data_Pts = num_Data_Pts;
		Std_Dev = std_Dev;
	}

	/**
	 * Get the sequence number
	 * @return the sequence number
	 */
	public String getSeq()
	{
		return Seq;
	}
	
	/**
	 * Set the sequence number
	 * @param seq the sequence number
	 */
	public void setSeq(String seq)
	{
		Seq = seq;
	}
	
	/**
	 * Get the amount of food
	 * @return the amount of food
	 */
	public double getAmount()
	{
		return Amount;
	}
	
	/**
	 * Set the amount of food
	 * @param amount the amount of food
	 */
	public void setAmount(double amount)
	{
		Amount = amount;
	}
	
	/**
	 * Get the pieces type
	 * @return the pieces type
	 */
	public String getMsre_Desc()
	{
		return Msre_Desc;
	}
	
	/**
	 * Set the pieces type
	 * @param msre_Desc the pieces type
	 */
	public void setMsre_Desc(String msre_Desc)
	{
		Msre_Desc = msre_Desc;
	}
	
	/**
	 * Get the weight
	 * @return the weight
	 */
	public double getGm_Wgt()
	{
		return Gm_Wgt;
	}
	
	/**
	 * Set the weight
	 * @return the weight
	 */
	public void setGm_Wgt(double gm_Wgt)
	{
		Gm_Wgt = gm_Wgt;
	}
	
	/**
	 * Get the number of data points
	 * @return the number of data points
	 */
	public int getNum_Data_Pts()
	{
		return Num_Data_Pts;
	}
	
	/**
	 * Set the number of data points
	 * @return the number of data points
	 */
	public void setNum_Data_Pts(int num_Data_Pts)
	{
		Num_Data_Pts = num_Data_Pts;
	}
	
	/**
	 * Get the standard deviation of the data
	 * @return the standard deviation
	 */
	public double getStd_Dev()
	{
		return Std_Dev;
	}
	
	/**
	 * Set the standard deviation of the data
	 * @return the standard deviation
	 */
	public void setStd_Dev(double std_Dev)
	{
		Std_Dev = std_Dev;
	}

	@Override
	/**
	 * Compare this weight to another weight
	 */
	public int compareTo(Weight item)
	{
		return 0;
	}
	
}
