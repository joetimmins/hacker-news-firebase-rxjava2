package com.novoda.materialised.hackernews.stories

import com.novoda.materialised.hackernews.stories.provider.Story
import com.novoda.materialised.hackernews.stories.view.StoryViewData
import org.junit.Test
import java.util.*

class StorySectionPresenterTest {

    @Test
    fun presenterGivesCorrectListOfIdsToView_AsViewData_WhenPresentingMultipleStories() {

    }

    @Test
    fun presenterTellsViewToShowErrorScreen_WhenNoIdOnlyStoriesAreRetrieved() {
    }

    @Test
    fun presenterGivesViewModelsWithFullViewDataToView_OneAtATime_WhenPresentingMultipleStories() {
    }

    @Test
    fun presenterGivesViewModelWithNavigatingClickListenerToView_WhenPresenting() {
    }

    private fun createStoryViewDataFrom(story: Story): StoryViewData {
        return StoryViewData(
                story.by, story.kids, story.id, story.score, story.title, story.url
        )
    }

    private fun buildIdOnlyViewData(storyId: Int): StoryViewData = StoryViewData(id = storyId)

    companion object {

        private val TEST_TIME = 3471394
        private val FIRST_STORY_ID = 56L
        private val SECOND_STORY_ID = 78L
        private val ID_ONLY_STORIES = Arrays.asList(
                FIRST_STORY_ID,
                SECOND_STORY_ID
        )

        private val A_STORY = Story("test author", 123, FIRST_STORY_ID.toInt(), Arrays.asList(1, 2), 123, TEST_TIME, "test title", "test type", "http://test.url")
        private val ANOTHER_STORY = Story("another author", 456, SECOND_STORY_ID.toInt(), Arrays.asList(3, 4), 456, TEST_TIME, "another title", "another type", "http://another.url")
    }
}
