import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line = br.readLine();
        String outName = args[1];


        PrintStream outputAVL = new PrintStream(outName + "_avl.txt");
        PrintStream outputBST = new PrintStream(outName + "_bst.txt");
        AvlTree avlTree = new AvlTree();
        BstTree bstTree = new BstTree();

        while (line != null) {
            String[] commands = line.split(" ");
            if (commands.length == 1) {
                System.setOut(outputAVL);
                avlTree.addNode(commands[0]);
                System.setOut(outputBST);
                bstTree.addNode(commands[0]);
            }
            if (Objects.equals(commands[0], "ADDNODE")) {
                System.setOut(outputAVL);
                avlTree.addNode(commands[1]);
                System.setOut(outputBST);
                bstTree.addNode(commands[1]);
            }
            if (Objects.equals(commands[0], "DELETE")) {
                System.setOut(outputAVL);
                avlTree.deleteNode(commands[1]);
                System.setOut(outputBST);
                bstTree.deleteNode(commands[1]);
            }
            if (Objects.equals(commands[0], "SEND")) {
                System.setOut(outputAVL);
                avlTree.sendMessage(commands[1], commands[2]);
                System.setOut(outputBST);
                bstTree.sendMessage(commands[1], commands[2]);
            }
            line = br.readLine();
        }
        br.close();


    }

}