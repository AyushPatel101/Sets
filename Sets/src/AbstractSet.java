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

/**
 * Students are to complete this class. Students should implement as many
 * methods as they can using the Iterator from the iterator method and the other
 * methods.
 *
 */
public abstract class AbstractSet<E> implements ISet<E> {

	/*
	 * NO INSTANCE VARIABLES ALLOWED.
	 * 
	 * NO DIRECT REFERENCE TO UnsortedSet OR SortedSet ALLOWED. (In other words
	 * the data types UnsortedSet and SortedSet will not appear any where in
	 * this class.)
	 * 
	 * NO DIRECT REFERENCES to ArrayList or other Java Collections.
	 * 
	 * NO METHODS ADDED other than those in ISet and Object.
	 */

	/**
	 * A union operation. Add all items of otherSet that are not already present
	 * in this set to this set.
	 * 
	 * @param otherSet != null
	 * @return true if this set changed as a result of this operation, false
	 *         otherwise.
	 */
	// for Unsorted: O(N^2)
	// for Sorted: O(NlogN)
	public boolean addAll(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("parameter cannot be null");
		Iterator<E> otherIter = otherSet.iterator();
		// using boolean to keep track if at least one element added
		// successfully
		boolean result = false;
		// traversing to otherSet to see if it has any elements not in calling
		// object in order to add it
		while (otherIter.hasNext()) {
			E element = otherIter.next();
			// if calling object does not have this item, it needs to be added
			if (!this.contains(element)) {
				// by putting add in if, we will only make result true and keep
				// it true (in case add fails at an element, we still want to
				// keep result true because an element was added before that)
				if (this.add(element))
					result = true;
			}
		}
		return result;
	}

	/**
	 * Make this set empty. <br>
	 * pre: none <br>
	 * post: size() = 0
	 */
	// O(N^2)
	public void clear() {
		Iterator<E> iter = this.iterator();
		// going through each item and removing it using iterator remove method
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
	}

	/**
	 * Determine if item is in this set. <br>
	 * pre: item != null
	 * 
	 * @param item element whose presence is being tested. Item may not equal
	 *             null.
	 * @return true if this set contains the specified item, false otherwise.
	 */
	// O(N)
	public boolean contains(E item) {
		if (item == null)
			throw new IllegalArgumentException("item cannot be null");
		Iterator<E> iter = this.iterator();
		// going through each element, and if find one that equals item, we can
		// stop and return true
		while (iter.hasNext()) {
			if (iter.next().equals(item))
				return true;
		}
		// if got here, then no element in in calling object is equal to item,
		// so return false
		return false;

	}

	/**
	 * Determine if all of the elements of otherSet are in this set. <br>
	 * pre: otherSet != null
	 * 
	 * @param otherSet != null
	 * @return true if this set contains all of the elements in otherSet, false
	 *         otherwise.
	 */
	// for Unsorted: O(N^2)
	// for Sorted: O(NlogN)
	public boolean containsAll(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("parameter cannot be null");
		Iterator<E> otherIter = otherSet.iterator();
		// traversing through otherSet to see if every element is in calling
		// object
		while (otherIter.hasNext()) {
			// if find an element in otherSet that is not in calling object,
			// return false
			if (!contains(otherIter.next())) {
				return false;
			}
		}
		// if got here, every element in otherSet is in calling object, return
		// true
		return true;
	}

	/**
	 * Determine if this set is equal to other. Two sets are equal if they have
	 * exactly the same elements. The order of the elements does not matter.
	 * <br>
	 * pre: none
	 * 
	 * @param other the object to compare to this set
	 * @return true if other is a Set and has the same elements as this set
	 */
	// O(N^2)
	public boolean equals(Object other) {
		// checking to see if other is a ISet
		if (other instanceof ISet) {
			// can cast after making sure its instanceof ISet
			ISet<?> otherSet = (ISet<?>) other;
			Iterator<E> iter1 = this.iterator();
			// if sizes not equal, return false
			if (this.size() != otherSet.size())
				return false;
			// while calling object still has elements
			while (iter1.hasNext()) {
				E element = iter1.next();
				boolean notFound = true;
				// have to do this for each element in calling object because
				// for each object, have to see if otherSet has that item
				Iterator<?> iter2 = otherSet.iterator();
				while (iter2.hasNext() && notFound) {
					if (iter2.next().equals(element))
						notFound = false;
				}
				if (notFound)
					return false;
			}
			// if no different elements & same size, return true
			return true;

		}
		// if other is not instanceof ISet, return false
		return false;
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
	// for Unsorted: O(N^2)
	// for Sorted: O(N)
	public ISet<E> intersection(ISet<E> otherSet) {
		// if u call difference of this and otherSet, and u call difference of
		// this and that resulting set, you get the intersection between both
		// sets
		return difference(difference(otherSet));
	}

	/**
	 * Remove the specified item from this set if it is present. pre: item !=
	 * null
	 * 
	 * @param item the item to remove from the set. item may not equal null.
	 * @return true if this set changed as a result of this operation, false
	 *         otherwise
	 */
	// O(N)
	public boolean remove(E item) {
		if (item == null)
			throw new IllegalArgumentException("parameter cannot be null");
		Iterator<E> iter = this.iterator();
		// traversing through calling object
		while (iter.hasNext()) {
			// if u find an item that is equal to item
			if (iter.next().equals(item)) {
				// remove it and return true (efficiency)
				iter.remove();
				return true;
			}
		}
		// if get here, then not item removed, so return false
		return false;
	}

	/**
	 * Return the number of elements of this set. pre: none
	 * 
	 * @return the number of items in this set
	 */
	// O(N)
	public int size() {
		int count = 0;
		Iterator<E> iter = this.iterator();
		// traversing thrugh calling object's iterator
		while (iter.hasNext()) {
			// add to count and move to next item
			iter.next();
			count++;
		}
		return count;
	}

	/**
	 * Return a String version of this set. Format is (e1, e2, ... en)
	 * 
	 * @return A String version of this set.
	 */
	// O(N)
	public String toString() {
		StringBuilder result = new StringBuilder();
		String seperator = ", ";
		result.append("(");
		Iterator<E> it = this.iterator();
		// appends each item to result along with seperator
		while (it.hasNext()) {
			result.append(it.next());
			result.append(seperator);
		}
		// get rid of extra separator
		if (this.size() > 0)
			result.setLength(result.length() - seperator.length());

		result.append(")");
		return result.toString();
	}

}