import java.io.*;
import java.util.*;

public class Hw02 {
    private Node negative;
    private Node positive;

    public Hw02(){
        negative = new Node(Integer.MIN_VALUE);
        positive = new Node(Integer.MAX_VALUE);

        negative.setRight(positive);
        positive.setLeft(negative);
    }

    public static class Node{
        public int value;
        public int height;
        public Node left;
        public Node right;
        public Node up;
        public Node down;

        public Node(int value){
            this.value = value;
            this.left = null;
            this.right = null;
            this.up = null;
            this.down = null;
            this.height = 0;
        }

        public Node(Node tmp){
            this.value = tmp.value;
            this.left = null;
            this.right = null;
            this.up = null;
            this.down = tmp;
            this.height = 0;
        }

        public Node getLeft(){
            return left;
        }
        public void setLeft(Node left){
            this.left = left;
        }
        
        public Node getRight() {
            return right;
        }
        public void setRight(Node right) {
            this.right = right;
        }

        public Node getUp() {
            return up;
        }
        public void setUp(Node up) {       
            this.up = up;     
        }

        public Node getDown() {       
            return down;
        }
        public void setDown(Node down) {      
            this.down = down;        
        }

    }//end node class

    public Node search(int value){
        Node current = negative;

        while(current != null){

            while(current.right != null && current.right.value <= value){
                current = current.right;
            }

            if(current.value == value){
                break;
            }

            current = current.down;
        }
        return current;
    }

    public boolean insert(int value, Random r){
        Node current = negative; 
        List<Node> updatedlist = new ArrayList<Node>();

        while(current != null){

            while(current.right != null && current.right.value <= value){
                if(current.right.value == value){
                    return false;
                }
                current = current.right;
            }

            updatedlist.add(current);
            current = current.down;
        }

        int level = 0;
        Node tmp = null;

        while(level == 0 || (coinToss(r, value)==1)){

            if(tmp == null){
                tmp = new Node(value);
            }
            else{
                tmp = new Node(tmp);
            }

            Node tmp2 = null;

            if(updatedlist.size() <= level){
                newLevel();
                tmp2 = this.negative;
            }
            else{
                tmp2 = updatedlist.get(updatedlist.size() - level - 1);
            }

            tmp.right = tmp2.right;
            tmp.left = tmp2;
            tmp.right.left = tmp;
            tmp2.right = tmp;

            level++;
        }
        tmp.height = level;
        return true;
    }


    public boolean delete(int value){
        Node current = this.negative;
        List<Node> updatedlist = new ArrayList<Node>();
        boolean state = false;

        while(current != null){

            while(current.right != null && current.right.value < value){
                current = current.right;
            }

            if(current.right.value == value){
                updatedlist.add(current);

                for(int i=0; i<updatedlist.size(); i++){
                    Node update = updatedlist.get(i);
                    Node delete = update.right;

                    update.right = delete.right;
                    delete.right.left = update;
                    delete.up = null;
                    delete.down = null;
                }
                //System.out.println("test"+state);
                state = true;
            }
            current = current.down;
        }  
        //System.out.println("test2"+state);      
        return state;
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

    public static int coinToss(Random r, int current){
        int v = (r.nextInt())%2;
        return v;
    }
        
    public void printAll(){
        Node current = this.negative;
        while(current != null){

            while(current.down != null){
                current = current.down;
            }

            Node head = current;
            head = head.right;

            while(head.right != null){
                System.out.print(" "+head.value + "; ");

                Node tmp = this.negative;
                Node tracker = tmp.right;
                while(tmp.down != null){
                    tracker = tmp.right;

                    while(tracker.right != null){

                        if(tracker.value == head.value){
                            System.out.print(" "+tracker.value + "; "); 
                            break; 
                        }
                        tracker = tracker.right;
                    }
                    //System.out.println();
                    tmp = tmp.down;
                    //tracker = tmp.right;
                }
                    
                System.out.println();
                head = head.right;
            }
            current = current.down;
        }
    }

    public static void complexityIndicator(){
        System.err.println("al144291;4;20");
    }

    public static void main(String[] args){

        Hw02 list = new Hw02();
        boolean result = false;
        String substr = "";

        String infile = args[0];

        Random r = new Random();
        r.setSeed(42);

        try{
            complexityIndicator();
            System.out.println("For the input file named " + infile);
            System.out.println("With the RNG unseeded,");

            Scanner scnr = new Scanner(new File(infile));

            char command = 'o';
            int current = 0;

            while(scnr.hasNext()){
                command = scnr.next().charAt(0);

                if(command == 'i'){
                    list.insert(scnr.nextInt(), r);
                }
                else if(command == 's'){
                    current = scnr.nextInt();
                    list.search(current);
                    if(list.search(current) != null){
                        System.out.println(current+ " found");
                    }else{
                        System.out.println(current+ " NOT FOUND");
                    }
                }
                else if(command == 'd'){
                    current = scnr.nextInt();
                    //list.delete(current);
                    if(list.delete(current) == true){
                        System.out.println(current+ " deleted");
                    }else{
                        System.out.println(current+ " integer not found - delete not successful");
                    }
                }
                else if(command == 'p'){
                    System.out.println("the current Skip List is shown below: ");
                    System.out.println("---infinity");
                    list.printAll();
                    System.out.println("+++infinity");
                    System.out.println("---End of Skip List---");
                }
            }//end of while
            
        }//end try

        catch(Exception e){
            e.printStackTrace();
        }
    }//end main
}