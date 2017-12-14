package com.novoda.materialised.hackernews.stories.provider

import io.reactivex.Single

interface StoryIdProvider {
    fun listOfStoryIds(section: String): Single<List<Long>>
}
