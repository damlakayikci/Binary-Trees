import java.util.ArrayList;
import java.util.Collections;

public class BstTree {
    Node root;




    public Node findNode(String key){
        Node currentNode = root;
        while (true) {
            // If the key is smaller than the root key move left
            if (key.compareTo(currentNode.getKey()) < 0) {
                currentNode = currentNode.getLeftChild();
            } else if (key.compareTo(currentNode.getKey()) > 0) {
                // If the key is bigger than the root key move right
                currentNode = currentNode.getRightChild();
            } else {
                return currentNode;
            }
        }
    }

    public void printAdd(String key) {
        // Print all the nodes that the added element passes through
        ArrayList<String> path = findNodePath(key);
        for (int i = 1; i < path.size(); i++) {
            String parent = findParent(path.get(i));
            System.out.println(parent + ": New node being added with IP:" + key);
        }
    }

    public void addNode(String key) {
        // Create the node to be added
        Node newNode = new Node(key);
        // If there is no root, newNode becomes root
        if (root == null) {
            root = newNode;
        } else {
            // Else determine a current node
            // which is the root node in the beginning
            Node currentNode = root;
            while (true) {
                if (key.compareTo(currentNode.getKey()) < 0) {
                    // If the left child has no children
                    if (currentNode.getLeftChild() == null) {
                        // then place the new node to the left of it
                        currentNode.setLeftChild(newNode);
                        printAdd(key);
                        return;
                    }
                    // Or switch to the left child
                    currentNode = currentNode.getLeftChild();
                } else if (key.compareTo(currentNode.getKey()) > 0) {
                    // If the right child has no children
                    if (currentNode.getRightChild() == null) {
                        // then place the new node to the right of it
                        currentNode.setRightChild(newNode);
                        printAdd(key);
                        return;
                    }
                    // Or switch to the right child
                    currentNode = currentNode.getRightChild();

                } else
                    return;
            }
        }
    }
    // Get the min value of the tree, which is the left most element
    String getMin(Node root) {
        String minValue = root.getKey();
        while (root.getLeftChild() != null) {
            minValue = root.getLeftChild().getKey();
            root = root.getLeftChild();
        }
        return minValue;
    }


    public void deleteNode(String key) {
        // Degree is for finding the number of children of the deleted node
        int degree = findBranch(key);
        String parent = findParent(key);
        if (degree == 0) {
            System.out.println(parent + ": Leaf Node Deleted: " + key);
        } else if (degree == 1) {
            System.out.println(parent + ": Node with single child Deleted: " + key);
        } else if (degree == 2) {
            String replacedNode = getMin(findNode(key).getRightChild());
            System.out.println(parent + ": Non Leaf Node Deleted; removed: " + key + " replaced: " + replacedNode);
        }
        root = deleteNode(root, key);
    }

    public String findParent(String key) {
        // parent is the (length -1)th element
        ArrayList<String> path = findNodePath(key);
        return path.get(path.size() - 2);
    }

    public int findBranch(String key) {
        int degree = 0;
        Node currentNode = root;
        while (true) {
            if (key.compareTo(currentNode.getKey()) < 0) {
                currentNode = currentNode.getLeftChild();
            } else if (key.compareTo(currentNode.getKey()) > 0) {
                currentNode = currentNode.getRightChild();
            } else {
                if ((currentNode.getLeftChild() == null) && (currentNode.getRightChild() == null)) {
                    // If element has no child its degree is 0
                    degree = 0;
                    return degree;
                } else if ((currentNode.getLeftChild() != null) && (currentNode.getRightChild() != null)) {
                    // If it has to children its degree is 2
                    degree = 2;
                    return degree;
                } else {
                    // else its degree is 1
                    degree = 1;
                    return degree;
                }
            }
        }
    }

    private Node deleteNode(Node root, String key) {
        // If we've reached the null element, return it
        if (root == null)
            return root;

        if (key.compareTo(root.getKey()) < 0)
            // then if the key is smaller, move to the left
            root.setLeftChild(deleteNode(root.getLeftChild(), key));
        else if (key.compareTo(root.getKey()) > 0)
            // then if the key is bigger, move to the right
            root.setRightChild(deleteNode(root.getRightChild(), key));

            // if we've found the key, it is the one to be deleted
        else {
            if ((root.getLeftChild() == null) && (root.getRightChild() == null)) {
                // If root has no child move left
                return root.getLeftChild();
            } else if (root.getLeftChild() == null) {
                return root.getRightChild();
            } else if (root.getRightChild() == null) {
                return root.getLeftChild();
            }
            // if node has 2 children get the smallest node in the right subtree
            root.setKey(getMin(root.getRightChild()));

            // Delete the node
            root.setRightChild(deleteNode(root.getRightChild(), root.getKey()));
        }

        return root;
    }

    public ArrayList<String> findNodePath(String key) {
        ArrayList<String> path = new ArrayList<>();
        Node currentNode = root;
        while (true) {
            // If the key is smaller than the root key move left
            if (key.compareTo(currentNode.getKey()) < 0) {
                path.add(currentNode.getKey());
                currentNode = currentNode.getLeftChild();
            } else if (key.compareTo(currentNode.getKey()) > 0) {
                // If the key is bigger than the root key move right
                path.add(currentNode.getKey());
                currentNode = currentNode.getRightChild();
            } else {
                path.add(currentNode.getKey());
                return path;
            }
        }
    }

    public void sendMessage(String sender, String receiver) {
        ArrayList<String> senderPath = findNodePath(sender);
        ArrayList<String> receiverPath = findNodePath(receiver);
        int count = 0;
        for (String element : senderPath) {
            if (receiverPath.contains(element)) {
                count++;
            }
        }
        for (int i = 0; i < count - 1; i++) {
            senderPath.remove(0);
            receiverPath.remove(0);
        }
        senderPath.remove(0);

        Collections.reverse(senderPath);
        // this is the path to be followed
        senderPath.addAll(receiverPath);
        System.out.println(sender + ": Sending message to: " + receiver);

        for (int i = 0; i < senderPath.size(); i++) {
            if (i != senderPath.size() -1 && i != senderPath.size() -2) {
                System.out.println(senderPath.get(i+1)+": Transmission from: " + senderPath.get(i) + " receiver: " + receiver + " sender:" + sender);
            }
        }
        System.out.println(receiver + ": Received message from: " + sender);

    }
}
