package com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public interface CreateEntryNotifier extends BaseNotifier {
    void onNewEntryCreated(long rowId);
}
