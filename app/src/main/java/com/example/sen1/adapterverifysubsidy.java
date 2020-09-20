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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class adapterverifysubsidy extends RecyclerView.Adapter<adapterverifysubsidy.MyViewHolder>
{
    private DatabaseReference ref;
    Context context;
    ArrayList<pojo_subsidy> notifysubsidy = new ArrayList<pojo_subsidy>();



    adapterverifysubsidy(Context c, ArrayList<pojo_subsidy> p, String s, EditText u)
    {
        context = c;
        notifysubsidy = p;
        ref = FirebaseDatabase.getInstance().getReference("/").child("subsidyget").child(s);
        if(getItemCount()!=0){
            u.setEnabled(false);
        }
        else{
            u.setEnabled(true);
        }

    }

    public adapterverifysubsidy()
    {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_verifysubsidy, viewGroup, false);
        return new MyViewHolder(view,context,notifysubsidy);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)
    {
        holder.nsubsidyname.setText(notifysubsidy.get(position).getSubsidyname());
        holder.ndocument.setText(notifysubsidy.get(position).getDocuments()+"");
        holder.namount.setText(notifysubsidy.get(position).getAmount()+"");

        holder.nverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String s= notifysubsidy.get(position).getSubsidyname();
                ref.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot user:dataSnapshot.getChildren())
                        {
                            String us=user.getKey();
                            pojo_subsidy p = user.getValue(pojo_subsidy.class);
                            if(p.getSubsidyname().equals(s))
                            {
                               ref.child(us).child("status").setValue("1");
                               break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return notifysubsidy.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView nsubsidyname;
        TextView ndocument;
        TextView namount;
        Button nverify;




        Context ctx;
        ArrayList<pojo_subsidy> mysubsidy = new ArrayList<pojo_subsidy>();

        public MyViewHolder(View itemView,Context ctx,ArrayList<pojo_subsidy> mysubsidy)
        {
            super(itemView);
            nsubsidyname = (TextView) itemView.findViewById(R.id.nsubsidyname);
            ndocument = (TextView) itemView.findViewById(R.id.ndocument);
            namount = (TextView) itemView.findViewById(R.id.namount);
            nverify= itemView.findViewById(R.id.verify);

            itemView.setOnClickListener(this);
            this.ctx=ctx;
            this.mysubsidy=mysubsidy;


        }

        @Override
        public void onClick(View view)
        {
            int position = getAdapterPosition();
            pojo_subsidy p= this.mysubsidy.get(position);
            Log.d("bosssss",p.getAmount()+"");
        }

    }
}