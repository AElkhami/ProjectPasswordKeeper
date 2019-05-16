package com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers;

import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.SubEntryModel;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public interface EntryDetailsNotifier extends BaseNotifier {
    void onSelectedSubEntryReceived(SubEntryModel subEntry);
}
