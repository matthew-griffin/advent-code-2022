package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 implements Puzzle {
    static final int MAX_TIME = 30;
    static final Pattern valvePattern = Pattern.compile("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (\\w+(?:, \\w+)*)");
    static final Pattern splitPattern = Pattern.compile(", ");

    static class Tunnel {
        private String destination;
        private int cost = 1;

        Tunnel(String destination) {
            this.destination = destination;
        }

        void combineTunnel(Tunnel other) {
            destination = other.destination;
            cost += other.cost;
        }

        @Override
        public String toString() {
            return "Tunnel{" +
                    "destination='" + destination + '\'' +
                    ", cost=" + cost +
                    '}';
        }
    }


    static class Node {
        String name;
        int flowRate;
        List<Tunnel> tunnels;


        public Node(String name, int flowRate, List<Tunnel> tunnels) {
            this.name = name;
            this.flowRate = flowRate;
            this.tunnels = tunnels;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    ", flowRate=" + flowRate +
                    ", tunnels=" + tunnels +
                    '}';
        }
    }

    record NodeAndDistance(Node node, int distance) {

    }

    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {

        Map<String, Node> nodes = valvePattern.matcher(inputText).results()
                .map(match -> {
                    var name = match.group(1);
                    var flowRate = Integer.parseInt(match.group(2));
                    var tunnels = splitPattern.splitAsStream(match.group(3))
                            .map(Tunnel::new)
                            .toList();
                    var node = new Node(name, flowRate, tunnels);
                    System.out.println(node);
                    return new AbstractMap.SimpleEntry<>(name, node);
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        for (var node : nodes.values()) {
            if (node.flowRate == 0) {
                int numTunnels = node.tunnels.size();
                if (numTunnels == 1) {
                    nodes.get(node.tunnels.get(0).destination).tunnels.removeIf(t -> t.destination.equals(node.name));
                    continue;
                }
                if (numTunnels == 2) {
                    var tunnel1 = node.tunnels.get(0);
                    var tunnel2 = node.tunnels.get(1);
                    nodes.get(tunnel1.destination).tunnels.stream()
                            .filter(tunnel -> tunnel.destination.equals(node.name)).findFirst()
                            .ifPresent(tunnel -> tunnel.combineTunnel(tunnel2));
                    nodes.get(tunnel2.destination).tunnels.stream()
                            .filter(tunnel -> tunnel.destination.equals(node.name)).findFirst()
                            .ifPresent(tunnel -> tunnel.combineTunnel(tunnel1));
                }
            }
        }
        nodes.entrySet()
                .removeIf(entry -> entry.getValue().flowRate == 0 && entry.getValue().tunnels.size() < 3);

        System.out.println("After Simplification:");
        nodes.values().forEach(System.out::println);

        var currentNode = nodes.get("AA");
        var currentPressureRelease = 0;
        var totalPressureRelease = 0;
        var currentTime = 0;
        var bestTarget = getRoutesToNodes(currentNode, nodes).stream()
                .min(Comparator.comparingDouble(node -> node.distance / (double) node.node.flowRate))
                .orElse(null);

        while (bestTarget != null && currentTime + bestTarget.distance < MAX_TIME) {
            currentTime += bestTarget.distance;
            totalPressureRelease += currentPressureRelease * bestTarget.distance;

            if (currentTime + 1 > MAX_TIME) {
                break;
            }
            currentTime++;
            totalPressureRelease += currentPressureRelease;
            currentPressureRelease += bestTarget.node.flowRate;
            currentNode = bestTarget.node;
            System.out.println(bestTarget);
            currentNode.flowRate = 0;
            bestTarget = getRoutesToNodes(currentNode, nodes).stream()
                    .min(Comparator.comparingDouble(node -> node.distance / (double) node.node.flowRate))
                    .orElse(null);
        }
        var remaining = MAX_TIME - currentTime;
        totalPressureRelease += remaining * currentPressureRelease;

        return new PuzzleResult(Integer.toString(totalPressureRelease),"");
    }

    private List<NodeAndDistance> getRoutesToNodes(Node startNode, Map<String, Node> nodes) {
        var costSoFar = new HashMap<String, Integer>();
        costSoFar.put(startNode.name, 0);

        var frontier = new PriorityQueue<String>(Comparator.comparingInt(costSoFar::get));
        frontier.add(startNode.name);

        while (!frontier.isEmpty()) {
            var currentName = frontier.poll();

            var currentNode = nodes.get(currentName);
            for (var neighbour : currentNode.tunnels) {
                var newCost = costSoFar.get(currentName) + neighbour.cost;
                var destination = neighbour.destination;
                if (!costSoFar.containsKey(destination) || newCost < costSoFar.get(destination)) {
                    costSoFar.put(destination, newCost);
                    frontier.add(destination);
                }
            }
        }

        return costSoFar.entrySet().stream()
                .map(entry -> new NodeAndDistance(nodes.get(entry.getKey()), entry.getValue()))
                .filter(n -> n.node.flowRate > 0 && n.distance > 0)
                .toList();
    }
}
