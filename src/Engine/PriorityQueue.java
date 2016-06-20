package Engine;
/**
 * Is an ordered queue
 * @author Alex Raita & William Xu
 * @param <E> the class that the objects will be
 */
public class PriorityQueue<E extends Comparable<E>> extends Queue<E>
{
	private DoublyLinkedList<Double> listOfPriorities;
	private DoublyLinkedList <E>listOfItems;

	/**
	 * Constructor
	 */
	public PriorityQueue()
	{
		super();
		listOfPriorities = new DoublyLinkedList<Double>();
		listOfItems = new DoublyLinkedList<E>();
	}

	/**
	 * Adds an item
	 * @param item the item to be added
	 * @param priority the priority of the item
	 */
	public void enqueue(E item, double priority)
	{
		if (listOfItems.size() ==0)
		{
			listOfPriorities.add(priority);
			listOfItems.add(item);
		}
		else
		{
			for (int index = listOfItems.size() - 1; index >= 0; index--)
			{
				if (listOfPriorities.get(index) >= priority)
				{
					listOfPriorities.add(priority, index + 1);
					listOfItems.add(item, index + 1);
					break;
				}
			}
			if (priority > listOfPriorities.get(0))
			{
				listOfPriorities.add(priority, 0);
				listOfItems.add(item, 0);
			}
		}
	}
	
	/**
	 * Removes an item
	 * @return the item that was removed
	 */
	public E dequeue()
	{
		E itemRemoved = (E) listOfItems.getFirst();
		listOfItems.remove(0);
		listOfPriorities.remove(0);
		return itemRemoved;
	}
	
	/**
	 * Gets an item at a given index
	 * @param index the index
	 * @return the item at the index
	 */
	public E get(int index)
	{
		return (E) listOfItems.get(index);
	}
	
	/**
	 * Gets the priority at a given inde
	 * @param index the index
	 * @return the priority at the index
	 */
	public double getPriority(int index)
	{
		return listOfPriorities.get(index);
	}
	
	/**
	 * Gets the size of the queue
	 * @return the size of the queue
	 */
	public int size()
	{
		return listOfItems.size();
	}
	
	/**
	 * Gets the list of items
	 * @return the list of items
	 */
	public DoublyLinkedList<E> getList()
	{
		return listOfItems;
	}
}
