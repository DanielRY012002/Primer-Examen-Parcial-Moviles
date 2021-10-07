package com.example.examenparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examenparcial.model.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddProductoActivity extends AppCompatActivity {
    EditText nombre,precio,stock;
    Button addBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);
        nombre=findViewById(R.id.txt_add_nombrePro);
        precio=findViewById(R.id.txt_add_precio);
        stock=findViewById(R.id.txt_add_stock);
        addBtn=findViewById(R.id.btn_add_pro);
        initFirebase();
        addBtn.setOnClickListener(view -> {
            String vNombre = nombre.getText().toString().trim();
            String vPrecio = precio.getText().toString().trim();
            String vStock = stock.getText().toString().trim();
            System.out.println(vNombre);
            System.out.println(vPrecio);
            System.out.println(vStock);
            if (vNombre == null || vNombre.equals("")) {
                nombre.setError("Required");
            } else if (vPrecio == null || vPrecio.equals("")) {
                precio.setError("Required");
            } else if (vStock == null || vStock.equals("")) {
                stock.setError("Required");
            } else {
                Producto p = new Producto();
                p.setUid(UUID.randomUUID().toString());
                p.setNombre(vNombre);
                p.setPrecio(Double.valueOf(vPrecio));
                p.setStock(Integer.valueOf(vStock));
                System.out.println(p);
                databaseReference.child("Producto").child(p.getUid()).setValue(p);
                Toast.makeText(
                        this,
                        "Producto registrado!",
                        Toast.LENGTH_SHORT
                ).show();
                limpiarCajas();
            }
        });
    }
    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, ListProductosActivity.class));
    }
    private void limpiarCajas(){
        nombre.setText("");
        precio.setText("");
        stock.setText("");
    }
}