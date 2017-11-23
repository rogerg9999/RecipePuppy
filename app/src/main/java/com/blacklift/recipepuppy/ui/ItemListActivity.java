package com.blacklift.recipepuppy.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import com.blacklift.recipepuppy.R;
import com.blacklift.recipepuppy.domain.model.Search;
import com.blacklift.recipepuppy.domain.model.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class ItemListActivity extends AppCompatActivity implements HasSupportFragmentInjector, ItemClickListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SearchView searchView;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.item_list)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.emptyView)
    View emptyView;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        setupRecyclerView(recyclerView);

        initViewModel();

        observeSearch();
    }

    private void initViewModel() {
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
    }

    private void observeSearch(){
        searchViewModel.search().observe(ItemListActivity.this, resource -> {
            if(resource.status == Status.LOADING){
                progressBar.setVisibility(View.VISIBLE);
                showEmptyView(false);
            }else{
                progressBar.setVisibility(View.INVISIBLE);
                List<Search> list = resource.data != null ? resource.data : new ArrayList<>();
                showEmptyView(list.size() == 0);
                swapAdapter(list);
            }

        });
    }

    void showEmptyView(boolean show){
        emptyView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(new ArrayList<>(), mTwoPane, this));
        showEmptyView(true);
    }

    private void swapAdapter(List<Search> items) {
        SimpleItemRecyclerViewAdapter adapter = new SimpleItemRecyclerViewAdapter(items, mTwoPane, this);
        recyclerView.swapAdapter(adapter, false);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.search, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchViewModel.setQuery(s);

                return false;
            }
        });
        return true;
    }



    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onItemClick(Search search) {
        if(mTwoPane){
            new Router(getSupportFragmentManager()).goToDetailFragment(search.getHref());
        }else{
            Intent i = ItemDetailActivity.newIntent(this, search.getHref());
            startActivity(i);
        }
    }
}
