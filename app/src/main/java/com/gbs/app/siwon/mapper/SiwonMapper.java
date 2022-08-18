package com.gbs.app.siwon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gbs.app.siwon.vo.SiwonVO;

@Mapper
public interface SiwonMapper {

	List<SiwonVO> getUserList();
}
