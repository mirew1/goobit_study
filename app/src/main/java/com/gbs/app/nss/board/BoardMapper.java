package com.gbs.app.nss.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    Map<String, String> selectBoardList();
}
