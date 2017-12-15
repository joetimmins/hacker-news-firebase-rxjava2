package com.novoda.materialised.hackernews.stories.provider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import kotlin.jvm.functions.Function1;

final class FirebaseSingleStoryProvider {
    private final FirebaseDatabase firebaseDatabase;

    FirebaseSingleStoryProvider(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    Single<Story> obtainStory(int storyId) {
        DatabaseReference items = firebaseDatabase.getReference("v0").child("item");

        DatabaseReference storyWithId = items.child(Integer.toString(storyId));
        Function1<DataSnapshot, Story> converter = new Function1<DataSnapshot, Story>() {
            @Override
            public Story invoke(DataSnapshot dataSnapshot) {
                return dataSnapshot.getValue(Story.class);
            }
        };
        return FirebaseSingleEventListener.listen(storyWithId, converter);
    }

    Observable<Story> obtainStories(List<Integer> input) {
        return Observable.fromIterable(input)
                .flatMap(new Function<Integer, ObservableSource<Story>>() {
                    @Override
                    public ObservableSource<Story> apply(Integer integer) throws Exception {
                        return obtainStory(integer).toObservable();
                    }
                });
    }
}
