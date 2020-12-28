//*  Student information for assignment:

// *
// *  On my honor, Ayush Patel, 
// *  this programming assignment is my own work
// *  and I have not provided this code to any other student.
// *
// *  Number of slip days used: 2
// *
// *  Student 1 (Student whose Canvas account is being used)
// *  UTEID: ap55837
// *  email address: patayush01@utexas.edu
// *  TA name: Tony
// *    
// */

import java.util.Iterator;
import java.util.ArrayList;

/**
 * A simple implementation of an ISet. Elements are not in any particular order.
 * Students are to implement methods that were not implemented in AbstractSet
 * and override methods that can be done more efficiently. An ArrayList must be
 * used as the internal storage container.
 *
 */
public class UnsortedSet<E> extends AbstractSet<E> {
	private ArrayList<E> myCon;

	// Creates an UnsortedSet Object
	// pre:none
	// post: myCon is initialized (empty)
	// O(N)
	public UnsortedSet() {
		clear();
	}

	/**
	 * Add an item to this set. <br>
	 * item != null
	 * 
	 * @param item the item to be added to this set. item may not equal null.
	 * @return true if this set changed as a result of this operation, false
	 *         otherwise.
	 */
	// O(N)
	public boolean add(E item) {
		if (item == null)
			throw new IllegalArgumentException("item cannot be null");
		// if the item is not in the set, then adds it to the end
		if (!contains(item))
			return myCon.add(item);
		// if got here, then item is contained in set, so return false (no
		// duplicated allowed0
		return false;
	}

	/**
	 * Make this set empty. <br>
	 * pre: none <br>
	 * post: size() = 0
	 */
	// O(N)
	public void clear() {
		myCon = new ArrayList<E>();
	}

	/**
	 * Create a new set that is the difference of this set and otherSet. Return
	 * an ISet of elements that are in this Set but not in otherSet. Also called
	 * the relative complement. <br>
	 * Example: If ISet A contains [X, Y, Z] and ISet B contains [W, Z] then
	 * A.difference(B) would return an ISet with elements [X, Y] while
	 * B.difference(A) would return an ISet with elements [W]. <br>
	 * pre: otherSet != null <br>
	 * post: returns a set that is the difference of this set and otherSet.
	 * Neither this set or otherSet are altered as a result of this operation.
	 * <br>
	 * pre: otherSet != null
	 * 
	 * @param otherSet != null
	 * @return a set that is the difference of this set and otherSet
	 */
	// O(N^2)
	public ISet<E> difference(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("otherSet is null");
		// static type is UnsortedSet rather than ISet in order to call
		// arraylist's add method on myCon
		UnsortedSet<E> result = new UnsortedSet<E>();
		Iterator<E> thisIter = this.iterator();
		// traversing through calling object's elements because only adding
		// those elements if otherSet doesn't have them
		while (thisIter.hasNext()) {
			E element = thisIter.next();
			// if otherSet does not have element, then we add it to result's
			// myCon (not using result's add for efficiency)
			if (!otherSet.contains(element))
				result.myCon.add(element);
		}
		// this works even though static type of result is UnsortedSet because
		// this class is a child class of ISet
		return result;
	}

	/**
	 * create a new set that is the intersection of this set and otherSet. <br>
	 * pre: otherSet != null<br>
	 * <br>
	 * post: returns a set that is the intersection of this set and otherSet.
	 * Neither this set or otherSet are altered as a result of this operation.
	 * <br>
	 * pre: otherSet != null
	 * 
	 * @param otherSet != null
	 * @return a set that is the intersection of this set and otherSet
	 */
	// O(N^2)
	public ISet<E> intersection(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("otherSet is null");
		// static type is UnsortedSet rather than ISet in order to call
		// arraylist's add method on myCon
		UnsortedSet<E> result = new UnsortedSet<E>();
		Iterator<E> thisIter = this.iterator();
		// traversing through each element of calling object to see if each of
		// its elements is also in otherSet
		while (thisIter.hasNext()) {
			E element = thisIter.next();
			// if both sets have element, then add it directly to result's myCon
			if (otherSet.contains(element))
				result.myCon.add(element);
		}
		// this works even though static type of result is UnsortedSet because
		// this class is a child class of ISet
		return result;
	}

	/**
	 * Return an Iterator object for the elements of this set. pre: none
	 * 
	 * @return an Iterator object for the elements of this set
	 */
	// O(1)
	public Iterator<E> iterator() {
		// returning ArrayList's iterator
		return myCon.iterator();
	}

	/**
	 * Return the number of elements of this set. pre: none
	 * 
	 * @return the number of items in this set
	 */
	// O(1)
	public int size() {
		return myCon.size();
	}

	/**
	 * Create a new set that is the union of this set and otherSet. <br>
	 * pre: otherSet != null <br>
	 * post: returns a set that is the union of this set and otherSet. Neither
	 * this set or otherSet are altered as a result of this operation. <br>
	 * pre: otherSet != null
	 * 
	 * @param otherSet != null
	 * @return a set that is the union of this set and otherSet
	 */
	// O(N^2)
	public ISet<E> union(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("otherSet is null");
		UnsortedSet<E> result = new UnsortedSet<E>();
		// addAll method is a union operation, it only adds if set doesnt have
		// that object already, so can just add both sets to new set and return
		// it
		result.addAll(this);
		result.addAll(otherSet);
		// this works even though static type of result is UnsortedSet because
		// this class is a child class of ISet
		return result;
	}
}
