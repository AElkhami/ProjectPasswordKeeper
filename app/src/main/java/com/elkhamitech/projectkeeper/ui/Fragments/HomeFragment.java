package com.elkhamitech.projectkeeper.ui.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.ui.Activities.CreateNewEntry;
import com.elkhamitech.projectkeeper.ui.Activities.EntryMainActivity;
import com.elkhamitech.projectkeeper.ui.Activities.MainActivity;
import com.elkhamitech.projectkeeper.Adapters.EntriesAdapter;
import com.elkhamitech.projectkeeper.Adapters.Handlers.DividerItemDecoration;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.Adapters.Handlers.RecyclerTouchListener;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;
import com.elkhamitech.projectkeeper.Adapters.Handlers.SwipeUtil;
import com.elkhamitech.data.helper.DatabaseHelper;
import com.elkhamitech.data.model.EntryModel;

import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //    private Button FAB;
    private FloatingActionButton FAB;
    private EntryModel eMail;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private EntriesAdapter mAdapter;
    private long userId;
    private boolean fromListView, doubleBackToExitPressedOnce = false;
    private HashMap<String, Long> rid;
    private List<EntryModel> Email;
    private TextView txtv;


//    final Activity activity = getActivity();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        final Activity activity = getActivity();

        eMail = new EntryModel();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Passwords");


        //Floating Action Button
        FAB = (FloatingActionButton) activity.findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show PopUp Menu
//                showPopupMenu(v);

                Intent myIntent = new Intent(getActivity(), CreateNewEntry.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(myIntent);
            }
        });

        //Populate data from sqlite DB to Recycler View
        recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view);

        db = new DatabaseHelper(activity.getApplicationContext());

        rid = SessionManager.getRowDetails();
        userId = rid.get(SessionManager.KEY_ID);
        Email = db.getContacts(userId);

        mAdapter = new EntriesAdapter(Email);

        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        //Declare List Item Lines
        checkView();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(activity.getApplicationContext(), LinearLayoutManager.VERTICAL));

        //on list item click
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(activity.getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EntryModel entryModel = Email.get(position);
                fromListView = true;
                Intent mIntent = new Intent(activity.getApplicationContext(), EntryMainActivity.class);
                mIntent.putExtra("boolean", fromListView);
                mIntent.putExtra("long", entryModel.getRowId());

                startActivity(mIntent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));



    }

    public void checkView(){

        txtv = (TextView)getActivity().findViewById(R.id.txtaddmain);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        if( mLayoutManager.getItemCount() == 0 ){
            //Do something
            recyclerView.setVisibility(View.GONE);
            txtv.setVisibility(View.VISIBLE);

        }else {

            recyclerView.setVisibility(View.VISIBLE);
            txtv.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();

    }

//    public void showPopupMenu(View view){
//
//        PopupMenu popup = new PopupMenu(getActivity(), view);
//
//        MenuInflater inflater = popup.getMenuInflater();
//
//        inflater.inflate(R.menu.add_fab_menu, popup.getMenu());

//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
//
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                switch (item.getItemId()) {
//                    case R.id.id_Email:
//                        Intent myIntent = new Intent (getActivity(),CreateNewEntry.class);
//                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        startActivity(myIntent);
//                        return true;
//                    case R.id.id_BankAccount:
////                        Intent myIntent2 = new Intent (getActivity(),UserProfile.class);
////                        startActivity(myIntent2);
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });
//        popup.show();
//    }

//    private List<String> getData() {
//        List<String> modelList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            modelList.add("data item : " + i);
//        }
//        return modelList;
//    }


    private void setSwipeForRecyclerView() {
        SwipeUtil swipeHelper = new SwipeUtil(getActivity(), recyclerView) {
            @Override
            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeUtil.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeUtil.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {
                                // TODO: onDelete

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Delete entry")
                                        .setMessage("Are you sure you want to delete this entry?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                db = new DatabaseHelper(getActivity().getApplicationContext());
                                                db.deleteContact(Email.get(pos).getRowId());
                                                Email.remove(pos);
                                                mAdapter.notifyItemRemoved(pos);

                                                if (Email.isEmpty()){

                                                    recyclerView.setVisibility(View.GONE);
                                                    txtv.setVisibility(View.VISIBLE);

                                                }else {

                                                    recyclerView.setVisibility(View.VISIBLE);
                                                    txtv.setVisibility(View.GONE);
                                                }
//
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        })
//                .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();
                            }
                        }
                ));

//                underlayButtons.add(new SwipeUtil.UnderlayButton(
//                        "Transfer",
//                        0,
//                        Color.parseColor("#FF9502"),
//                        new SwipeUtil.UnderlayButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) {
//                                // TODO: OnTransfer
//                            }
//                        }
//                ));
//                underlayButtons.add(new SwipeUtil.UnderlayButton(
//                        "Unshare",
//                        0,
//                        Color.parseColor("#C7C7CB"),
//                        new SwipeUtil.UnderlayButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) {
//                                // TODO: OnUnshare
//                            }
//                        }
//                ));
            }
        };

    }



    @Override
    public void onResume() {
        super.onResume();
        //Refresh Recycler View after resuming
        Email.clear();
        Email.addAll(db.getContacts(userId));

        doubleBackToExitPressedOnce = false;

        mAdapter = new EntriesAdapter(Email);
        recyclerView.setAdapter(mAdapter);

        checkView();

        mAdapter.notifyDataSetChanged();

        setSwipeForRecyclerView();

    }
}
