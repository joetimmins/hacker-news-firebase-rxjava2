package com.novoda.materialised.hackernews.stories.provider

import io.reactivex.Single

interface StoryIdProvider {
    fun topStoryIds(): Single<List<Long>>
}
