package com.novoda.materialised.hackernews.stories;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.novoda.materialised.R;
import com.novoda.materialised.databinding.MainActivityBinding;
import com.novoda.materialised.hackernews.stories.provider.FirebaseStoryIdProvider;
import com.novoda.materialised.hackernews.stories.provider.FirebaseStoryProvider;
import com.novoda.materialised.hackernews.stories.provider.ProviderFactory;

public final class HackerNewsStoriesActivity extends AppCompatActivity {

    private StoriesPresenter storiesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivityBinding mainActivityLayout = DataBindingUtil.setContentView(this, R.layout.main_activity);

        setSupportActionBar(mainActivityLayout.toolbar);

        FirebaseStoryIdProvider storyIdProvider = ProviderFactory.newStoryIdProvider(this);
        FirebaseStoryProvider storyProvider = ProviderFactory.newStoryProvider(this);

        storiesPresenter = new StoriesPresenter(
                storyIdProvider,
                storyProvider,
                mainActivityLayout.loadingView,
                mainActivityLayout.storiesView
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        storiesPresenter.present();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
