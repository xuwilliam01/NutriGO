package Engine;
/**
 * Used to make searching more efficient by storing various foods under their corresponding keywords
 * @author Alex Raita & William Xu
 *
 */
public class Keyword implements Comparable<Keyword>
{
	private String keyword;
	private DoublyLinkedList<Food> listOfFoods = new DoublyLinkedList<Food>();
	
	/**
	 * Constructor
	 * @param keyword the string that defines the keyword object
	 */
	public Keyword (String keyword)
	{
		this.keyword = keyword.toLowerCase();
	}
	
	/**
	 * Adds a food to the list
	 * @param item the food to be added
	 */
	public void addFood(Food item)
	{
		listOfFoods.add(item);
	}

	/**
	 * Compares two keywords by their keyword string
	 * @param other the keyword to be compared to
	 * @return the integer difference between the keywords
	 */
	public int compareTo(Keyword other)
	{
		return keyword.compareTo(other.getKeyword());
	}

	/**
	 * Gets the keyword
	 * @return the keyword
	 */
	public String getKeyword()
	{
		return keyword;
	}

	/**
	 * Gets the list of foods
	 * @return the list of foods
	 */
	public DoublyLinkedList<Food> getListOfFoods()
	{
		return listOfFoods;
	}
	
	/**
	 * Gets the index of a given food
	 * @param item the given food
	 * @return the index of the food, -1 if food is not in the list
	 */
	public int indexOf(Food item)
	{
		return listOfFoods.indexOf(item);
	}
	
}
