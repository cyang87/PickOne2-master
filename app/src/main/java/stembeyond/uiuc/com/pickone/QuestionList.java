package stembeyond.uiuc.com.pickone;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Button;

import java.util.Random;

public class QuestionList extends AppCompatActivity {

    static final String EXTRA_QUESTION_ID = "com.stembeyond.uiuc.pickone.position";
    //static GraphList graphs;

    /*GraphList getGraphs() {
        return graphs;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        Resources res = getResources();
        String[] qs = res.getStringArray(R.array.questions);
        //graphs = new GraphList();
        //graphs.setGraphs(qs);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.question_view, qs);
        ListView qList = (ListView) findViewById(R.id.question_list);
        qList.setAdapter(adapter);

        qList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getBaseContext(), Comparison.class);
                intent.putExtra(EXTRA_QUESTION_ID, position);
                startActivity(intent);
            }
        });


        /* STUFF THAT I ADDED */
        ImageButton tButton = (ImageButton) findViewById(R.id.topicsButton);
        ImageButton mButton = (ImageButton) findViewById(R.id.mainButton);
        ImageButton pButton = (ImageButton) findViewById(R.id.profileButton);

        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), QuestionList.class);
                startActivity(intent);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Profile.class);
                startActivity(intent);
            }
        });

        /* STUFF THAT I ADDED */

        Button rButton = (Button) findViewById(R.id.randomchoose);
        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Resources res = getResources();
                String[] qs = res.getStringArray(R.array.questions);
                Random rand = new Random();
                int randomno = rand.nextInt(qs.length);
                Intent intent = new Intent(getBaseContext(), Comparison.class);
                intent.putExtra(EXTRA_QUESTION_ID,randomno);
                startActivity(intent);
            }
        });

        Button aButton = (Button) findViewById(R.id.add_question);
        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddQuestion.class);
                startActivity(intent);
            }
        });
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.menu_main, menu);
    }


}
