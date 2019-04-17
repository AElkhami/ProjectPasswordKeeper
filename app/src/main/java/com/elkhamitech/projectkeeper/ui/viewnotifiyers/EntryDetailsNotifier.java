package com.elkhamitech.projectkeeper.ui.viewnotifiyers;

import com.elkhamitech.projectkeeper.data.roomdatabase.model.SubEntryModel;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public interface EntryDetailsNotifier extends BaseNotifier {
    void onSelectedSubEntryReceived(SubEntryModel subEntry);
}
