package com.example.sen1;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class adapterfarmerconnect extends RecyclerView.Adapter<adapterfarmerconnect.myViewHolder>
{
    private static final String TAG = "adapterfarmerconnect";
    private Context mContext;
    private ArrayList<pojo_farmerconnect> temp = new ArrayList<>();
    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView name , contact , expertise;
        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            contact = itemView.findViewById(R.id.contact);
            expertise = itemView.findViewById(R.id.expertise);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String call = temp.get(getPosition()).getContact();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + call));

                }
            });
        }
    }
    adapterfarmerconnect(Context context , ArrayList<pojo_farmerconnect> foo)
    {
        mContext = context;
        temp = foo;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_farmerconnect, viewGroup , false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i)
    {
        myViewHolder.name.setText(temp.get(i).getFarmername());
        myViewHolder.contact.setText(temp.get(i).getContact());
        myViewHolder.expertise.setText(temp.get(i).getExpertise());
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }
}

