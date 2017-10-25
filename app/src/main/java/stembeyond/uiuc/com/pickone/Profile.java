package stembeyond.uiuc.com.pickone;

import android.content.Intent;


import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;
import android.content.Context;

import java.io.ByteArrayOutputStream;



public class Profile extends AppCompatActivity {
    int position;
    Graph graph;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDbReference_user;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        position = getIntent().getIntExtra(QuestionList.EXTRA_QUESTION_ID, 0);
        graph = MainActivity.getGraphs().getGraph(position);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("users");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = mAuth.getCurrentUser();
                String userid = user.getUid();
                String encoded = (String) dataSnapshot.child(userid).child("Profile Picture").getValue();
                System.out.println(encoded);
                if(encoded != null) {
                    byte[] bytarray = Base64.decode(encoded, Base64.DEFAULT);
                    Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0, bytarray.length);
                    ImageView iv = (ImageView) findViewById(R.id.circularPicture);
                    iv.setImageBitmap(bmimage);

                }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    /* STUFF THAT I ADDED */
        ImageButton mButton = (ImageButton) findViewById(R.id.mainButton);
        ImageButton tButton = (ImageButton) findViewById(R.id.topicsButton);
        ImageButton pButton = (ImageButton) findViewById(R.id.profileButton);

        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Profile.class);
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

        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), QuestionList.class);
                startActivity(intent);
            }
        });
        //profile pic change button
        Button pickPic = (Button) findViewById(R.id.profilePicture);
       pickPic.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
            if(requestCode == 0 && resultCode == RESULT_OK && data != null) {
                Bitmap propic = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                propic.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                byte[] bytearray = outstream.toByteArray();
                String encodedImageString = Base64.encodeToString(bytearray, Base64.DEFAULT);
                FirebaseUser user = mAuth.getCurrentUser();
                String userid = user.getUid();
                mDatabaseReference.child(userid).child("Profile Picture").setValue(encodedImageString);
                Toast toast = Toast.makeText(this, "Profile picture has been changed", Toast.LENGTH_SHORT);
                toast.show();
            }

    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.menu_main, menu);


    }

}
