package com.company;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class TSP2 {
    Frame f=new Frame("Maze Generator");
    TSP2.MyCanvas c=new TSP2.MyCanvas();
    TSP2(){
        c.setBackground(Color.WHITE);
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
        c.DFS(24,0);
        c.stopRendering();
    }
    class MyCanvas extends Canvas{
        int h,o;
        TSP2.MyCanvas.paintagain p;

        LinkedList<Point> visited = new LinkedList<Point>();
        void startRendering() {
            p=new TSP2.MyCanvas.paintagain();
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
                            graph[i][j].addVertex(new Point(0,33));
                            graph[i][j].addVertex(new Point(1,34));

                        }else {
                            graph[i][j].addVertex(new Point(0,j-1));
                            graph[i][j].addVertex(new Point(1,j));
                            graph[i][j].addVertex(new Point(0,j+1));

                        }
                    }else if(i==49) {
                        if(j==0) {
                            graph[i][j].addVertex(new Point(48,0));
                            graph[i][j].addVertex(new Point(49,1));
                        }else if(j==34) {
                            graph[i][j].addVertex(new Point(48,34));
                            graph[i][j].addVertex(new Point(49,33));

                        }else {
                            graph[i][j].addVertex(new Point(48,j));
                            graph[i][j].addVertex(new Point(49,j-1));
                            graph[i][j].addVertex(new Point(49,j+1));

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
                            graph[i][j].addVertex(new Point(0,33));
                            graph[i][j].addVertex(new Point(1,34));

                        }else if(i==49) {
                            graph[i][j].addVertex(new Point(48,34));
                            graph[i][j].addVertex(new Point(49,33));

                        }else {
                            graph[i][j].addVertex(new Point(i-1,j));
                            graph[i][j].addVertex(new Point(i,j-1));
                            graph[i][j].addVertex(new Point(i+1,j));

                        }
                    }else {
                        graph[i][j].addVertex(new Point(i-1,j));
                        graph[i][j].addVertex(new Point(i,j-1));
                        graph[i][j].addVertex(new Point(i+1,j));

                        graph[i][j].addVertex(new Point(i,j+1));

                    }
                }
            }
        }
        void DFS(int x,int y){
            dfs(x,y);
            vector=new boolean[100][70];
            c.repaint();
            inorder(x,y);
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
            int r=0;
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

        void inorder(int x,int y){
            vector[x*2][y*2] = true;
            for(int i=0;i<resgraph[x][y].getSize();i++){
                Point p = resgraph[x][y].getVertex(i);

                if(!vector[p.x*2][p.y*2]){
                    inorder(p.x,p.y);
                }

                if(x==p.x){
                    vector[p.x*2][p.y+y] = true;
                }else {
                    vector[p.x+x][p.y*2] = true;
                }
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                h=p.x*2;o=p.y*2;
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
            g2.setColor(Color.BLACK);
            for(int i=0;i<100;i++) {
                for (int j = 0; j < 70; j++) {
                    if (vector[i][j]) {
                        if (h == i && j == o)
                            g2.setColor(Color.RED);
                        else g2.setColor(Color.BLACK);
                        g2.fillRect(i * 10, j * 10, 10, 10);
                    }
                }
            }
        }
    }
    public static void main(String args[]) {
        new TSP2();
    }

}
