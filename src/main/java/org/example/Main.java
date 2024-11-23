package org.example;

import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        firstTask();
        secondTask();
        thirdTask();
    }

    public static void firstTask() {
        Scanner scanner = new Scanner(System.in);

        // Input n
        System.out.print("Enter the number of pairs of parentheses: ");
        int n = scanner.nextInt();

        // Check if n is negative
        if (n < 0) {
            throw new IllegalArgumentException("n must be a non-negative");
        }

        // Calculate and print result
        BigInteger result = countParentheses(n);
        System.out.println("Valid expressions: " + result);
    }

    public static BigInteger countParentheses(int n) {
        if (n == 0) return BigInteger.ONE;

        // Create an array to store Catalan numbers
        BigInteger[] catalan = new BigInteger[n + 1];

        // Initialize first number (C_0 = 1)
        catalan[0] = BigInteger.ONE;

        // Use formula to calculate all Catalan numbers
        for (int i = 1; i <= n; i++) {
            BigInteger current = BigInteger.ZERO;
            for (int j = 0; j < i; j++) {
                current = current.add(catalan[j].multiply(catalan[i - j - 1]));
            }
            catalan[i] = current;
        }
        // Return the result
        return catalan[n];
    }

    public static void secondTask() {
        Map<String, City> graph = createGraph();

        // City names for example
        String startCity = "torun";
        String endCity = "gdansk";

        // Calculate the minimum cost
        int result = findMinimumCost(graph, startCity, endCity);
        if (result != -1) {
            System.out.println("Minimum cost: " + result);
        } else {
            System.out.println("No path found");
        }
    }

    private static Map<String, City> createGraph() {
        Map<String, City> graph = new HashMap<>();

        // Adding cities and their neighbors
        City gdansk = new City("gdansk");
        gdansk.addNeighbor(new Neighbor("bydgoszcz", 1));
        gdansk.addNeighbor(new Neighbor("torun", 3));

        City bydgoszcz = new City("bydgoszcz");
        bydgoszcz.addNeighbor(new Neighbor("gdansk", 1));
        bydgoszcz.addNeighbor(new Neighbor("torun", 1));
        bydgoszcz.addNeighbor(new Neighbor("warszawa", 4));

        City torun = new City("torun");
        torun.addNeighbor(new Neighbor("gdansk", 3));
        torun.addNeighbor(new Neighbor("bydgoszcz", 1));
        torun.addNeighbor(new Neighbor("warszawa", 1));

        City warszawa = new City("warszawa");
        warszawa.addNeighbor(new Neighbor("bydgoszcz", 4));
        warszawa.addNeighbor(new Neighbor("torun", 1));

        // Adding cities to the graph
        graph.put("gdansk", gdansk);
        graph.put("bydgoszcz", bydgoszcz);
        graph.put("torun", torun);
        graph.put("warszawa", warszawa);

        return graph;
    }

    public static int findMinimumCost(Map<String, City> graph, String start, String end) {
        PriorityQueue<Neighbor> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        pq.add(new Neighbor(start, 0));

        while (!pq.isEmpty()) {
            Neighbor current = pq.poll();

            // Check if we have reached the destination city
            if (current.name.equals(end)) return current.cost;

            // Traverse the neighbors of the current city and update their costs
            for (Neighbor neighbor : graph.get(current.name).getNeighbors()) {
                pq.add(new Neighbor(neighbor.name, current.cost + neighbor.cost));
            }
        }

        return -1; // Return -1 if there is no path found
    }

    public static void thirdTask() {
        // Calculate 100!
        BigInteger fact = BigInteger.ONE;
        for (int i = 100; i > 0; i--) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        //Convert to string and use stream to sum the digits
        int sum = fact.toString().chars()
                .map(c -> c - '0')
                .sum();

        // Print the result
        System.out.println("The sum: " + sum);
    }

    static class City {
        private final String name;
        private final List<Neighbor> neighbors;

        public City(String name) {
            this.name = name;
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(Neighbor neighbor) {
            neighbors.add(neighbor);
        }

        public List<Neighbor> getNeighbors() {
            return neighbors;
        }
    }

    static class Neighbor {
        private final String name;
        private final int cost;

        public Neighbor(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }
    }
}