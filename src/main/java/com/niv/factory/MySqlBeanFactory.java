package com.niv.factory;

import com.niv.configure.ConfigManager;
import com.niv.models.entity.BaseModel;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.platform.mysql.MySqlPlatform;
import io.vertx.core.json.JsonObject;


//in this enum developer create the database connection
public enum MySqlBeanFactory {
    INSTANCE;
    private DataSourceConfig getDataSourceConfig(){
        JsonObject sqlConfig= ConfigManager.INSTANCE.getMySqlConfig();
        System.out.println("sql" +sqlConfig);
        DataSourceConfig config = new DataSourceConfig();
        config.setUrl(sqlConfig.getString("url"));
        config.setDriver(sqlConfig.getString("driver"));
        config.setPassword(sqlConfig.getString("password"));
        config.setUsername(sqlConfig.getString("username"));
        return config;
    }
    public Database dbConnection(){
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setDataSourceConfig(getDataSourceConfig());
        dbConfig.addPackage("com.moonlight.models.sql");
//		dbConfig.addClass(Employee.class);

        dbConfig.setDdlGenerate(true);
        dbConfig.setDdlRun(true);
        dbConfig.setDefaultServer(true);
        dbConfig.setDdlCreateOnly(true);
        dbConfig.setRegister(true);
        //this will provide mysql database
        dbConfig.setDatabasePlatform(new MySqlPlatform());
        return DatabaseFactory.create(dbConfig);
    }
    public void init() {
        dbConnection();
    }
    public void stop() {
        dbConnection().shutdown();
    }

    public void save(BaseModel baseModel) {
        saveBean(baseModel);
    }

    public void update(BaseModel baseModel) {
        updateBean(baseModel);
    }

    public void delete(BaseModel baseModel) {
        deleteBean(baseModel);
    }
    public void saveBean(Object o) {
        dbConnection().save(o);
    }

    public void updateBean(Object o) {
        dbConnection().update(o);
    }

    public void deleteBean(Object o) {
        dbConnection().delete(o);
    }
}
