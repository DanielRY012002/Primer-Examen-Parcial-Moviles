package com.example.examenparcial.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examenparcial.R;
import com.example.examenparcial.model.Producto;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<Producto> data;
    private LayoutInflater inflater;
    private Context context;
    private OnCardListener mOnCardListener;
    public ListAdapter(List<Producto> itemList, Context context, OnCardListener onCardListener){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = itemList;
        this.mOnCardListener = onCardListener;

    }
    @Override
    public int getItemCount(){ return data.size(); }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType){
        View view = inflater.inflate(R.layout.list_element_pr, null);
        return new ListAdapter.ViewHolder(view, mOnCardListener);
    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(data.get(position));
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView nombre, precio,stock;
        OnCardListener onCardListener;
        ViewHolder(View itemView, OnCardListener onCardListener){
            super(itemView);
            nombre = itemView.findViewById(R.id.txt_nombre);
            precio = itemView.findViewById(R.id.txt_precio);
            stock = itemView.findViewById(R.id.txt_stock);
            this.onCardListener = onCardListener;
            itemView.setOnClickListener(this);
        }
        @Override public void onClick(View view ){ onCardListener.onCardClick(getAdapterPosition());}

        void bindData(final Producto item){
            nombre.setText(item.getNombre());
            precio.setText(String.valueOf(item.getPrecio()));
            stock.setText(String.valueOf(item.getStock()));
        }
    }
    public interface OnCardListener{
        void onCardClick(int position);
    }
}
