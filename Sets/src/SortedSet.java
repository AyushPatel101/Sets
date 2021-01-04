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
 * In this implementation of the ISet interface the elements in the Set are
 * maintained in ascending order.
 * 
 * The data type for E must be a type that implements Comparable.
 * 
 * Students are to implement methods that were not implemented in AbstractSet
 * and override methods that can be done more efficiently. An ArrayList must be
 * used as the internal storage container. For methods involving two set, if
 * that method can be done more efficiently if the other set is also a SortedSet
 * do so.
 */
public class SortedSet<E extends Comparable<? super E>> extends AbstractSet<E> {

	private ArrayList<E> myCon;

	/**
	 * pre:none post: create an empty SortedSet, (myCon initialized to empty)
	 */
	// O(N)
	public SortedSet() {
		clear();
	}

	/**
	 * create a SortedSet out of an unsorted set. <br>
	 * 
	 * @param other != null
	 */
	// O(1) best case (set is sorted)
	// O(NlogN) worst case (set is unsorted)
	public SortedSet(ISet<E> other) {
		// initialize myCon
		this();
		// if its instanceof SortedSet, can just set this myCon to other's myCon
		if (other instanceof SortedSet<?>) {
			SortedSet<E> otherSet = (SortedSet<E>) other;
			myCon = otherSet.myCon;
		}
		// else, its instanceof UnsortedSet
		else {
			Iterator<E> iter = other.iterator();
			// adding all elements to myCon
			while (iter.hasNext()) {
				myCon.add(iter.next());
			}
			// sorting myCon using mergeSort
			mergeSort(myCon);
		}

	}

	// Adapted from topic17 Fast Sorting slides by Mike Scott
	/**
	 * perform a merge sort on the elements of data
	 * 
	 * @param data data != null, all elements of data are the same data type
	 *
	 */
	// O(NlogN)
	private void mergeSort(ArrayList<E> thisCon) {
		sort(thisCon, fillArrayList(thisCon.size()), 0, size() - 1);
	}

	// pre: none
	// post: returns an ArrayList of size that is filled with nulls (changed
	// later by sort)
	// O(N)
	private ArrayList<E> fillArrayList(int size) {
		ArrayList<E> temp = new ArrayList<E>(size);
		// adding nulls to temp, size times
		for (int i = 0; i < size; i++)
			temp.add(null);
		return temp;
	}

	// Adapted from topic17 Fast Sorting slides by Mike Scott
	// pre: none
	// post: recursively sorts thisCon and stores it into thisCon
	// O(NlogN)
	private void sort(ArrayList<E> thisCon, ArrayList<E> temp, int low,
			int high) {
		if (low < high) {
			int center = (low + high) / 2;
			sort(thisCon, temp, low, center);
			sort(thisCon, temp, center + 1, high);
			merge(thisCon, temp, low, center + 1, high);
		}
	}

	// Adapted from topic17 Fast Sorting slides by Mike Scott
	// pre: none
	// post: merges element into thisCon based on leftPos, rightPos and rightEnd
	// O(N)
	private void merge(ArrayList<E> thisCon, ArrayList<E> temp, int leftPos,
			int rightPos, int rightEnd) {
		// leftEnd is one less than rightEnd (essentially comparing elements
		// starting at index 0 and rightPos, so leftPos cannot be greater than
		// rightPos-1 (overlapping))
		int leftEnd = rightPos - 1;
		// tempPos is index of temp that needs to be set to new value
		int tempPos = leftPos;
		int numElements = rightEnd - leftPos + 1;
		// main loop
		while (leftPos <= leftEnd && rightPos <= rightEnd) {
			// if element at leftPos is less than rightPos, then set temp @
			// tempPos to that value
			if ((thisCon.get(leftPos)).compareTo(thisCon.get(rightPos)) <= 0) {
				temp.set(tempPos, thisCon.get(leftPos));
				// increment leftPoss to compare thisCon @ leftPos + 1 to
				// thisCon @ rightPos
				leftPos++;
			}
			// else, element at rightPos is less than element at leftPos
			else {
				// set temp @ tempPos to element @ rightPos
				temp.set(tempPos, thisCon.get(rightPos));
				// increment rightPos to compare element at leftPos to element
				// at rightPos + 1
				rightPos++;
			}
			// increment tempPos each time to set new value to lesser of thisCon
			// @ leftPos and rightPos
			tempPos++;
		}

		// copy rest of left half
		while (leftPos <= leftEnd) {
			temp.set(tempPos, thisCon.get(leftPos));
			tempPos++;
			leftPos++;
		}
		// copy rest of right half
		while (rightPos <= rightEnd) {
			temp.set(tempPos, thisCon.get(rightPos));
			tempPos++;
			rightPos++;
		}
		// Copy temp back into data
		for (int i = 0; i < numElements; i++, rightEnd--) {
			thisCon.set(rightEnd, temp.get(rightEnd));
		}
		// by end, thisCon stores sorted order of elements based on lettPos,
		// rightPos and rightEnd
	}

