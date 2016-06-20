package Engine;
/**
 * A two way linked list
 * @author Alex Raita && William Xu
 * @param <E> the type of class that will be used to define objects in the list
 * @version November 22, 2015
 */
class DoublyLinkedList<E extends Comparable<E>>
{
	private TwoWayNode<E> head = null;
	private TwoWayNode<E> tail = null;
	private int size = 0;
	
	/**
	 * Makes a copy of the current list, so it is not accidentally modified
	 * @return a copy of the current list
	 */
	public DoublyLinkedList<E> copy()
	{
		DoublyLinkedList<E> ret = new DoublyLinkedList<E>();
		if(head == null)
			return ret;
		
		TwoWayNode<E> current = head;
		ret.add(head.getItem());
		while(current.getNext() != null)
		{
			current = current.getNext();
			ret.add(current.getItem());
		}
		
		return ret;
	}
	
	/**
	 * Add an item to the end of the list
	 * @param item the item to be added
	 */
	public void add(E item)
	{
		size++;
		if (head == null)
		{
			head = new TwoWayNode<E>(item, null);
		}
		else
		{
			TwoWayNode<E> tempNode = head;
			while (tempNode.getNext() != null)
			{
				tempNode = tempNode.getNext();
			}
			tempNode.setNext(new TwoWayNode<E>(item, null));
		}

	}

	/**
	 * Add an item to the list at a given index
	 * @param item the item to be added
	 */
	public void add(E item, int itemIndex)
	{
		size++;
		if (itemIndex == 0)
		{
			TwoWayNode<E> oldHead = head;
			head = new TwoWayNode<E>(item);
			head.setNext(oldHead);
		}
		else
		{
			TwoWayNode<E> tempNode = head;

			for (int index = 0; index < itemIndex - 1; index++)
			{
				tempNode = tempNode.getNext();
			}

			if (tempNode.getNext() != null)
			{
				TwoWayNode<E> oldNext = tempNode.getNext();
				TwoWayNode<E> newItem = new TwoWayNode<E>(item);
				tempNode.setNext(newItem);
				newItem.setPrevious(tempNode);
				newItem.setNext(oldNext);
			}
			else
			{
				TwoWayNode<E> newItem = new TwoWayNode<E>(item);
				tempNode.setNext(newItem);
				newItem.setPrevious(tempNode);
			}

		}
	}
	
	/**
	 * Gets the head of the list
	 * @return the head of the list
	 */
	public TwoWayNode<E> getHead()
	{
		return head;
	}

	/**
	 * Add an item to the list
	 * @param item the item that will replace the current item
	 */
	public void replace(E item, int itemIndex)
	{
		remove(itemIndex);
		add(item, itemIndex);
	}

	/**
	 * Return an item from the list at the given index
	 * @param item the index of the item
	 * @return the item at the index
	 */
	public E get(int item)
	{
		if (item < 0)
		{
			throw new ArrayIndexOutOfBoundsException("You cannot get this item at the index of -1");
		}
		TwoWayNode<E> tempNode = head;

		for (int index = 0; index < item; index++)
		{
			tempNode = tempNode.getNext();
		}
		return (E) tempNode.getItem();
	}

	/**
	 * Find the index of a given item
	 * @param item the item to be found
	 * @return the index of the item, -1 if it isn't found
	 */
	public int indexOf(E item)
	{
		if(size() == 0)
			return -1;
		
		TwoWayNode<E> current = head;
		if(current.getItem().compareTo(item) == 0)
			return 0;
		int index = 1;
		
		while(current.getNext() != null)
		{	
			current = current.getNext();
			if(current.getItem().compareTo(item) == 0)
				return index;
			index++;				
		}
		
		
		return -1;
	}

	/**
	 * Remove the element at the given index from the list
	 * @param item the index of the item
	 */
	public E remove(int item)
	{
		size--;
		if (item == 0)
		{
			E itemRemoved = head.getItem();
			if (head.getNext() != null)
			{
				head = head.getNext();
			}
			else
			{
				head = null;
			}
			return itemRemoved;
		}

		TwoWayNode<E> tempNode = head;

		for (int index = 0; index < item-1; index++)
		{
			tempNode = tempNode.getNext();
		}

		E itemRemoved = tempNode.getNext().getItem();
		if (tempNode.getNext().getNext() == null)
		{
			
			tempNode.setNext(null);
			tail = tempNode;
			return itemRemoved;
		}
	    tempNode.setNext(tempNode.getNext().getNext());
		return itemRemoved;

	}

	/**
	 * Remove the given item from the list
	 * @param item the item to be removed
	 */
	public void remove(E item)
	{

		if (head == null)
		{
		}
		else
		{
			size--;
			if (head.getItem().compareTo(item) == 0)
			{
				if (head.getNext() != null)
				{
					head = head.getNext();
				}
				else
				{
					head = null;
				}

			}

			TwoWayNode<E> tempNode = head;

			while (tempNode.getNext().getNext() != null
					&& tempNode.getNext().getItem().compareTo(item) != 0)
			{
				tempNode = tempNode.getNext();
			}

			if (tempNode.getNext().getNext() == null)
			{
				tempNode.setNext(null);
				tail = tempNode;
			}
			else
			{
				tempNode.setNext(tempNode.getNext().getNext());
			}
		}
	}

	/**
	 * Remove all elements from the list
	 */
	public void clear()
	{
		head = null;
		size=0;
	}

	/**
	 * Get the number of elements in the list
	 * @return the number of elements in the list
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Gets the first elements in the list
	 * @return the first element in the list
	 */
	public E getFirst()
	{
		return head.getItem();
	}

	/**
	 * Get the last element in the list
	 * @return the last element in the list
	 */
	public E getLast()
	{
		return tail.getItem();
	}
}
