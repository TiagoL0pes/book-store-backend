package com.system.store.models;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "books")
public class Book implements Serializable {

    private static final long serialVersionUID = -1224270923029563915L;

    @Id
    private ObjectId id;
    private String isbn;

    @NotBlank(message = "{field.not.blank}")
    private String title;

    @DBRef
    private Author author;
}