	/**
	 * Return the smallest element in this SortedSet. <br>
	 * pre: size() != 0
	 * 
	 * @return the smallest element in this SortedSet.
	 */
	// O(1)
	public E min() {
		if (size() == 0)
			throw new IllegalArgumentException("set is empty");
		// since sorted, can get element at beginning of set (index 0)
		return myCon.get(0);
	}

	/**
	 * Return the largest element in this SortedSet. <br>
	 * pre: size() != 0
	 * 
	 * @return the largest element in this SortedSet.
	 */
	// O(1)
	public E max() {
		if (size() == 0)
			throw new IllegalArgumentException("set is empty");
		// since sorted, can get element at end of set(index: size() -1)
		return myCon.get(size() - 1);

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
		// setting oldSize to size before potential add to see if something is
		// added
		int oldSize = size();
		// edge case for bsearchAdd, doesn't account for myCon being empty
		if (size() == 0) {
			// adds to end of myCon
			return myCon.add(item);
		}
		// running bsearchAdd, which returns index to add item
		int index = bsearchAdd(item);
		// if bsearchAdd returns -1, then the set already has this item, so
		// don't add it (return false)
		if (index == -1)
			return false;
		// else, item needs to be added at index
		myCon.add(index, item);
		// see if size changed by comparing new size to old size (if changed,
		// return true)
		return size() != oldSize;
	}

