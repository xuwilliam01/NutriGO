package Engine;
/**
 * Stores items in a list using FIFO
 * @author Alex Raita & William Xu
 * @version 22 November, 2015
 * @param <E> The type of class that each object will be
 */
public class Queue <E extends Comparable<E>>
{
	private DoublyLinkedList <E>list;
	
	/**
	 * Constructor
	 */
	public Queue()
	{
		list = new DoublyLinkedList<E>();
	}
	
	/**
	 * Adds an item to the queue
	 * @param item the item to be added
	 */
	public void enqueue(E item)
	{
		list.add(item);
	}
	
	/**
	 * Removes an item from the queue
	 * @return the item that was removed
	 */
	public E dequeue()
	{
		E itemRemoved = (E) list.getFirst();
		list.remove(0);
		return itemRemoved;
	}
	
	/**
	 * Gets the item at a certain index
	 * @param index the index
	 * @return the item at the index
	 */
	public E get(int index)
	{
		return (E) list.get(index);
	}
	
	/**
	 * Gets the size of the queue
	 * @return the size of the queue
	 */
	public int size()
	{
		return list.size();
	}
}
