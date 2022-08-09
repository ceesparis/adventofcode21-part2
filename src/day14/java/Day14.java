package day14.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day14 {
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src/day14/resources/test.txt");
        Scanner scan = new Scanner(file);

        // make variables
        StringBuilder polyTemplate = new StringBuilder();
        HashMap<String, String> pIRules = new HashMap<>();
        HashMap<String, Long> pairCount = new HashMap<>();
        String polyString = scan.nextLine();
        polyTemplate.append(polyString);
        scan.nextLine();
        while(scan.hasNextLine()){
            String[] rule = scan.nextLine().split(" -> ");
            pIRules.put(rule[0], rule[1]);
        }

        for(int i = 0; i<polyString.length()-1; i++){
            String curPair = polyString.substring(i, i+2);
            if(pairCount.containsKey(curPair)){
                pairCount.replace(curPair, pairCount.get(curPair)+1);
            } else pairCount.put(curPair, 1L);
        }

        // part one
        // grow polymer
//        for(int i = 0; i<10; i++){
//            polyTemplate = pairInsertion(polyTemplate, pIRules);
//        }
//        // get hashmap with count of each element
//        HashMap<Character, Integer> countMap = findQuantities(polyTemplate);
//
//        // get result
//        long result = getResult(countMap);
//        System.out.println(result);

        // part two
//        for(int i = 0; i<10; i++){
//            pairCount =
//        }
        for(int i = 0; i<40; i++){
            pairCount = pairInsertionMap(pairCount, pIRules);
        }


        HashMap<String, Integer> singleLetterMap = countPairMap(pairCount);
        long result2 = getResult(singleLetterMap);
        System.out.println(result2);



    }

    public static StringBuilder pairInsertion(StringBuilder currentPoly, HashMap pIRules){
        StringBuilder growingPoly = new StringBuilder();
        for(int i = 0; i < currentPoly.length(); i++){
            growingPoly.append(currentPoly.charAt(i));
            if(i > currentPoly.length()-2) continue;
            String pair = currentPoly.substring(i, i+2);
            if(pIRules.containsKey(pair)){
                growingPoly.append(pIRules.get(pair).toString());
            }
        }
        return growingPoly;
    }

    public static HashMap findQuantities(StringBuilder finalPoly){
        HashMap<Character, Integer> countMap = new HashMap<>();
        for(int i = 0; i<finalPoly.length(); i++){
            Character curElement = finalPoly.charAt(i);
            if (countMap.containsKey(curElement)){
                Integer old = countMap.get(curElement);
                countMap.replace(curElement, old+1);
            } else{
                countMap.put(curElement, 1);
            }
        }
        return countMap;
    }

    public static long getResult(HashMap countMap){
        Long max = (Long) Collections.max(countMap.entrySet(), Map.Entry.comparingByValue()).getValue();
        Long min = (Long) Collections.min(countMap.entrySet(), Map.Entry.comparingByValue()).getValue();
        return max/2 - min/2;
    }

    public static HashMap<String, Long> pairInsertionMap(HashMap countMap, HashMap pIRules){
        HashMap<String, Long> newPolyPairs = new HashMap<>();
        Iterator<Map.Entry<String, Long>> it = countMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Long> pair = it.next();
            String currentPair = pair.getKey();
            Long currentQuantity = pair.getValue();
            if (currentQuantity < 0){
                currentQuantity = currentQuantity * -1;
            }
            if(!pIRules.containsKey(currentPair)) continue;
            String inBetween =String.valueOf(pIRules.get(currentPair));
            String newCombo1 = String.valueOf(currentPair.charAt(0)) + inBetween;
            String newCombo2 = inBetween + String.valueOf(currentPair.charAt(1));
            addNewPairToCountMap(newPolyPairs, newCombo1, currentQuantity);
            addNewPairToCountMap(newPolyPairs, newCombo2, currentQuantity);
        }
        return newPolyPairs;
    }

    public static void addNewPairToCountMap(HashMap countMap, String combo, long quantity){
        if (!countMap.containsKey(combo)) countMap.put(combo, quantity);
        else{
            long oldValue = (long) countMap.get(combo);
            countMap.replace(combo, oldValue + quantity);
        }
    }

    public static HashMap countPairMap(HashMap countMap){
        HashMap<String, Long> singleLetterMap = new HashMap<>();
        Iterator<Map.Entry<String, Long>> it = countMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Long> pair = it.next();
            String currentPair = pair.getKey();
            Long currentQuantity = pair.getValue();
            addNewPairToCountMap(singleLetterMap, currentPair.substring(0, 1), currentQuantity);
            addNewPairToCountMap(singleLetterMap, currentPair.substring(1), currentQuantity);
        }
        return singleLetterMap;
    }
}
