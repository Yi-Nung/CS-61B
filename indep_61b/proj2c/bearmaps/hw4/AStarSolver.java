package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>  {
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private ExtrinsicMinPQ<Vertex> pq;
    private AStarGraph<Vertex> graph;
    private Vertex goal;
    private Vertex source;
    private SolverOutcome outcome;
    private double solutionWeight = 0;
    private LinkedList<Vertex> solution = new LinkedList<>();
    private double timeSpent;
    private int numStates = 0;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();

        pq = new ArrayHeapMinPQ<>();
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        graph = input;
        goal = end;
        source = start;

        pq.add(source, graph.estimatedDistanceToGoal(source, goal));
        distTo.put(source, 0.0);

        while (sw.elapsedTime() < timeout) {
            if (pq.size() == 0) {
                timeSpent = sw.elapsedTime();
                outcome = SolverOutcome.UNSOLVABLE;
                return;
            }

            if (pq.getSmallest().equals(goal)) {
                Vertex curr = goal;
                while (!curr.equals(source)) {
                    solution.addFirst(curr);
                    curr = edgeTo.get(curr);
                }
                solution.addFirst(source);
                solutionWeight = distTo.get(goal);
                timeSpent = sw.elapsedTime();
                outcome = SolverOutcome.SOLVED;
                return;
            }

            Vertex p = pq.removeSmallest();
            numStates += 1;
            List<WeightedEdge<Vertex>> neighborEdges = graph.neighbors(p);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e);
            }
        }
        timeSpent = sw.elapsedTime();
        outcome = SolverOutcome.TIMEOUT;
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q) || distTo.get(p) + w < distTo.get(q)) {
            edgeTo.put(q, p);
            distTo.put(q, distTo.get(p) + w);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + graph.estimatedDistanceToGoal(q, goal));
            } else {
                pq.add(q, distTo.get(q) + graph.estimatedDistanceToGoal(q, goal));
            }
        }
    }
    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return numStates;
    }
    public double explorationTime() {
        return timeSpent;
    }
}
