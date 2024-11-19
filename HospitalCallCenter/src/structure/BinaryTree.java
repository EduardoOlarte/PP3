package structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BinaryTree<T> implements ITree<T> {
	private NodeTree<T> root;
	private Comparator<T> comparator;

	public BinaryTree(Comparator<T> comparator) {
		this.comparator = comparator;
		this.root = null;
	}

	@Override
	public boolean add(T value) {
		if (root == null) {
			root = new NodeTree<>(value);
			return true;
		}
		return addRecursive(root, value);
	}

	private boolean addRecursive(NodeTree<T> current, T value) {
		int cmp = comparator.compare(value, current.getData());
		if (cmp == 0) {
			return false;
		} else if (cmp < 0) {
			if (current.getLeft() == null) {
				current.setLeft(new NodeTree<>(value));
				return true;
			}
			return addRecursive(current.getLeft(), value);
		} else {
			if (current.getRight() == null) {
				current.setRight(new NodeTree<>(value));
				return true;
			}
			return addRecursive(current.getRight(), value);
		}
	}

	@Override
	public boolean contains(T value) {
		return containsRecursive(root, value);
	}

	private boolean containsRecursive(NodeTree<T> current, T value) {
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

	@Override
	public Iterator<T> iterator() {
		return new InOrderIterator(root);
	}

	public Iterator<T> preorderIterator() {
		return new PreOrderIterator(root);
	}

	private class InOrderIterator implements Iterator<T> {
		private List<T> elements;
		private int currentIndex = 0;

		public InOrderIterator(NodeTree<T> root) {
			elements = new ArrayList<>();
			inorderTraversal(root);
		}

		private void inorderTraversal(NodeTree<T> node) {
			if (node != null) {
				inorderTraversal(node.getLeft());
				elements.add(node.getData());
				inorderTraversal(node.getRight());
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

	private class PreOrderIterator implements Iterator<T> {
		private List<T> elements;
		private int currentIndex = 0;

		public PreOrderIterator(NodeTree<T> root) {
			elements = new ArrayList<>();
			preorderTraversal(root);
		}

		private void preorderTraversal(NodeTree<T> node) {
			if (node != null) {
				elements.add(node.getData());
				preorderTraversal(node.getLeft());
				preorderTraversal(node.getRight());
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

	private NodeTree<T> removeRecursive(NodeTree<T> current, T value) {
		if (current == null) {
			return null;
		}
		int cmp = comparator.compare(value, current.getData());
		if (cmp < 0) {
			current.setLeft(removeRecursive(current.getLeft(), value));
		} else if (cmp > 0) {
			current.setRight(removeRecursive(current.getRight(), value));
		} else {
			if (current.getLeft() == null) {
				return current.getRight();
			} else if (current.getRight() == null) {
				return current.getLeft();
			}
			current.setData(findMin(current.getRight()).getData());
			current.setRight(removeRecursive(current.getRight(), current.getData()));
		}
		return current;
	}

	private NodeTree<T> findMin(NodeTree<T> node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}

	public NodeTree<T> search(T value) {
		return searchRecursive(root, value);
	}

	private NodeTree<T> searchRecursive(NodeTree<T> current, T value) {
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

	private int sizeRecursive(NodeTree<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + sizeRecursive(node.getLeft()) + sizeRecursive(node.getRight());
		}
	}
}
