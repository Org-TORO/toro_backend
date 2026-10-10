package com.toro.backend.infrastructure.storage;

public final class StoragePath {

    public static final String DEV_BASE_STORAGE_URL = "http://localhost:8080";

    public static final String LOCAL_STORAGE_BASE_FOLDER = "Uploads";

    public static final String USER_AVATAR_IMAGE_FOLDER = "UserAvatars";

    public static final String DEFAULT_USER_AVATAR_IMAGE_PATH = USER_AVATAR_IMAGE_FOLDER + "/default.png";

    private StoragePath() {
    }
}
