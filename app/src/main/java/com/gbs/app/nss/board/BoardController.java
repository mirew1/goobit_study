package com.gbs.app.nss.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{loc}/main")
    public String main(Model model, @PathVariable String loc) {
        Map<String, String> result = boardService.selectBoardList();
        model.addAttribute("result", result.toString());
        return "layout/"+loc+"/main";
    }
}
