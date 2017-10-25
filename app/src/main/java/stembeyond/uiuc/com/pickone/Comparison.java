//////////////////////////////////////////
package stembeyond.uiuc.com.pickone;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Comparison extends AppCompatActivity {

    Button lButton;
    Button rButton;
    Button pollButton;
    BarChart chart;
    int position;
    BarData data;
    Graph graph;
    static final String EXTRA_RVOTES = "com.stembeyond.uiuc.pickone.rvotes";
    static final String EXTRA_LVOTES = "com.stembeyond.uiuc.pickone.lvotes";
    static final String EXTRA_GRAPH_TITLE = "com.stembeyond.uiuc.pickone.mtitle";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDbReference_user;
    static float RVotes = 0;
    static float LVotes = 0;
    static String has_voted = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        position = getIntent().getIntExtra(QuestionList.EXTRA_QUESTION_ID, 0);
        graph = MainActivity.getGraphs().getGraph(position);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("topics");
        mDatabaseReference.child(graph.mTitle);

        mDatabaseReference.child(graph.mTitle).child("RVotes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    int temp= dataSnapshot.getValue(Integer.class);
                    RVotes = (float) temp;
                }
                else {
                    mDatabaseReference.child(graph.mTitle).child("RVotes").setValue(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseReference.child(graph.mTitle).child("LVotes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    int temp = dataSnapshot.getValue(Integer.class);
                    LVotes = (float) temp;
                } else {
                    mDatabaseReference.child(graph.mTitle).child("LVotes").setValue(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseReference.child(graph.mTitle).child("RVotes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    int temp = dataSnapshot.getValue(Integer.class);
                    RVotes = (float) temp;
                } else {
                    mDatabaseReference.child(graph.mTitle).child("RVotes").setValue(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseReference.child(graph.mTitle).child("LVotes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    int temp = dataSnapshot.getValue(Integer.class);
                    LVotes = (float) temp;
                } else {
                    mDatabaseReference.child(graph.mTitle).child("LVotes").setValue(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        mDbReference_user = mDatabase.getReference("users").child(userId);

        mDbReference_user.child(graph.mTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    has_voted = dataSnapshot.getValue(String.class);
                } else {
                    has_voted = "N";
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDbReference_user.child(graph.mTitle).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    has_voted = dataSnapshot.getValue(String.class);
                } else {
                    has_voted = "N";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /* STUFF THAT I ADDED */
        ImageButton tButton = (ImageButton) findViewById(R.id.topicsButton);
        ImageButton mButton = (ImageButton) findViewById(R.id.mainButton);
        ImageButton pButton = (ImageButton) findViewById(R.id.profileButton);

        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Profile.class);
                startActivity(intent);
            }
        });

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
        /* STUFF THAT I ADDED */

        chart = (BarChart) findViewById(R.id.graph);
        lButton = (Button) findViewById(R.id.choiceOne);
        rButton = (Button) findViewById(R.id.choiceTwo);
        pollButton = (Button) findViewById(R.id.showPoll);

        data = MainActivity.getGraphs().getGraph(position).getData();

        lButton.setText(graph.mLeftObj);
        rButton.setText(graph.mRightObj);
        //lButton.setImageResource(R.drawable.apple);
        //rButton.setImageResource(R.drawable.banana);


        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //float LVotes = graph.getmLVotes();
                //graph.setmLVotes(LVotes + 1);
                if (has_voted.equals("N")) {
                    mDatabaseReference.child(graph.mTitle).child("LVotes").setValue(LVotes + 1);
                    LVotes++;
                    mDbReference_user.child(graph.mTitle).setValue("L");
                }
            }
        });

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //float RVotes = graph.getmRVotes();
                //graph.setmRVotes(RVotes + 1);
                if (has_voted.equals("N")) {
                    mDatabaseReference.child(graph.mTitle).child("RVotes").setValue(RVotes + 1);
                    RVotes++;
                    mDbReference_user.child(graph.mTitle).setValue("R");
                }
            }
        });

        pollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Poll.class);
                intent.putExtra(QuestionList.EXTRA_QUESTION_ID, position);
                intent.putExtra(EXTRA_LVOTES, LVotes);
                intent.putExtra(EXTRA_RVOTES, RVotes);
                intent.putExtra(EXTRA_GRAPH_TITLE, graph.mTitle);
                startActivity(intent);
            }
        });
        ImageButton rArrowButton = (ImageButton) findViewById(R.id.right);
        ImageButton lArrowButton = (ImageButton) findViewById(R.id.left);
        rArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                String[] qs = res.getStringArray(R.array.questions);
                Intent intent = new Intent(getBaseContext(), Comparison.class);
                intent.putExtra(QuestionList.EXTRA_QUESTION_ID, ((position+1)%qs.length));
                startActivity(intent);
            }
        });
        lArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                String[] qs = res.getStringArray(R.array.questions);
                Intent intent = new Intent(getBaseContext(), Comparison.class);
                intent.putExtra(QuestionList.EXTRA_QUESTION_ID, ((position-1+qs.length)%qs.length));
                startActivity(intent);
            }
        });

        Button cancel_button = (Button) findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (has_voted.equals("L")) {
                    if (LVotes > 0) {
                        mDatabaseReference.child(graph.mTitle).child("LVotes").setValue(LVotes - 1);
                        mDbReference_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.child(graph.mTitle).getRef().removeValue();
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Toast toast = Toast.makeText(getApplicationContext(), "Vote cancelled",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
                else if (has_voted.equals("R")) {
                    if (RVotes > 0) {
                        mDatabaseReference.child(graph.mTitle).child("RVotes").setValue(RVotes - 1);
                        mDbReference_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.child(graph.mTitle).getRef().removeValue();
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Toast toast = Toast.makeText(getApplicationContext(), "Right vote cancelled",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }
        });
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.menu_main, menu);
    }

}
