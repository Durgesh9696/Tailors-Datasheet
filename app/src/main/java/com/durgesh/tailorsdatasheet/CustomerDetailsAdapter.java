package com.durgesh.tailorsdatasheet;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.durgesh.tailorsdatasheet.model.CustomerModel;

import java.util.ArrayList;

/**
 * Created by Durgesh Parekh on 01/18/18.
 */

class CustomerDetailsAdapter extends BaseAdapter {
    private ArrayList<CustomerModel> customerDetailsArrayList;
    private Context context;
    private LayoutInflater inflater;

    public CustomerDetailsAdapter(Context context,ArrayList<CustomerModel> customerDetailsArrayList) {
        this.customerDetailsArrayList = customerDetailsArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return customerDetailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return customerDetailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.inflate_list_item, null);
            holder = new Holder();
            holder.tvPersonName = v.findViewById(R.id.tvPersonName);
            holder.ivEditPesonDetail= v.findViewById(R.id.ivEditPesonDetail);
            holder.ivDeletePerson= v.findViewById(R.id.ivDeletePerson);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.tvPersonName.setText(customerDetailsArrayList.get(position).getName());
        holder.ivEditPesonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel dataToEditModel= MainActivity.getInstance()
                        .searchPerson(customerDetailsArrayList.get(position).getCustomer_id());
                MainActivity.getInstance().addOrUpdatePersonDetailsDialog(dataToEditModel,position);
            }
        });


        holder.ivDeletePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowConfirmDialog(context,customerDetailsArrayList.get(position).getCustomer_id(), position);
            }
        });
        return v;
    }

    private void ShowConfirmDialog(Context context, final int customer_id, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage("Are you sure you want to delete this record?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        MainActivity.getInstance().deletePerson(customer_id,position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    class Holder {
        TextView tvPersonName;
        ImageView ivDeletePerson, ivEditPesonDetail;
    }
}
