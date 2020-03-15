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
        }

        public Node(Node tmp){
            this.value = tmp.value;
            this.left = null;
            this.right = null;
            this.up = null;
            this.down = tmp;
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

    public boolean insert(int value){
        Node current = negative; 
        List<Node> updatedlist = new ArrayList<Node>();

        while(current != null){

            while(current.right != null && current.right.value < value){
                current = current.right;
            }

            updatedlist.add(current);
            current = current.down;
        }

        int level = 0;
        Node tmp = null;

        while(level == 0 || coinToss()){

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
        return true;
    }

    public boolean delete(int value){
        Node current = this.negative;
        List<Node> updatedlist = new ArrayList<Node>();

        while(current != null){

            while(current.right != null && current.right.value < value){
                current = current.right;
            }

            if(current.right.value == value){
                updatedlist.add(current);
            }
            current = current.down;
        }

        for(int i=0; i<updatedlist.size(); i++){
            Node update = updatedlist.get(i);
            Node delete = update.right;

            update.right = delete.right;
            delete.right.left = update;
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

    public boolean coinToss(){
        // Random r = new Random();
        // long seed = 42;
        // r.setSeed(seed);
        // return r.nextBoolean();
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
            Node first = current;
            first = first.right;

            while(first.right != null){
                System.out.print(first.value + " ");
                first = first.right;
            }

            current = current.down;
            System.out.println();
        }
    }

    public static void complexityIndicator(){
        System.out.println("al144291;4;15");
    }

    public static void main(String[] args){

        Hw02 list = new Hw02();
        boolean result = false;
        String substr = "";

        String infile = args[0];

        try{
            complexityIndicator();
            System.out.println("For the input file named " + infile);
            System.out.println("With the RNG unseeded,");
            System.out.println("the current Skip List is shown below:");
            System.out.println("---infinity");

            BufferedReader br = new BufferedReader(new FileReader(infile));
            // StringBuilder sb = new StringBuilder();
            // String line = br.readLine();

            // while(line != null){
            //     sb.append(line);
            //     sb.append(System.lineSeparator());
            //     line = br.readLine();
            // }

            // String everything = sb.toString();
            // String[] input = everything.split("\n");

        

            // for(int i=0; i<input.length; i++){
            //     String tmp = input[i];
            // String line = "";
            // while((line = br.readLine()) != null){

            //     if(tmp.startsWith("i")){
            //         substr=tmp.substring(2,4);
            //         result = list.insert(Integer.parseInt(substr)); 
            //     }
            //     else if(tmp.startsWith("s")){
            //         Node node = list.search(Integer.parseInt(tmp.substring(2,4)));

            //         if(node!=null){
            //             System.out.println(tmp.substring(2,5) + " found");
            //         }
            //         else{
            //             System.out.println(tmp.substring(2,5) + " not found");
            //         }
            //     }
            //     else if(tmp.startsWith("d")){
            //         result = list.delete(Integer.parseInt(tmp.substring(2,4)));

            //         if(result == true){
            //             System.out.println(tmp.substring(2,5) + " deleted");
            //         }
            //         else{
            //             System.out.println(tmp.substring(2,5) + " not deleted");
            //         }
            //     }
            Scanner scnr = new Scanner(new File(infile));

            char command = 'o';

            while(scnr.hasNext()){
                command = scnr.next().charAt(0);

                if(command == 'i'){
                    list.insert(scnr.nextInt());
                }
                else if(command == 's'){
                    list.search(scnr.nextInt());
                    if(list.search(scnr.nextInt()) != null){
                        System.out.print("found");
                    }else{
                        System.out.println("not found");
                    }
                }
                else if(command == 'd'){
                    list.delete(scnr.nextInt());
                    if(list.delete(scnr.nextInt()) != false){
                        System.out.print("deleted");
                    }else{
                        System.out.println("not deleted");
                    }
                }
                else if(command == 'p'){
                    list.printAll();
                }
                // else if (tmp.startsWith("p")){
                //     list.printAll();
                // }
            }//end of while
            System.out.println("+++infinity");
            System.out.print("---End of Skip List---");
        }//end try

        catch(Exception e){
            e.printStackTrace();
        }
    }//end main
}