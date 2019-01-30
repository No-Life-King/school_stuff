package quadratic_assignment_problem;

import java.io.FileReader;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author phil
 */
public class QAP {

    private static int problem_size;
    private static int[][] flow_matrix;
    private static int[][] distance_matrix;

    public static void main(String[] args) {
        get_data("src/kra32.dat");

        int[] optimal_path = {31, 23, 18, 21, 22, 19, 10, 11, 15, 9, 30, 29, 14, 12, 17, 26, 27, 28, 1, 7, 6, 25, 5, 3, 8, 24, 32, 13, 2, 20, 4, 16};
        int opt_cost = calc_cost(optimal_path);
        print("Opt: " + opt_cost + "\n");

        NumberFormat nanoFormat = NumberFormat.getNumberInstance();
        long start = System.nanoTime();
        int[] greedy_solution = calc_greedy_path();
        long stop = System.nanoTime() - start;
        int greedy_cost = calc_cost(greedy_solution);
        print("Greedy: " + greedy_cost + ", " + nanoFormat.format(stop) + " ns\n");

        
        
        int[] hist = new int[30];
        for (int x=0; x<1; x++) {
            int best = evolutionary_search(100, 1000, false);
            hist[(int) (best/1000-92)] += 1;
        }
        for (int v: hist) {
            print(v + "\n");
        }
        
        random_search(1_000_000, false);
        tabu_search(100, 10000, 100, false);
        
        //calc_greedy2_path();
        //calc_greedy3_path();
        
    }

    private static int evolutionary_search(int starting_population_size, int iterations, boolean print_histogram) {
        LinkedList<int[][]> population = new LinkedList<>();
        HashSet<String> history = new HashSet<>();
        int lowest_cost = Integer.MAX_VALUE;
        int[] bins = new int[80];
        double total = 0;
        int[] best_path;

        int[] path = new int[problem_size];
        for (int x = 0; x < path.length; x++) {
            path[x] = x + 1;
        }

        NumberFormat nanoFormat = NumberFormat.getNumberInstance();
        long start = System.nanoTime();

        while (population.size() < starting_population_size) {
            for (int x = 0; x < 5; x++) {
                path = mutate(path);
            }
            int[] cost = {calc_cost(path)};
            int[][] soln = new int[2][];
            soln[0] = path;
            soln[1] = cost;

            population.add(soln);
            history.add(path_to_string(path));
        }

        while (history.size() < iterations) {
            ArrayList<int[][]> sample = new ArrayList<>();

            while (sample.size() < population.size() / 3) {
                int random_index = ThreadLocalRandom.current().nextInt(0, population.size());
                int[][] candidate = population.get(random_index);
                sample.add(candidate);
            }

            int min_cost = Integer.MAX_VALUE;
            int[] min_path = new int[problem_size];

            for (int[][] candidate : sample) {
                if (candidate[1][0] < min_cost) {
                    min_cost = candidate[1][0];
                    min_path = candidate[0];
                }
            }

            int[] child = mutate(min_path);
            while (history.contains(path_to_string(child))) {
                child = mutate(child);
            }

            int[] child_cost = {calc_cost(child)};
            bins[(int) child_cost[0] / 1000 - 85] += 1;
            total += child_cost[0];

            int[][] soln = new int[2][];
            soln[0] = child;
            soln[1] = child_cost;
            population.add(soln);

            population.remove(0);
//            int[][] worst = {{}, {Integer.MIN_VALUE}};
//            for (int[][] solution : population) {
//                if (solution[1][0] < worst[1][0]) {
//                    worst = solution;
//                }
//            }
//
//            population.remove(worst);
            history.add(path_to_string(child));

            if (child_cost[0] < lowest_cost) {
                lowest_cost = child_cost[0];
                best_path = child;
            }
            
        }

        long stop = System.nanoTime() - start;

        print("Evolutionary: " + lowest_cost + ", " + total / 1000000.0 + ", " + nanoFormat.format(stop) + " ns\n");
        if (print_histogram) {
            int z = 85;
            for (int bin : bins) {
                print(bin / 1000000.0 + "\n");
                z++;
            }
        }
        
        return lowest_cost;
    }

    private static String path_to_string(int[] path) {
        StringBuilder sb = new StringBuilder();

        for (int value : path) {
            sb.append(value);
            sb.append(" ");
        }

        return sb.toString();
    }

