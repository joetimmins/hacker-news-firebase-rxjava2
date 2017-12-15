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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class StoriesPresenter {
    private final FirebaseStoryIdProvider storyIdProvider;
    private final FirebaseStoryProvider storyProvider;
    private final TextView loadingView;
    private final RecyclerView storiesView;

    private List<Story> stories;

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
                        return storyProvider.obtainStories(ids);
                    }
                })
                .doOnNext(new Consumer<Story>() {
                    @Override
                    public void accept(Story story) throws Exception {
                        stories.add(story);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Log.d("something", "something");
    }
}
