package com.example.authentication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
private Context mcontext;
private ArrayList<GsonData> mdata;

    public Adapter(Context context, ArrayList<GsonData> data){
        mcontext = context;
        mdata = data;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.dataget,parent,false);
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        GsonData currentItem = mdata.get(position);

        String name = currentItem.getLogin();
        holder.mTextView.setText(name);
    }



    @Override
    public int getItemCount() {
        return mdata.size();
    }
public class AdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.name);
    }
}
}
