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

import java.util.ArrayList;

public class adapterallsubsidy extends RecyclerView.Adapter<adapterallsubsidy.MyViewHolder>
{

    Context context;
    ArrayList<pojo_allsubsidy> subsidy = new ArrayList<pojo_allsubsidy>();

    adapterallsubsidy(Context c, ArrayList<pojo_allsubsidy> p)
    {
        context = c;
        subsidy = p;
        Log.d("bosss",subsidy.size()+"");
    }

    public adapterallsubsidy()
    {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_allsubsidy, viewGroup, false);
        return new MyViewHolder(view,context,subsidy);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.name.setText(subsidy.get(position).getmSubsidyName());
        holder.maxincome.setText(subsidy.get(position).getmCriteria()+"");
        holder.maxwin.setText(subsidy.get(position).getmMaxValue()+"");
        holder.statename.setText(subsidy.get(position).getmState());




    }

    @Override
    public int getItemCount() {
        return subsidy.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name;
        TextView maxincome;
        TextView maxwin;
        TextView statename;


        Button btn;
        Context ctx;
        ArrayList<pojo_allsubsidy> subsidy = new ArrayList<pojo_allsubsidy>();

        public MyViewHolder(View itemView,Context ctx,ArrayList<pojo_allsubsidy> subsidy)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            maxincome = (TextView) itemView.findViewById(R.id.maxincome);
            maxwin = (TextView) itemView.findViewById(R.id.maxwin);
            statename = (TextView) itemView.findViewById(R.id.statename);

            itemView.setOnClickListener(this);
            this.ctx=ctx;
            this.subsidy=subsidy;


        }

        @Override
        public void onClick(View view)
        {
            int position = getAdapterPosition();
            pojo_allsubsidy p= this.subsidy.get(position);
            Log.d("bosss",p.getmDetails());
            Intent i = new Intent(ctx,susbidydetails.class);
            i.putExtra("mDetails",p.getmDetails());
            i.putExtra("mDocuments",p.getmDocuments());
            i.putExtra("mSubsidy",p.getmSubsidyName());
            i.putExtra("mAmount",p.getmMaxValue());


            ctx.startActivity(i);

        }

    }
}

