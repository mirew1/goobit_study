package com.gbs.app.nss.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardDTO {
    private String usrId;
    private String passWd;
    private String usrNm;
    private String usrMob;
	private String nckNm = "성이름";


    @Builder
	public BoardDTO(String id, String password) {
		this.usrId = id;
        this.passWd = password;
		this.usrNm = "임시발급용입니다.";
    }

	@Builder(builderClassName = "allBoardInfo", builderMethodName = "allBuilder")
	public BoardDTO(String id, String password, String name, String phone, String nickName) {
		this.usrId = id;
		this.passWd = password;
		this.usrNm = name;
		this.usrMob = phone;
		this.nckNm = nickName;
	}

	public static allBoardInfo allBuilderMustParam(String id, String name) {
		return allBuilder().name(name).id(id);
	}

}
