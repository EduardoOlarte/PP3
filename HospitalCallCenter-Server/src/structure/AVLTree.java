package structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class AVLTree<T> implements ITree<T> {
	private NodeTreeAvl<T> root;
	private Comparator<T> comparator;

	public AVLTree(Comparator<T> comparator) {
		this.comparator = comparator;
		this.root = null;
	}

	@Override
	public boolean add(T value) {
		if (root == null) {
			root = new NodeTreeAvl<>(value);
			return true;
		}
		root = addRecursive(root, value);
		return true;
	}

	private NodeTreeAvl<T> addRecursive(NodeTreeAvl<T> node, T value) {
		if (node == null) {
			return new NodeTreeAvl<>(value);
		}
		int cmp = comparator.compare(value, node.getData());
		if (cmp < 0) {
			node.setLeft(addRecursive(node.getLeft(), value));
		} else if (cmp > 0) {
			node.setRight(addRecursive(node.getRight(), value));
		} else {
			return node;
		}

		node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));

		return balance(node);
	}

	private NodeTreeAvl<T> balance(NodeTreeAvl<T> node) {
		int balanceFactor = giveBalance(node);

		if (balanceFactor > 1 && giveBalance(node.getLeft()) >= 0) {
			return rotateRight(node);
		}

		if (balanceFactor < -1 && giveBalance(node.getRight()) <= 0) {
			return rotateLeft(node);
		}

		if (balanceFactor > 1 && giveBalance(node.getLeft()) < 0) {
			node.setLeft(rotateLeft(node.getLeft()));
			return rotateRight(node);
		}

		if (balanceFactor < -1 && giveBalance(node.getRight()) > 0) {
			node.setRight(rotateRight(node.getRight()));
			return rotateLeft(node);
		}

		return node;
	}

	private int height(NodeTreeAvl<T> node) {
		return node == null ? 0 : node.getHeight();
	}

	private int giveBalance(NodeTreeAvl<T> node) {
		return node == null ? 0 : height(node.getLeft()) - height(node.getRight());
	}

	private NodeTreeAvl<T> rotateRight(NodeTreeAvl<T> y) {
		NodeTreeAvl<T> x = y.getLeft();
		NodeTreeAvl<T> T2 = x.getRight();

		x.setRight(y);
		y.setLeft(T2);

		y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
		x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);

		return x;
	}

	private NodeTreeAvl<T> rotateLeft(NodeTreeAvl<T> x) {
		NodeTreeAvl<T> y = x.getRight();
		NodeTreeAvl<T> T2 = y.getLeft();

		y.setLeft(x);
		x.setRight(T2);

		x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
		y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);

		return y;
	}

	@Override
	public boolean contains(T value) {
		return containsRecursive(root, value);
	}

	private boolean containsRecursive(NodeTreeAvl<T> current, T value) {
		if (current == null) {
			return false;
		}
		int cmp = comparator.compare(value, current.getData());
		if (cmp == 0) {
			return true;
		} else if (cmp < 0) {
			return containsRecursive(current.getLeft(), value);
		} else {
			return containsRecursive(current.getRight(), value);
		}
	}

	@Override
	public Comparator<T> comparator() {
		return comparator;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	public String showIterator() {
		String message = "";
		Iterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			message += " " + iterator.next();
		}
		return message;
	}

	@Override
	public Iterator<T> iterator() {
		return new InOrderIterator(root);
	}



	private class InOrderIterator implements Iterator<T> {
		private List<T> elements;
		private int currentIndex = 0;

		public InOrderIterator(NodeTreeAvl<T> root) {
			elements = new ArrayList<>();
			inorderTraversal(root);
		}

		private void inorderTraversal(NodeTreeAvl<T> root) {
			if (root != null) {
				inorderTraversal(root.getLeft());
				elements.add(root.getData());
				inorderTraversal(root.getRight());
			}
		}

		@Override
		public boolean hasNext() {
			return currentIndex < elements.size();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return elements.get(currentIndex++);
		}
	}
	public Iterator<T> preorderIterator() {
		return new PreOrderIterator(root);
	}

	private class PreOrderIterator implements Iterator<T> {
		private List<T> elements;
		private int currentIndex = 0;

		public PreOrderIterator(NodeTreeAvl<T> root) {
			elements = new ArrayList<>();
			preorderTraversal(root);
		}

		private void preorderTraversal(NodeTreeAvl<T> root) {
			if (root != null) {
				elements.add(root.getData());
				preorderTraversal(root.getLeft());
				preorderTraversal(root.getRight());
			}
		}

		@Override
		public boolean hasNext() {
			return currentIndex < elements.size();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return elements.get(currentIndex++);
		}
	}

	@Override
	public boolean remove(T value) {
		if (contains(value)) {
			root = removeRecursive(root, value);
			return true;
		}
		return false;
	}

	private NodeTreeAvl<T> removeRecursive(NodeTreeAvl<T> node, T value) {
		if (node == null) {
			return null;
		}

		int cmp = comparator.compare(value, node.getData());
		if (cmp < 0) {
			node.setLeft(removeRecursive(node.getLeft(), value));
		} else if (cmp > 0) {
			node.setRight(removeRecursive(node.getRight(), value));
		} else {
			if (node.getLeft() == null || node.getRight() == null) {
				NodeTreeAvl<T> temp = (node.getLeft() != null) ? node.getLeft() : node.getRight();
				if (temp == null) {
					temp = node;
					node = null;
				} else {
					node = temp;
				}
			} else {
				NodeTreeAvl<T> temp = findMin(node.getRight());
				node.setData(temp.getData());
				node.setRight(removeRecursive(node.getRight(), temp.getData()));
			}
		}

		if (node == null) {
			return node;
		}

		node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);

		return balance(node);
	}

	private NodeTreeAvl<T> findMin(NodeTreeAvl<T> node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}

	public NodeTreeAvl<T> search(T value) {
		return searchRecursive(root, value);
	}

	private NodeTreeAvl<T> searchRecursive(NodeTreeAvl<T> current, T value) {
		if (current == null) {
			return null;
		}

		int cmp = comparator.compare(value, current.getData());

		if (cmp == 0) {
			return current;
		} else if (cmp < 0) {
			return searchRecursive(current.getLeft(), value);
		} else {
			return searchRecursive(current.getRight(), value);
		}
	}

	public int size() {
		return sizeRecursive(root);
	}

	private int sizeRecursive(NodeTreeAvl<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + sizeRecursive(node.getLeft()) + sizeRecursive(node.getRight());
		}
	}

}
