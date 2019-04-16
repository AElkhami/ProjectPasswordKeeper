package com.elkhamitech.projectkeeper.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.dagger.AppComponent;
import com.elkhamitech.projectkeeper.dagger.ContextModule;
import com.elkhamitech.projectkeeper.dagger.DaggerAppComponent;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.presenter.CreateEntryPresenter;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SecurityModerator;
import com.elkhamitech.projectkeeper.viewnotifiyers.CreateEntryNotifier;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEntryActivity extends BaseActivity
        implements CreateEntryNotifier {


    @BindView(R.id.contactName)
    EditText edtxtName;
    @BindView(R.id.userName)
    EditText edtxtUsrName;
    @BindView(R.id.contactPassword)
    EditText edtxtUsrPass;
    @BindView(R.id.contactwebsite)
    EditText edtxtWebsite;
    @BindView(R.id.contactnotes)
    EditText edtxtNote;

//    @BindView(R.id.created)
//    TextView txtCreated;

    private Boolean fromListView = false;

    @Inject
    CreateEntryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        ButterKnife.bind(this);

        AppComponent component = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close_white_24dp);
        }

        setTitle("Passwords");

        presenter.setListener(this);
    }

    public void createNewEntry() {

        EntryModel newEntry = new EntryModel();
        newEntry.setName(edtxtName.getText().toString());
        newEntry.setUserName(edtxtUsrName.getText().toString());
        newEntry.setPassword( edtxtUsrPass.getText().toString());
        newEntry.setWebsite(edtxtWebsite.getText().toString());
        newEntry.setNote(edtxtNote.getText().toString());

        presenter.createNewEntry(newEntry);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_done_btn, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_done:
                createNewEntry();
                break;
            case android.R.id.home:
                finish();
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
        SecurityModerator.lockAppCheck(this);
    }

    @Override
    public void onNewEntryCreated(long rowId) {
        Intent mIntent = new Intent(CreateEntryActivity.this,
                EntryActivity.class);

        fromListView = true;
        mIntent.putExtra("boolean", fromListView);
        mIntent.putExtra("long", rowId);
        startActivity(mIntent);

        finish();
    }

    @Override
    public void userMessage(String msg) {

    }
}