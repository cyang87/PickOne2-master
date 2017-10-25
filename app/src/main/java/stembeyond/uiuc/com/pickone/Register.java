package stembeyond.uiuc.com.pickone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private Button sign_in_button;
    private Button sign_out_button;
    private Button register_button;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

    private int registerUser(){
        progressDialog = new ProgressDialog(this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Register: Please enter email", Toast.LENGTH_SHORT).show();
            //stopping execution
            return 0;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Register: Please enter password", Toast.LENGTH_SHORT).show();
            //stopping execution
            return 0;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Registration is Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            mDatabase.child(userId);
                        }
                        else {
                            Toast.makeText(getBaseContext(), "Could Not Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return 1;
    }


    private int signInUser(){
        progressDialog = new ProgressDialog(this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Sign in: Please enter email", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Sign in: Please enter password", Toast.LENGTH_SHORT).show();
            //stopping execution
            return 0;
        }

        progressDialog.setMessage("Signing in  User...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            //user is successfully registered and logged in
                            Toast.makeText(getBaseContext(), "Sign In Is Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            mDatabase.child(userId);
                        } else {
                            Toast.makeText(getBaseContext(), "Could Not Sign in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return 1;
    }

    /*void setButtons(Button register, Button sign) {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //user is signed in
                if (user != null) {
                    setContentView(R.layout.activity_account_sign_in);
                    topButton = (Button) findViewById(R.id.topButton);
                    bottomButton = (Button) findViewById(R.id.bottomButton);
                    topButton.setText("Sign In");
                    bottomButton.setText("Register");
                    setButtons(bottomButton, topButton);
                }
                //user is not signed in
                else {
                    setContentView(R.layout.account_sign_out);
                    topButton = (Button) findViewById(R.id.topButton);
                    bottomButton = (Button) findViewById(R.id.bottomButton);
                    topButton.setText("register");
                    bottomButton.setText("Sign Out");
                    setButtons(topButton, bottomButton);
                }
            }
        });
    }*/

    void setSign_in(Button sign_in_button, Button register_button) {
        Toast.makeText(getBaseContext(), "Sign in: set sign in", Toast.LENGTH_SHORT).show();
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sign in : on click", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "email is : " + editTextEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                if (signInUser() == 1) {
                    //Toast.makeText(getBaseContext(), "Sign in complete", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.account_sign_out);
                    sign_out_button = (Button) findViewById(R.id.sign_out_button);
                    //set sign out listener function
                    setSign_out(sign_out_button);
                    Toast.makeText(getBaseContext(), "You are signed in. Want to sign out?", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    void setSign_out (Button sign_out_button) {
        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getBaseContext(), "You are signed out", Toast.LENGTH_SHORT).show();

                //if sign out successful
                setContentView(R.layout.activity_account_sign_in);
                sign_in_button = (Button) findViewById(R.id.sign_in_button);
                register_button = (Button) findViewById(R.id.register_button);

                //set register and sign in button listerner function
                setSign_in(sign_in_button, register_button);
                Toast.makeText(getBaseContext(), "Register or sign in?", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);

        if (user == null) {
            setContentView(R.layout.activity_account_sign_in);
            sign_in_button = (Button) findViewById(R.id.sign_in_button);
            register_button = (Button) findViewById(R.id.register_button);
            setSign_in(sign_in_button, register_button);
            Toast.makeText(getBaseContext(), "Register or sign in?", Toast.LENGTH_SHORT).show();
        }
        else {
            setContentView(R.layout.account_sign_out);
            sign_out_button = (Button) findViewById(R.id.sign_out_button);
            setSign_out(sign_out_button);
            Toast.makeText(getBaseContext(), "You are signed in. Want to sign out?", Toast.LENGTH_SHORT).show();
        }

        progressDialog = new ProgressDialog(this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
