package com.smartmanager.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Entity
// @Table(name = "user") will be created automatically by spring 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class user {
    @Id // specifying the primary key to be this below userid
  private String userId;
  @Column(nullable = false)
  private String name;
    @Column(unique = true, nullable = false)
  private String email;
  private String password;
  @Column(length = 1000)  
  private String about;
  @Column(length = 1000)
  private String profilePic;
  private String phone;
//   other things as well i can add
  private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    @Enumerated(value = EnumType.STRING)
    private Providers provider=Providers.SELF;
    private String providerId;
  
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)

    private List<contact> contacts=new ArrayList<>();
}
