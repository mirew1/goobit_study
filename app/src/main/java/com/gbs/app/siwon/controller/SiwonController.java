package com.gbs.app.siwon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gbs.app.siwon.service.SiwonService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/")
public class SiwonController {

	@Autowired
	SiwonService siwonService;
	
	@GetMapping("")
	public String boardList(ModelAndView mav) {
		mav.addObject("menuList", siwonService.getMenuList());
		log.info(siwonService.getUserList() +"");
		return "siwon/index";
	}

}
