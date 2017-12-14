/** @author Phil Smith */
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TravellingSalesman {
	/**
	 * This program explores several different techniques for solving or estimating
	 * solutions to optimization problems - specifically the well-known travelling
	 * salesman problem. 
	 */
	
	// the city coordinates and labels
	private static final double[] coords = {0.134643599, 0.644606626,		//a
							   		  		0.085715042, 0.392759724,		//b
							   		  		0.773477251, 0.581039988,		//c
							   		  		0.686998724, 0.746717332,		//d
							   		  		0.516545774, 0.398441588,		//e
							   		  		0.939667609, 0.546435841,		//f
							   		  		0.197769913, 0.008271149,		//g
							   		  		0.67671762 , 0.374678047,		//h
							   		  		0.099895976, 0.364538864,		//i
							   		  		0.216475744, 0.75104157 ,		//j
							   		  		0.019240325, 0.168853455,		//k
							   		  		0.895383164, 0.512507985,		//l
							   		  		0.225475686, 0.67818289 ,		//m
							   		  		0.841331763, 0.156130221};		//n
	
	private static double[][] distances;
	private static HashMap<String, Integer> cityLabels;
	private static String[] cities = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};

	public static void main(String[] args) {
		distances = distanceArray(coords);
		
		// print the distance array if so desired
		/*
		for(int i = 0; i < 14; i++) {
			for (int j=0; j < 14; j++) {
				print(distances[i][j]);
			}
		}
		*/
		
		cityLabels = mapCityLabels();
		
		// some benchmarking code for timing different solution implementations
		NumberFormat nanoFormat = NumberFormat.getNumberInstance();
        long start = System.nanoTime();
        
        /*
         * The different solutions can be uncommented and enabled below. They all print out
         * the best path, worst path, average path distance, and standard deviation followed
         * by data for a histogram of the solutions. 
         */
		//generateRandomSolutions(1_000_000);
        //exhaustiveSearch();
        geneticAlgorithmSolutions(50000, 100);
        //simulatedAnnealingSolutions(1);
		
		long finish = System.nanoTime() - start;
        System.out.println("Took " + nanoFormat.format(finish) + " nanoseconds to execute.");

	}
	
	/**
	 * Ran out of time and never really did get this working properly unfortunately. Oh well. 
	 * @param numSolutions The number of solutions to attempt to find.
	 */
	private static void simulatedAnnealingSolutions(int numSolutions) {
		double temperature = 0.005;
		double coolingRate = .005;
		double best = 10;

		String[] path = generateRandomPath();
		
		int count = 0;
		while (temperature < 10) {
			String[] permutation = randomSwap(path);
			double permutationDistance = calcPathDistance(permutation);
			
			double delta = (permutationDistance - calcPathDistance(path));
			
			
			if (delta >= 0) {
				path = permutation;
				if (permutationDistance < best) 
					best = permutationDistance;
			} else {
				double probability = 1/Math.pow(Math.E, temperature/5);
				double z = Math.pow(Math.E, -delta/temperature);
				print(delta + "\t" + probability + "\t" + z);
				if (Math.random() < probability/z) {
					path = permutation;
				}
			}
			
			temperature += coolingRate;
			count++;
		}
		
		print(best);
		print(calcPathDistance(path) + "\n" + count);
	}
	
	/**
	 * Randomly swap two cities of a path.
	 * @param path The path whose cities should be swapped.
	 * @return The new path with the swapped cities.
	 */
	private static String[] randomSwap(String[] path) {
		String[] newPath = path.clone();
		int i1 = (int) (Math.random() * path.length);
		int i2 = (int) (Math.random() * path.length);
		
		String temp = newPath[i1];
		newPath[i1] = newPath[i2];
		newPath[i2] = temp;
		
		return newPath;
	}

	/**
	 * An implementation of a genetic algorithm geared towards solving the travelling salesman problem.
	 * @param startingPopulation The number of random paths to generate for the starting population.
	 * @param iterations The number of generations to produce.
	 */
	private static void geneticAlgorithmSolutions(int startingPopulation, int iterations) {
		HashMap<String[], Double> population = new HashMap<String[], Double>(startingPopulation);
		double bias = 15;
		
		// generate all the random paths for the starting population
		for (int x=0; x<startingPopulation; x++) {
				String[] path = generateRandomPath();
				population.put(path, 0.0);
		}
		
		// loop until a satisfactory number of generations have been produced
		for(int n=0; n<iterations; n++) {
			HashMap<String[], Double> nextGen = new HashMap<String[], Double>();
			ArrayList<String[]> rouletteWheel = new ArrayList<String[]>(population.size());
			
			/* 
			 * Generate the roulette wheel for the population.
			 * The wheel is weighted by the speed of the path, the top 20% receive 4 slots,
			 * the next 20% receive 3 slots and so forth. The last 20% receive no slots.
			 */
			for (String[] path: population.keySet()) {
				int slots = (int) Math.round(1/calcPathDistance(path)*bias) - 1;
				for (int y=0; y<slots; y++) {
					rouletteWheel.add(path);
				}
			}
			
			// breed the generation 
			for (int z=population.size(); z>0; z--) {
				String[] parent1 = rouletteWheel.get((int) (rouletteWheel.size() * Math.random()));
				String[] parent2 = rouletteWheel.get((int) (rouletteWheel.size() * Math.random()));
				
				// don't breed a parent with itself
				while (pathEquals(parent1, parent2)) {
					parent2 = rouletteWheel.get((int) (rouletteWheel.size() * Math.random()));
				}
				
				int len = coords.length/2;
				String[] child = new String[len];
				int bound1 = (int) Math.random() * len;
				int bound2 = (int) Math.random() * len;
				int startIndex = Math.min(bound1, bound2);
				int endIndex = Math.max(bound1, bound2);
				
				// copy down a randomly sized window of cities from parent 1 into the child
				for (int a=startIndex; a<endIndex; a++) {
					child[a] = parent1[a];
				}
				
				// fill the empty slots with cities from parent 2 where possible
				for (int b=0; b<startIndex; b++) {
					String city = parent2[b];
					if (notIn(city, child)) {
						child[b] = city;
					} else {
						String letter = cities[(int) (Math.random()*14)];
						if(notIn(letter, child)) {
							child[b] = letter;
						}
					}
				}
				
				for (int b=endIndex; b<len; b++) {
					String city = parent2[b];
					if (notIn(city, child)) {
						child[b] = city;
					} else {
						String letter = cities[(int) (Math.random()*14)];
						if(notIn(letter, child)) {
							child[b] = letter;
						}
					}
				}
				
				// provide a chance for the path to mutate
				if (Math.random() < .05) {
					child = randomSwap(child);
				}
				
				nextGen.put(child, calcPathDistance(child));
			}
			
			population = nextGen;
		}
		
		// do some statistics and generate histogram data
		double total = 0;
		double min = 10;
		double max = 0;
		double sumOfSquares = 0;
		int histogramData[] = new int[100];
		double numSolutions = population.size();
		
		// total up the final population
		for (double value: population.values()) {
			total += value;
			sumOfSquares += Math.pow(value, 2);
			
			int index = (int) Math.round(((value - 3) * (12.5)));
			histogramData[index] += 1;
			
			if (value>max) {
				max = value;
			}
			
			if (value<min) {
				min = value;
			}
		}
		
		double average = total/numSolutions;
		print("Min:\t\t\t" + min);
		print("Max:\t\t\t" + max);
		print("Average:\t\t" + average);
		
		
		double stdDev = Math.sqrt((sumOfSquares - Math.pow(average, 2) * numSolutions)/numSolutions);
		print("Standard Deviation:\t" + stdDev);
		print("\n");
		
		int histogramEntries = 0;
		for (int x=0; x<100; x++) {
			print((3 + .08*x) + "\t" + histogramData[x]);
			histogramEntries += histogramData[x];
		}
		
		print(histogramEntries);
	}
	
	/**
	 * Check if a city is not in a path.
	 * @param letter The city to check for.
	 * @param path The incomplete path.
	 * @return True if the city is not in the path. Otherwise, false.
	 */
	private static boolean notIn(String letter, String[] path) {
		for (String character: path) {
			if (character == letter) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Perform an (n-1)! exhaustive search of the travelling salesman problem with the 
	 * cities and coordinates described in the properties of this class.
	 */
	private static void exhaustiveSearch() {
		int n = coords.length/2;
		String[] path = new String[n];
		double total = 0;
		double sumOfSquares = 0;
		long[] histogramData = new long[100];

		int[] countArray = new int[n-1];

		for (int x=0; x<path.length; x++) {
			path[x] = cities[x];
		}
		
		double firstPath = calcPathDistance(path);
		total += firstPath;
		double best = firstPath;
		String[] bestPath = path.clone();
		double worst = firstPath;
		String[] worstPath = path.clone();
		sumOfSquares += Math.pow(firstPath, 2);
		
		// an algorithm similar to heaps algorithm for generating permutations
		int i = 0;
		while (i < n-1) {
			if (countArray[i] < i) {
				if (i % 2 == 0) {
					String temp = path[0];
					path[0] = path[i];
					path[i] = temp;
				} else {
					String temp = path[i];
					path[i] = path[countArray[i]];
					path[countArray[i]] = temp;
				}
				
				// the exhaustive search can be completed in (n-1)! time by always using the same starting point
				path[n-1] = cities[n-1];
				double distance = calcPathDistance(path);
				sumOfSquares += Math.pow(distance, 2);
				
				int index = (int) ((distance - 3) * (12.5));
				histogramData[index] += 1;
				
				if (distance < best) {
					best = distance;
					bestPath = path.clone();
				}
				
				if (distance > worst) {
					worst = distance;
					worstPath = path.clone();
				}
				
				total += distance;
				
				countArray[i] += 1;
				i = 0;
			} else {
				countArray[i] = 0;
				i += 1;
			}
		}
		
		long numSolutions = factorial(n-1);
		double average = total/numSolutions;
		double stdDev = Math.sqrt((sumOfSquares - Math.pow(average, 2) * numSolutions)/numSolutions);
				
		print("Average:\t\t" + average);
		System.out.print("Best:\t\t\t" + best + "\t");
		
		for (int x=0; x<bestPath.length; x++) {
			System.out.print(bestPath[x]);
		}
		print("");
		
		System.out.print("Worst:\t\t\t" + worst + "\t");
		
		for (int y=0; y<worstPath.length; y++) {
			System.out.print(worstPath[y]);
		}
		print("");
		
		print("Standard Deviation:\t" + stdDev);
		print("");
		
		for (int x=0; x<100; x++) {
			print((3 + .08*x) + "\t" + histogramData[x]);
		}

	}

	/**
	 * Render a random path through all of the cities.
	 * @return A string containing a random path through each city without repeats.
	 */
	private static String[] generateRandomPath() {
			LinkedList<String> cities = new LinkedList<String>();
			
			cities.add("a");
			cities.add("b");
			cities.add("c");
			cities.add("d");
			cities.add("e");
			cities.add("f");
			cities.add("g");
			cities.add("h");
			cities.add("i");
			cities.add("j");
			cities.add("k");
			cities.add("l");
			cities.add("m");
			cities.add("n");
			
			String[] path = new String[14];
			
			for (int j=0; j<14; j++) {
				int randInt = (int) Math.floor(Math.random() * cities.size());
				
				path[j] = cities.remove(randInt);
			}
			
			return path;
		}
	
	/**
	 * Prints the results of a random sample of solutions to the travelling salesman problem.
	 * @param numSolutions The number of random paths to generate.
	 */
	private static void generateRandomSolutions(int numSolutions) {
		double total = 0;
		double best = 10;
		double worst = 0;
		double sumOfSquares = 0;
		long[] histogramData = new long[100];
		
		for (int i=0; i<numSolutions; i++) {
			String[] path = generateRandomPath();
			
			double distance = calcPathDistance(path);
			
			if (distance < best) {
				best = distance;
			}
			
			if (distance > worst) {
				worst = distance;
			}
			
			int index = (int) Math.round(((distance - 3) * (12.5)));
			histogramData[index] += 1;
			
			total += distance;
			sumOfSquares += Math.pow(distance, 2);
		}
		
		double average = total/numSolutions;
		double stdDev = Math.sqrt((sumOfSquares - Math.pow(average, 2) * numSolutions)/numSolutions);
				
		System.out.println("Average:\t\t" + average);
		print("Best:\t\t\t" + best);
		print("Worst:\t\t\t" + worst);
		print("Standard Deviation:\t" + stdDev);
		print("\n");
		
		int histogramEntries = 0;
		for (int x=0; x<100; x++) {
			print((3 + .08*x) + "\t" + histogramData[x]);
			histogramEntries += histogramData[x];
		}
		
		print(histogramEntries);
	}

	/**
	 * Calculate the distance of a path using a prepopulated array of distances.
	 * @param path The path whose distance should be calculated.
	 * @return The distance of the path.
	 */
	private static double calcPathDistance(String[] path) {
		double distance = 0;
		
		// get the distance between each city and add it to a running total
		for (int x=0; x<coords.length/2 - 1; x++) {
			double cityDistance = distances[cityLabels.get(path[x])][cityLabels.get(path[x+1])];
			distance += cityDistance;
		}
		
		// return to the starting point
		double lastDistance = distances[cityLabels.get(path[coords.length/2 - 1])][cityLabels.get(path[0])];
		distance += lastDistance;
		
		return distance;
	}
	
	/**
	 * Create a hashmap of city labels so that the letters that represent the cities can be used
	 * to look up values in arrays. 
	 * @return A hashmap of labels and array values.
	 */
	private static HashMap<String, Integer> mapCityLabels() {
		HashMap<String, Integer> cityLabels = new HashMap<String, Integer>();
		
		cityLabels.put("a", 0);
		cityLabels.put("b", 1);
		cityLabels.put("c", 2);
		cityLabels.put("d", 3);
		cityLabels.put("e", 4);
		cityLabels.put("f", 5);
		cityLabels.put("g", 6);
		cityLabels.put("h", 7);
		cityLabels.put("i", 8);
		cityLabels.put("j", 9);
		cityLabels.put("k", 10);
		cityLabels.put("l", 11);
		cityLabels.put("m", 12);
		cityLabels.put("n", 13);
		
		return cityLabels;
	}
	
	/**
	 * The distance formula. This implementation only works on 2D points.
	 * @param x1 The x value of point 1.
	 * @param y1 The y value of point 1.
	 * @param x2 The x value of point 2.
	 * @param y2 The y value of point 2.
	 * @return The distance between points 1 and 2.
	 */
	private static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
	}
	
	/**
	 * Dynamically generate a 2D array containing all of the precalculated distances between cities.
	 * @param coords The coordinates of the cities on an x, y plane.
	 * @return A multidimensional array that can be used to look up the distances.
	 */
	private static double[][] distanceArray(double[] coords) {
		int numCoords = coords.length/2;
		double[][] distanceArray = new double[numCoords][numCoords];
		
		for (int i=0; i < numCoords; i++) {
			for (int j=0; j < numCoords; j++) {
				distanceArray[i][j] = distance(coords[i*2], coords[i*2 + 1], coords[j*2], coords[j*2 + 1]);
			}
		}
		
		return distanceArray;
	}
	
	/**
	 * Calculate the factorial of a number. Hopefully I don't have to explain what "factorial" is. 
	 * @param n The number whose factorial should be computed. 
	 * @return	The factorial of the number. 
	 */
	private static long factorial(long n) {
        long factorial = 1;
        for (int i=1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
	
	/**
	 * Determines whether two paths through the points are the same.
	 * @param p1 The first path to be compared.
	 * @param p2 The second path to be compared.
	 * @return True if they are the same. Otherwise false. 
	 */
	private static boolean pathEquals(String[] p1, String[] p2) {
		for (int x=0; x<p2.length; x++) {
			if (p1[x] != p2[x]) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * A simple method for printing out an array of strings.
	 * @param array The string array to be printed.
	 */
	private static void printStringArray(String[] array) {
		for (String letter: array) {
			System.out.print(letter);
		}
		print("");
	}
	
	/**
	 * I honestly just wrote this method because typing System.out.println() 400 times a day feels like it's giving me arthritis. 
	 * @param o The object to print.
	 */
	private static void print(Object o) {
		System.out.println(o);
	}
	

}
