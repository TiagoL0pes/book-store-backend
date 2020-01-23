package com.system.store.models;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "authors")
public class Author implements Serializable {

    private static final long serialVersionUID = -2535585730153552207L;

    @Id
    private ObjectId id;

    @NotBlank(message = "{field.not.blank}")
    private String name;

    @NotBlank(message = "{field.not.blank}")
    private String email;
}
