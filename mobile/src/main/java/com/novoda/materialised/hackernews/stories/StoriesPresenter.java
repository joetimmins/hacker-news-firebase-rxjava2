package com.novoda.materialised.hackernews.stories;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.novoda.materialised.hackernews.stories.provider.FirebaseStoryIdProvider;
import com.novoda.materialised.hackernews.stories.provider.FirebaseStoryProvider;
import com.novoda.materialised.hackernews.stories.provider.Story;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class StoriesPresenter {
    private final FirebaseStoryIdProvider storyIdProvider;
    private final FirebaseStoryProvider storyProvider;
    private final TextView loadingView;
    private final RecyclerView storiesView;

    private static final String TAG = "something";

    private List<Story> stories = new ArrayList<>();

    StoriesPresenter(FirebaseStoryIdProvider storyIdProvider,
                     FirebaseStoryProvider storyProvider,
                     TextView loadingView,
                     RecyclerView storiesView) {
        this.storyIdProvider = storyIdProvider;
        this.storyProvider = storyProvider;
        this.loadingView = loadingView;
        this.storiesView = storiesView;
    }

    void present() {
        storyIdProvider.topStoryIds()
                .map(new Function<List<Long>, List<Integer>>() {
                    @Override
                    public List<Integer> apply(List<Long> longs) throws Exception {
                        Log.d(TAG, "mapping longs to ints");
                        List<Integer> result = new ArrayList<>(longs.size());
                        for (Long id : longs) {
                            result.add(id.intValue());
                        }
                        return result;
                    }
                })
                .flatMapObservable(new Function<List<Integer>, ObservableSource<Story>>() {
                    @Override
                    public ObservableSource<Story> apply(List<Integer> ids) throws Exception {
                        Log.d(TAG, "obtaining stories");
                        return storyProvider.obtainStories(ids);
                    }
                })
                .doOnNext(new Consumer<Story>() {
                    @Override
                    public void accept(Story story) throws Exception {
                        Log.d(TAG, "adding stories");
                        stories.add(story);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "stories" + stories);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
