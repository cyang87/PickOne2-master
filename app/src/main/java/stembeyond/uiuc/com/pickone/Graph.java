package stembeyond.uiuc.com.pickone;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Jeff on 2016/10/3.
 */
//list of graphs class needed
public class Graph {
    int graphId; //to keep track in category list
    String mTitle;
    String mLeftObj;
    String mRightObj;
    float mLVotes;
    float mRVotes;
    ArrayList<BarEntry> entries;
    ArrayList<String> labels;
    BarDataSet barDataSet;
    BarData data;

    Graph(String title, String leftObj, String rightObj, float leftVotes, float rightVotes, int ID) {
        mTitle = title;
        mLeftObj = leftObj;
        mRightObj = rightObj;
        mLVotes = leftVotes;
        mRVotes = rightVotes;
        graphId = ID;

        entries = new ArrayList<>();
        entries.add(new BarEntry(this.mLVotes, 0));
        entries.add(new BarEntry(this.mRVotes, 1));

        barDataSet = new BarDataSet(entries, "# of Votes");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        labels = new ArrayList<>();
        labels.add(leftObj);
        labels.add(rightObj);
        data = new BarData(labels, barDataSet);
    }

    void setGraph(BarChart chart, BarData data) {
        chart.setData(data);
        chart.setDescription(this.mTitle);
        chart.animateY(5000);
    }

    int getGraphId() {
        return graphId;
    }

    float getmLVotes() {
        return mLVotes;
    }

    float getmRVotes() {
        return mRVotes;
    }

    void setmLVotes(float n){
        mLVotes = n;
    }

    void setmRVotes(float n) {
        mRVotes = n;
    }

    void addLVotes(int n) {
        mLVotes += n;
    }

    void addRVotes(int n) {
        mRVotes += n;
    }

    BarData getData() {
        return data;
    }
}

