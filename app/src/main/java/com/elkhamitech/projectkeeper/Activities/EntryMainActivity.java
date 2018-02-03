package com.elkhamitech.projectkeeper.Activities;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.AccessHandler.AESHelper;
import com.elkhamitech.projectkeeper.Adapters.SubEntriesAdapter;
import com.elkhamitech.projectkeeper.Adapters.Handlers.DividerItemDecoration;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.Adapters.Handlers.RecyclerTouchListener;
import com.elkhamitech.projectkeeper.AccessHandler.SecurityModerator;
import com.elkhamitech.sqlite.helper.DatabaseHelper;
import com.elkhamitech.sqlite.model.EmailModel;
import com.elkhamitech.sqlite.model.SubContactModel;

import java.util.HashMap;
import java.util.List;

public class EntryMainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {


    private DatabaseHelper db;
    private EmailModel eMail;
    private EditText edtxtName, edtxtUsrName, edtxtUsrPass, edtxtWebsite, edtxtNotes;
    private TextView txtCreated, txtv;
    private String cName, cUsrName, cPass, cWebsite, cNote;
    private long UserId, row_id;
    private HashMap<String, Long> rid;
    private KeyListener mKeyListener1, mKeyListener2, mKeyListener3, mKeyListener4, mKeyListener5;
    private boolean fromListView, onEditPressed = false;
    private RecyclerView recyclerView;
    private FloatingActionButton SUB_FAB;
    private ImageButton expand, collapse;
    private List<SubContactModel> SubModel;
    private SubEntriesAdapter mAdapter;
    private AppBarLayout appBarLayout;
    private TextInputLayout textLayoutWeb, textLayoutNotes;

    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_main);

        //===============================Declaration===============================================

        edtxtName = (EditText) findViewById(R.id.contactName);
        edtxtUsrName = (EditText) findViewById(R.id.userName);
        edtxtUsrPass = (EditText) findViewById(R.id.Password);
        edtxtWebsite = (EditText) findViewById(R.id.website);
        edtxtNotes = (EditText) findViewById(R.id.notes);
        txtCreated = (TextView) findViewById(R.id.created);
        txtv = (TextView) findViewById(R.id.txtaddsub);

        setTitle("Passwords");

        recyclerView = (RecyclerView) findViewById(R.id.subAccountsRCV);

        row_id = getIntent().getLongExtra("long", 1L);
        fromListView = getIntent().getBooleanExtra("boolean", false);

        expand = (ImageButton) findViewById(R.id.expand);
        collapse = (ImageButton) findViewById(R.id.collapse);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        textLayoutWeb = (TextInputLayout) findViewById(R.id.websiteLayout);
        textLayoutNotes = (TextInputLayout) findViewById(R.id.notesLayout);

        appBarLayout.addOnOffsetChangedListener(this);

        //=========================================================================================

        //Adding SubAccount Floating Action Button --> go to add
        SUB_FAB = (FloatingActionButton) findViewById(R.id.SUB_FAB);
        SUB_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryMainActivity.this, SubEntryActivity.class);
                i.putExtra("long", row_id);
                startActivity(i);
            }
        });

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                expand.setVisibility(View.INVISIBLE);
                collapse.setVisibility(View.VISIBLE);
                textLayoutWeb.setVisibility(View.VISIBLE);
                textLayoutNotes.setVisibility(View.VISIBLE);
                txtCreated.setVisibility(View.VISIBLE);

            }
        });

        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expand.setVisibility(View.VISIBLE);
                collapse.setVisibility(View.INVISIBLE);
                textLayoutWeb.setVisibility(View.GONE);
                textLayoutNotes.setVisibility(View.GONE);
                txtCreated.setVisibility(View.GONE);
            }
        });


        //if coming from RecyclerView Item (View Existing Contact)
        if (fromListView) {

            showContact();
            showSubContact();

            Toast.makeText(this, "Double tap to copy the text", Toast.LENGTH_SHORT).show();


        } else {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
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
        SUB_FAB.setVisibility(View.VISIBLE);

        db = new DatabaseHelper(getApplicationContext());
        db.getOneContact(row_id);
        edtxtName.setText(db.getOneContact(row_id).getE_name());
        edtxtUsrName.setText(db.getOneContact(row_id).getE_user_name());

        try {
            normalTextDec = AESHelper.decrypt(seedValue, db.getOneContact(row_id).getE_password());
        } catch (Exception e) {
            e.printStackTrace();
        }

        edtxtUsrPass.setText(normalTextDec);
        edtxtWebsite.setText(db.getOneContact(row_id).getE_website());
        edtxtNotes.setText(db.getOneContact(row_id).getE_note());
        txtCreated.setText("Last Updated " + db.getOneContact(row_id).getCreated_at());
    }

    public void showSubContact() {

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

        SubModel = db.getSubContacts(row_id);

        mAdapter = new SubEntriesAdapter(SubModel);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SubContactModel subModel = SubModel.get(position);
                fromListView = true;
                Intent mIntent = new Intent(EntryMainActivity.this, SubEntryActivity.class);
                mIntent.putExtra("boolean", fromListView);
                mIntent.putExtra("long", subModel.getS_row_id());

                startActivity(mIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        if (mLayoutManager.getItemCount() == 0) {
            //Do something

        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtv.setVisibility(View.GONE);
        }
    }

    public void onDelete() {


        new AlertDialog.Builder(EntryMainActivity.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        db = new DatabaseHelper(getApplicationContext());
                        row_id = getIntent().getLongExtra("long", 1L);
                        db.deleteContact(row_id);

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

        row_id = getIntent().getLongExtra("long", 1L);


        db = new DatabaseHelper(getApplicationContext());
        eMail = new EmailModel();

        cName = edtxtName.getText().toString();
        cUsrName = edtxtUsrName.getText().toString();
        cPass = edtxtUsrPass.getText().toString();
        cWebsite = edtxtWebsite.getText().toString();
        cNote = edtxtNotes.getText().toString();

        eMail.setE_row_id(row_id);
        eMail.setE_name(cName);
        eMail.setE_user_name(cUsrName);

        try {
            normalTextEnc = AESHelper.encrypt(seedValue, cPass);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        eMail.setE_password(normalTextEnc);
        eMail.setE_website(cWebsite);
        eMail.setE_note(cNote);

        db.updateContact(eMail);
        disableEditText();
        onEditPressed = false;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtxtName.getWindowToken(), 0);
    }

    public void onCancel() {
        disableEditText();
        onEditPressed = false;


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
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
                        Toast.makeText(EntryMainActivity.this, "Text Copied", Toast.LENGTH_SHORT).show();

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

        //work around provided on the up button
        //Refresh Recycler View after resuming
        SubModel.clear();
        SubModel.addAll(db.getSubContacts(row_id));
        mAdapter.notifyDataSetChanged();

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        if (mLayoutManager.getItemCount() == 0) {
            //Do something
            txtv.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtv.setVisibility(View.GONE);
        }


        SecurityModerator.lockAppCheck(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
            // Collapsed
            SUB_FAB.setVisibility(View.GONE);

        } else if (verticalOffset == 0) {
            // Expanded
            SUB_FAB.setVisibility(View.VISIBLE);

        } else {
            // Somewhere in between
            SUB_FAB.setVisibility(View.VISIBLE);
        }
    }

}