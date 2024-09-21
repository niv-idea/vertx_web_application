package com.niv.configure;

import io.vertx.core.json.JsonObject;
//with the help of it we are passing the database related configuration, as we know that this will become in key value pair
public enum ConfigManager {

    INSTANCE;
    private JsonObject mainConfig;

    public JsonObject getMainConfig() {
        return mainConfig;
    }

    public void setMainConfig(JsonObject mainConfig) {
        this.mainConfig = mainConfig;
    }
    public JsonObject getMySqlConfig(){
        return this.mainConfig.getJsonObject("sql");
    }
}
