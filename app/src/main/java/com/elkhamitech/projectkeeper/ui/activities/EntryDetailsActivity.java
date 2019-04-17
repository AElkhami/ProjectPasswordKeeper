package com.elkhamitech.projectkeeper.ui.activities;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.dagger.AppComponent;
import com.elkhamitech.projectkeeper.dagger.ContextModule;
import com.elkhamitech.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;
import com.elkhamitech.projectkeeper.presenter.EntryDetailsPresenter;
import com.elkhamitech.projectkeeper.ui.viewnotifiyers.EntryDetailsNotifier;
import com.elkhamitech.projectkeeper.utils.accesshandler.SecurityModerator;

import javax.inject.Inject;

import androidx.appcompat.widget.PopupMenu;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EntryDetailsActivity extends BaseActivity implements EntryDetailsNotifier {

    @BindView(R.id.contactName)
    EditText edtxtName;
    @BindView(R.id.userName)
    EditText edtxtUsrName;
    @BindView(R.id.contactPassword)
    EditText edtxtUsrPass;
    @BindView(R.id.contactwebsite)
    EditText edtxtWebsite;
    @BindView(R.id.contactnotes)
    EditText edtxtNotes;
    @BindView(R.id.supCreated)
    TextView txtCreated;

    private long parentId;

    private boolean fromList, onEditPressed = false;

    private KeyListener mKeyListener1;
    private KeyListener mKeyListener2;
    private KeyListener mKeyListener3;
    private KeyListener mKeyListener4;
    private KeyListener mKeyListener5;

    private SubEntryModel subEntryModel;

    private InputMethodManager imm;
    private long selectedId;

    @Inject
    EntryDetailsPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        ButterKnife.bind(this);
        setTitle("Passwords");

        init();

        presenter.setListener(this);

        if (fromList) {
            disableEditText();
            presenter.getSelectedSubEntry(selectedId);
            onCopy();
        }
    }

    private void init() {
        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);

        parentId = getIntent().getLongExtra("long", 1L);
        selectedId = getIntent().getLongExtra("selectedId", 1L);
        fromList = getIntent().getBooleanExtra("boolean", false);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
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
                        Toast.makeText(EntryDetailsActivity.this, "Text Copied", Toast.LENGTH_SHORT).show();

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

    public void whenDone() {
        createUpdateObject();
        presenter.createSubEntry(subEntryModel);
        finish();
    }

    public void onDelete() {

        new AlertDialog.Builder(EntryDetailsActivity.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        presenter.deleteSelectedSubEntry(subEntryModel);
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

    private void createUpdateObject() {

        if (subEntryModel == null) {
            subEntryModel = new SubEntryModel();
            subEntryModel.setParentId(parentId);
        }
        subEntryModel.setName(edtxtName.getText().toString());
        subEntryModel.setUserName(edtxtUsrName.getText().toString());
        subEntryModel.setPassword(edtxtUsrPass.getText().toString());
        subEntryModel.setWebsite(edtxtWebsite.getText().toString());
        subEntryModel.setNote(edtxtNotes.getText().toString());
    }

    public void onUpdateDone() {

        createUpdateObject();

        presenter.editSelectedSubEntry(subEntryModel);

        disableEditText();
        onEditPressed = false;


        imm.hideSoftInputFromWindow(edtxtName.getWindowToken(), 0);
    }

    public void onCancel() {
        disableEditText();
        onEditPressed = false;

        imm.hideSoftInputFromWindow(edtxtName.getWindowToken(), 0);
    }

    public void disableEditText() {
//        edtxtName.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener1 = edtxtName.getKeyListener();
        edtxtName.setKeyListener(null);

//        edtxtUsrName.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener2 = edtxtUsrName.getKeyListener();
        edtxtUsrName.setKeyListener(null);

//        edtxtUsrPass.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener3 = edtxtUsrPass.getKeyListener();
        edtxtUsrPass.setKeyListener(null);

        mKeyListener4 = edtxtWebsite.getKeyListener();
        edtxtWebsite.setKeyListener(null);

        mKeyListener5 = edtxtNotes.getKeyListener();
        edtxtNotes.setKeyListener(null);
    }

    public void enableEditText() {
        imm.showSoftInput(edtxtName, 0);

        edtxtName.setSelection(edtxtName.getText().length());

        edtxtName.setTextColor(Color.parseColor("#000000"));
        edtxtUsrName.setTextColor(Color.parseColor("#000000"));
        edtxtUsrPass.setTextColor(Color.parseColor("#000000"));
        edtxtWebsite.setTextColor(Color.parseColor("#000000"));
        edtxtNotes.setTextColor(Color.parseColor("#000000"));

        edtxtName.setKeyListener(mKeyListener1);
        edtxtUsrName.setKeyListener(mKeyListener2);
        edtxtUsrPass.setKeyListener(mKeyListener3);
        edtxtWebsite.setKeyListener(mKeyListener4);
        edtxtNotes.setKeyListener(mKeyListener5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        fromList = getIntent().getBooleanExtra("boolean", false);

        if (onEditPressed) {
            inflater.inflate(R.menu.actionbar_updone_btn, menu);
        } else if (fromList) {
            inflater.inflate(R.menu.actionbar_edt_delt, menu);
        } else {
            inflater.inflate(R.menu.actionbar_done_btn, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_done:
                whenDone();
                return true;
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
            // work around to handel on resume on EntryActivity.class
            case android.R.id.home:
                finish();
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
        SecurityModerator.lockAppCheck(this);
    }

    @Override
    public void onSelectedSubEntryReceived(SubEntryModel subEntry) {
        this.subEntryModel = subEntry;

        edtxtName.setText(subEntry.getName());
        edtxtUsrName.setText(subEntry.getUserName());
        edtxtUsrPass.setText(subEntry.getPassword());
        edtxtWebsite.setText(subEntry.getWebsite());
        edtxtNotes.setText(subEntry.getNote());
        txtCreated.setText("Last Updated " + subEntry.getCreatedAt());
    }
}
