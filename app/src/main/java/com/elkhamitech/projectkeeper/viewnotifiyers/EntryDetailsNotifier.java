package com.elkhamitech.projectkeeper.viewnotifiyers;

import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;

import java.util.List;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public interface EntryDetailsNotifier extends BasePresenterListener {
    void onSelectedSubEntryReceived(SubEntryModel subEntry);
}
