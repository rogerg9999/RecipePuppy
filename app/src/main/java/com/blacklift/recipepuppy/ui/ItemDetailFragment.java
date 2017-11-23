package com.blacklift.recipepuppy.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacklift.recipepuppy.R;
import com.blacklift.recipepuppy.di.injector.Injectable;
import com.blacklift.recipepuppy.domain.model.Search;
import com.blacklift.recipepuppy.domain.model.Status;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;


public class ItemDetailFragment extends Fragment implements Injectable{

    public static final String URL = "URL";


    @Nullable
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout appBarLayout;

    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.content)
    TextView contentView;
    @BindView(R.id.url)
    TextView urlView;
    private String url;
    private SearchViewModel searchViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static Fragment newInstance(String url){
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString(URL, url);
        fragment.setArguments(arguments);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
        if (appBarLayout != null) {
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        ButterKnife.bind(this, rootView);
        initViewModel();
        observeSearchDetail();
        return rootView;
    }

    private void getArgs(){
        Bundle args = getArguments();
        url = args.getString(URL);
    }

    void initViewModel(){
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
    }

    void observeSearchDetail(){
        searchViewModel.getDetail(url).observe(this, resource -> {
            if(resource.status == Status.SUCCESS){
                Search search = resource.data;
                if(search != null){
                    titleView.setText(search.getTitle());
                    contentView.setText(search.getIngredients());
                    urlView.setText(search.getHref());

                }
            }
        });
    }
}
