package com.elkhamitechnologies.projectkeeper.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.elkhamitechnologies.projectkeeper.R;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.EntryModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Ahmed on 1/16/2017.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.MyViewHolder>
        implements Filterable {

    private List<EntryModel> emailList;
    private List<EntryModel> emailListFiltered;

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
        this.emailListFiltered = emailList;
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

        EntryModel entryModel = emailListFiltered.get(position);
        holder.rid = (int) emailList.get(position).getRowId();
        holder.name.setText(entryModel.getName());
        holder.user_name.setText(entryModel.getUserName());
        holder.created.setText(entryModel.getCreatedAt());
//        holder.itemImage.setImageResource(R.mipmap.credit_card);

    }

    @Override
    public int getItemCount() {
        return emailListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    emailListFiltered = emailList;
                } else {
                    List<EntryModel> filteredList = new ArrayList<>();
                    for (EntryModel row : emailList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getUserName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    emailListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = emailListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                emailListFiltered = (ArrayList<EntryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}



