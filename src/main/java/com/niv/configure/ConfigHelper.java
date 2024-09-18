package com.niv.configure;

import io.vertx.core.json.JsonObject;

public enum ConfigHelper {
    INSTANCE;

    public JsonObject getMySqlConfig(){
        return ConfigManager.INSTANCE.getMainConfig().getJsonObject("sql");
    }

}
