package com.gbs.app.siwon.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gbs.app.siwon.mapper.SiwonMapper;
import com.gbs.app.siwon.vo.SiwonVO;

@Service
public class SiwonService {
	
	@Autowired
	SiwonMapper siwonMapper;
	
	public List<Map<String, String>> getMenuList(){
		List<Map<String, String>> list = List.of(
				Map.of("name", "목록", "url", "/testList")
				, Map.of("name", "상세", "url", "/testDetail?idx=1")
				, Map.of("name", "등록", "url", "/testForm")
				, Map.of("name", "수정", "url", "/testForm?idx=1")
				);
		return list;
	}
	
	public List<SiwonVO> getUserList(){
		return siwonMapper.getUserList();
	}

}
