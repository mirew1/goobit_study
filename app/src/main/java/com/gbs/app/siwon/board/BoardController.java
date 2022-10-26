package com.gbs.app.siwon.board;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/")
public class BoardController {
	
	@RequestMapping("")
	public String main(Model model) {
		// model.addAttribute("list", getList(null));
		return "board/boardList";
	}
	
	
	/*
	 * @PostMapping("/list") public List<Map<String, Integer>> postList(String no) {
	 * return getList(no); }
	 */
	

	@RequestMapping("/list")
	public String list(Model model, String no) {
		model.addAttribute("list", getList(no));
		return "board/boardList :: #commentTable";
	}
	
	private List<Map<String, Integer>> getList(String no) {
		int number = 1;
		if(no != null) {
			number = Integer.parseInt(no);
		}
		return List.of(
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2),
				Map.of("key1",number,"key2",number++ *2)
			);
	}
}
