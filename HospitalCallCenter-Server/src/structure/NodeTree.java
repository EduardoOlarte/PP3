package structure;

public class NodeTree<T> {
	private T data;
	private NodeTree<T> left;
	private NodeTree<T> right;
	
	
	public NodeTree(T data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public NodeTree<T> getLeft() {
		return left;
	}
	public void setLeft(NodeTree<T> left) {
		this.left = left;
	}
	public NodeTree<T> getRight() {
		return right;
	}
	public void setRight(NodeTree<T> right) {
		this.right = right;
	}
	@Override
	public String toString() {
		return "NodeTree [data=" + data + ", left=" + left + ", right=" + right + "]";
	}
	

}
