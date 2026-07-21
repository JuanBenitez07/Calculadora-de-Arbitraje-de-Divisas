package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Detector {

    // Lógica compartida: corre Bellman-Ford y devuelve el ciclo reconstruido (o null si no hay)
    private List<Integer> runBellmanFordAndFindCycle(Grafo graph) {

        int n = graph.size();
        List<Arista> edges = graph.getEdges();

        double[] distance = new double[n];
        int[] parent = new int[n];

        Arrays.fill(distance, 0);
        Arrays.fill(parent, -1);

        int lastUpdated = -1;

        for (int i = 0; i < n; i++) {
            lastUpdated = -1;
            for (Arista edge : edges) {
                int u = edge.getSource();
                int v = edge.getDestination();
                if (distance[u] + edge.getWeight() < distance[v]) {
                    distance[v] = distance[u] + edge.getWeight();
                    parent[v] = u;
                    lastUpdated = v;
                }
            }
        }

        if (lastUpdated == -1) {
            return null; // no hay ciclo negativo
        }

        int cycleNode = lastUpdated;
        for (int i = 0; i < n; i++) {
            cycleNode = parent[cycleNode];
        }

        List<Integer> cycle = new ArrayList<>();
        int current = cycleNode;

        do {
            cycle.add(current);
            current = parent[current];
        } while (current != cycleNode);

        cycle.add(cycleNode);
        Collections.reverse(cycle);

        return cycle;
    }

    public List<Integer> detectCycle(Grafo graph) {
        return runBellmanFordAndFindCycle(graph);
    }

    public Results detect(Grafo graph) {
        List<Integer> cycle = runBellmanFordAndFindCycle(graph);
        if (cycle == null) {
            return new Results(false, null);
        }
        return new Results(true, cycle);
    }
}