package dataStructures;

import java.util.LinkedList;
import java.util.List;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {
	private static int totalLinks;
	private static int marks;
	private int potential;
	private HeapNode min;
	private int size;

	private static void increaseMarks(int i) {
		marks += i;
	}

	/**
	 * public boolean empty()
	 *
	 * precondition: none
	 * 
	 * The method returns true if and only if the heap is empty.
	 * 
	 */
	public boolean empty() {
		return false; // should be replaced by student code
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and inserts
	 * it into the heap.
	 */
	public HeapNode insert(int key) {
		return new HeapNode(key); // should be replaced by student code
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 *
	 */
	public void deleteMin() {
		return; // should be replaced by student code

	}

	/**
	 * public HeapNode findMin()
	 *
	 * Return the node of the heap whose key is minimal.
	 *
	 */
	public HeapNode findMin() {
		return min;
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		return; // should be replaced by student code
	}

	/**
	 * public int size()
	 *
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return size; // should be replaced by student code
	}

	/**
	 * public int[] countersRep()
	 *
	 * Return a counters array, where the value of the i-th entry is the number of
	 * trees of order i in the heap.
	 * 
	 */
	public int[] countersRep() {
		int[] arr = new int[42];
		return arr; // to be replaced by student code
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 * TODO should probably implement this with decreaseKey+deleteMin
	 */
	public void delete(HeapNode x) {
		return; // should be replaced by student code
	}

	/**
	 * public void decreaseKey(HeapNode x, int delta)
	 *
	 * The function decreases the key of the node x by delta. The structure of the
	 * heap should be updated to reflect this chage (for example, the cascading cuts
	 * procedure should be applied if needed).
	 */
	public void decreaseKey(HeapNode x, int delta) {
		return; // should be replaced by student code
	}

	/**
	 * public int potential()
	 *
	 * This function returns the current potential of the heap, which is: Potential
	 * = #trees + 2*#marked The potential equals to the number of trees in the heap
	 * plus twice the number of marked nodes in the heap.
	 */
	public int potential() {
		return potential;
	}

	/**
	 * public static int totalLinks()
	 *
	 * This static function returns the total number of link operations made during
	 * the run-time of the program. A link operation is the operation which gets as
	 * input two trees of the same rank, and generates a tree of rank bigger by one,
	 * by hanging the tree which has larger value in its root on the tree which has
	 * smaller value in its root.
	 */
	public static int totalLinks() {
		return totalLinks;
	}

	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made during
	 * the run-time of the program. A cut operation is the operation which
	 * diconnects a subtree from its parent (during decreaseKey/delete methods).
	 */
	public static int totalCuts() {
		return 0; // should be replaced by student code
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than FibonacciHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	public class HeapNode {
		private String info;
		private HeapNode parent;
		private int rank; // Needs to be updated only if node is the root of a tree
		private List<HeapNode> children;
		
		public int key;
		private boolean mark;

		public HeapNode(int key) {
			this.key = key;
		}

		public int getKey() {
			return this.key;
		}
		
		/**
		 * check if node is marked, for cascading cuts
		 * @return
		 */
		public boolean isMarked() {
			return mark;
		}
		
		/**
		 * Mark the current node, if it's not a root node
		 * Assumes node is unmarked 
		 */
		public void mark() {
			if (!this.isMarked() && !this.isRoot()){
				this.mark = true;
				FibonacciHeap.increaseMarks(1);
			}
		}
		
		/**
		 * Cuts the child from the parent, so that it can be added to the root list
		 * assumes this == child.parent
		 * @param child
		 * @return child
		 */
		public HeapNode cut(HeapNode child) {
			this.children.remove(child);
			child.removeParent();
			return child;
		}
		
		/**
		 * remove's this node's parent
		 */
		private void removeParent() {
			this.setParent(null);
		}
		
		/**
		 * sets this node's new parent
		 * @param newParent
		 */
		private void setParent(HeapNode newParent) {
			this.parent = newParent;
			
			
		}

		private boolean isRoot() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
