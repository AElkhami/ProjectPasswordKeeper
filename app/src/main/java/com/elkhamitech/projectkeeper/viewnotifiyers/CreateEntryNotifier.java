package com.elkhamitech.projectkeeper.viewnotifiyers;

/**
 * Created by A.Elkhami on 14,April,2019
 */
public interface CreateEntryNotifier extends BasePresenterListener {
    void onNewEntryCreated(long rowId);
}
