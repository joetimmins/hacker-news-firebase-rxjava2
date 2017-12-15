package com.novoda.materialised.hackernews.stories.provider;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

public class FirebaseStoryIdProviderTest {

    @Test
    public void testThatReadingStoryIdsForSection_callsBackWithStoryIds() {
        // Arrange
        TestObserver<List<Long>> testObserver = new TestObserver<>();
        List<Long> expectedStoryIds = Arrays.asList(8863L, 9001L, 9004L);

        FirebaseDatabase storyTypeFirebaseDatabase = FakeFirebase.storyIdDatabase(expectedStoryIds);

        // Act
        FirebaseStoryIdProvider provider = new FirebaseStoryIdProvider(storyTypeFirebaseDatabase);
        Single<List<Long>> idOnlyStories = provider.topStoryIds();
        idOnlyStories.subscribe(testObserver);

        // Assert
        testObserver.assertValue(expectedStoryIds);
    }
}
