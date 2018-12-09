package com.company;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.LinkedList;

public class Kruskal {
    Frame f=new Frame("Maze Generator");
    Kruskal.MyCanvas c=new Kruskal.MyCanvas();
    Kruskal(){
        c.setBackground(Color.BLUE);
        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent windowevent) {
                System.exit(0);
            }
        });
        f.setLayout(new FlowLayout());
        f.setSize(1300, 800);
        c.setSize(1000,700);
        f.add(c);
        f.setVisible(true);
        c.startRendering();
        c.dfs();
        c.stopRendering();
    }
    class MyCanvas extends Canvas{
        int h,o;
        Kruskal.MyCanvas.paintagain p;

        LinkedList<Point> visited = new LinkedList<Point>();
        void startRendering() {
            p=new Kruskal.MyCanvas.paintagain();
        }
        void stopRendering() {
            p.stop();
        }
        boolean[][] vector=new boolean[100][70];
        LinkedList<Edge> edges = new LinkedList<Edge>();
        Graph[][] graph=new Graph[50][35];
        Graph[][] resgraph=new Graph[50][35];
        MyCanvas() {
            for(int i=0;i<100;i++) {
                for(int j=0;j<70;j++) {
                    vector[i][j]=false;
                }
            }
            for(int i=0;i<50;i++) {
                for(int j=0;j<35;j++){
                    graph[i][j]=new Graph(i,j);
                    resgraph[i][j]=new Graph(i,j);
                    if(i==0) {
                        if(j==0) {
                            edges.add(new Edge(new Point(i,j),new Point(1,0)));
                            graph[i][j].addVertex(new Point(1,0));
                            edges.add(new Edge(new Point(i,j),new Point(0,1)));
                            graph[i][j].addVertex(new Point(0,1));
                        }else if(j==34) {
                            edges.add(new Edge(new Point(i,j),new Point(1,34)));
                            graph[i][j].addVertex(new Point(1,34));
                            edges.add(new Edge(new Point(i,j),new Point(0,33)));
                            graph[i][j].addVertex(new Point(0,33));
                        }else {
                            edges.add(new Edge(new Point(i,j),new Point(0,j-1)));
                            graph[i][j].addVertex(new Point(0,j-1));
                            edges.add(new Edge(new Point(i,j),new Point(0,j+1)));
                            graph[i][j].addVertex(new Point(0,j+1));
                            edges.add(new Edge(new Point(i,j),new Point(1,j)));
                            graph[i][j].addVertex(new Point(1,j));
                        }
                    }else if(i==49) {
                        if(j==0) {
                            edges.add(new Edge(new Point(i,j),new Point(48,0)));
                            graph[i][j].addVertex(new Point(48,0));
                            edges.add(new Edge(new Point(i,j),new Point(49,1)));
                            graph[i][j].addVertex(new Point(49,1));
                        }else if(j==34) {
                            edges.add(new Edge(new Point(i,j),new Point(49,33)));
                            graph[i][j].addVertex(new Point(49,33));
                            edges.add(new Edge(new Point(i,j),new Point(48,34)));
                            graph[i][j].addVertex(new Point(48,34));
                        }else {
                            edges.add(new Edge(new Point(i,j),new Point(49,j-1)));
                            graph[i][j].addVertex(new Point(49,j-1));
                            edges.add(new Edge(new Point(i,j),new Point(49,j+1)));
                            graph[i][j].addVertex(new Point(49,j+1));
                            edges.add(new Edge(new Point(i,j),new Point(48,j)));
                            graph[i][j].addVertex(new Point(48,j));
                        }
                    }else if(j==0) {
                        if(i==0) {
                            edges.add(new Edge(new Point(i,j),new Point(1,0)));
                            graph[i][j].addVertex(new Point(1,0));
                            edges.add(new Edge(new Point(i,j),new Point(0,1)));
                            graph[i][j].addVertex(new Point(0,1));
                        }else if(i==49) {
                            edges.add(new Edge(new Point(i,j),new Point(48,0)));
                            graph[i][j].addVertex(new Point(48,0));
                            edges.add(new Edge(new Point(i,j),new Point(49,1)));
                            graph[i][j].addVertex(new Point(49,1));
                        }else {
                            edges.add(new Edge(new Point(i,j),new Point(i-1,j)));
                            graph[i][j].addVertex(new Point(i-1,j));
                            edges.add(new Edge(new Point(i,j),new Point(i+1,j)));
                            graph[i][j].addVertex(new Point(i+1,j));
                            edges.add(new Edge(new Point(i,j),new Point(i,j+1)));
                            graph[i][j].addVertex(new Point(i,j+1));
                        }
                    }else if(j==34) {
                        if(i==0) {
                            edges.add(new Edge(new Point(i,j),new Point(1,34)));
                            graph[i][j].addVertex(new Point(1,34));
                            edges.add(new Edge(new Point(i,j),new Point(0,33)));
                            graph[i][j].addVertex(new Point(0,33));
                        }else if(i==49) {
                            edges.add(new Edge(new Point(i,j),new Point(49,33)));
                            graph[i][j].addVertex(new Point(49,33));
                            edges.add(new Edge(new Point(i,j),new Point(48,34)));
                            graph[i][j].addVertex(new Point(48,34));
                        }else {
                            edges.add(new Edge(new Point(i,j),new Point(i-1,j)));
                            graph[i][j].addVertex(new Point(i-1,j));
                            edges.add(new Edge(new Point(i,j),new Point(i+1,j)));
                            graph[i][j].addVertex(new Point(i+1,j));
                            edges.add(new Edge(new Point(i,j),new Point(i,j-1)));
                            graph[i][j].addVertex(new Point(i,j-1));
                        }
                    }else {
                        edges.add(new Edge(new Point(i,j),new Point(i,j-1)));
                        graph[i][j].addVertex(new Point(i,j-1));
                        edges.add(new Edge(new Point(i,j),new Point(i-1,j)));
                        graph[i][j].addVertex(new Point(i-1,j));
                        edges.add(new Edge(new Point(i,j),new Point(i,j+1)));
                        graph[i][j].addVertex(new Point(i,j+1));
                        edges.add(new Edge(new Point(i,j),new Point(i+1,j)));
                        graph[i][j].addVertex(new Point(i+1,j));
                    }
                }
            }
        }
        boolean allvisited(){
            for(int i=0;i<50;i++){
                for(int j=0;j<35;j++){
                    if(!vector[i*2][j*2]){
                        return false;
                    }
                }
            }
            return true;
        }
        URL url = getClass().getResource("beep.wav");
        AudioClip clip = Applet.newAudioClip(url);
        void dfs() {
            while(!allvisited()){
                int r=genRandom(edges.size()-1);
                if(!checkequal(getUnionSet(edges.get(r).p1),getUnionSet(edges.get(r).p2))){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int x1 = edges.get(r).p1.x;
                    int y1 = edges.get(r).p1.y;
                    int x2 = edges.get(r).p2.x;
                    int y2 = edges.get(r).p2.y;
                    vector[x1*2][y1*2] = true;
                    vector[x2*2][y2*2] = true;
                    if(x1==x2) {
                        vector[x1*2][y1+y2]=true;
                        h = x1*2;
                        o = y1+y2;
                    }else {
                        vector[x1+x2][y1*2]=true;
                        h = x1+x2;
                        o = y1*2;
                    }
                    clip.play();
                    //Toolkit.getDefaultToolkit().beep();
                    resgraph[x1][y1].addVertex(new Point(x2,y2));
                    resgraph[x2][y2].addVertex(new Point(x1,y1));
                    edges.remove(r);
                }

            }
        }

        boolean checkequal(LinkedList<Point> l1,LinkedList<Point> l2){
            for(int i=0;i<l1.size();i++){
                if(l2.contains(l1.get(i))){
                    return true;
                }
            }
            return false;
        }
        LinkedList<Point> v1 = new LinkedList<Point>();
        LinkedList<Point> getUnionSet(Point point){
            v1.clear();
            LinkedList<Point> l = getUnionSet1(point);
            return l;
        }
        LinkedList<Point> getUnionSet1(Point point){
            v1.add(point);
            LinkedList<Point> l = new LinkedList<Point>();
            LinkedList<Point> temp,temp1;
            for(int i=0;i<resgraph[point.x][point.y].getSize();i++){
                temp = resgraph[point.x][point.y].getList();
                for(int j=0;j<temp.size();j++){
                    l.add(temp.get(j));
                    if(!v1.contains(temp.get(i))){
                        temp1 = getUnionSet1(temp.get(i));
                        for(int k=0;k<temp1.size();k++){
                            if(!l.contains(temp1.get(k))){
                                l.add(temp1.get(k));
                            }
                        }
                    }
                }
            }
            return l;
        }

        class paintagain extends Thread{
            paintagain(){
                start();
            }
            public void run() {
                while(true) {
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Graphics g = c.getGraphics();
                    c.paint(g);
                }
            }
        }
        LinkedList<Point> getNonVisited(int x,int y) {
            LinkedList<Point> result =new LinkedList<Point>();
            for(int i=0;i<graph[x][y].getSize();i++) {
                if(!vector[graph[x][y].getVertex(i).x*2][graph[x][y].getVertex(i).y*2]) {
                    result.add(graph[x][y].getVertex(i));
                }
            }
            return result;
        }
        int genRandom(int x) {
            return (int)(Math.floor(Math.random()*(x+1)));
        }
        public void paint(Graphics g) {
            Graphics2D g2=(Graphics2D)g;
            g2.setColor(Color.GREEN);
            for(int i=0;i<100;i++) {
                for (int j = 0; j < 70; j++) {
                    if (vector[i][j]) {
                        if (h == i && j == o)
                            g2.setColor(Color.RED);
                        else g2.setColor(Color.GREEN);
                        g2.fillRect(i * 10, j * 10, 10, 10);
                    }
                }
            }
        }
    }
    public static void main(String args[]) {
        new Kruskal();
    }

}
