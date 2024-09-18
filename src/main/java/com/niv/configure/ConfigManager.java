package com.niv.configure;

import io.vertx.core.json.JsonObject;

public enum ConfigManager {
    INSTANCE;
    private JsonObject mainConfig;

    public JsonObject getMainConfig() {
        return mainConfig;
    }

    public void setMainConfig(JsonObject mainConfig) {
        this.mainConfig = mainConfig;
    }
}
