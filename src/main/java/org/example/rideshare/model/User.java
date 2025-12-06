package org.example.rideshare.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="users")

public class User {
    @Id
    private String id;

    private String username; //this is used to store the username
    private String password; //this is used to store the password of the user..
    private String role; //role can be ROLE_USER OR ROLE_DRIVER

}