    private static int tabu_search(int num_children, int num_iterations, int tabu_list_size, boolean print_histogram) {
        LinkedList<int[]> tabu_list = new LinkedList<>();
        int[] best_path = new int[problem_size];
        int lowest_overall = Integer.MAX_VALUE;
        int[] path = new int[problem_size];
        int[] bins = new int[80];
        double total = 0;

        for (int x = 0; x < path.length; x++) {
            path[x] = x + 1;
        }

        tabu_list.add(path.clone());
        long start = System.nanoTime();
        for (int x = 0; x < num_iterations; x++) {
            int lowest_cost = Integer.MAX_VALUE;
            int[] best_child = new int[problem_size];

            for (int y = 0; y < num_children; y++) {
                int[] child = mutate(path);
                int child_cost = calc_cost(child);
                total += child_cost;
                bins[(int) child_cost / 1000 - 85] += 1;

                if (child_cost < lowest_cost) {
                    boolean tabu = false;
                    for (int[] element : tabu_list) {
                        for (int z = 0; z < element.length; z++) {
                            if (child[z] != element[z]) {
                                break;
                            }
                            if (z == 31) {
                                tabu = true;
                            }
                        }
                        if (tabu) {
                            break;
                        }
                    }

                    if (!tabu) {
                        lowest_cost = child_cost;
                        best_child = child;
                        if (lowest_cost < lowest_overall) {
                            lowest_overall = lowest_cost;
                            best_path = best_child;
                        }
                    }
                }
                
            }
            path = best_child;
            tabu_list.addLast(best_child.clone());
            if (tabu_list.size() > tabu_list_size) {
                tabu_list.removeFirst();
            }
            
            if (lowest_overall == 88700) {
                break;
            }

        }
        
        long stop = System.nanoTime() - start;
        //print("Tabu: " + lowest_overall + ", " + total / 1000000.0 + ", " + NumberFormat.getNumberInstance().format(stop) + " ns\n");

        if (print_histogram) {
            int z = 85;
            for (int bin : bins) {
                print(bin / 1000000.0 + "\n");
                z++;
            }
        }
        
        return lowest_overall;
    }

    private static int[] mutate(int[] path) {
        int i1 = ThreadLocalRandom.current().nextInt(0, problem_size);
        int i2 = ThreadLocalRandom.current().nextInt(0, problem_size);
        int[] child = path.clone();

        while (i1 == i2) {
            i2 = ThreadLocalRandom.current().nextInt(0, problem_size);
        }

        int temp = child[i1];
        child[i1] = child[i2];
        child[i2] = temp;

        return child;
    }

