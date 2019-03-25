package com.elkhamitech.projectkeeper.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;

import java.util.List;

/**
 * Created by Ahmed on 1/24/2017.
 */

public class SubEntriesAdapter extends RecyclerView.Adapter<SubEntriesAdapter.MyViewHolder> {

    private List<SubEntryModel> subContactList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,user_name,created;
        int rid;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.itemText);
            user_name = (TextView) view.findViewById(R.id.itemText2);
            created = (TextView) view.findViewById(R.id.itemDate);

        }

    }

    public SubEntriesAdapter(List<SubEntryModel> subContactList) {
        this.subContactList = subContactList;
    }

    @Override
    public SubEntriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mail_list_item, parent, false);

        return new SubEntriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubEntriesAdapter.MyViewHolder holder, int position) {

        SubEntryModel subModel = subContactList.get(position);
        holder.rid = (int) subContactList.get(position).getRowId();
        holder.name.setText(subModel.getName());
        holder.user_name.setText(subModel.getUserName());
        holder.created.setText(subModel.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return subContactList.size();
    }


}
