package com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers;

/**
 * Created by A.Elkhami on 30,March,2019
 */
public interface SetViewNotifier<E extends BaseNotifier> {
    void setListener(E listener);
}
