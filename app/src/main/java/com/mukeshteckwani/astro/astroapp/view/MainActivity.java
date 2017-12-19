package com.mukeshteckwani.astro.astroapp.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.adapter.ChannelsAdapter;
import com.mukeshteckwani.astro.astroapp.databinding.ActivityMainBinding;
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;
import com.mukeshteckwani.astro.astroapp.utils.Constants;
import com.mukeshteckwani.astro.astroapp.viewmodel.ChannelsListViewModel;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private ActivityMainBinding binding;
    private ChannelsListViewModel viewModel;
    private List<ChannelsListModel.Channel> mChannels;
    private ChannelsAdapter allChannelsAdapter;
    private ChannelsListModel.Channel mChannel;
    private Menu menu;
    private ChannelsAdapter mFavChannelsAdapter;
    private List<ChannelsListModel.Channel> mFavChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setSupportActionBar(binding.toolbar);
        viewModel = ViewModelProviders.of(this).get(ChannelsListViewModel.class);
        fetchChannels();
        fetchFavouites();
    }

    private void fetchFavouites() {
        viewModel.getFavouriteChannels().observe(this, channelsList -> {
            if (channelsList != null && channelsList.size() > 0) {//check for fav ,set using view model callback
                mFavChannels =channelsList;
                binding.layoutMain.rvFavourites.setNestedScrollingEnabled(false);
                binding.layoutMain.tvFavLabel.setVisibility(View.VISIBLE);
                binding.layoutMain.rvFavourites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mFavChannelsAdapter = new ChannelsAdapter(channelsList, channel -> {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        channel.setChecked(!channel.isChecked());
                        viewModel.writeOrRemoveChannelsData(channel);
                    }
                });
                binding.layoutMain.rvFavourites.setAdapter(mFavChannelsAdapter);
            }
            viewModel.getSortOrder().observe(this, sortOrder -> {
                sort(sortOrder, allChannelsAdapter, mChannels);
                sort(sortOrder, mFavChannelsAdapter,mFavChannels);

            });
        });
    }

    private void fetchChannels() {
        viewModel.getChannelList().observe(this, channelsListModel -> {
            if (channelsListModel != null) {
                mChannels = channelsListModel.getChannels();
                initChannelsList(channelsListModel.getChannels());
            }
            binding.pb.setVisibility(View.GONE);
        });
    }

    private void initChannelsList(List<ChannelsListModel.Channel> channels) {
        binding.layoutMain.rvMain.setNestedScrollingEnabled(false);
        binding.layoutMain.rvMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        allChannelsAdapter = new ChannelsAdapter(channels, channel -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                channel.setChecked(!channel.isChecked());
                viewModel.writeOrRemoveChannelsData(channel);
            } else {
                initFirebaseAuth();
                mChannel = channel;
            }
        });
        binding.layoutMain.rvMain.setAdapter(allChannelsAdapter);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.name_ascending:
                viewModel.setSortOrder(Constants.SORT_NAME_ASC);
                sort(Constants.SORT_NAME_ASC,allChannelsAdapter,mChannels);
                return true;

            case R.id.name_descending:
                viewModel.setSortOrder(Constants.SORT_NAME_DESC);
                sort(Constants.SORT_NAME_DESC,allChannelsAdapter,mChannels);
                return true;

            case R.id.channel_no_ascending:
                viewModel.setSortOrder(Constants.SORT_ID_ASC);
                sort(Constants.SORT_ID_ASC,allChannelsAdapter,mChannels);
                return true;

            case R.id.channel_no_descending:
                viewModel.setSortOrder(Constants.SORT_ID_DESC);
                sort(Constants.SORT_ID_DESC,allChannelsAdapter,mChannels);
                return true;

            case R.id.logout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(task -> {
                            //delete favourites data
                        });
                return true;

            case R.id.tv_guide:
                return true;

            default:
               return super.onOptionsItemSelected(item);

        }

    }

    private void sort(int sortOrder,ChannelsAdapter channelsAdapter,List<ChannelsListModel.Channel> channels) {
        if (channels == null || channels.size() == 0)
            return;
        MenuItem sortItem = menu.findItem(R.id.sort_order);
            switch (sortOrder) {
                case Constants.SORT_NAME_ASC :
                    sortItem.setTitle("Sort Order: Name Ascending");
                    Collections.sort(channels, (o1, o2) -> o1.getChannelTitle().compareTo(o2.getChannelTitle()));
                    channelsAdapter.notifyDataSetChanged();
                    break;

                case Constants.SORT_NAME_DESC :
                    sortItem.setTitle("Sort Order: Name Descending");
                    Collections.sort(channels, (o1, o2) -> o1.getChannelTitle().compareTo(o2.getChannelTitle()));
                    Collections.reverse(channels);
                    channelsAdapter.notifyDataSetChanged();
                    break;

                case Constants.SORT_ID_ASC :
                    sortItem.setTitle("Sort Order: Channel No. Ascending");
                    Collections.sort(channels, (o1, o2) -> o1.getChannelId() - o2.getChannelId());
                    channelsAdapter.notifyDataSetChanged();
                    break;

                case Constants.SORT_ID_DESC :
                    sortItem.setTitle("Sort Order: Channel No. Descending");
                    Collections.sort(channels, (o1, o2) -> o1.getChannelId() - o2.getChannelId());
                    Collections.reverse(channels);
                    channelsAdapter.notifyDataSetChanged();
                    break;

            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == ResultCodes.OK) {
                // Successfully signed in
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // save to db the channel id
                viewModel.writeOrRemoveChannelsData(mChannel);
                mChannel.setChecked(!mChannel.isChecked());
                fetchFavouites();
//                allChannelsAdapter.notifyItemChanged(mChannels.indexOf(mChannel));

            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }
}
