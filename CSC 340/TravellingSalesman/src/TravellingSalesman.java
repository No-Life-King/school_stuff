import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TravellingSalesman {
	
	private static double[] coords = {0.134643599, 0.644606626,	//a
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
		
		/* Print the distance array if needed.
		for(int i = 0; i < 14; i++) {
			for (int j=0; j < 14; j++) {
				print(distances[i][j]);
			}
		}
		*/
		
		cityLabels = mapCityLabels();
		
		NumberFormat nanoFormat = NumberFormat.getNumberInstance();
        long start = System.nanoTime();
        
		//generateRandomSolutions(1_000_000);
        //exhaustiveSearch();
        geneticAlgorithmSolutions(75000, 75);
		
		long finish = System.nanoTime() - start;
        System.out.println("Took " + nanoFormat.format(finish) + " nanoseconds to execute.");

	}
	
	private static void geneticAlgorithmSolutions(int startingPopulation, int iterations) {
		HashMap<String[], Double> population = new HashMap<String[], Double>(startingPopulation);
		double bias = 15;
		
		for (int x=0; x<startingPopulation; x++) {
				String[] path = generateRandomPath();
				population.put(path, 0.0);
		}
		
		for(int n=0; n<iterations; n++) {
			HashMap<String[], Double> nextGen = new HashMap<String[], Double>();
			ArrayList<String[]> rouletteWheel = new ArrayList<String[]>(population.size());
			
			for (String[] path: population.keySet()) {
				int slots = (int) Math.round(1/calcPathDistance(path)*bias);
				for (int y=0; y<slots; y++) {
					rouletteWheel.add(path);
				}
			}
			
			for (int z=population.size(); z>0; z--) {
				String[] parent1 = rouletteWheel.get((int) (rouletteWheel.size() * Math.random()));
				String[] parent2 = rouletteWheel.get((int) (rouletteWheel.size() * Math.random()));
				
				while (pathEquals(parent1, parent2)) {
					parent2 = rouletteWheel.get((int) (rouletteWheel.size() * Math.random()));
				}
				
				int len = coords.length/2;
				String[] child = new String[len];
				int bound1 = (int) Math.random() * len;
				int bound2 = (int) Math.random() * len;
				int startIndex = Math.min(bound1, bound2);
				int endIndex = Math.max(bound1, bound2);
				
				for (int a=startIndex; a<endIndex; a++) {
					child[a] = parent1[a];
				}
				
				for (int b=0; b<startIndex; b++) {
					String city = parent2[b];
					if (notIn(city, child)) {
						child[b] = city;
					} else {
						for (String letter: cities) {
							if(notIn(letter, child)) {
								child[b] = letter;
							}
						}
					}
				}
				
				for (int b=endIndex; b<len; b++) {
					String city = parent2[b];
					if (notIn(city, child)) {
						child[b] = city;
					} else {
						for (String letter: cities) {
							if(notIn(letter, child)) {
								child[b] = letter;
							}
						}
					}
				}
				
				if (Math.random() < .05) {
					// mutate
					int i1 = (int) (Math.random() * child.length);
					int i2 = (int) (Math.random() * child.length);
					
					String temp = child[i1];
					child[i1] = child[i2];
					child[i2] = temp;
				}
				
				nextGen.put(child, calcPathDistance(child));
			}
			
			population = nextGen;
		}
		
		
		double total = 0;
		double min = 10;
		double max = 0;
		double sumOfSquares = 0;
		int histogramData[] = new int[100];
		double numSolutions = population.size();
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
	
	private static boolean notIn(String letter, String[] path) {
		for (String character: path) {
			if (character == letter) {
				return false;
			}
		}
		
		return true;
	}

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

	private static double calcPathDistance(String[] path) {
		double distance = 0;
		
		for (int x=0; x<coords.length/2 - 1; x++) {
			double cityDistance = distances[cityLabels.get(path[x])][cityLabels.get(path[x+1])];
			distance += cityDistance;
		}
		
		double lastDistance = distances[cityLabels.get(path[coords.length/2 - 1])][cityLabels.get(path[0])];
		distance += lastDistance;
		return distance;
	}

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

	private static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
	}
	
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
	
	public static long factorial(long n) {
        long factorial = 1;
        for (int i=1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
	
	private static boolean pathEquals(String[] p1, String[] p2) {
		for (int x=0; x<p2.length; x++) {
			if (p1[x] != p2[x]) {
				return false;
			}
		}
		
		return true;
	}
	
	private static void printStringArray(String[] array) {
		for (String letter: array) {
			System.out.print(letter);
		}
		print("");
	}
	
	private static void print(Object o) {
		System.out.println(o);
	}
	

}
