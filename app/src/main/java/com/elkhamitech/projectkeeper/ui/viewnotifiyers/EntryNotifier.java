package com.elkhamitech.projectkeeper.ui.viewnotifiyers;

import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;

import java.util.List;

/**
 * Created by A.Elkhami on 15,April,2019
 */
public interface EntryNotifier extends BaseNotifier {
    void onSubEntryListReceived(List<SubEntryModel> subEntryList);
    void onSelectedEntryReceived(EntryModel entry);
}
