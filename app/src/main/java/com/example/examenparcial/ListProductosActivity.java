package com.example.examenparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.examenparcial.adapter.ListAdapter;
import com.example.examenparcial.model.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListProductosActivity extends AppCompatActivity implements ListAdapter.OnCardListener{
    private List<Producto> productoList;
    private RecyclerView recyclerViewProductos;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  Producto productoSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_productos);
        recyclerViewProductos=findViewById(R.id.recycler_view_pro);
        initFirebase();
        listarDatos();
    }
    private void listarDatos() {
        productoList = new ArrayList<>();
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productoList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Producto producto =  dataSnapshot.getValue(Producto.class);
                    productoList.add(producto);
                }
                ListAdapter listAdapter = new ListAdapter(productoList, ListProductosActivity.this, ListProductosActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recycler_view_pro);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListProductosActivity.this));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_pro, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_add_pro) {
            finish();
            startActivity(new Intent(this, AddProductoActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, SelectionActivity.class));
    }
    @Override
    public void onCardClick(int position) {
        productoSelected = productoList.get(position);
        final CharSequence[] items = {
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder menu = new AlertDialog.Builder(this);
        menu.setTitle("Opciones");
        menu.setItems(items, (dialogInterface, i) -> {
            switch (i){
                case 0:
                    Intent intent = new Intent(ListProductosActivity.this, EditProductoActivity.class);
                    intent.putExtra("PRODUCTO_ID", productoSelected.getUid());
                    startActivity(intent);
                    break;
                case 1:
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListProductosActivity.this);
                    alert.setMessage("¿Desea eliminar este registro?");
                    alert.setPositiveButton("Si", (dialog, a)->{
                        Producto p = new Producto();
                        p.setUid(productoSelected.getUid());
                        databaseReference.child("Producto").child(p.getUid()).removeValue();
                        Toast.makeText(this, "Registro Eliminado", Toast.LENGTH_SHORT).show();
                    });
                    alert.setNegativeButton("No", (dialog, a)-> dialog.dismiss());
                    alert.show();
                    break;
            }
        });
        menu.show();
    }
}