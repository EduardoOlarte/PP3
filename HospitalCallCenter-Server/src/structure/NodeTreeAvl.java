package structure;


public class NodeTreeAvl<T> {
    T data;
    NodeTreeAvl<T> left;
    NodeTreeAvl<T> right;
    int height; 

    public NodeTreeAvl(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 0; 
    }

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public NodeTreeAvl<T> getLeft() {
		return left;
	}

	public void setLeft(NodeTreeAvl<T> left) {
		this.left = left;
	}

	public NodeTreeAvl<T> getRight() {
		return right;
	}

	public void setRight(NodeTreeAvl<T> right) {
		this.right = right;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "NodeTree [data=" + data + ", left=" + left + ", right=" + right + ", height=" + height + "]";
	}


    
}
