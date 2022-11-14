package com.gbs.app.nss.login;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	// security
    private String usrId;
    private String passwd;
    private String authCd;
    
    private String usrNm;
    private String nckNm;
    private String aptIdx;
    private String loginIp;
    private String pushKey;
    private String delYn;
    private String usrMob;
    private String aptDong;
    private String aptHo;
    private String hshdIdx;
    private String relIdx;
    private String eml;
    private String tmpPasswdYn;
    
    private String orgFleNm;
    private String svFleNm;
    private String flePath;
    
    private String ciYn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(authCd.split(",")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return usrId;
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
    public boolean isCredentialsNonExpired() { // false 시 만료 처리(== 임시비밀번호 Y)
        return "N".equals(tmpPasswdYn);
    }

    @Override
    public boolean isEnabled() {
        return "N".equals(delYn);
    }

	@Override
	public String getPassword() {
		return passwd;
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
