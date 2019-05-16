package com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers;

import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.EntryModel;

import java.util.List;

/**
 * Created by A.Elkhami on 11,April,2019
 */
public interface HomeNotifier extends BaseNotifier {
    void displayPasswordsList(List<EntryModel> passwordEntries);
}
