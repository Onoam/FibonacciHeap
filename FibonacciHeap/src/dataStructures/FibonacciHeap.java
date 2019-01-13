package dataStructures;

import java.util.Iterator;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {
	private HeapList trees;
	private static int totalLinks;
	private static int totalCuts;
	private static int marks;
	private HeapNode min;
	private int size;
	
	public static void main(String[] args){ //TODO delete this method
		System.out.println("project");
		//deleteMinTests();
		generalTest();
		//decreaseKeyTest();
	}
	
	public static void decreaseKeyTest() { //TODO delete this method
		FibonacciHeap fib = new FibonacciHeap();
		fib.insert(14);
		fib.insert(30);
		fib.insert(5);
		fib.insert(9);
		fib.insert(3);
		fib.insert(21);
		fib.insert(50);
		fib.insert(19);
		fib.insert(1);
		fib.insert(15);
		fib.insert(11);
		fib.insert(20);
		fib.insert(34);
		System.out.println("fib");
		fib.printRootKeys();
		fib.deleteMin();
		fib.printRootKeys();
		System.out.println(fib.findMin().children.first.getKey()+ "  "+ fib.findMin().getKey());
		HeapNode todec = fib.findMin().children.first;
		fib.decreaseKey(todec, 19); // decrease from 21 to 2
		fib.printRootKeys(); // expected 3 in roots
		HeapNode x11 = fib.findMin().getNext();
		System.out.println("should be 11 " + x11.getKey());
		HeapNode child = x11.children.first;
		System.out.println(child.getKey());
		fib.decreaseKey(child, 5);
		fib.printRootKeys();
		
		//added by dvir
		fib.decreaseKey(x11, 10); // check min update, should be 1
		fib.printRootKeys();
		HeapNode x14 = x11.getNext().children.first.getNext().children.first.getNext();
		fib.decreaseKey(x14, 9); // check child=parent, decrease 14 to 5
		fib.printRootKeys();
		fib.decreaseKey(x14, 1); // decrease 5 to 4
		fib.printRootKeys();
	}
	
	public static void generalTest() { //TODO delete this method
		FibonacciHeap fib = new FibonacciHeap();
		System.out.println("fib");
		System.out.println(fib.findMin()); // findMin returns null if heap is empty
		fib.insert(14);
		fib.insert(30);
		fib.insert(5);
		fib.insert(9);
		fib.insert(3);
		fib.insert(22);
		fib.insert(50);
		fib.insert(19);
		fib.insert(21);
		fib.insert(13);
		fib.insert(7);
		fib.printRootKeys();
		
		// countersRep with deleteMin test
		System.out.println("---### countersRepAndDelMinTest ###---");
		countersRepAndDelMinTest(fib);
		
		// meld test
		System.out.println("---### Meld Test ###---");
		FibonacciHeap fib2 = new FibonacciHeap();
		fib2.insert(1);
		fib2.insert(15);
		fib2.insert(11);
		fib2.insert(20);
		fib2.insert(34);
		fib2.deleteMin();
		System.out.println("fib2");
		fib2.printRootKeys();
		printCountersRep(fib2);
		System.out.println("");
		fib.meld(fib2);
		System.out.println("fib after meld");
		fib.printRootKeys();
		printCountersRep(fib);
		
		// decreaseKeyTest
		System.out.println("");
		System.out.println("---### decreaseKeyTest ###---");
		decreaseKeyTest();
	}
	
	public static void deleteMinTests(){ // TODO delete this
		FibonacciHeap fib = new FibonacciHeap();
		fib.insert(14);
		fib.insert(30);
		fib.insert(5);
		fib.insert(9);
		fib.insert(3);
		fib.insert(21);
		fib.insert(50);
		fib.insert(19);
		fib.printRootKeys();
		fib.deleteMin();
		fib.printRootKeys();
		fib.deleteMin();
		fib.printRootKeys();
		System.out.println("");
		
	}
	
	private static void countersRepAndDelMinTest(FibonacciHeap fib) { // TODO delete this
		printCountersRep(fib);
		fib.deleteMin();
		fib.printRootKeys();
		printCountersRep(fib);
		fib.deleteMin();
		fib.printRootKeys();
		printCountersRep(fib);
		System.out.println("");
	}
	
	private static void printCountersRep(FibonacciHeap fib) { // TODO delete this
		int[] countersRepArr = fib.countersRep();
		for (int i = 0; i < countersRepArr.length; i++) {
			System.out.print("Rank "+ i +": " + countersRepArr[i] + " tree(s)");
			if (i!=countersRepArr.length-1)
				System.out.print(", ");
		}
		System.out.println("");
	}
	
	public void printRootKeys(){ // TODO delete this
		/*for(HeapNode node: this.trees){
			System.out.print(node.getKey()+"("+node.getRank()+")"+"\t");
		}*/
		System.out.print("size:"+this.size+" minkey:");
		if (this.findMin()==null)
			System.out.println("null");
		else
			System.out.println(this.findMin().getKey());
		//System.out.println("");
		printHeap(this.trees, "");
		System.out.println("");
	}
	
	private static void printHeap(HeapList lst, String prefix) { // TODO delete this
		for (HeapNode node : lst) {
			System.out.print(" "+prefix+node.getKey()+"("+node.getRank()+")");
			if (node.children.size()>0)
			{
				System.out.println("");
				printHeap(node.children, prefix+"--");
			}
			else
				System.out.println("");
		}
	}
	
	public FibonacciHeap(){
		this.trees = new HeapList();
		this.min = new HeapNode(Integer.MAX_VALUE);
		marks = 0;
		this.size = 0;
	}
	
	/**
	 * increase marks of the Heap
	 * used for potential function
	 * @param i
	 */
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
		return this.trees.size() == 0;
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and inserts
	 * it into the heap.
	 */
	public HeapNode insert(int key) {
		HeapNode newNode = new HeapNode(key);
		newNode.setRank(0);
		this.trees.add(newNode);
		this.size++;
		this.min=chooseTheSmallerNode(this.min,newNode);
		
		
		return newNode;
	}
	
	private int numberOfTrees() {
		return this.trees.size();
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 *
	 */
	public void deleteMin()
    {
		//##part I
    	if (this.empty()){
    		return;
    	}
    	if(this.size()==1){
    		emptyTheHeap();
    		return;
    	}
    	removeMinNodeAndUpgradeItsChildren();
    	
    	//this.printRootKeys();
    	
    	//##part II
    	double boundingRatio=1.4404;
    	double maxTreeRank = (Math.log(this.size())/Math.log(2))*boundingRatio;
    	//according to slide 84 the maximal tree rank is bounded by maxTreeRank
    	int roundedMaxTreeRank = (int) Math.floor(maxTreeRank)+1;
    	HeapNode[] Bins = new HeapNode[roundedMaxTreeRank];
    	successiveLinking(Bins);
    	
    	//##part III
    	UpdateNewRootsAndMinNode(Bins);
    	
    	
    }
	
	public void emptyTheHeap(){
		
		this.trees = new HeapList();
		marks = 0;
		this.size=0;
		min = null;
	}
    /**slide 37
     * removing the minimal node from the list trees and upgrading it's list of children
     * to be new roots
     * Notice!: the field min becomes null after calling thing function
     * **/
    public void removeMinNodeAndUpgradeItsChildren(){
    	
    	
    	HeapNode minNode = this.min;
    	
    	int numberOfChildren = minNode.children.size();
    	this.trees.remove(minNode);
    	for(HeapNode child :minNode.children){
    		child.parent=null;
    		this.trees.add(child);
    	}
    	this.min=null;
    	this.size--;
    	
    }
    public void successiveLinking(HeapNode[]Bins){
    	for(HeapNode root:this.trees){
    		continuousAddition(Bins,root,root.getRank());
    		
    	}
    }
    public void continuousAddition(HeapNode[]Bins,HeapNode root,int index){
    	if(Bins[index]==null){
    		Bins[index]=root;
    		return;
    	}
    	continuousAddition(Bins,linkTrees(root,Bins[index]),index+1);
    	Bins[index]=null;
    	
    	
    }
    /**slide 12
     * @pre trees contains root1 and root2
     * returns the new root of the new tree**/
    public HeapNode linkTrees(HeapNode root1,HeapNode root2){
    	if(root1.getRank()!=root2.getRank()){
    		System.out.print("problem! trying to link unmatched ranks");
    		return null;
    	}
    	HeapNode smallRoot;
		HeapNode bigRoot;
    	if (root1.getKey()<=root2.getKey()){
    		 smallRoot=root1;
    		 bigRoot=root2;
    	}
    	else{
    		 smallRoot=root2;
    		 bigRoot=root1;
    	}
    	/*
    	 * needed only if we link to withing the successive linking proccess:
    	this.trees.remove(bigRoot);
    	this.numberOfTrees--;
    	*/
    	smallRoot.children.add(bigRoot);
    	bigRoot.parent=smallRoot;
    	smallRoot.setRank(smallRoot.getRank() + 1);
    	totalLinks++;
    	return smallRoot;
    }
    public void UpdateNewRootsAndMinNode(HeapNode[] bins){
    	
    	boolean searchForMin=this.min==null;
    	HeapNode tmpMin = new HeapNode(Integer.MAX_VALUE);
    	
    	this.trees = new HeapList();
    	for(int i=0;i<bins.length;i++){
    		if(bins[i]!=null){
    			bins[i].parent=null;
    			this.trees.add(bins[i]);
    			if(searchForMin){
    				tmpMin=chooseTheSmallerNode(tmpMin,bins[i]);
    			}
    		}
    	}
    	if(searchForMin){
    		this.min=tmpMin;
    	}
    	
    }
    public HeapNode chooseTheSmallerNode(HeapNode a,HeapNode b){
    	if(a.key<=b.key){
    		return a;
    	}
    	return b;
    }

	/**
	 * public HeapNode findMin()
	 *
	 * Return the node of the heap whose key is minimal.
	 * If heap is empty returns null
	 */
	public HeapNode findMin() {
		if (!this.empty())
			return min;
		return null;
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		if (heap2.size()>0)
		{
			this.trees.concat(heap2.trees); // concatenate two lists
			increaseSize(heap2.size()); // update num of nodes
			this.min = chooseTheSmallerNode(min, heap2.min); // update min
		}
	}

	private void increaseSize(int size2) {
		this.size += size2;
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
		int[] arr = new int[getMaxRank()+1];
		for (HeapNode node : trees)
			arr[node.getRank()] += 1;
		return arr; // to be replaced by student code
	}
	
	/**
	 * Finds the highest rank of a root in the heap
	 * @return Max rank of tree in the heap's roots
	 */
	private int getMaxRank() {
		int max = 0;
		for (HeapNode node : trees) {
			max = Math.max(node.getRank(), max);
		}
		return max;
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 * TODO should probably implement this with decreaseKey+deleteMin
	 */
	public void delete(HeapNode x) {
		this.decreaseKey(x,x.key-(this.min.key-1));
		this.deleteMin();
	}

	/**
	 * public void decreaseKey(HeapNode x, int delta)
	 *
	 * The function decreases the key of the node x by delta. The structure of the
	 * heap should be updated to reflect this chage (for example, the cascading cuts
	 * procedure should be applied if needed).
	 */
	public void decreaseKey(HeapNode x, int delta) {
		HeapNode parent = x.getParent();
		x.setKey(x.getKey()-delta);
		if (parent != null && x.getKey() < parent.getKey()) {
			cut(parent, x);
			x.unmark();
			cascadingCuts(parent);
		}
		if (x.getKey() < findMin().getKey()) {
			this.min = x;
		}
	}
	
	private void cascadingCuts(HeapNode y) {
		HeapNode z = y.getParent();
		if (z != null) {
			if (!y.isMarked()) {
				y.mark();
			}
			else {
				cut(z, y);
				cascadingCuts(z);
			}
		}		
	}
	/**
	 * Cuts child from parent, adding the child to the list of roots.
	 * @param parent
	 * @param child
	 */
	private void cut(HeapNode parent, HeapNode child) {
		parent.cut(child);
		trees.add(child);
		child.unmark();
		FibonacciHeap.increaseCuts(1);
		
	}

	/**
	 * public int potential()
	 *
	 * This function returns the current potential of the heap, which is: Potential
	 * = #trees + 2*#marked The potential equals to the number of trees in the heap
	 * plus twice the number of marked nodes in the heap.
	 */
	public int potential() {
		return this.numberOfTrees()+2*marks;
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
	 * 
	 * @param node the node to check
	 * @return true iff node is a root of the heap
	 */
	private boolean isRoot(HeapNode node) {
		return trees.contains(node);
	}
	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made during
	 * the run-time of the program. A cut operation is the operation which
	 * diconnects a subtree from its parent (during decreaseKey/delete methods).
	 */
	public static int totalCuts() {
		return totalCuts; // should be replaced by student code
	}

	public static void increaseCuts(int i) {
		totalCuts += i;
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than FibonacciHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	public class HeapNode {
		private HeapNode parent;
		private int rank = 0; // Dead field, getRank() returns children.size();
		private HeapList children;
		private HeapNode next;
		private HeapNode prev;
		public int key; // TODO Why is this public
		private boolean mark;
		
		public HeapNode(int key, HeapList children, boolean mark, HeapNode next, HeapNode prev) {
			this.key = key;
			this.children = children;
			this.setRank(children.size());
			this.next = next;
			this.prev = prev;
		}
		
		public HeapNode(int key) {
			this(key, new HeapList(), false, null, null);
		}
		
		public void unmark() {
			this.mark = false;
			
		}

		public void setKey(int i) {
			this.key = i;
			
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
			if (!this.isMarked() && !FibonacciHeap.this.isRoot(this)){
				this.mark = true;
				FibonacciHeap.increaseMarks(1);
			}
		}
		
		/**
		 * Cuts the child from the parent
		 * assumes this == child.parent
		 * @param child
		 * @return child
		 */
		public void cut(HeapNode child) {
			this.children.remove(child);
			child.removeParent();
		}
		
		/**
		 * remove's this node's parent
		 */
		private void removeParent() {
			this.setParent(null);
		}
		
		private void setParent(HeapNode newParent) {
			this.parent = newParent;
		}

		public HeapNode getParent() {
			return parent;
		}

		public int getRank() {
			return this.children.size();
		}
		
		private HeapNode getNext() {
			return next;
		}
		
		private void setNext(HeapNode next) {
			this.next = next;
		}
		
		private HeapNode getPrev() {
			return prev;
		}
		
		private void setPrev(HeapNode prev) {
			this.prev = prev;
		}

		private void setRank(int rank) {
			this.rank = rank;
		}
	}
	/**
	 * A self implemented list to contain HeapNodes 
	 *
	 */
	public class HeapList implements Iterable<HeapNode>{
		/*
		 * listSize=0 iff first=last=null
		 */
		private int listSize;
		private HeapNode first;
		private HeapNode last;
		
		public HeapList() {
			listSize = 0;
			first = last = null;
		}

		/**
		 * concatenating list2 at the end of current list in O(1) in WC
		 * @pre list2.size()>0
		 */
		public void concat(HeapList list2) {
			this.last.setNext(list2.first);
			this.first.setPrev(list2.last);
			list2.first.setPrev(this.last);
			list2.last.setNext(this.first);
			
			this.last = list2.last; // update last
			
			this.listSize += list2.size(); // update number of trees in trees list
		}

		public int size() {
			return listSize;
		}
		
		public boolean contains(HeapNode node) {
			if (node==first || node==last)
				return true;
			else // at least 3 nodes in list
			{
				HeapNode current = first.getNext();
				while (current!=last)
				{
					if (current==node)
						return true;
					current = current.getNext();
				}
				return false;
			}
		}
		
		/**
		 * Removes node from list
		 */
		public void remove(HeapNode node) {
			HeapNode nextNode = node.getNext();
			HeapNode prevNode = node.getPrev();
			nextNode.setPrev(prevNode);
			prevNode.setNext(nextNode);
			node.setNext(null);
			node.setPrev(null);
			if (listSize==1)
			{
				first = last = null;
			}
			else
			{
				if (node==first)
					first = nextNode;
				else if (node==last)
					last = prevNode;
			}
			decSize();
		}
		
		private void decSize() {
			if (listSize>0)
				listSize--;
			else
				System.out.println("invalid decSize() call");
		}

		/**
		 * Inserts newNode at the end of list
		 */
		public void add(HeapNode newNode) {
			if (listSize==0)
			{
				newNode.setNext(newNode);
				newNode.setPrev(newNode);
				first = last = newNode;
			}
			else
			{
				newNode.setNext(first);
				newNode.setPrev(last);
				first.setPrev(newNode);
				last.setNext(newNode);
				last = newNode;
			}
			incSize();
		}
		
		private void incSize() {
			listSize++;
		}

		@Override
		public Iterator<HeapNode> iterator() {
			return new HeapListIterator();
		}
		
		public class HeapListIterator implements Iterator<HeapNode>
		{
			private HeapNode nextNode;
			private boolean firstIteration;
			
			public HeapListIterator() {
				nextNode = first;
				firstIteration = true;
			}

			@Override
			public boolean hasNext() {
				if (listSize>0)
				{
					if (firstIteration) // always return first item
					{
						firstIteration = false;
						return true;
					}
					return nextNode != first;
				}
				else
					return false;
			}

			@Override
			public HeapNode next() {
				HeapNode current = nextNode;
				nextNode = nextNode.getNext();
				return current;
			}
			
			public void remove() { throw new UnsupportedOperationException(); }
		}
	}
}
