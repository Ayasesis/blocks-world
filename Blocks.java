import java.util.*;
import java.io.*;

public class Blocks {

    static List<List<String>> initial_state = new ArrayList<List<String>>(4);


    static List<List<String>> goal_state = new ArrayList<List<String>>(4);

    public static void print_state(List<List<String>> state) {
        for (List<String> l1 : state) {
            System.out.printf("L%d: [Table] ", state.indexOf(l1));
            for (Object l2 : l1)
                System.out.printf("%s ", l2);
            System.out.println();
        }
    }

    public static void read_state(File f) throws FileNotFoundException {

//        initial_state.get(0).add("a");
//        initial_state.get(0).add("b");
//        initial_state.get(1).add("c");
//        initial_state.get(1).add("d");
//        initial_state.get(2).add("e");
//        initial_state.get(2).add("f");
//        initial_state.get(3).add("g");
//        initial_state.get(3).add("h");
//
//        goal_state.get(3).add("a");
//        goal_state.get(3).add("b");
//        goal_state.get(2).add("c");
//        goal_state.get(2).add("d");
//        goal_state.get(1).add("e");
//        goal_state.get(1).add("f");
//        goal_state.get(0).add("g");
//        goal_state.get(0).add("h");


        Scanner scan = new Scanner(f);
        boolean in_initial = false;
        String line;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.contains("initial state")) {
                initial_state.add(new LinkedList<String>());
                initial_state.add(new LinkedList<String>());
                initial_state.add(new LinkedList<String>());
                initial_state.add(new LinkedList<String>());
                in_initial = true;
            }
            if (line.contains("final state")) {
                goal_state.add(new LinkedList<String>());
                goal_state.add(new LinkedList<String>());
                goal_state.add(new LinkedList<String>());
                goal_state.add(new LinkedList<String>());
                in_initial = false;
            }
            if (in_initial) {
                if (line.startsWith("L0")) {
                    String[] elements = line.split(" ");


                    if (elements.length > 2) {
                        initial_state.get(0).addAll(Arrays.asList(elements).subList(2,elements.length));
                    }
                }
                if (line.startsWith("L1")) {
                    String[] elements = line.split(" ");
                    if (elements.length > 2) {
                        initial_state.get(1).addAll(Arrays.asList(elements).subList(2,elements.length));
                    }
                }
                if (line.startsWith("L2")) {
                    String[] elements = line.split(" ");
                    if (elements.length > 2) {
                        initial_state.get(2).addAll(Arrays.asList(elements).subList(2,elements.length));
                    }
                }
                if (line.startsWith("L3")) {
                    String[] elements = line.split(" ");
                    if (elements.length > 2) {
                        initial_state.get(3).addAll(Arrays.asList(elements).subList(2,elements.length));
                    }
                }
            }
            else{
                    if (line.startsWith("L0")) {
                        String[] elements = line.split(" ");
                        if (elements.length > 2) {
                            goal_state.get(0).addAll(Arrays.asList(elements).subList(2,elements.length));
                        }
                    }
                    if (line.startsWith("L1")) {
                        String[] elements = line.split(" ");
                        if (elements.length > 2) {
                            goal_state.get(1).addAll(Arrays.asList(elements).subList(2,elements.length));
                        }
                    }
                    if (line.startsWith("L2")) {
                        String[] elements = line.split(" ");
                        if (elements.length > 2) {
                            goal_state.get(2).addAll(Arrays.asList(elements).subList(2,elements.length));
                        }
                    }
                    if (line.startsWith("L3")) {
                        String[] elements = line.split(" ");
                        if (elements.length > 2) {
                            goal_state.get(3).addAll(Arrays.asList(elements).subList(2,elements.length));
                        }
                    }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        read_state(new File("input.txt"));
        System.out.println("initial state");
        print_state(initial_state);

        for (int i = 0; i < 4; i++) {

            if (goal_state.get(i).size() > 0) {

                boolean is_identical = goal_state.get(i).equals(initial_state.get(i));
                if (is_identical) {
                    continue;
                }

                int temp_1, temp_2;
                temp_1 = (i + 1) % 4;
                temp_2 = (i + 2) % 4;

                for (int j = 0; j < goal_state.get(i).size(); j++) {
                    if (initial_state.get(i).size() > j &&
                            goal_state.get(i).get(j) == initial_state.get(i).get(j)) {
                        continue;
                    }

                    if (initial_state.get(i).size() > j &&
                            goal_state.get(i).get(j) != initial_state.get(i).get(j)) {
                        String goal_block = goal_state.get(i).get(j);
                        for (int k = initial_state.get(i).size() - 1; k >= j; k--) {
                            String move_out = initial_state.get(i).get(k);
                            initial_state.get(i).remove(k);
                            initial_state.get(temp_1).add(move_out);

                            System.out.println("middle state");
                            print_state(initial_state);
                        }
                    }

                    String goal_block = goal_state.get(i).get(j);
                    // look for goal block
                    int[] temp = {0,1,2,3};
                    for (int k : temp) {
                        if (initial_state.get(k).contains(goal_block)) {
                            if (initial_state.get(k).indexOf(goal_block) != initial_state.get(k).size() - 1) {
                                int temp_i;
                                if (k == temp_1)
                                    temp_i = temp_2;
                                else if (k == temp_2)
                                    temp_i = temp_1;
                                else
                                    temp_i = temp_1;

                                for (int p = initial_state.get(k).size() - 1; p > initial_state.get(k).indexOf(goal_block); p--) {
                                    String move_out = initial_state.get(k).get(p);
                                    initial_state.get(k).remove(p);
                                    initial_state.get(temp_i).add(move_out);

                                    System.out.println("middle state");
                                    print_state(initial_state);
                                }
                            }
                            initial_state.get(k).remove(goal_block);
                            initial_state.get(i).add(goal_block);

                            System.out.println("middle state");
                            print_state(initial_state);
                            break;
                        }

                    }

                }


            }

        }

        System.out.println("final state");
        print_state(initial_state);
        // end of main function
    }
}