	/**
	 * A union operation. Add all items of otherSet that are not already present
	 * in this set to this set.
	 * 
	 * @param otherSet != null
	 * @return true if this set changed as a result of this operation, false
	 *         otherwise.
	 */
	// O(N) best case (set is sorted)
	// O(N^2) worst case (set is unsorted)
	public boolean addAll(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("otherSet cannot be null");
		// if otherSet is SortedSet, can write more efficient addAll method
		if (otherSet instanceof SortedSet) {
			// storing current size into oldSize to see if size changed after
			// following operations
			int oldSize = size();
			// my calling difference between otherSet and this, we can find the
			// elements that need to be added to this
			ISet<E> result = otherSet.difference(this);
			// casting result to SortedSet in order to access result's myCon to
			// use addALl method from ArrayList class
			SortedSet<E> resultSorted = ((SortedSet<E>) result);
			// combines arrays of resultStored and myCon
			myCon.addAll(resultSorted.myCon);
			// now we have all the elements in myCon, but we need to sort it by
			// comparing elements starting at 0 and oldSize (index 0 of
			// resultStored.myCon before merge)
			merge(myCon, fillArrayList(size()), 0, oldSize, size() - 1);
			// see if sizes changed, if they are not equal than set changed, so
			// return true, false otherwise
			return oldSize != size();

		}
		// else, otherSet is UnsortedSet, so utilize AbstractSet's addAll method
		else
			return super.addAll(otherSet);
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
	 * Determine if item is in this set. <br>
	 * pre: item != null
	 * 
	 * @param item element whose presence is being tested. Item may not equal
	 *             null.
	 * @return true if this set contains the specified item, false otherwise.
	 */
	// O(logN)
	public boolean contains(E item) {
		// runs traditional binary search, if bsearch does not return -1, then
		// item is in set somewhere, so return true, false otherwise
		return bsearch(item) != -1;
	}

	// Adapted from Topic 14 slides by Mike Scott
	// binary search algorithm (LogN). finds the target value and returns index
	// of it, else returns -1 if target value not in container
	private int bsearch(E target) {
		return bsearch(target, 0, size() - 1);
	}

	// recursively completes binary search algorithm
	// O(logN) (recursive)
	private int bsearch(E target, int low, int high) {
		// traditional binary search algorithm, if low <= high then search is
		// still continuing
		if (low <= high) {
			// mid calculated by distance average of high-low index and then
			// adding that to low index
			int mid = low + ((high - low) / 2);
			int compare = myCon.get(mid).compareTo(target);
			// found element, return that index
			if (compare == 0)
				return mid;
			// element we checking is greater than target, so cut of everything
			// to the right of mid
			else if (compare > 0)
				return bsearch(target, low, mid - 1);
			// element we checking is less than target, so cut of everything to
			// the left of mid
			else
				return bsearch(target, mid + 1, high);

		}
		// if got here, than low became > than high, which means the target
		// value is not in list, so return -1
		return -1;
	}

	// Adapted from Topic 14 slides by Mike Scott
	// adjusted binary search algorithm (LogN). If it finds target value,
	// returns -1, else returns index where target should be added to keep set
	// sorted (mid)
	public int bsearchAdd(E target) {
		return bsearchAdd(target, 0, size() - 1);
	}

	// recursive adapted binary search algorithm
	// O(logN) (rescurive)
	private int bsearchAdd(E target, int low, int high) {
		// compared to traditional binary search, mid calculated before checking
		// if search is valid because thats the index that needs to be returned
		// if search is not valid (low > high)
		int mid = low + ((high - low) / 2);
		if (low <= high) {
			int compare = myCon.get(mid).compareTo(target);
			// if found target, return -1 (indicating item should not be added
			// to set)
			if (compare == 0) {
				return -1;
			}
			// same as traditional, cut off everything right of mid
			else if (compare > 0) {
				return bsearchAdd(target, low, mid - 1);
			}
			// same as traditional, cut off everything left of mid
			else {
				return bsearchAdd(target, mid + 1, high);
			}

		}
		// if got here, then target is not found, so returning index it should
		// be added to
		return mid;
	}

	/**
	 * Determine if all of the elements of otherSet are in this set. <br>
	 * pre: otherSet != null
	 * 
	 * @param otherSet != null
	 * @return true if this set contains all of the elements in otherSet, false
	 *         otherwise.
	 */
	// O(N) best case (otherSet is sorted)
	// O(N^2) worst case (otherSet is unsorted)
	public boolean containsAll(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("parameter cannot be null");
		// if otherSet is instanceof SortedSet, can make containsAll more
		// efficient compared to AbstractSet's containsAll
		if (otherSet instanceof SortedSet) {
			Iterator<E> otherIter = otherSet.iterator();
			Iterator<E> thisIter = this.iterator();
			// setting eleemntTwo to null out of while loop because it is not
			// changed each iteration of loop
			E elementTwo = null;
			// moveOn determines if need to move to next element of otherSet
			boolean moveOn = true;
			// edge case: if otherSet has no elements, return true
			if (!otherIter.hasNext())
				return true;
			// traversing through this set
			while (thisIter.hasNext()) {
				if (moveOn) {
					// set elementTwo to otherSet's next element and check it
					elementTwo = otherIter.next();
					moveOn = false;
				}
				E element = thisIter.next();
				int compare = elementTwo.compareTo(element);
				// if compare==0, then elementTwo is in this set, so move on to
				// next element in otherSet (moveOn= true)
				if (compare == 0) {
					moveOn = true;
					// if there is no elements left to check in otherIter, then
					// all are contained in this set, so return true
					if (!otherIter.hasNext())
						return true;
				}
				// if compare<0, then elementTwo is not in this set, so return
				// false
				if (compare < 0)
					return false;
				// implicit else, compare>0, then continue iterating through
				// this set to see if elementTwo is contained in it

			}
			// if got here, then finished traversing through this set, but
			// elements left in otherSet that were not found in this set, so
			// return false
			return false;
		}
		// else, otherSet is instanceof UnsortedSet, so need to call
		// AbstractSet's containsAll
		return super.containsAll(otherSet);
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
	// O(N) best case (otherSet is SortedSet)
	// O(N^2) worst case (otherSet is an UnsortedSet)
	public ISet<E> difference(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("otherSet is null");
		SortedSet<E> otherSortedSet;
		// if otherSet is not instanceof SortedSet, then sort it
		if (!(otherSet instanceof SortedSet<?>))
			otherSortedSet = new SortedSet<E>(otherSet);
		// else, its already sorted so just cast it
		else
			otherSortedSet = (SortedSet<E>) otherSet;
		// call mergeDiff to return ISet<E> that has elements of this that are
		// not in otherSet
		return mergeDiff(otherSortedSet, 0, size() - 1, 0,
				otherSortedSet.size() - 1);

	}

	// Adapted from topic17 Fast Sorting slides by Mike Scott
	// neither temp or calling object are altered as a result of this method
	// pre:none
	// post: very similar to merge from topic17 slides, with slight change to
	// return difference between calling object and temp
	// O(N)
	private ISet<E> mergeDiff(SortedSet<E> temp, int leftPos, int leftEnd,
			int rightPos, int rightEnd) {
		// can declare result as sorted because this method only called if
		// otherSet (from difference) is SortedSet (which is why temp is
		// SortedSet)
		SortedSet<E> result = new SortedSet<E>();
		// main loop
		while (leftPos <= leftEnd && rightPos <= rightEnd) {
			int compare = this.myCon.get(leftPos)
					.compareTo(temp.myCon.get(rightPos));
			// if compare < 0, then thisCon @ leftPos is < thisCon @ rightPos,
			// so add it to result
			if (compare < 0) {
				result.myCon.add(this.myCon.get(leftPos));
				leftPos++;
			}
			// else, compare is >=0
			else {
				// if compare==0, then need to increment leftPos as well (notice
				// not adding it to result because this is difference function)
				if (compare == 0)
					leftPos++;
				// if compare>=0, then element at rightPos >= element at
				// leftPos, either way need to move to next rightPos
				rightPos++;
			}
		}
		// copy rest of left half
		while (leftPos <= leftEnd) {
			result.myCon.add(this.myCon.get(leftPos));
			leftPos++;
		}
		// compared to traditional merge, not adding rest of right half because
		// this is difference function
		return result;
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
	// O(1) best case(when other is not instanceof ISet)
	// O(N) average case (when other is SortedSet)
	// O(N^2) worst case (when other is UnsortedSet)
	public boolean equals(Object other) {
		if (other instanceof ISet) {
			// if other is instanceof SortedSet, can do more efficient equals
			// check (linear search)
			if (other instanceof SortedSet) {
				// if sizes not equal, return false
				if (this.size() != ((ISet<?>) other).size())
					return false;
				Iterator<E> iter1 = this.iterator();
				Iterator<?> iter2 = ((ISet<?>) other).iterator();
				// traversing through iter1 (which has same number of elements
				// as iter2)
				while (iter1.hasNext()) {
					// if find an element that are not equal, returns false
					if (!iter1.next().equals(iter2.next()))
						return false;
				}
				// if got here, then all elements are same, and same amount of
				// elements in both sets, so return true
				return true;
			}
			// else, other is instanceOf UnsortedSet, so have to use
			// AbstractSet's equals method
			else
				return super.equals(other);
		}
		// if got here, than other is not instanceof ISet, so return false
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
	// O(N)
	public ISet<E> intersection(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("otherSet is null");
		SortedSet<E> otherSortedSet;
		// if otherSet is not instanceof SortedSet, make it into SortedSet by
		// utilizing SortedSet constructor
		if (!(otherSet instanceof SortedSet<?>))
			otherSortedSet = new SortedSet<E>(otherSet);
		// else, otherSet is instanceof SortedSet, so just cast it
		else
			otherSortedSet = (SortedSet<E>) otherSet;
		// returns set created by mergeInter, which creates a new ISet<E> that
		// is an intersection of this and otherSet
		return mergeInter(otherSortedSet, 0, size() - 1, 0,
				otherSortedSet.size() - 1);
	}

	// Adapted from topic17 Fast Sorting slides by Mike Scott
	// neither temp or calling object are altered as a result of this method
	// pre:none
	// post: very similar to merge from topic17 slides, with slight change to
	// return intersection between calling object and temp
	// O(N)
	private ISet<E> mergeInter(SortedSet<E> temp, int leftPos, int leftEnd,
			int rightPos, int rightEnd) {
		// can declare result as sorted because this method only called if
		// otherSet (from difference) is SortedSet (which is why temp is
		// SortedSet)
		SortedSet<E> result = new SortedSet<E>();
		// main loop
		while (leftPos <= leftEnd && rightPos <= rightEnd) {
			int compare = this.myCon.get(leftPos)
					.compareTo(temp.myCon.get(rightPos));
			// if compare == 0, then elements are equal, so add it to result
			if (compare == 0) {
				result.myCon.add(this.myCon.get(leftPos));
				leftPos++;
			}
			// else if compare > 0, then element at leftPos is > element at
			// rightPos, so only increment rightPos
			else if (compare > 0) {
				rightPos++;
			}
			// else compare < 0, then element at leftPos is < element at
			// rightPos, so only increment leftPos
			else
				leftPos++;
		}
		// only getting elements that are same in both, so no need to copy and
		// remaining elements (compared to traditional merge) so return result
		return result;
	}

	/**
	 * Return an Iterator object for the elements of this set. pre: none
	 * 
	 * @return an Iterator object for the elements of this set
	 */
	// O(1)
	public Iterator<E> iterator() {
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
	// O(N)
	public ISet<E> union(ISet<E> otherSet) {
		if (otherSet == null)
			throw new IllegalArgumentException("otherSet is null");
		SortedSet<E> result = new SortedSet<E>();
		// addALl is essentially union (adds elements that are not already in
		// calling object), so can addAll(this) and addALl(otherSet) to result
		// to get union of sets
		result.addAll(this);
		result.addAll(otherSet);
		return result;
	}
}
