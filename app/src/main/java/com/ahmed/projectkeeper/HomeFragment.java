package com.ahmed.projectkeeper;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ahmed.sqlite.helper.DatabaseHelper;
import com.ahmed.sqlite.model.EmailModel;

import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Button FAB;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private SessionManager session;
    private EmailAdapter mAdapter;
    private long userId;
    private boolean fromListView,doubleBackToExitPressedOnce = false;
    private HashMap<String, Long> rid;
    private List<EmailModel> Email ;


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

        //Floating Action Button
        FAB = (Button) activity.findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show PopUp Menu
                showPopupMenu(v);
            }
        });

        //Populate data from sqlite DB to Recycler View
        recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view);

        db = new DatabaseHelper(activity.getApplicationContext());
        session = new SessionManager(activity.getApplicationContext());


        rid = session.getRowDetails();
        userId = rid.get(session.KEY_ID);
        Email = db.getContacts(userId);

        mAdapter = new EmailAdapter(Email);

        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        //Declare List Item Lines
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(activity.getApplicationContext(), LinearLayoutManager.VERTICAL));

        //on list item click
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(activity.getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EmailModel emailModel = Email.get(position);
                fromListView = true;
                Intent mIntent = new Intent (activity.getApplicationContext(),EmailMainActivity.class);
                mIntent.putExtra("boolean", fromListView);
                mIntent.putExtra("long", emailModel.getE_row_id());

                startActivity(mIntent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }

    public void showPopupMenu(View view){

        PopupMenu popup = new PopupMenu(getActivity(), view);

        MenuInflater inflater = popup.getMenuInflater();

        inflater.inflate(R.menu.add_fab_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_Email:
                        Intent myIntent = new Intent (getActivity(),EmailCreateForm.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(myIntent);
                        return true;
                    case R.id.id_BankAccount:
//                        Intent myIntent2 = new Intent (getActivity(),UserProfile.class);
//                        startActivity(myIntent2);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Refresh Recycler View after resuming
        Email.clear();
        Email.addAll(db.getContacts(userId));
        mAdapter.notifyDataSetChanged();

        doubleBackToExitPressedOnce = false;
    }
}
