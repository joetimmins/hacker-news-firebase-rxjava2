package com.novoda.materialised.hackernews.stories;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.jetbrains.annotations.NotNull;

final class IntentNavigator {
    private final Context context;

    IntentNavigator(Context context) {
        this.context = context;
    }

    void navigateTo(@NotNull Uri uri) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
