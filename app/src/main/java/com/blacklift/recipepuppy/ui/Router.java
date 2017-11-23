package com.blacklift.recipepuppy.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.blacklift.recipepuppy.R;

/**
 * Created by rogergarzon on 22/11/17.
 */

public class Router {
    private FragmentManager fragmentManager;

    public Router(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void goToDetailFragment(String url){
        Fragment f = ItemDetailFragment.newInstance(url);
        fragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, f)
                .commit();
    }
}
