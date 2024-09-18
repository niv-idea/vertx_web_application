package com.niv.models.entity;

import com.niv.factory.MySqlBeanFactory;
import io.ebean.annotation.NotNull;
import io.ebean.annotation.SoftDelete;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
//import io.ebean.annotation.*;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.sql.Timestamp;

@Data
@MappedSuperclass
public class BaseModel {
    @NotNull
    @Id
    @Column(length = 40)
    private Integer id;

    @WhenCreated
    private Timestamp createdAt;

    @WhenModified
    private Timestamp updatedAt;

    @SoftDelete
    private boolean deleted;

    public void save(){
        if(createdAt==null){
            MySqlBeanFactory.INSTANCE.saveBean(this);
        }else {

            MySqlBeanFactory.INSTANCE.update(this);
        }
    }
    public void delete(){
        MySqlBeanFactory.INSTANCE.delete(this);
    }

}
