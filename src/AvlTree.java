import java.util.ArrayList;
import java.util.Collections;

public class AvlTree {
    Node root;



    // find the height of the node
    public int height(Node node) {
        if (node == null)
            return 0;

        return node.height;
    }

    public Node leftRotate(Node rootNode) {
        Node rightNode = rootNode.getRightChild();
        Node middleNode = null;
        if (rightNode.getLeftChild() != null) {
            middleNode = rightNode.getLeftChild();
        }

        rightNode.setLeftChild(rootNode);
        rootNode.setRightChild(middleNode);

        // Update heights

        rootNode.height = Math.max(height(rootNode.getLeftChild()), height(rootNode.getRightChild())) + 1;
        rightNode.height = Math.max(height(rightNode.getLeftChild()), height(rightNode.getRightChild())) + 1;

        // Return new root
        return rightNode;
    }

    public Node rightRotate(Node rootNode) {
        Node leftNode = rootNode.getLeftChild();
        Node middleNode = null;
        if (leftNode.getRightChild() != null) {
            middleNode = leftNode.getRightChild();
        }

        leftNode.setRightChild(rootNode);
        rootNode.setLeftChild(middleNode);

        // Update heights
        rootNode.height = Math.max(height(rootNode.getLeftChild()), height(rootNode.getRightChild())) + 1;
        leftNode.height = Math.max(height(leftNode.getLeftChild()), height(leftNode.getRightChild())) + 1;

        // Return new root

        return leftNode;
    }

    private int checkBalance(Node root) {
        if (root == null) {
            return -1;
        }
        return root.height;
    }

    public int getBalance(Node node) {
        if (node == null)
            return 0;
        int leftHeight = 0;
        int rightHeight = 0;
        if (node.getRightChild() != null) {
            rightHeight = height(node.getRightChild());
        }
        if (node.getLeftChild() != null) {
            leftHeight = height(node.getLeftChild());
        }
        // balance is the difference between right height and left height
        return (leftHeight - rightHeight);
    }


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

    public ArrayList<String> findNodePath(String key) {
        ArrayList<String> path = new ArrayList<>();
        Node currentNode = root;
        if (root != null) {
            while (true) {
                // If the key is smaller than the root key move left
                if (key.compareTo(currentNode.getKey()) < 0 ) {
                    path.add(currentNode.getKey());
                    currentNode = currentNode.getLeftChild();
                } else if (key.compareTo(currentNode.getKey()) > 0 ) {
                    // If the key is bigger than the root key move right
                    path.add(currentNode.getKey());
                    currentNode = currentNode.getRightChild();
                } else {
                    path.add(currentNode.getKey());
                    return path;
                }
            }
        }
        path.add(key);
        return path;
    }

    public Node balanceTree(Node node, String key) {
        int balance = getBalance(node);
        // there are 4 cases for rotation
        // 2 for single 2 for double rotation
        // Double rotations
        if (balance < -1 && key.compareTo(node.getRightChild().getKey())  < 0 ) {
            node.setRightChild(rightRotate(node.getRightChild()));
            System.out.println("Rebalancing: right-left rotation");
            return leftRotate(node);
        }
        if (balance > 1 && key.compareTo(node.getLeftChild().getKey()) >0 ) {
            node.setLeftChild(leftRotate(node.getLeftChild()));
            System.out.println("Rebalancing: left-right rotation");
            return rightRotate(node);
        }
        // Single Rotations:
        if (balance < -1 && key.compareTo(node.getRightChild().getKey())>0) {
            System.out.println("Rebalancing: left rotation");
            return leftRotate(node);
        }
        if (balance > 1 && key.compareTo(node.getLeftChild().getKey())<0) {
            System.out.println("Rebalancing: right rotation");
            return rightRotate(node);
        }


        return node;
    }

    public void addNode(String key) {
        if (root != null){
            // Print all the nodes that the added element passes through
            ArrayList<String> path = findParentOfNodeToBeAdded(key);
            for (int i = 0; i < path.size(); i++) {
                String parent = path.get(i);
                System.out.println(parent + ": New node being added with IP:" + key);
            }
        }
        root = addNode(root, key);

    }

    public ArrayList<String> findParentOfNodeToBeAdded(String key) {
        ArrayList<String> path = new ArrayList<>();
        Node currentNode = root;
        if (root != null) {
            while (true) {
                // If the key is smaller than the root key move left
                if (key.compareTo(currentNode.getKey()) < 0) {
                    path.add(currentNode.getKey());
                    if (currentNode.getLeftChild() != null) {
                        currentNode = currentNode.getLeftChild();
                    }
                    else{
                        return path;
                    }
                } else if (key.compareTo(currentNode.getKey()) > 0) {
                    // If the key is bigger than the root key move right
                    path.add(currentNode.getKey());
                    if (currentNode.getRightChild() != null) {
                        currentNode = currentNode.getRightChild();
                    } else{
                        return path;
                    }
                }
                else{
                    return path;
                }
            }
        }
        path.add(key);
        return path;
    }

    public Node addNode(Node node, String key) {

//        if (root == null) {
//            root = new Node(key);
//            return root;
//        }
        // If we have reached the null root, add element here
        if (node == null) {
            return (new Node(key));
        }

        if (key.compareTo(node.getKey()) < 0)
            node.setLeftChild(addNode(node.getLeftChild(), key));
        else if (key.compareTo(node.getKey()) > 0)
            node.setRightChild(addNode(node.getRightChild(), key));
        else
            return node;

        // update heights
        node.height = 1 + Math.max(height(node.getLeftChild()),
                height(node.getRightChild()));

		// and balance the tree
        return balanceTree(node, key);
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
                    degree = 0;
                    return degree;
                } else if ((currentNode.getLeftChild() != null) && (currentNode.getRightChild() != null)) {
                    degree = 2;
                    return degree;
                } else {
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
        // update height
        root.height = Math.max(height(root.getLeftChild()), height(root.getRightChild())) + 1;

        return balanceTree(root, key);
    }

    String getMin(Node root) {
        String minValue = root.getKey();
        while (root.getLeftChild() != null) {
            minValue = root.getLeftChild().getKey();
            root = root.getLeftChild();
        }
        return minValue;
    }

    public void sendMessage(String sender, String receiver) {
        ArrayList<String> senderPath = findNodePath(sender);
        ArrayList<String> receiverPath = findNodePath(receiver);
        int count = 0;
        for (String element : senderPath) {
            if (receiverPath.contains(element)) { //if (receiverPath.get(0) == element)
                count++;
            }
        }
        for (int i = 0; i < count - 1; i++) {
            senderPath.remove(0);
            receiverPath.remove(0);
        }
        senderPath.remove(0);

        Collections.reverse(senderPath);
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
