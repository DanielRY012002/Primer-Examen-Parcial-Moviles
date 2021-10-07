package com.example.examenparcial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SelectionActivity extends AppCompatActivity {
    Button showProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        showProductos=(Button) findViewById(R.id.btnProducto);
        showProductos.setOnClickListener(view -> {
            startActivity(new Intent(this, ListProductosActivity.class));
            finish();
        });
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas cerrar sesión?")
                .setPositiveButton("Si", (dialog, i) -> {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}