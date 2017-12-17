package com.elkhamitech.projectkeeper.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.sqlite.model.EmailModel;

import java.util.List;

/**
 * Created by Ahmed on 1/16/2017.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.MyViewHolder> {

    private List<EmailModel> emailList;

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

    public EntriesAdapter(List<EmailModel> emailList) {
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

        EmailModel emailModel = emailList.get(position);
        holder.rid = (int) emailList.get(position).getE_row_id();
        holder.name.setText(emailModel.getE_name());
        holder.user_name.setText(emailModel.getE_user_name());
        holder.created.setText(emailModel.getCreated_at());
//        holder.itemImage.setImageResource(R.mipmap.credit_card);

    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

}



