package com.novoda.materialised.hackernews.stories.provider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.Single;
import kotlin.jvm.functions.Function1;

final class FirebaseStoryIdProvider {
    private final FirebaseDatabase firebaseDatabase;

    FirebaseStoryIdProvider(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public Single<List<Long>> topStoryIds() {
        DatabaseReference reference = firebaseDatabase.getReference("v0").child("topstories");

        Function1<DataSnapshot, List<Long>> converter = new Function1<DataSnapshot, List<Long>>() {
            @Override
            public List<Long> invoke(DataSnapshot dataSnapshot) {
                //noinspection unchecked - we know it's a list of longs because the docs tell us so
                return (List<Long>) dataSnapshot.getValue();
            }
        };

        return FirebaseSingleEventListener.listen(reference, converter);
    }
}
