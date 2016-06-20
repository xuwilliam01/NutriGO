package Engine;
/**
 * A node for the doubly linked list
 * @author Alex Raita & William Xu
 * @version 22 November, 2015
 * @param <E> the type of object to use
 */
class TwoWayNode<E>
{
	private E item;
	private TwoWayNode<E> next;
	private TwoWayNode <E>previous;

	/**
	 * Constructor 
	 * @param item the item in the node
	 */
	public TwoWayNode(E item)
	{
		this.item = item;
	}

	/**
	 * Constructor
	 * @param item the item in the node
	 * @param next the next node
	 */
	public TwoWayNode(E item, TwoWayNode <E>next)
	{
		this.item = item;
		this.next = next;
	}

	/**
	 * Constructor
	 * @param item the item in the node
	 * @param next the next node
	 * @param last the previous node
	 */
	public TwoWayNode(E item, TwoWayNode <E>next, TwoWayNode<E> last)
	{
		this.item = item;
		this.next = next;
		this.previous = last;
	}

	/**
	 * Gets the next item
	 * @return the next item
	 */
	public TwoWayNode<E> getNext()
	{
		return next;
	}

	/**
	 * Gets the previous node
	 * @return the previous node
	 */
	public TwoWayNode<E> getPrevious()
	{
		return previous;
	}

	/**
	 * Set the next node
	 * @param next the next node
	 */
	public void setNext(TwoWayNode<E> next)
	{
		this.next = next;
	}

	/**
	 * Sets the previous node
	 * @param previous the previous node
	 */
	public void setPrevious(TwoWayNode<E> previous)
	{
		this.previous = previous;
	}

	/**
	 * Gets the item in the node
	 * @return the item in the node
	 */
	public E getItem()
	{
		return this.item;
	}

}