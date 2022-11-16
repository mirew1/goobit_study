package com.gbs.app.siwon.login;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserDTO implements UserDetails {

	private static final long serialVersionUID = -6008661340047490462L;

    private String userId;
    private String password;
    private String userNm;
    private String authCd;
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(authCd.split(",")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	// UserDetails를 구현하는 경우 equals와 hashcode를 Override해야 중복로그인을 방지할 수 있음
	@Override
    public boolean equals(Object obj) {
        if (obj instanceof UserDTO) {
          return getUsername().equals( ((UserDTO) obj).getUsername() );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername() != null ? getUsername().hashCode() : 0;
    }
}
