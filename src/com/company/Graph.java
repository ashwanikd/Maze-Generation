package com.company;

import java.util.*;

public class Graph {
    int x;
    int y;
    LinkedList<Point> graph=new LinkedList<Point>();
    Graph(int xcoordinate,int ycoordinate){
        x=xcoordinate;
        y=ycoordinate;
    }
    void addVertex(Point p) {
        graph.add(p);
    }
    void remove(Point p) {
        graph.remove(p);
    }
    Point getVertex(int index) {
        return graph.get(index);
    }
    int getSize() {
        return graph.size();
    }
    LinkedList<Point> getList(){
        return graph;
    }
}