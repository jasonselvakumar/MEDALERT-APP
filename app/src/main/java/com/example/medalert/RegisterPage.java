package com.example.medalert;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterPage extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button signupbtn;
    private TextInputEditText emailinput, pwdinput, cpwsdinput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        auth = FirebaseAuth.getInstance();
        emailinput = findViewById(R.id.emailtxt);
        pwdinput = findViewById(R.id.passwordtxt);
        cpwsdinput = findViewById(R.id.cnfpwdtxt);
        signupbtn = findViewById(R.id.button);

        TextView logintext  = findViewById(R.id.textView2);
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.medalert.RegisterPage.this, HomePage.class);
                startActivity(intent);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailinput.getText().toString();
                String password = pwdinput.getText().toString();
                String confirmPassword = cpwsdinput.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(RegisterPage.this, "Email is empty", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterPage.this, "Password is empty", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterPage.this, "Passwords didn't match", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterPage.this, task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterPage.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = auth.getCurrentUser();
                                    startActivity(new Intent(RegisterPage.this, HomePage.class));
                                } else {
                                    Toast.makeText(RegisterPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(RegisterPage.this, HomePage.class));
        }
    }
}




