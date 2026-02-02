package model;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    public static User currentUser = null ;
}