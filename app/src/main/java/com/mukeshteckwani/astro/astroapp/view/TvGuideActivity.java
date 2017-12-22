package com.mukeshteckwani.astro.astroapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.adapter.TvGuideAdapter;
import com.mukeshteckwani.astro.astroapp.databinding.ActivityTvGuideBinding;
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;
import com.mukeshteckwani.astro.astroapp.utils.BundleKeys;
import com.mukeshteckwani.astro.astroapp.utils.SpacesItemDecoration;
import com.mukeshteckwani.astro.astroapp.viewmodel.TvGuideViewModel;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

public class TvGuideActivity extends AppCompatActivity {

    private ActivityTvGuideBinding binding;
    private Menu menu;
    private TvGuideViewModel viewModel;
    private boolean isLoaderShowing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_guide);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = ViewModelProviders.of(this).get(TvGuideViewModel.class);
        fetchTvGuide();
    }

    private void fetchTvGuide() {
        viewModel.setChannelIds(getIntent().getIntegerArrayListExtra(BundleKeys.CHANNELS_LIST));

        binding.pb.setVisibility(View.VISIBLE);
        viewModel.getTvGuide(viewModel.getStartTime(),viewModel.getEndTime(), viewModel.getChannelIdsString()).observe(
                this, tvGuideModel -> {
                    if (tvGuideModel != null) {
                        initChannelsGrid(tvGuideModel);
                        viewModel.incrementCurrentPage();
                    }
                    binding.pb.setVisibility(View.GONE);
                }
        );
    }

    private void initChannelsGrid(TvGuideModel tvGuideModel) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.layoutMain.rvTvGuide.setLayoutManager(layoutManager);
        binding.layoutMain.rvTvGuide.setAdapter(new TvGuideAdapter(tvGuideModel.getGetevent()));
        binding.layoutMain.rvTvGuide.addItemDecoration(new SpacesItemDecoration(20));
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position >= tvGuideModel.getGetevent().size() ? 2 : 1;
            }
        });

        binding.layoutMain.rvTvGuide.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isLoaderShowing) {
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        loadMoreItems();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

        });
    }

    private void loadMoreItems() {
        isLoaderShowing = true;
        viewModel.getTvGuide(viewModel.getStartTime(),viewModel.getEndTime(),viewModel.getChannelIdsString()).observe(this, tvGuideModel -> {
            if (tvGuideModel != null) {
                TvGuideAdapter adapter = (TvGuideAdapter) binding.layoutMain.rvTvGuide.getAdapter();
                adapter.appendItems(tvGuideModel.getGetevent());
                viewModel.incrementCurrentPage();
            }
            else {
                TvGuideAdapter adapter = (TvGuideAdapter) binding.layoutMain.rvTvGuide.getAdapter();
                adapter.hideLoader();
            }
            isLoaderShowing = false;

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_tv_guide, menu);
        return true;
    }


}
