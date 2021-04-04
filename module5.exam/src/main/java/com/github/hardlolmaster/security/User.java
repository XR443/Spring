package com.github.hardlolmaster.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.hardlolmaster.domain.IdObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class User extends IdObject {
    @JsonIgnore
    @ToString.Exclude
    private String password;
    private String username;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles;
}
