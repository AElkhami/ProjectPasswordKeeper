package com.elkhamitech.projectkeeper.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Ahmed on 1/16/2017.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.MyViewHolder> {

    private List<EntryModel> emailList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, user_name, created;
//        private ImageView itemImage;
        int rid;

        private MyViewHolder(final View view) {
            super(view);
            name = view.findViewById(R.id.itemText);
            user_name = view.findViewById(R.id.itemText2);
            created = view.findViewById(R.id.itemDate);
//            itemImage = view.findViewById(R.id.itemImg);

        }
    }

    public EntriesAdapter(List<EntryModel> emailList) {
        this.emailList = emailList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from
                (parent.getContext()).inflate(R.layout.mail_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        EntryModel entryModel = emailList.get(position);
        holder.rid = (int) emailList.get(position).getRowId();
        holder.name.setText(entryModel.getName());
        holder.user_name.setText(entryModel.getUserName());
        holder.created.setText(entryModel.getCreatedAt());
//        holder.itemImage.setImageResource(R.mipmap.credit_card);

    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

}



