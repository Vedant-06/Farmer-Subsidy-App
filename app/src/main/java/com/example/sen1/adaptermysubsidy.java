package com.example.sen1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class adaptermysubsidy extends RecyclerView.Adapter<adaptermysubsidy.MyViewHolder>
{

    Context context;
    ArrayList<pojo_mysubsidy> mysubsidy = new ArrayList<pojo_mysubsidy>();

    adaptermysubsidy(Context c, ArrayList<pojo_mysubsidy> p)
    {
        context = c;
        mysubsidy = p;
        Log.d("bosss",mysubsidy.size()+"");
    }

    public adaptermysubsidy() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_mysubsidy, viewGroup, false);
        return new MyViewHolder(view,context,mysubsidy);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.subsidyname.setText(mysubsidy.get(position).getSubsidyname());
        holder.status.setText(mysubsidy.get(position).getStatus()+"");
        holder.amount.setText(mysubsidy.get(position).getAmount()+"");





    }

    @Override
    public int getItemCount() {
        return mysubsidy.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView subsidyname;
        TextView status;
        TextView amount;




        Context ctx;
        ArrayList<pojo_mysubsidy> mysubsidy = new ArrayList<pojo_mysubsidy>();

        public MyViewHolder(View itemView,Context ctx,ArrayList<pojo_mysubsidy> mysubsidy)
        {
            super(itemView);
            subsidyname = (TextView) itemView.findViewById(R.id.subsidyname);
            status = (TextView) itemView.findViewById(R.id.status);
            amount = (TextView) itemView.findViewById(R.id.amount);

            itemView.setOnClickListener(this);
            this.ctx=ctx;
            this.mysubsidy=mysubsidy;


        }

        @Override
        public void onClick(View view)
        {
            int position = getAdapterPosition();
            pojo_mysubsidy p= this.mysubsidy.get(position);
            Log.d("bosssss",p.getAmount()+"");
        }

    }
}

