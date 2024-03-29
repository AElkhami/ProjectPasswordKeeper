package com.elkhamitechnologies.projectkeeper.ui.activities;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitechnologies.projectkeeper.Constants;
import com.elkhamitechnologies.projectkeeper.R;
import com.elkhamitechnologies.projectkeeper.dagger.AppComponent;
import com.elkhamitechnologies.projectkeeper.dagger.ContextModule;
import com.elkhamitechnologies.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.SubEntryModel;
import com.elkhamitechnologies.projectkeeper.presenter.EntryPresenter;
import com.elkhamitechnologies.projectkeeper.ui.adapters.SubEntriesAdapter;
import com.elkhamitechnologies.projectkeeper.ui.adapters.handlers.RecyclerTouchListener;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.EntryNotifier;
import com.elkhamitechnologies.projectkeeper.utils.accesshandler.Ciphering;
import com.elkhamitechnologies.projectkeeper.utils.accesshandler.SecurityModerator;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EntryActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener, EntryNotifier {


    @BindView(R.id.contactName)
    EditText edtxtName;
    @BindView(R.id.userName)
    EditText edtxtUsrName;
    @BindView(R.id.Password)
    EditText edtxtUsrPass;
    @BindView(R.id.website)
    EditText edtxtWebsite;
    @BindView(R.id.notes)
    EditText edtxtNotes;
    @BindView(R.id.created)
    TextView txtCreated;
    @BindView(R.id.txtaddsub)
    TextView emptyListTextView;
    @BindView(R.id.subAccountsRCV)
    RecyclerView recyclerView;
    @BindView(R.id.SUB_FAB)
    FloatingActionButton SUB_FAB;
    @BindView(R.id.expand_btn)
    ImageButton expand;
    @BindView(R.id.collapse_btn)
    ImageButton collapse;
    @BindView(R.id.websiteLayout)
    TextInputLayout textLayoutWeb;
    @BindView(R.id.notesLayout)
    TextInputLayout textLayoutNotes;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.expandable_layout)
    ConstraintLayout expandableView;
    @BindView(R.id.shadowLayout)
    ConstraintLayout shadowView;

    @Inject
    EntryPresenter presenter;

    private SubEntriesAdapter mAdapter;
    private long parentId;
    private EntryModel entry;
    private KeyListener mKeyListener1,
            mKeyListener2, mKeyListener3,
            mKeyListener4, mKeyListener5;
    private boolean fromListView, onEditPressed = false;
    private List<SubEntryModel> SubModel = new ArrayList<>();
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_main);
        ButterKnife.bind(this);

        parentId = getIntent().getLongExtra("long", 1L);
        fromListView = getIntent().getBooleanExtra("boolean", false);

        initDagger();
        initView();

        presenter.setListener(this);
        collapse(expandableView);

        //if coming from RecyclerView Item (View Existing Contact)
        checkFromWhereTheScreenIsOpened();
    }

    private void initDagger() {
        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);
    }

    private void initView() {

        setTitle("Passwords");

        imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);

        setSubEntryAdapter();

        appBarLayout.addOnOffsetChangedListener(this);

        checkView();

        //Adding SubAccount Floating Action Button --> go to add
        SUB_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryActivity.this,
                        EntryDetailsActivity.class);
                i.putExtra("long", parentId);
                startActivity(i);
            }
        });

        //Handling expanded button
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                expand(expandableView);
                expand.setVisibility(View.INVISIBLE);
            }
        });

        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collapse(expandableView);
                expand.setVisibility(View.VISIBLE);
            }
        });
    }

    private void checkFromWhereTheScreenIsOpened() {
        if (fromListView) {

            showContact();
            presenter.getSelectedEntry(parentId);
            presenter.getSubEntries(parentId);

            Toast.makeText(this, "Double tap to copy the text",
                    Toast.LENGTH_SHORT).show();
        } else {
            edtxtName.requestFocus();
            edtxtName.setSelection(edtxtName.getText().length());
            imm.showSoftInput(edtxtName, 0);
        }
    }

    public void showContact() {
        //Show selected Contact
        disableEditText();
        onCopy();

        recyclerView.setVisibility(View.VISIBLE);
        SUB_FAB.show();
    }

    public void setSubEntryAdapter() {

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedAppbar);

        mAdapter = new SubEntriesAdapter(SubModel);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext()
                , recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SubEntryModel subModel = SubModel.get(position);
                fromListView = true;
                Intent mIntent = new Intent(EntryActivity.this, EntryDetailsActivity.class);
                mIntent.putExtra("boolean", fromListView);
                mIntent.putExtra("selectedId", subModel.getRowId());

                startActivity(mIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        if (mLayoutManager.getItemCount() != 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyListTextView.setVisibility(View.GONE);
        }
    }

    public void onDelete() {

        new AlertDialog.Builder(EntryActivity.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        presenter.deleteSelectedEntry(entry);
                        checkView();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
//                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

    public void onUpdate() {
        enableEditText();
        onEditPressed = true;
    }

    public void onUpdateDone() {

        parentId = getIntent().getLongExtra("long", 1L);

        prepareObjectToUpdate();

        presenter.editSelectedEntry(entry);

        disableEditText();
        onEditPressed = false;

        imm.hideSoftInputFromWindow(edtxtName.getWindowToken(), 0);
    }

    private void prepareObjectToUpdate() {

        entry.setRowId(parentId);
        entry.setName(edtxtName.getText().toString());
        entry.setUserName(edtxtUsrName.getText().toString());
        entry.setPassword(Ciphering.encrypt(edtxtUsrPass.getText().toString(),
                Constants.ENCRYPT_KEY));
        entry.setWebsite(edtxtWebsite.getText().toString());
        entry.setNote(edtxtNotes.getText().toString());
    }

    public void onCancel() {
        disableEditText();
        onEditPressed = false;


        imm.hideSoftInputFromWindow(edtxtName.getWindowToken(), 0);
    }

    public void disableEditText() {

        edtxtName.clearFocus();
        edtxtName.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener1 = edtxtName.getKeyListener();
        edtxtName.setKeyListener(null);

        edtxtUsrName.clearFocus();
        edtxtUsrName.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener2 = edtxtUsrName.getKeyListener();
        edtxtUsrName.setKeyListener(null);

        edtxtUsrPass.clearFocus();
        edtxtUsrPass.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener3 = edtxtUsrPass.getKeyListener();
        edtxtUsrPass.setKeyListener(null);

        edtxtWebsite.clearFocus();
        edtxtWebsite.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener4 = edtxtWebsite.getKeyListener();
        edtxtWebsite.setKeyListener(null);

        edtxtNotes.clearFocus();
        edtxtNotes.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener5 = edtxtNotes.getKeyListener();
        edtxtNotes.setKeyListener(null);
    }

    public void enableEditText() {
        imm.showSoftInput(edtxtName, 0);

        edtxtName.setSelection(edtxtName.getText().length());

        edtxtName.setTextColor(Color.parseColor("#E0E0E0"));
        edtxtUsrName.setTextColor(Color.parseColor("#E0E0E0"));
        edtxtUsrPass.setTextColor(Color.parseColor("#E0E0E0"));
        edtxtWebsite.setTextColor(Color.parseColor("#E0E0E0"));
        edtxtNotes.setTextColor(Color.parseColor("#E0E0E0"));

        edtxtName.setKeyListener(mKeyListener1);
        edtxtUsrName.setKeyListener(mKeyListener2);
        edtxtUsrPass.setKeyListener(mKeyListener3);
        edtxtWebsite.setKeyListener(mKeyListener4);
        edtxtNotes.setKeyListener(mKeyListener5);
    }

    public void copyText(final EditText tdtxt, View v) {

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_copy, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_Copy:

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tdtxt.getText().toString());
                        Toast.makeText(EntryActivity.this, "Text Copied", Toast.LENGTH_SHORT).show();

                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();

    }

    public void onCopy() {
        edtxtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtName, v);

            }
        });
        edtxtUsrName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtUsrName, v);
            }
        });
        edtxtUsrPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtUsrPass, v);
            }
        });
        edtxtWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtWebsite, v);
            }
        });
        edtxtNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtNotes, v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        fromListView = getIntent().getBooleanExtra("boolean", false);

        if (onEditPressed) {
            inflater.inflate(R.menu.actionbar_updone_btn, menu);
        } else {
            inflater.inflate(R.menu.actionbar_edt_delt, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.action_delete:
                onDelete();
                return true;
            case R.id.action_edit:
                invalidateOptionsMenu();
                onUpdate();
                return true;
            case R.id.action_update_done:
                invalidateOptionsMenu();
                onUpdateDone();
                return true;
            case R.id.action_update_cancel:
                invalidateOptionsMenu();
                onCancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem editDone = menu.findItem(R.id.action_update_done);

        if (onEditPressed) {
            editDone.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SecurityModerator.lockAppStoreTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSubEntries(parentId);
        mAdapter.notifyDataSetChanged();
        checkView();
        SecurityModerator.lockAppCheck(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
            // Collapsed
            SUB_FAB.hide();

        } else if (verticalOffset == 0) {
            // Expanded
            SUB_FAB.show();

        } else {
            // Somewhere in between
            SUB_FAB.show();
        }
    }

    private void checkView() {

        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(this.getApplicationContext());
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

    @Override
    public void onSubEntryListReceived(List<SubEntryModel> subEntryList) {
        SubModel.clear();
        SubModel.addAll(subEntryList);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelectedEntryReceived(EntryModel entry) {

        this.entry = entry;

        edtxtName.setText(entry.getName());
        edtxtUsrName.setText(entry.getUserName());
        edtxtUsrPass.setText(Ciphering.decrypt(entry.getPassword(), Constants.ENCRYPT_KEY));
//        edtxtUsrPass.setText(entry.getPassword());
        edtxtWebsite.setText(entry.getWebsite());
        edtxtNotes.setText(entry.getNote());
        txtCreated.setText("Last Updated " + entry.getCreatedAt());

    }

    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}