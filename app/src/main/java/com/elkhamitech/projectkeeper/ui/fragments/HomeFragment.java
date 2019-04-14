package com.elkhamitech.projectkeeper.ui.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.dagger.AppComponent;
import com.elkhamitech.projectkeeper.dagger.ContextModule;
import com.elkhamitech.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.presenter.HomePresenter;
import com.elkhamitech.projectkeeper.ui.activities.CreateEntryActivity;
import com.elkhamitech.projectkeeper.ui.activities.EntryActivity;
import com.elkhamitech.projectkeeper.ui.activities.MainActivity;
import com.elkhamitech.projectkeeper.ui.adapters.EntriesAdapter;
import com.elkhamitech.projectkeeper.ui.adapters.Handlers.DividerItemDecoration;
import com.elkhamitech.projectkeeper.ui.adapters.Handlers.RecyclerTouchListener;
import com.elkhamitech.projectkeeper.ui.adapters.Handlers.SwipeUtil;
import com.elkhamitech.projectkeeper.viewnotifiyers.HomeNotifier;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements HomeNotifier {

    private EntriesAdapter mAdapter;
    private long userId;
    private boolean fromListView;

    private ArrayList<EntryModel> entriesList = new ArrayList<>();

    @BindView(R.id.imageButton)
    FloatingActionButton FAB;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.txtaddmain)
    TextView txtv;

    @Inject
    HomePresenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        presenter.getPasswordsList();
    }

    private void init() {

        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(getActivity()))
                .build();

        component.inject(this);

        ((MainActivity) getActivity())
                .setActionBarTitle("Passwords");

        presenter.setListener(this);

        setAdapter(entriesList);
        setSwipeForRecyclerView();

        //Floating Action Button
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getActivity(), CreateEntryActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(myIntent);
            }
        });

    }

    private void setAdapter(List<EntryModel> passwordEntries) {

        mAdapter = new EntriesAdapter(passwordEntries);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        checkView();


        //on list item click
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                EntryModel entryModel = entriesList.get(position);
                fromListView = true;
                Intent mIntent = new Intent(getActivity().getApplicationContext(), EntryActivity.class);
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
                = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        if (mLayoutManager.getItemCount() == 0) {

            recyclerView.setVisibility(View.GONE);
            txtv.setVisibility(View.VISIBLE);

        } else {

            recyclerView.setVisibility(View.VISIBLE);
            txtv.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();

    }

    private void setSwipeForRecyclerView() {

        new SwipeUtil(getActivity(), recyclerView) {
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

        new AlertDialog.Builder(getActivity())
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //todo preform deletion

                        presenter.deletePasswordEntry(entryModel);
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
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayPasswordsList(List<EntryModel> passwordEntries) {
        entriesList.addAll(passwordEntries);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void userMessage(String msg) {

    }
}
