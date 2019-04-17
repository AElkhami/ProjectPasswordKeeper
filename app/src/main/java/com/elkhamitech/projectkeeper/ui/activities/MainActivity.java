package com.elkhamitech.projectkeeper.ui.activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.dagger.AppComponent;
import com.elkhamitech.projectkeeper.dagger.ContextModule;
import com.elkhamitech.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.presenter.HomePresenter;
import com.elkhamitech.projectkeeper.ui.adapters.EntriesAdapter;
import com.elkhamitech.projectkeeper.ui.adapters.handlers.RecyclerTouchListener;
import com.elkhamitech.projectkeeper.ui.adapters.handlers.SwipeUtil;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.HomeNotifier;
import com.elkhamitech.projectkeeper.utils.accesshandler.SecurityModerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements HomeNotifier {

    private EntriesAdapter mAdapter;
    private boolean fromListView;

    private ArrayList<EntryModel> entriesList = new ArrayList<>();

    @BindView(R.id.imageButton)
    FloatingActionButton FAB;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.txtaddmain)
    TextView emptyListTextView;

    @BindView(R.id.lock_imageView)
    ImageView lockImageButton;

    @BindView(R.id.screen_title)
    TextView screenTitle;

    @Inject
    HomePresenter presenter;

    private boolean doubleBackToExitPressedOnce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDagger();
        init();

        presenter.getPasswordsList();
    }

    private void initDagger() {

        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);
    }

    private void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter.setListener(this);

        setAdapter(entriesList);
        setSwipeForRecyclerView();

        lockImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        FortressGateActivity.class);
                startActivity(i);
            }
        });


        //Floating Action Button
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, CreateEntryActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(myIntent);
            }
        });

    }

    private void setAdapter(List<EntryModel> passwordEntries) {

        mAdapter = new EntriesAdapter(passwordEntries);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        checkView();

        //on list item click
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener
                (getApplicationContext(), recyclerView,
                        new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                EntryModel entryModel = entriesList.get(position);
                                fromListView = true;
                                Intent mIntent = new Intent(getApplicationContext()
                                        , EntryActivity.class);
                                mIntent.putExtra("boolean", fromListView);
                                mIntent.putExtra("long", entryModel.getRowId());

                                startActivity(mIntent);
                            }

                            @Override
                            public void onLongClick(View view, int position) {
                            }
                        }));
    }

    private void checkView() {

        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        if (mLayoutManager.getItemCount() == 0) {

            recyclerView.setVisibility(View.GONE);
            emptyListTextView.setVisibility(View.VISIBLE);

        } else {

            recyclerView.setVisibility(View.VISIBLE);
            emptyListTextView.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();

    }

    private void setSwipeForRecyclerView() {

        new SwipeUtil(this, recyclerView) {
            @Override
            public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeUtil.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeUtil.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {
                                showConfirmationDialog(entriesList.get(pos));
                            }
                        }
                ));
            }
        };

    }

    private void showConfirmationDialog(EntryModel entryModel) {

        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //todo preform deletion
                        presenter.deletePasswordEntry(entryModel);
                        entriesList.remove(entryModel);
                        mAdapter.notifyDataSetChanged();
                        checkView();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_main, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        assert manager != null;
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SecurityModerator.lockAppStoreTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;

        SecurityModerator.lockAppCheck(this);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {

        //press back button twice to exit
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    public void displayPasswordsList(List<EntryModel> passwordEntries) {
        entriesList.addAll(passwordEntries);
        mAdapter.notifyDataSetChanged();
        checkView();
    }

    @Override
    public void userMessage(String msg) {

    }
}