package com.gbs.app.nss.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("main")
    public String main(Model model) {
        Map<String, String> result = boardService.selectBoardList();
        model.addAttribute("result", result.toString());
        return "main";
    }
}
