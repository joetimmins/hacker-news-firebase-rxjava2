package com.novoda.materialised.hackernews.stories.provider;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

public class FirebaseStoryProviderTest {

    @Test
    public void testThatAListOfIdsIsConvertedIntoAnObservableOfStories() {
        int firstStoryId = 12;
        int secondStoryId = 34;
        List<Integer> input = Arrays.asList(firstStoryId, secondStoryId);

        Story firstStory = new Story("author", 890, firstStoryId, Arrays.asList(1, 2), 4, 1232, "test title", "test type", "http://test.url");
        Story secondStory = new Story("another author", 567, secondStoryId, Arrays.asList(3, 4), 5, 7897, "another title", "another type", "http://another.url");
        List<Story> stories = Arrays.asList(firstStory, secondStory);
        FirebaseStoryProvider provider = new FirebaseStoryProvider(FakeFirebase.storyDatabase(stories));

        Observable<Story> storyObservable = provider.obtainStories(input);

        TestObserver<Story> testObserver = new TestObserver<>();
        storyObservable.subscribe(testObserver);

        testObserver.assertValues(firstStory, secondStory);
    }
}
