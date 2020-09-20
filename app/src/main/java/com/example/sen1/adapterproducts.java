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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class adapterproducts extends RecyclerView.Adapter<adapterproducts.MyViewHolder> implements Filterable
{

    Context context;
    ArrayList<Products> ppp = new ArrayList<Products>();
    ArrayList<Products>npp;
    String username;
    String phoneno;


    adapterproducts(Context c, ArrayList<Products> p,String username,String phoneno)
    {
        context = c;
        ppp = p;
        npp=new ArrayList<>(p);
        this.username=username;
        this.phoneno=phoneno;
        Log.d("bosss",ppp.size()+"");
    }

    public adapterproducts()
    {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_products, viewGroup, false);
        return new MyViewHolder(view,context,ppp);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.productname.setText(ppp.get(position).getProductname());
        holder.amount.setText(ppp.get(position).getProductprice()+"");
        Glide.with(context)
                .load(ppp.get(position).getImage())
                .into(holder.image);




    }


    @Override
    public int getItemCount() {
        return ppp.size();
    }

    @Override
    public Filter getFilter() {
        return exfilter;
    }
    private Filter exfilter=new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Products> np=new ArrayList<>();
            if(constraint==null||constraint.length()==0){

                np.addAll(npp);
            }
            else{
                String Filepattern=constraint.toString().toLowerCase().trim();
                for(int i=0;i<npp.size();i++){
                    if(npp.get(i).getProductname().toString().toLowerCase().trim().contains(Filepattern)){

                        np.add(npp.get(i));
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=np;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ppp.clear();
            ppp.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productname;
       ImageView image;
        TextView amount;




        Context ctx;
        ArrayList<Products> ppp = new ArrayList<Products>();

        public MyViewHolder(View itemView,Context ctx,ArrayList<Products> ppp)
        {
            super(itemView);
            productname = (TextView) itemView.findViewById(R.id.productname);
            image=(ImageView)itemView.findViewById(R.id.productimage);
            amount = (TextView) itemView.findViewById(R.id.pprice);

            itemView.setOnClickListener(this);
            this.ctx=ctx;
            this.ppp=ppp;


        }

        @Override
        public void onClick(View view)
        {


            int position = getAdapterPosition();
            Products p= this.ppp.get(position);
            Intent i = new Intent(ctx,productdetails.class);
            i.putExtra("mDetails",p.getProductdiscription());
            i.putExtra("mName",p.getProductname());
            i.putExtra("mPrice",p.getProductprice());
            i.putExtra("mImage",p.getImage());
            i.putExtra("username",username);
            i.putExtra("phoneno",phoneno);

            ctx.startActivity(i);
        }

    }
}

