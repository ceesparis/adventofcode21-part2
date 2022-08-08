package day12.java;

import java.util.*;

public class Solution {
    public List<List<String>> allPaths(List<String[]> graph){
        List<List<String>> result = new ArrayList<>();
        Queue<List<String>> queue = new LinkedList<>();
        queue.add(Arrays.asList("start"));
        String goalNode = "end";
        while(!queue.isEmpty()){
            List<String> path = queue.poll();
//            System.out.println(path);
            String lastNode = path.get(path.size()-1);
            if (lastNode.equals(goalNode)){
                result.add(new ArrayList<>(path));
            } else{
                String[] neighbours = getNeighbours(graph, path, lastNode);
                for (String neighbour: neighbours){
                    List<String> list = new ArrayList<>(path);
                    list.add(neighbour);
                    queue.add(list);
                }
            }

        }
        return result;
    }

    public List<List<String>> allPathsPartTwo(List<String[]> graph, String specialTinyCave){
        List<List<String>> result = new ArrayList<>();
        Queue<List<String>> queue = new LinkedList<>();
        queue.add(Arrays.asList("start"));
        String goalNode = "end";
        while(!queue.isEmpty()){
            List<String> path = queue.poll();
//            System.out.println(path);
            String lastNode = path.get(path.size()-1);
            if (lastNode.equals(goalNode)){
                result.add(new ArrayList<>(path));
            } else{
                String[] neighbours = getNeighboursPartTwo(graph, path, lastNode, specialTinyCave);
                for (String neighbour: neighbours){
                    List<String> list = new ArrayList<>(path);
                    list.add(neighbour);
                    queue.add(list);
                }
            }

        }
        return result;
    }

    public static String[] getNeighbours(List<String[]> graph, List<String> path, String currentNode){
        ArrayList<String> neighbours = new ArrayList<>();
        for(String[] connect: graph){
            if(connect[0].equals(currentNode) && !connect[1].equals("start")){
                if (!(connect[1].toLowerCase()==connect[1] && path.contains(connect[1]) && !neighbours.contains(connect[1])))neighbours.add(connect[1]);
            }
            if(connect[1].equals(currentNode) && !connect[0].equals("start")){
                if (!(connect[0].toLowerCase()==connect[0] && path.contains(connect[0]) &&!neighbours.contains(connect[0]) ))neighbours.add(connect[0]);
            }
        }

        String[] arr = new String[neighbours.size()];
        return neighbours.toArray(arr);
    }

    public static String[] getNeighboursPartTwo(List<String[]> graph, List<String> path, String currentNode, String specialTinyCave){
        ArrayList<String> neighbours = new ArrayList<>();
        for(String[] connect: graph){
            if(connect[0].equals(currentNode) && !connect[1].equals("start")){
                if(connect[1].equals(specialTinyCave)){
                    int occurrences = Collections.frequency(path, specialTinyCave);
                    if (occurrences < 2) neighbours.add(connect[1]);
                }
                if (!(connect[1].toLowerCase()==connect[1] && path.contains(connect[1]) && !neighbours.contains(connect[1]))) neighbours.add(connect[1]);
            }
            if(connect[1].equals(currentNode) && !connect[0].equals("start")){
                if(connect[0].equals(specialTinyCave)){
                    int occurrences = Collections.frequency(path, specialTinyCave);
                    if (occurrences < 2) neighbours.add(connect[0]);
                }
                if (!(connect[0].toLowerCase()==connect[0] && path.contains(connect[0]) &&!neighbours.contains(connect[0]) ))neighbours.add(connect[0]);
            }
        }
        String[] arr = new String[neighbours.size()];
        return neighbours.toArray(arr);
    }
}
