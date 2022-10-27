package com.gbs.app.nss.board;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/board/boardMain")
    public String boardMain(Model model) {
        List<BoardDTO> contentsList = boardService.selectBoardContentsList();
        model.addAttribute("contentsList", contentsList);
        return "board/contentsMain";
    }


    /**
     *      1. Ajax 요청 후 location.reload가 없어도 다시 해당 페이지 재 요청함.
     *          - Ajax 등록 처리 완료 후 input 값이 날라가는 현상이 발생 (-> <Button type="button"></Button> 타입을 적어주면 됨.. 기본이 submit인듯..?)
     *          - 빌더패턴으로 인해 @ModelAttribute를 사용할 수 없어 @RequestParam 붙인 Map을 사용
     *          - 빌더를 쓰면서 @ModelAttribute 쓸 수 있는 방법? -> 없는듯. @ModelAttribute는 동작 원리에 따라 @Setter 없이는 불가능
     */
    @GetMapping("/board/reg")
    public String boardRegPage(@RequestParam Map<String, String> param, Model model) {
        if (param.isEmpty()) { // 등록페이지 초기 진입 시
            model.addAttribute("data", new BoardDTO()); // 값 등록 후 진입 시
        }else {
            model.addAttribute("data", param);
        }

        return "board/boardRegPage";
    }

    /**
     *      1. DTO에 builder를 사용하는 경우 @ModelAttribute를 통한 값 매핑 불가.
     *      2. @NoArgsConstructor 없이는 @RequestBody를 이용한 DTO 값 매핑 불가.
     * */
    @PostMapping("/board/insertBoard")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> insertBoard(@RequestBody BoardDTO boardDTO) {
        System.out.println("=============================");
        System.out.println(boardDTO.toString());
        System.out.println("=============================");
        return new ResponseEntity<>(boardService.insertBoard(boardDTO), HttpStatus.OK);
    }
}