    private static int random_search(int number_of_paths, boolean print_histogram) {
        int[] path = new int[problem_size];
        int[] bins = new int[80];

        for (int x = 0; x < path.length; x++) {
            path[x] = x + 1;
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        double total = 0;

        long start = System.nanoTime();
        for (int y = 0; y < number_of_paths; y++) {
            int i1 = ThreadLocalRandom.current().nextInt(0, problem_size);
            int i2 = ThreadLocalRandom.current().nextInt(0, problem_size);

            while (i1 == i2) {
                i2 = ThreadLocalRandom.current().nextInt(0, problem_size);
            }

            int temp = path[i1];
            path[i1] = path[i2];
            path[i2] = temp;

            int path_cost = calc_cost(path);
            total += path_cost;

            if (path_cost < min) {
                min = path_cost;
            } else if (path_cost > max) {
                max = path_cost;
            }

            if (print_histogram) {
                bins[(int) path_cost / 1000 - 85] += 1;
            }
        }

        long stop = System.nanoTime() - start;
        print("Random: " + min + ", " + max + ", " + total / number_of_paths + ", " + NumberFormat.getInstance().format(stop) + " ns\n");

        if (print_histogram) {
            int z = 85;
            for (int bin : bins) {
                print(z + "k " + bin / 1000000.0 + "\n");
                z++;
            }
        }
        
        return min;
    }

    private static int[] calc_greedy_path() {
        int[] distance_row_sums = calc_row_sums(distance_matrix);
        int[] flow_row_sums = calc_row_sums(flow_matrix);
        int[] path = new int[problem_size];

        for (int x = 0; x < problem_size; x++) {
            int max_distance_index = 0;
            int min_flow_index = 0;
            int max_distance = Integer.MIN_VALUE;
            int min_flow = Integer.MAX_VALUE;

            for (int y = 0; y < problem_size; y++) {
                if (distance_row_sums[y] > max_distance) {
                    max_distance = distance_row_sums[y];
                    max_distance_index = y;
                } else if (flow_row_sums[y] < min_flow) {
                    min_flow = flow_row_sums[y];
                    min_flow_index = y;
                }
            }

            path[min_flow_index] = max_distance_index;
            distance_row_sums[max_distance_index] = 0;
            flow_row_sums[min_flow_index] = Integer.MAX_VALUE;
        }

        for (int z = 0; z < path.length; z++) {
            path[z] += 1;
        }

        return path;
    }

    private static int[] calc_greedy2_path() {
        int[] path = new int[problem_size];
        HashSet<Integer> taken = new HashSet<>();

        for (int x = 0; x < problem_size; x++) {
            int min_row_product = Integer.MAX_VALUE;
            int min_row = 0;

            for (int y = 0; y < problem_size; y++) {
                int row_cost = 0;
                if (taken.contains(y)) {
                    continue;
                }

                for (int z = 0; z < problem_size; z++) {
                    row_cost += distance_matrix[x][z] * flow_matrix[y][z];
                }

                if (row_cost < min_row_product) {
                    min_row_product = row_cost;
                    min_row = y;
                }
            }

            taken.add(min_row);
            path[min_row] = x;
        }

        for (int z = 0; z < path.length; z++) {
            path[z] += 1;
        }

        print(calc_cost(path) + "\n");

        return path;
    }

    private static int[] calc_greedy3_path() {
        int[] path = new int[problem_size];
        HashSet<Integer> taken_flow = new HashSet<>();
        HashSet<Integer> taken_distance = new HashSet<>();

        for (int w = 0; w < problem_size; w++) {
            int min_row_product = Integer.MAX_VALUE;
            int min_distance_row = 0;
            int min_flow_row = 0;

            for (int x = 0; x < problem_size; x++) {
                if (taken_distance.contains(x)) {
                    continue;
                }

                for (int y = 0; y < problem_size; y++) {
                    int row_cost = 0;
                    if (taken_flow.contains(y)) {
                        continue;
                    }

                    for (int z = 0; z < problem_size; z++) {
                        row_cost += distance_matrix[x][z] * flow_matrix[y][z];
                    }

                    if (row_cost < min_row_product) {
                        min_row_product = row_cost;
                        min_flow_row = y;
                        min_distance_row = x;
                    }
                }

            }

            taken_flow.add(min_flow_row);
            taken_distance.add(min_distance_row);
            path[min_flow_row] = min_distance_row;
        }

        for (int z = 0; z < path.length; z++) {
            path[z] += 1;
        }

        print(path_to_string(path) + "\n");
        print(calc_cost(path));

        return path;
    }

    private static int[] calc_row_sums(int[][] matrix) {
        int[] row_sums = new int[matrix.length];

        for (int x = 0; x < matrix.length; x++) {
            int sum = 0;
            for (int y = 0; y < matrix.length; y++) {
                sum += matrix[x][y];
            }

            row_sums[x] = sum;
        }

        return row_sums;
    }

    private static int calc_cost(int[] path) {
        int cost = 0;

//        int flow_row = 0;
//        for (int x : path) {
//            for (int y = 0; y < path.length; y++) {
//                cost += distance_matrix[x - 1][path[y] - 1] * flow_matrix[flow_row][y];
//            }
//
//            flow_row += 1;
//        }
        for (int x = 0; x < problem_size; x++) {
            for (int y = 0; y < problem_size; y++) {
                cost += distance_matrix[path[x] - 1][path[y] - 1] * flow_matrix[x][y];
            }
        }

        return cost;
    }

    private static void get_data(String path) {

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            problem_size = Integer.parseInt(br.readLine());
            flow_matrix = new int[problem_size][problem_size];
            distance_matrix = new int[problem_size][problem_size];

            boolean populate_flow_matrix = true;
            int matrix_index = 0;
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    populate_flow_matrix = false;
                    matrix_index = 0;
                    continue;
                }

                String[] values = line.split(" ");
                for (int x = 0; x < values.length; x++) {
                    if (populate_flow_matrix) {
                        flow_matrix[matrix_index][x] = Integer.parseInt(values[x]);
                    } else {
                        distance_matrix[matrix_index][x] = Integer.parseInt(values[x]);
                    }
                }

                matrix_index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading file.");
        }

    }

    private static void print_matrix(int[][] matrix) {
        for (int x = 0; x < matrix[0].length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                System.out.print(matrix[x][y] + " ");
            }
            System.out.println();
        }
    }

    private static void print(Object o) {
        System.out.print(o);
    }

}
