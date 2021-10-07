package com.example.examenparcial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText txtUser,txtPass;
    Button login;
    FirebaseAuth firebaseAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser=(EditText) findViewById(R.id.txtUser);
        txtPass=(EditText) findViewById(R.id.txtPass);
        login=(Button) findViewById(R.id.btnLogin);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startActivity(new Intent(this, SelectionActivity.class));
            finish();
        }
        login.setOnClickListener(view -> {
            String mail = txtUser.getText().toString();
            String password = txtPass.getText().toString();
            if(mail==null||mail.equals("")){
                txtUser.setError("Required");
            }else if(password==null||password.equals("")){
                txtPass.setError("Required");

            }else{
                if(mail.matches(emailPattern)||mail.matches(emailPattern2)){
                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(
                                    this,
                                    "Bienvenido!",
                                    Toast.LENGTH_SHORT
                            ).show();
                            startActivity(new Intent(this, SelectionActivity.class));
                            finish();
                        }else{
                            Toast.makeText(
                                    this,
                                    "Username or Password incorrect",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                }else{
                    Toast.makeText(
                            this,
                            "Correo inválido",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas salir?")
                .setPositiveButton("Si", (dialog, i) -> {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}