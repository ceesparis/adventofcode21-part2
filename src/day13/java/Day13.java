package day13.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day13 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer[]> dotMap = new ArrayList<>();
        ArrayList<String[]> instructions = new ArrayList<>();

        File file = new File("src/day13/resources/day13.txt");
        Scanner scan = new Scanner(file);
        int xMax = 0;
        int yMax = 0;
        while (scan.hasNextLine()){
            String nextLine = scan.nextLine();
            if (nextLine == ""){
                while (scan.hasNextLine()){
                    String[] splitString = scan.nextLine().split("=");
                    instructions.add(new String[]{splitString[0].substring(splitString[0].length() - 1), splitString[1]});
                }
                break;
            }
            String[] stringArray = nextLine.split(",");
            int x = Integer.parseInt(stringArray[0]);
            int y = Integer.parseInt(stringArray[1]);
            xMax = Math.max(x, xMax);
            yMax = Math.max(y, yMax);
            Integer[] intArray = {x, y};
            dotMap.add(intArray);
        }

        // sort dots on y coordinates
        Collections.sort(dotMap, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[1].compareTo(o2[1]);
            }
        });

        // make paper
        int curY = 0;
        ArrayList<ArrayList<Integer>> paper = new ArrayList<>();
        for(int y = 0;  y <= yMax; y++){
            ArrayList<Integer> newRow = new ArrayList<>();
            for(int x = 0; x <= xMax; x++){
                int newX = 0;
                for (int i = curY; i<dotMap.size(); i++){
                    Integer[] curDot = dotMap.get(i);
                    if (curDot[1] == y && dotMap.get(curY)[1] != y) curY = i;
                    if(curDot[0] == x && curDot[1] == y) newX = 1;
                }
                newRow.add(newX);
            }
            paper.add(newRow);
        }

//        part one
//        foldHorizontal(paper, 4);
//        int count = 0;
//        for (ArrayList<Integer> line: paper){
//            System.out.println(line);
//            for(int i : line){
//                if (i == 1) count ++;
//            }
////        }
//        System.out.println(count);
        for (String[] instruction: instructions) {
            int foldingLine = Integer.parseInt(instruction[1]);
            if (instruction[0].equals("y")) foldHorizontal(paper, foldingLine);
            else foldVertical(paper, foldingLine);
        }

        ArrayList<StringBuilder> readable = new ArrayList<>();
        for(ArrayList<Integer> line: paper){
            StringBuilder readableLine = new StringBuilder();
            for(int i : line){
                if (i == 0) readableLine.append(".");
                else readableLine.append("#");
            }
            readable.add(readableLine);
        }
        for (StringBuilder line: readable){
            System.out.println(line);
        }
    }

    public static void foldHorizontal(ArrayList<ArrayList<Integer>> paper, int foldingLine){
        ArrayList<ArrayList<Integer>> newTop = new ArrayList<>();
        int mirrorLineDist = 2;
        for(int i = foldingLine+1; i<paper.size(); i++){
            ArrayList<Integer> line = paper.get(i);
            // combine this line with line above the fold (if there is such a line)
            if (i - mirrorLineDist >= 0){
                ArrayList<Integer> mirrorLine = paper.get(i - mirrorLineDist);
                paper.set(i-mirrorLineDist, mergeLinesHorizontal(mirrorLine, line));
            }
            mirrorLineDist+=2;
        }
        for(int i = paper.size()-1; i >= foldingLine; i--){
            paper.remove(i);
        }
    }

    public static void foldVertical(ArrayList<ArrayList<Integer>> paper, int foldingLine){
        for(int i = 0; i<paper.size(); i++){
            mergeLinesVertical(paper.get(i), foldingLine);
        }
    }


    public static ArrayList<Integer> mergeLinesHorizontal(ArrayList<Integer> line1, ArrayList<Integer> line2){
        for (int i = 0; i<line1.size(); i++){
            if (line2.get(i) == 1) line1.set(i, 1);
        }
        return line1;
    }

    public static ArrayList<Integer> mergeLinesVertical(ArrayList<Integer> line1, int foldLine){
       int mirrorPlace = 2;
       for(int i = foldLine -1; i >= 0; i--){
           if(i + mirrorPlace >= line1.size()) break;
           if(line1.get(i+mirrorPlace) == 1) line1.set(i, 1);
           mirrorPlace+=2;
       }
       for(int i = line1.size()-1; i >= foldLine; i--){
           line1.remove(i);
       }
        return line1;
    }
}
