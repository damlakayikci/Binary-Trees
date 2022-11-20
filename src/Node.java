public class Node {

    private String key;
    private Node leftChild;
    private Node rightChild;
    private Node parent;
    public int height;

    // Constructors
    public Node(){};
    public Node(String key){
        this.key = key; 
        leftChild = null;
        rightChild = null;
        parent = null;
        height = 1;
    }
    public Node(String key, Node rightChild, Node leftChild){
        this.key = key;
        this.rightChild = rightChild;
        this.leftChild = leftChild;
        parent = null;
        height = 1;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {this.rightChild = rightChild;}

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

}
