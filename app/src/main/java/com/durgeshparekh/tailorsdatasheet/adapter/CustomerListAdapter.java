package com.durgeshparekh.tailorsdatasheet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.durgeshparekh.tailorsdatasheet.R;
import com.durgeshparekh.tailorsdatasheet.ViewDetailsActivity;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Customer;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    private List<Bean_Customer> customerList;
    private CustomerListEditCallback editCallback;
    private Context context;

    public CustomerListAdapter(List<Bean_Customer> customerList, Context context) {
        this.customerList = customerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_list, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.name.setText(customerList.get(viewHolder.getAdapterPosition()).getName());
        viewHolder.contact.setText(customerList.get(viewHolder.getAdapterPosition()).getContact());

        viewHolder.editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCallback.editCustomerData(viewHolder.getAdapterPosition(), v);

            }
        });

        viewHolder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCallback.deleteData(viewHolder.getAdapterPosition());
            }
        });

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewDetailsActivity.class);
                intent.putExtra("customer_name", customerList.get(position).getName());
                intent.putExtra("customer_id", customerList.get(position).getCustomer_id());
                intent.putExtra("customer_address", customerList.get(position).getAddress());
                intent.putExtra("customer_contact", customerList.get(position).getContact());
                intent.putExtra("customer_gender", customerList.get(position).getGender());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }


    public interface CustomerListEditCallback{
        void editCustomerData(int postion, View view);
        void deleteData(int position);
    }

    public void setCallBack(CustomerListEditCallback editCallback){
        this.editCallback = editCallback;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, contact;
        ImageView editUser, deleteUser;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.customer_name);
            contact = itemView.findViewById(R.id.customer_contact);
            editUser = itemView.findViewById(R.id.edit_user);
            deleteUser = itemView.findViewById(R.id.delete_user);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
