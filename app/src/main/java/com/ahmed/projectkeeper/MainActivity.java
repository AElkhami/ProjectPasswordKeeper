package com.ahmed.projectkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmed.sqlite.helper.DatabaseHelper;
import com.ahmed.sqlite.model.EmailModel;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button FAB;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private SessionManager session;
    private EmailAdapter mAdapter;
    private long userId;
    private boolean fromListView,doubleBackToExitPressedOnce = false;
    private HashMap<String, Long> rid;
    private List<EmailModel> Email ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Floating Action Button
        FAB = (Button) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show PopUp Menu
                showPopupMenu(v);
            }
        });

        //Populate data from sqlite DB to Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        db = new DatabaseHelper(getApplicationContext());
        session = new SessionManager(getApplicationContext());


        rid = session.getRowDetails();
        userId = rid.get(session.KEY_ID);
        Email = db.getContacts(userId);

        mAdapter = new EmailAdapter(Email);

        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        //Declare List Item Lines
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //on list item click
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EmailModel emailModel = Email.get(position);
                fromListView = true;
                Intent mIntent = new Intent (MainActivity.this,EmailMainActivity.class);
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

        PopupMenu popup = new PopupMenu(this, view);

        MenuInflater inflater = popup.getMenuInflater();

        inflater.inflate(R.menu.add_fab_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_Email:
                        Intent myIntent = new Intent (MainActivity.this,EmailCreateForm.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(myIntent);
                        return true;
                    case R.id.id_BankAccount:
                        Intent myIntent2 = new Intent (MainActivity.this,UserProfile.class);
                        startActivity(myIntent2);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.context_delete_item, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Refresh Recycler View after resuming
        Email.clear();
        Email.addAll(db.getContacts(userId));
        mAdapter.notifyDataSetChanged();

        doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {

        //press back button twice to exit
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
