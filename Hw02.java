import java.io.*;
import java.util.*;

public class Hw02{

    public static class node{
        //public String key; //what is this for
        public Integer value;

        public node up;
        public node down;
        public node left;
        public node right;

        //helper infinity declarations
        public static int negative = Integer.MIN_VALUE;
        public static int positive = Integer.MAX_VALUE;
    }

    public static class skiplist{
        public node head;
        public node tail;

        public int n; //number of entries in list
        public int h; //height 
        public Random r; //coin toss

        public static void SkipList(){
            node tmp, tmp2;

            tmp = new node(node.negative, null);
            tmp2 = new node(node.positive, null);

            head = tmp;
            tail = tmp2;

            tmp.right = tmp2;
            tmp2.left = tmp;

            n = 0; //no entries to start
            h = 0; //initial height 0

            r = new Random(42)%2;
        }
//SEARCHING FUNCTIONS
        public node searching(int value){
            node tmp;
            tmp = head;

            while(true){
                while((tmp.right.value) != node.positive && (tmp.right.value) <= value){
                    tmp = tmp.right;
                }
                //try going down a level
                if(tmp.down != null){
                    tmp = tmp.down;
                }
                else{
                    break;
                }
            }
            return(tmp);
        }

        public Integer search(int value){
            node tmp;
            tmp = searching(value);

            if(tmp.value == value){
                 return(tmp.value);
            }
            else{
                System.out.println("not found");
                return(null);
            }
        }

        public void addLayer(){
            node tmp, tmp2;
            tmp = new node(node.negative, null);
            tmp2 = new node(node.positive, null);

            tmp.right = tmp2;
            tmp.down = head;

            tmp2.left = tmp;
            tmp.down = tail;

            head.up = tmp;
            tail.up = tmp2;

            head = tmp;
            tail = tmp2;

            h += 1; //add to height count
        }

//INSERTING FUNCTION
        public Integer insert(int value){
            node tmp, tmp2;
            int i;

            tmp = searching(value);

            if(tmp.value == value){ //does the value already exist in the list
                break;
            }
            else{ //it does not, enter it
                tmp2 = new node(value);

                tmp2.left = tmp;
                tmp2.right = tmp.right;
                tmp.right.left = tmp2;
                tmp.right = tmp2;

                i=0;
                while(r.nextDouble() < 0.5){
                    if(i >= h){
                        addLayer();
                    }

                    while(tmp.up == null){
                        tmp = tmp.left;
                    }
                    tmp = tmp.up;

                    node tmp3;

                    tmp3 = new node(value, null);
                    tmp3.left = tmp;
                    tmp3.right = tmp.right;
                    tmp3.down = tmp2;

                    tmp.tight.left = tmp3;
                    tmp.right = tmp3;
                    tmp2.up = tmp3;

                    tmp2 = tmp3;

                    i += 1;
                }
                n += 1; //another entry was added to the list
                return(null);
            }
        }
    }

}