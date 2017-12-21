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
import com.mukeshteckwani.astro.astroapp.utils.Commons;
import com.mukeshteckwani.astro.astroapp.viewmodel.TvGuideViewModel;

import java.util.ArrayList;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

public class TvGuideActivity extends AppCompatActivity {

    private ActivityTvGuideBinding binding;
    private Menu menu;
    private TvGuideViewModel viewModel;
    private boolean isLoading;

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
        ArrayList<Integer> channelsIdList = getIntent().getIntegerArrayListExtra(BundleKeys.CHANNELS_LIST);
        StringBuilder channelsIdListString = new StringBuilder("&channelId=");
        for (Integer channelId : channelsIdList) {
            channelsIdListString.append(String.valueOf(channelId));
            if (channelsIdList.indexOf(channelId) < channelsIdList.size() - 1) {
                channelsIdListString.append(",");
            }
        }
        binding.pb.setVisibility(View.VISIBLE);
        viewModel.getTvGuide(Commons.getCurrentTime(), Commons.addMinsToCurrentDate(Commons.DEFAULT_TIME_INTERVAL_IN_MINS), channelsIdListString.toString()).observe(
                this, tvGuideModel -> {
                    if (tvGuideModel != null) {
                        initChannelsGrid(tvGuideModel);
                    }
                    binding.pb.setVisibility(View.GONE);
                }
        );
    }

    private void initChannelsGrid(TvGuideModel tvGuideModel) {
        binding.layoutMain.rvTvGuide.setLayoutManager(new GridLayoutManager(this, 2));
        binding.layoutMain.rvTvGuide.setAdapter(new TvGuideAdapter(tvGuideModel.getGetevent()));
        binding.layoutMain.rvTvGuide.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position < tvGuideModel.getGetevent().size())
                            return 2;
                        return 1;
                    }
                });

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (isLoading) {
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_tv_guide, menu);
        return true;
    }

}
