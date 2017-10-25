package stembeyond.uiuc.com.pickone;

import java.util.ArrayList;

public class GraphList {
    ArrayList<Graph> categories;
    int number;

    GraphList() {
        number = 0;
        categories = new ArrayList<Graph>();
        for (int i = 0; i < 100; i++) {
            Graph curr = new Graph("title" + i,"Apples","Bananas",0f,0f, i);
            categories.add(curr);
            number++;
        }
    }

    void setGraph(String[] qs, String[] cs) {
        number = 0;
        categories = new ArrayList<Graph>();
        int offset = qs.length;
        for (int i = 0; i < qs.length; i++) {
            Graph curr = new Graph(qs[i], cs[i], cs[i+offset], 0f,0f, i);
            categories.add(curr);
            number++;
        }
    }

    Graph getGraph(int n) {
        for (Graph graph : categories) {
            if (graph.getGraphId() == n) {
                return graph;
            }
        }
        return null;
    }
}
