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

public final class FirebaseStoryProvider {
    private final FirebaseDatabase firebaseDatabase;
    private final Function1<DataSnapshot, Story> snapshotToStoryConverter = new Function1<DataSnapshot, Story>() {
        @Override
        public Story invoke(DataSnapshot dataSnapshot) {
            return dataSnapshot.getValue(Story.class);
        }
    };

    FirebaseStoryProvider(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public Observable<Story> obtainStories(List<Integer> input) {
        return Observable.fromIterable(input)
                .flatMap(new Function<Integer, ObservableSource<Story>>() {
                    @Override
                    public ObservableSource<Story> apply(Integer storyId) throws Exception {
                        DatabaseReference items = firebaseDatabase.getReference("v0").child("item");
                        DatabaseReference storyReference = items.child(Integer.toString(storyId));
                        Single<Story> storySingle = FirebaseSingleEventListener.listen(storyReference, snapshotToStoryConverter);
                        return storySingle.toObservable();
                    }
                });
    }
}
