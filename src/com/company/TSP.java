package com.company;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.LinkedList;

public class TSP {
    Frame f=new Frame("Maze Generator");
    TSP.MyCanvas c=new TSP.MyCanvas();
    TSP(){
        c.setBackground(Color.BLACK);
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
        c.DFS();
        c.stopRendering();
    }
    class MyCanvas extends Canvas{
        int h,o;
        TSP.MyCanvas.paintagain p;

        LinkedList<Point> visited = new LinkedList<Point>();
        void startRendering() {
            p=new TSP.MyCanvas.paintagain();
        }
        void stopRendering() {
            p.stop();
        }
        boolean[][] vector=new boolean[100][70];
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
                            graph[i][j].addVertex(new Point(1,0));
                            graph[i][j].addVertex(new Point(0,1));
                        }else if(j==34) {
                            graph[i][j].addVertex(new Point(1,34));
                            graph[i][j].addVertex(new Point(0,33));
                        }else {
                            graph[i][j].addVertex(new Point(0,j-1));
                            graph[i][j].addVertex(new Point(0,j+1));
                            graph[i][j].addVertex(new Point(1,j));
                        }
                    }else if(i==49) {
                        if(j==0) {
                            graph[i][j].addVertex(new Point(48,0));
                            graph[i][j].addVertex(new Point(49,1));
                        }else if(j==34) {
                            graph[i][j].addVertex(new Point(49,33));
                            graph[i][j].addVertex(new Point(48,34));
                        }else {
                            graph[i][j].addVertex(new Point(49,j-1));
                            graph[i][j].addVertex(new Point(49,j+1));
                            graph[i][j].addVertex(new Point(48,j));
                        }
                    }else if(j==0) {
                        if(i==0) {
                            graph[i][j].addVertex(new Point(1,0));
                            graph[i][j].addVertex(new Point(0,1));
                        }else if(i==49) {
                            graph[i][j].addVertex(new Point(48,0));
                            graph[i][j].addVertex(new Point(49,1));
                        }else {
                            graph[i][j].addVertex(new Point(i-1,j));
                            graph[i][j].addVertex(new Point(i+1,j));
                            graph[i][j].addVertex(new Point(i,j+1));
                        }
                    }else if(j==34) {
                        if(i==0) {
                            graph[i][j].addVertex(new Point(1,34));
                            graph[i][j].addVertex(new Point(0,33));
                        }else if(i==49) {
                            graph[i][j].addVertex(new Point(49,33));
                            graph[i][j].addVertex(new Point(48,34));
                        }else {
                            graph[i][j].addVertex(new Point(i-1,j));
                            graph[i][j].addVertex(new Point(i+1,j));
                            graph[i][j].addVertex(new Point(i,j-1));
                        }
                    }else {
                        graph[i][j].addVertex(new Point(i,j-1));
                        graph[i][j].addVertex(new Point(i-1,j));
                        graph[i][j].addVertex(new Point(i,j+1));
                        graph[i][j].addVertex(new Point(i+1,j));
                    }
                }
            }
        }
        void DFS(){
            dfs(24,17);
            vector=new boolean[100][70];
            c.repaint();
            inorder(24,17);
            vector=new boolean[100][70];
            traverseInorder();
        }
        void dfs(int x,int y) {
            if(visited.size()>=1749) {
                return;
            }
            vector[x*2][y*2]=true;
            visited.add(new Point(x,y));
            LinkedList<Point> aa = new LinkedList<Point>();
            LinkedList<Point> nv=new LinkedList<Point>();
            for(int i=0;i<visited.size();i++){
                LinkedList<Point> l = getNonVisited(visited.get(i).x,visited.get(i).y);
                for(int j=0;j<l.size();j++){
                    nv.add(new Point(l.get(j).x,l.get(j).y));
                    aa.add(new Point(visited.get(i).x,visited.get(i).y));
                }
            }
            int r=genRandom(nv.size()-1);
            int a=nv.get(r).x;
            int b=nv.get(r).y;
            int x1=aa.get(r).x;
            int y1=aa.get(r).y;
            resgraph[x1][y1].addVertex(new Point(a,b));
            resgraph[a][b].addVertex(new Point(x1,y1));
            //try {
            //    Thread.sleep(7);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
            if(x1==a) {
                vector[x1*2][y1+b]=true;
            }else {
                vector[x1+a][y1*2]=true;
            }
            h=a*2;o=b*2;
            dfs(a,b);
            dfs(x,y);
        }
        URL url = getClass().getResource("beep.wav");
        AudioClip clip = Applet.newAudioClip(url);
        LinkedList<Point> io = new LinkedList<Point>();
        void inorder(int x,int y){
            io.add(new Point(x,y));
            vector[x*2][y*2]= true;
            for(int i=0;i<resgraph[x][y].getSize();i++){
                Point p = resgraph[x][y].getVertex(i);

                if(!vector[p.x*2][p.y*2]){
                    inorder(p.x,p.y);
                }


            }
        }

        void traverseInorder(){
            for(int i=0;i<io.size()-1;i++){
                Point p1 = io.get(i);
                Point p2 = io.get(i+1);
                if(p1.x!=p2.x && p1.y!=p2.y){
                    Point p = io.remove(i+1);
                    boolean check = false;
                    for(int j=i+1;j<io.size();j++){
                        if(io.get(j).x==p.x || io.get(j).y==p.y){
                            check=true;
                            io.add(j+1,p);
                        }
                    }
                }
                vector[p1.x*2][p1.y*2] = true;
                vector[p2.x*2][p2.y*2] = true;
                if(p1.x == p2.x){
                    vector[p1.x*2][p1.y+p2.y] = true;
                }else {
                    vector[p1.x+p2.x][p1.y*2] = true;
                }
                //try {
                //    Thread.sleep(15);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
                h=io.get(i+1).x*2;o=io.get(i+1).y*2;
            }
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
                    if (vector[i][j] && (i%2!=0 || j%2!=0)) {
                        if (h == i && j == o)
                            g2.setColor(Color.RED);
                        else g2.setColor(Color.GREEN);
                        g2.fillRect(i * 10, j * 10, 10, 10);
                    }else if(vector[i][j]){
                        if (h == i && j == o)
                            g2.setColor(Color.RED);
                        else g2.setColor(Color.WHITE);
                        g2.fillRect(i * 10, j * 10, 10, 10);
                    }
                }
            }
        }
    }
    public static void main(String args[]) {
        new TSP();
    }

}
