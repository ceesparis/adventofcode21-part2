package day12.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12 {
    public static void main(String[] args) throws FileNotFoundException {
        List<String[]> graph = new ArrayList<>();
        File file = new File("src/day12/resources/day12.txt");
        Scanner scan = new Scanner(file);
        while(scan.hasNextLine()){
            String[] caveConnectList = scan.nextLine().split("-");
            graph.add(caveConnectList);
        }

        Solution solution = new Solution();
        List<List<String>> allPaths = solution.allPaths(graph);
        System.out.println(allPaths.size());

        // part two
        // get all tiny caves in an arrayList
        int total = 0;
        List<List<String>> totalSolutions = new ArrayList<>();
        ArrayList<String> tinyCaves = new ArrayList<>();
        for (String[] caveConnect: graph){
            if (caveConnect[0].toLowerCase().equals(caveConnect[0]) && !tinyCaves.contains(caveConnect[0])) tinyCaves.add(caveConnect[0]);
            if (caveConnect[1].toLowerCase().equals(caveConnect[1]) && !tinyCaves.contains(caveConnect[1])) tinyCaves.add(caveConnect[1]);
        }
        tinyCaves.remove("start");
        tinyCaves.remove("end");
        for(String tinyCave: tinyCaves){
            System.out.println(tinyCave);
            List<List<String>> allPathsTinyCave = solution.allPathsPartTwo(graph, tinyCave);
            for(List<String> path: allPathsTinyCave){
                totalSolutions.add(path);
            }
            total += allPathsTinyCave.size();

        }
        Set<List<String>> uniquePaths = new HashSet<List<String>>(totalSolutions);
        System.out.println(total);
        System.out.println(uniquePaths.size());
    }

}
