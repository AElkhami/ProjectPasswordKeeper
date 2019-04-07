package com.elkhamitech.projectkeeper.ui.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;

import java.util.List;

/**
 * Created by Ahmed on 1/16/2017.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.MyViewHolder> {

    private List<EntryModel> emailList;

//----------------------------ViewHolder------------------------------------------------------

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, user_name, created;
        public ImageView itemImage;
        int rid;


        public MyViewHolder(final View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.itemText);
            user_name = (TextView) view.findViewById(R.id.itemText2);
            created = (TextView) view.findViewById(R.id.itemDate);
            itemImage = (ImageView) view.findViewById(R.id.itemImg);

        }
    }

    //----------------------------------------------------------------------------------

    public EntriesAdapter(List<EntryModel> emailList) {
        this.emailList = emailList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from
                (parent.getContext()).inflate(R.layout.mail_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

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



