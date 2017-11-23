package com.blacklift.recipepuppy.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.blacklift.recipepuppy.R;
import com.blacklift.recipepuppy.domain.model.Status;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class ItemDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    public static final String URL = "URL";
    private String url;

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;


    @BindView(R.id.image)
    AppCompatImageView imageView;


    private SearchViewModel searchViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    public static Intent newIntent(Context context, String url){
        Intent i = new Intent(context, ItemDetailActivity.class);
        Bundle arguments = new Bundle();
        arguments.putString(URL, url);
        i.putExtras(arguments);
        return i;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        getArgs();
        setUpActionBar();
        initViewModel();
        observeSearchDetail();

        if (savedInstanceState == null) {

            new Router(getSupportFragmentManager()).goToDetailFragment(url);
        }
    }

    void setUpActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    void getArgs(){
        Bundle args = getIntent().getExtras();
        url = args.getString(URL);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void initViewModel(){
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
    }

    void observeSearchDetail(){
        searchViewModel.getDetail(url).observe(this, resource -> {
            if(resource.status == Status.SUCCESS){
                Glide.with(this)
                        .load(resource.data.getThumbnail())
                        .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                        .into(imageView);
            }
        });
    }


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
