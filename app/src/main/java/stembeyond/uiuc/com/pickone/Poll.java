package stembeyond.uiuc.com.pickone;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.google.firebase.database.DatabaseReference;

public class Poll extends AppCompatActivity {
    BarChart chart;
    BarData data;
    Graph graph;
    int position;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        position = getIntent().getIntExtra(QuestionList.EXTRA_QUESTION_ID, 0);
        //float lVotes = Comparison.LVotes;
        //float rVotes = Comparison.RVotes;

        float lVotes = getIntent().getFloatExtra(Comparison.EXTRA_LVOTES, 0);
        float rVotes = getIntent().getFloatExtra(Comparison.EXTRA_RVOTES, 0);

        int max = Math.max((int)lVotes, (int)rVotes);

        TextView title = (TextView) findViewById(R.id.graphTitle);
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/mark_my_words.ttf"); //my edits
        String mTitle = getIntent().getStringExtra(Comparison.EXTRA_GRAPH_TITLE);
        title.setText(mTitle);
        title.setTypeface(custom); // my edits

        data = MainActivity.getGraphs().getGraph(position).getData();
        data.setValueTextSize(30f); //added
        //CHANGING DECIMAL INTO INTEGER
        data.setValueFormatter(new MyValueFormatter());


        graph = MainActivity.getGraphs().getGraph(position);
        chart = (BarChart) findViewById(R.id.graph);
        chart.setData(data);
        chart.setDescription(graph.mTitle); //sdfdsfsdfsdf

        chart.getLegend().setEnabled(false); //added
        chart.setDescription(""); //added
        chart.animateY(5000);
        chart.getAxisLeft().setAxisMinValue(0);
        chart.getAxisRight().setAxisMinValue(0);
        chart.getAxisLeft().setAxisMaxValue(max * 2);
        chart.getAxisRight().setAxisMaxValue(max * 2);
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);

        BarDataSet curr = graph.barDataSet;
        curr.getEntryForXIndex(0).setVal((int)lVotes);
        curr.getEntryForXIndex(1).setVal((int)rVotes);

        TextView leftText = (TextView) findViewById(R.id.leftText);
        TextView rightText = (TextView) findViewById(R.id.rightText);

        leftText.setText(graph.mLeftObj);
        rightText.setText(graph.mRightObj);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
