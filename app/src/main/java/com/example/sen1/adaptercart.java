package com.example.sen1;





import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class adaptercart extends RecyclerView.Adapter<adaptercart.MyViewHolder>
{

    Context context;
    ArrayList<Productscart> ppp = new ArrayList<Productscart>();
    String username;
    Button b;
    DatabaseReference reference;



    adaptercart(Context c, ArrayList<Productscart> p,String username)
    {
        context = c;
        ppp = p;
        this.username=username;
        Log.d("bosss",ppp.size()+"");
    }

    public adaptercart()
    {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_cartitems, viewGroup, false);
        return new MyViewHolder(view,context,ppp);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)
    {
        holder.productname.setText(ppp.get(position).getProductname());
        holder.amount.setText(ppp.get(position).getProductprice()+"");
        Glide.with(context)
                .load(ppp.get(position).getImage())
                .into(holder.image);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference();
                reference.child("cart").child(username).child(username+ppp.get(position).getProductname()).removeValue();
                ppp.remove(holder.getPosition());
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return ppp.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView productname;
        ImageView image;
        TextView amount,qty;
        Button b,inc,dec;



        Context ctx;
        ArrayList<Productscart> ppp = new ArrayList<Productscart>();

        public MyViewHolder(View itemView,Context ctx,ArrayList<Productscart> ppp)
        {
            super(itemView);
            productname = (TextView) itemView.findViewById(R.id.producttname);
            image=(ImageView)itemView.findViewById(R.id.producttimage);
            amount = (TextView) itemView.findViewById(R.id.pptrice);
            b=itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(this);
            this.ctx=ctx;
            this.ppp=ppp;


        }

        @Override
        public void onClick(View view)
        {


            int position = getAdapterPosition();
            Productscart p= this.ppp.get(position);

        }


    }

}

