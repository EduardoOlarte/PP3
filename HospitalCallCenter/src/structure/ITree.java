package structure;

import java.util.Comparator;
import java.util.Iterator;

public interface ITree<T> {
	boolean add(T value);

	Comparator<T> comparator();

	boolean contains(T value);

	boolean isEmpty();

	Iterator<T> iterator();

	boolean remove(T value);
}
