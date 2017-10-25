package stembeyond.uiuc.com.pickone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddQuestion extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("topics");
    ArrayList<String> q_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title_text = (EditText) findViewById(R.id.title);
                EditText choice_one = (EditText) findViewById(R.id.choice_one);
                EditText choice_two = (EditText) findViewById(R.id.choice_two);

                String title = title_text.getText().toString().trim();
                String choice1 = choice_one.getText().toString().trim();
                String choice2 = choice_two.getText().toString().trim();

                HashMap<String,Integer> dict = new HashMap<String,Integer>();
                dict.put(choice1, 0);
                dict.put(choice2, 0);

                mDatabase.child(title).child("choices").setValue(dict);
                mDatabase.child("topics").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        q_list = (ArrayList<String>) dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                q_list.add(title);
                mDatabase.child("topics").setValue(q_list);

            }
        });
    }

}
