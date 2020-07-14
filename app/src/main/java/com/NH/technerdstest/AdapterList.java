package com.NH.technerdstest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class AdapterList  extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    private List<Contact> mCustomObjects;
    private ListListener listener;
    public interface  ListListener{
        void editItem(Contact contact, int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, email, contactNumber;
        Button edit, delete;
        ListListener mListener;
        public ViewHolder(View itemView, ListListener l){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            try {
                mListener = (ListListener) l;

            }catch (ClassCastException e){
                throw new ClassCastException(l.toString());
            }


        }
    }

    public AdapterList(List<Contact> arrayList, ListListener l){
        mCustomObjects = arrayList;
        listener = l;


    }



    @NonNull
    @Override
    public AdapterList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new AdapterList.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Contact object = mCustomObjects.get(position);
        holder.name.setText(object.firstName + " " + object.lastName);
        holder.email.setText(object.email);
        holder.contactNumber.setText(object.contactNumber);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.editItem(object, position);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCustomObjects.remove(position);
                FirebaseDatabase.getInstance().getReference("contacts").child(object.uid).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCustomObjects.size();
    }


}

