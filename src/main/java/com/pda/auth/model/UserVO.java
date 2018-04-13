package com.pda.auth.model;

import com.pda.auth.db.entity.Authority;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class UserVO implements Serializable {
    private static final long serialVersionUID = 5948983959347941971L;

    @JsonProperty("id")
    private long id;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("authority")
    private Authority authority;

    @JsonProperty("active")
    private boolean active;

}
