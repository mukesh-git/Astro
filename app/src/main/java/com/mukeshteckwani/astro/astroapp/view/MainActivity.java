package com.mukeshteckwani.astro.astroapp.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.adapter.ChannelsAdapter;
import com.mukeshteckwani.astro.astroapp.databinding.ActivityMainBinding;
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;
import com.mukeshteckwani.astro.astroapp.utils.BundleKeys;
import com.mukeshteckwani.astro.astroapp.utils.Constants;
import com.mukeshteckwani.astro.astroapp.utils.SimpleDividerItemDecoration;
import com.mukeshteckwani.astro.astroapp.viewmodel.ChannelsListViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private ActivityMainBinding binding;
    private ChannelsListViewModel viewModel;
    private List<ChannelsListModel.Channel> mChannels;
    private ChannelsAdapter mAllChannelsAdapter;
    private ChannelsListModel.Channel mChannel;
    private Menu menu;
    private ChannelsAdapter mFavChannelsAdapter;
    private List<ChannelsListModel.Channel> mFavChannels;
    private Integer mSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        viewModel = ViewModelProviders.of(this).get(ChannelsListViewModel.class);
        fetchChannels();
    }

    private void fetchFavouritesAndSort() {
        viewModel.getFavouriteChannels().observe(this, this::initFavAndSort);
    }

    private void initFavAndSort(List<ChannelsListModel.Channel> channelsList) {
        binding.pb.setVisibility(View.GONE);
        if (channelsList != null && channelsList.size() > 0) {//check for fav ,set using view model callback
            if (mFavChannels == null) {
                mFavChannels = channelsList;
                binding.layoutMain.rvFavourites.addItemDecoration(new SimpleDividerItemDecoration(this));
                binding.layoutMain.rvFavourites.setNestedScrollingEnabled(false);
                binding.layoutMain.tvFavLabel.setVisibility(View.VISIBLE);
                binding.layoutMain.rvFavourites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mFavChannelsAdapter = new ChannelsAdapter(channelsList, channel -> {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        channel.setChecked(!channel.isChecked());
                        if (mFavChannels.contains(channel)) {
                            removeFav(mFavChannels, channel, mFavChannelsAdapter, true);
                            if (mChannels.contains(channel)) {
                                removeFav(mChannels, channel, mAllChannelsAdapter, false);
                            }
                        }
                        viewModel.writeOrRemoveChannelsData(channel);
                    }
                });
                binding.layoutMain.rvFavourites.setAdapter(mFavChannelsAdapter);
            }
        }
        viewModel.getSortOrder().observe(this, sortOrder -> {
            if (sortOrder != null) {
                this.mSortOrder = sortOrder;
                sort(sortOrder, mAllChannelsAdapter, mChannels);
                sort(sortOrder, mFavChannelsAdapter, mFavChannels);
            }
        });
    }

    private void removeFav(List<ChannelsListModel.Channel> channelList, ChannelsListModel.Channel channel, ChannelsAdapter channelsAdapter, boolean isFromFav) {
        int index = channelList.indexOf(channel);
        if (isFromFav) {
            channelList.remove(channel);
            channelsAdapter.notifyItemRemoved(index);
            binding.layoutMain.tvFavLabel.setVisibility(View.GONE);
        } else {
            channelsAdapter.notifyItemChanged(index);
        }
    }


    private void fetchChannels() {
        viewModel.getChannelList().observe(this, channelsListModel -> {
            if (channelsListModel != null) {
                mChannels = channelsListModel.getChannels();
                initChannelsList(channelsListModel.getChannels());
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logout = menu.findItem(R.id.logout);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            logout.setTitle("Logout");
        } else {
            logout.setTitle("Login");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void initChannelsList(List<ChannelsListModel.Channel> channels) {
        binding.layoutMain.rvMain.setNestedScrollingEnabled(false);
        binding.layoutMain.rvMain.addItemDecoration(new SimpleDividerItemDecoration(this));
        binding.layoutMain.rvMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAllChannelsAdapter = new ChannelsAdapter(channels, channel -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                channel.setChecked(!channel.isChecked());
                if (mFavChannels != null) {
                    if (channel.isChecked() && !mFavChannels.contains(channel)) {
                        mFavChannels.add(channel);
                        mFavChannelsAdapter.notifyItemInserted(mFavChannels.size() - 1);
                        sort(mSortOrder, mFavChannelsAdapter, mFavChannels);
                    } else if (!channel.isChecked() && mFavChannels.contains(channel)) {
                        int index = mFavChannels.indexOf(channel);
                        mFavChannels.remove(channel);
                        mFavChannelsAdapter.notifyItemRemoved(index);
                    }
                }
                viewModel.writeOrRemoveChannelsData(channel);
            } else {
                initFirebaseAuth();
                mChannel = channel;
            }
        });
        binding.layoutMain.rvMain.setAdapter(mAllChannelsAdapter);
    }

    private void initFirebaseAuth() {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        fetchFavouritesAndSort();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.name_ascending:
                viewModel.setSortOrder(Constants.SORT_NAME_ASC);
                sort(Constants.SORT_NAME_ASC, mAllChannelsAdapter, mChannels);
                return true;

            case R.id.name_descending:
                viewModel.setSortOrder(Constants.SORT_NAME_DESC);
                sort(Constants.SORT_NAME_DESC, mAllChannelsAdapter, mChannels);
                return true;

            case R.id.channel_no_ascending:
                viewModel.setSortOrder(Constants.SORT_ID_ASC);
                sort(Constants.SORT_ID_ASC, mAllChannelsAdapter, mChannels);
                return true;

            case R.id.channel_no_descending:
                viewModel.setSortOrder(Constants.SORT_ID_DESC);
                sort(Constants.SORT_ID_DESC, mAllChannelsAdapter, mChannels);
                return true;

            case R.id.logout:
                binding.pb.setVisibility(View.VISIBLE);
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    initFirebaseAuth();
                    return true;
                }

                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(task -> {
                            binding.pb.setVisibility(View.GONE);
                        });
                return true;

            case R.id.tv_guide:
                ArrayList<Integer> channelIdList = new ArrayList<>();
                for (ChannelsListModel.Channel channel : mChannels) {
                    channelIdList.add(channel.getChannelId());
                }
                Intent intent = new Intent(this,TvGuideActivity.class);
                intent.putIntegerArrayListExtra(BundleKeys.CHANNELS_LIST,channelIdList);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void sort(int sortOrder, ChannelsAdapter channelsAdapter, List<ChannelsListModel.Channel> channels) {
        if (channels == null || channels.size() == 0)
            return;
        MenuItem sortItem = menu.findItem(R.id.sort_order);
        Handler handler = new Handler();
        binding.pb.setVisibility(View.VISIBLE);
        switch (sortOrder) {
            case Constants.SORT_NAME_ASC:
                sortItem.setTitle("Sort Order: Name Ascending");
                new Thread(() -> {
                    Collections.sort(channels, (o1, o2) -> o1.getChannelTitle().compareTo(o2.getChannelTitle()));
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_NAME_DESC:
                sortItem.setTitle("Sort Order: Name Descending");
                new Thread(() -> {
                    Collections.sort(channels, (o1, o2) -> o1.getChannelTitle().compareTo(o2.getChannelTitle()));
                    Collections.reverse(channels);
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_ID_ASC:
                sortItem.setTitle("Sort Order: Channel No. Ascending");
                new Thread(() -> {
                    Collections.sort(channels, (o1, o2) -> o1.getChannelId() - o2.getChannelId());
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_ID_DESC:
                sortItem.setTitle("Sort Order: Channel No. Descending");
                new Thread(() -> {
                    Collections.sort(channels, (o1, o2) -> o1.getChannelId() - o2.getChannelId());
                    Collections.reverse(channels);
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;
        }
        binding.pb.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                if (mChannel != null) {
                    mChannel.setChecked(!mChannel.isChecked());
                    viewModel.writeOrRemoveChannelsData(mChannel);
                }
                fetchFavouritesAndSort();
            }
            binding.pb.setVisibility(View.GONE);
        }
    }
}
