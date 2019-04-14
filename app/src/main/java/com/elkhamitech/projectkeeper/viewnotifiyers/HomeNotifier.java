package com.elkhamitech.projectkeeper.viewnotifiyers;

import com.elkhamitech.projectkeeper.data.roomdatabase.model.EntryModel;

import java.util.List;

/**
 * Created by A.Elkhami on 11,April,2019
 */
public interface HomeNotifier extends BasePresenterListener{
    void displayPasswordsList(List<EntryModel> passwordEntries);
}
