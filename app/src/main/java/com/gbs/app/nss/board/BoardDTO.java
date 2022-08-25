package com.gbs.app.nss.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardDTO {
    private String id;
    private String password;
    private String name;
    private String phone;
	private String nickName = "성이름";


    @Builder
	public BoardDTO(String id, String password) {
		this.id = id;
        this.password = password;
		this.name = "임시발급용입니다.";
    }

	@Builder(builderClassName = "allBoardInfo", builderMethodName = "allBuilder")
	public BoardDTO(String id, String password, String name, String phone, String nickName) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.nickName = nickName;
	}

	public static allBoardInfo allBuilderMustParam(String id, String name) {
		return allBuilder().name(name).id(id);
	}

}
