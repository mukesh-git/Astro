package com.mukeshteckwani.astro.astroapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.databinding.ActivityTvGuideBinding;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_guide);
        setSupportActionBar(binding.toolbar);
        viewModel = ViewModelProviders.of(this).get(TvGuideViewModel.class);
        fetchTvGuide();
    }

    private void fetchTvGuide() {
        ArrayList<Integer> channelsIdList = getIntent().getIntegerArrayListExtra(BundleKeys.CHANNELS_LIST);
        String channelsIdListString = "&channelId=";
        for (Integer channelId : channelsIdList) {
            channelsIdListString += String.valueOf(channelId);
        }
        viewModel.getTvGuide(Commons.getCurrentTime(),Commons.addMinsToCurrentDate(Commons.DEFAULT_TIME_INTERVAL_IN_MINS),channelsIdListString).observe(
                this, tvGuideModel -> {
                    if (tvGuideModel != null) {
                        initChannelsGrid();
                    }
                }
        );
    }

    private void initChannelsGrid() {
        binding.layoutMain.rvTvGuide;
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
