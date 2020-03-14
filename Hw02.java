import java.util.*;
import java.io.*;

class Hw02{

    //positive & negative infinify nodes
    public Node negative;
    public Node positive;

    //setting up the infinite nodes in skip list
    public SkipList(){
        negative = new Node(Integer.MIN_VALUE);
        positive = new Node(Integer.MAX_VALUE);

        negative.setRight(positive);
        positive.setRight(negative);
    }

    //SEARCH FUNCTION
    public Node search(int value){
        //start at left
        Node current = negative;

        while(current != null){
            while(current.right != null && current.right.data <= data){
                current = current.right;
            }
            if(current.data == data){
                break;
            }

            current = current.down;
        }
        return current;
    }

    //INSERT FUNCTION
    public boolean insert(int value){
        //start at left
        Node current = negative;

        List<Node> skiplist = new ArrayList<Node>();

        while(current != null){
            while(current.right != null && current.right.data < data){
                current = current.right;
            }
            skiplist.add(current);
            current = current.down;
        }

        //insert after finding the last qualifying node
        int level = 0;

        Node tmp = null;

        while(level == 0 || random()){
            //moving up
            if(tmp == null){
                tmp = new Node(value);
            }
            else{
                tmp = new Node(tmp);
            }

            Node tmp2 = null;

            if(skiplist.size() <= level){
                createLevel();
                tmp2 = this.negative;
            }
            else{
                tmp2 = skiplist.get(skiplist.size() - level - 1);
            }

            tmp.right = tmp2.right;
            tmp.left = tmp2;
            tmp.right.left = tmp;
            tmp2.right = tmp;
            level++;
            
        }
        return true;
    }

    //DELETE FUNCTION
    public boolean delete(int value){
        Node current = this.negative;

        List<Node> skiplist = new ArrayList<Node>();

        while(current != null){
            while(current.right != null && current.right.data < data){
                current = current.right;
            }

            if(current.right.data == data){
                skiplist.add(current);
            }

            current = current.down;
        }

        for(int i=0; i < skiplist.size(); i++){
            Node tmp = skiplist.get(i);
            Node delete = tmp.right;
            tmp.right = delete.right;
            delete.right.left = tmp;
            delete.up = null;
            delete.down = null;
        }

        return true;
    }

    public void newLevel(){
        Node negative = new Node(Integer.MIN_VALUE);
        Node positive = new Node(Integer.MAX_VALUE);

        negative.setRight(positive);
        positive.setLeft(negative);

        this.negative.up = negative;
        negative.down = this.negative;
        this.negative = negative;

        this.positive.up = positive;
        positive.down = this.positive;
        this.positive = positive;
    }

    public boolean rand(){
        return Math.random() >= 0.5;
    }

    public void print(){
        Node current = this.negative;

        while(current.down != null){
            current = current.down;
        }

        current = current.right;
        while(current.right != null){
            System.out.print(current.value + " ");
            current = current.right;
        }

        System.out.println();
    }

    public void printAll(){
        Node current = this.negative;

        while(current != null){
            Node tmp = current;
            tmp = tmp.right;

            while(tmp.right != null){
                System.out.print(tmp.value + " ");
                tmp = tmp.right;
            }
            current = current.down;
            System.out.println();
        }
    }

    public static void main(String[] args){

        SkipList list = new SkipList();

        boolean result = false;

        try{
            //getting file name from command input
            String infile = args[0];
            BufferedReader br;
            br = new BufferedReader(new FileReader(infile));

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line != null){
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            String everything = sb.toString();
            Strring[] input = everything.split(",");

            for(int i=0; i<input.length; i++){
                String temp = input[i];


            }
        }
    }


}