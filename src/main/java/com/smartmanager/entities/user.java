package com.smartmanager.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
// @Table(name = "user") will be created automatically by spring 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class user implements UserDetails {
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
  private String profilePic="";
  private String phone;
//   other things as well i can add
  private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;
    private String emailToken;

    @Enumerated(value = EnumType.STRING)
    private Providers provider=Providers.SELF;
    private String providerId;
  
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)

    private List<contact> contacts=new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList= new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      // TODO Auto-generated method stub
  //  list of roles( USER, ADMIN, etc) will be there in roleList
  //  // we need to convert it to SimpleGrantedAuthority
       Collection<SimpleGrantedAuthority> roles= roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
       return roles;

      
    }

    @Override   // overriding the method of userdetails to the user since we are using user entity as userdetails
    public String getUsername() {
       return this.email; 
    }

    @Override
    public boolean isAccountNonExpired() {
      // TODO Auto-generated method stub
      return true;
    }

   @Override
    public boolean isAccountNonLocked() {
      // TODO Auto-generated method stub
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      // TODO Auto-generated method stub
      return true;
    }

    @Override
    public boolean isEnabled() {
      // TODO Auto-generated method stub
      return this.enabled;
    }
}
