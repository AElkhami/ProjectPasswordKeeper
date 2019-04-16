package com.elkhamitech.projectkeeper.viewnotifiyers;

import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;

import java.util.List;

/**
 * Created by A.Elkhami on 15,April,2019
 */
public interface EntryNotifier extends BasePresenterListener {
    void onSubEntryListReceived(List<SubEntryModel> subEntryList);
    void onSelectedEntryReceived(EntryModel entry);
}
