package com.mukeshteckwani.astro.astroapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.adapter.TvGuideAdapter;
import com.mukeshteckwani.astro.astroapp.databinding.ActivityTvGuideBinding;
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;
import com.mukeshteckwani.astro.astroapp.utils.BundleKeys;
import com.mukeshteckwani.astro.astroapp.utils.Constants;
import com.mukeshteckwani.astro.astroapp.utils.SpacesItemDecoration;
import com.mukeshteckwani.astro.astroapp.viewmodel.TvGuideViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

public class TvGuideActivity extends AppCompatActivity {

    private ActivityTvGuideBinding binding;
    private Menu menu;
    private TvGuideViewModel viewModel;
    private boolean isLoaderShowing = false;
    private TvGuideAdapter mAdapter;
    private List<TvGuideModel.Getevent> mEventsList;
    private int sortOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_guide);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.tv_guide);
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
                        mEventsList = tvGuideModel.getGetevent();
                        viewModel.incrementCurrentPage();
                    }
                    binding.pb.setVisibility(View.GONE);
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.name_ascending:
                sort(Constants.SORT_NAME_ASC, mAdapter, mEventsList);
                return true;

            case R.id.name_descending:
                sort(Constants.SORT_NAME_DESC, mAdapter, mEventsList);
                return true;

            case R.id.channel_no_ascending:
                sort(Constants.SORT_ID_ASC, mAdapter, mEventsList);
                return true;

            case R.id.channel_no_descending:
                sort(Constants.SORT_ID_DESC, mAdapter, mEventsList);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sort(int sortOrder, TvGuideAdapter channelsAdapter, List<TvGuideModel.Getevent> events) {
        if (events == null || events.size() == 0)
            return;
        viewModel.setSortOrder(sortOrder);
        Handler handler = new Handler();
        MenuItem sortItem = menu.findItem(R.id.sort_order);
        binding.pb.setVisibility(View.VISIBLE);
        switch (sortOrder) {
            case Constants.SORT_NAME_ASC:
                sortItem.setTitle("Sort Order:Channel Name Ascending");
                new Thread(() -> {
                    Collections.sort(events, (o1, o2) -> o1.getChannelTitle().compareTo(o2.getChannelTitle()));
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_NAME_DESC:
                sortItem.setTitle("Sort Order:Channel Name Descending");
                new Thread(() -> {
                    Collections.sort(events, (o1, o2) -> o1.getChannelTitle().compareTo(o2.getChannelTitle()));
                    Collections.reverse(events);
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_ID_ASC:
                sortItem.setTitle("Sort Order: Channel No. Ascending");
                new Thread(() -> {
                    Collections.sort(events, (o1, o2) -> o1.getChannelId() - o2.getChannelId());
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_ID_DESC:
                sortItem.setTitle("Sort Order: Channel No. Descending");
                new Thread(() -> {
                    Collections.sort(events, (o1, o2) -> o1.getChannelId() - o2.getChannelId());
                    Collections.reverse(events);
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;
        }
        binding.pb.setVisibility(View.GONE);
    }

    private void initChannelsGrid(TvGuideModel tvGuideModel) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.layoutMain.rvTvGuide.setLayoutManager(layoutManager);
        mAdapter = new TvGuideAdapter(tvGuideModel.getGetevent());
        binding.layoutMain.rvTvGuide.setAdapter(mAdapter);
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
                if (viewModel.getSortOrder() != 0) {
                    sort(viewModel.getSortOrder(),mAdapter,mEventsList);
                }
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
