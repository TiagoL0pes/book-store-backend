package com.system.store.models;

import java.io.Serializable;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 0L;

    @Id
    private ObjectId id;
    private String email;
    private String password;

}
