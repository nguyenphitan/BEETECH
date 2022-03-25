/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectionperformance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class CollectionPerformance {
    // Test Performance method:
    static final long MAX_VALUE = 1000000;
    
    // List
    public static void testListPerformance(List list) {
        long startTime = System.currentTimeMillis();
        long i = 0;
        while(i < MAX_VALUE) {
            list.add(i);
            list.get(0);
            list.remove(0);
            i++;
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(list.getClass() + ", Time: " + endTime + " miliseconds");
    }

    // Stack
    public static void testStackPerformance(Stack list) {
        long startTime = System.currentTimeMillis();
        long i = 0;
        while(i < MAX_VALUE) {
            list.push(i);
            list.pop();
            list.search(0);
            i++;
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(list.getClass() + ", Time: " + endTime + " miliseconds");
    }
    
    // Set
    public static void testSetPerformance(Set list) {
    	long startTime = System.currentTimeMillis();
        long i = 0;
        while(i < MAX_VALUE) {
            list.add(i);
            list.contains(i);
            list.remove(i);
            i++;
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(list.getClass() + ", Time: " + endTime + " miliseconds");
    }

    public static void main(String[] args) {
        // List
        testListPerformance(new ArrayList());
        testListPerformance(new Vector());
        testListPerformance(new LinkedList());
        
        // Stack
        testStackPerformance(new Stack());
        
        // Set
        testSetPerformance(new HashSet());
        testSetPerformance(new LinkedHashSet());
        
    }
    
}

