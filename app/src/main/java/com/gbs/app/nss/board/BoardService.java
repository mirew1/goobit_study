package com.gbs.app.nss.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private List<BoardDTO> replaceDb = new ArrayList<>();
    private final BoardMapper boardMapper;

    public Map<String, String> selectBoardList() {
        return boardMapper.selectBoardList();
    }

    public List<BoardDTO> selectBoardContentsList() {
        BoardDTO fake1 = BoardDTO.allBuilderMustParam("userA", "사용자A").nickName("몽총이").password("1234").phone("010-1234-1234").build();
        BoardDTO fake2 = BoardDTO.allBuilderMustParam("userB", "사용자B").password("2345").build();
        BoardDTO fake3 = BoardDTO.allBuilderMustParam("userC", "사용자C").password("4444").phone("010-4444-4444").build();
        BoardDTO fake4 = BoardDTO.allBuilderMustParam("userD", "사용자D").nickName("메롱메롱").password("5678").build();
        BoardDTO fake5 = BoardDTO.allBuilderMustParam("userE", "사용자E").nickName("하나둘셋").password("3456").phone("010-2222-4444").build();
        BoardDTO fake6 = BoardDTO.builder().id("userF").password("9999").build();
        log.info("fake2 :: {}", fake2.toString());
        log.info("fake6 :: {}", fake6.toString());
        return Arrays.asList(fake1,fake2,fake3,fake4,fake5, fake6);
    }

    public Map<String, Object> insertBoard(BoardDTO boardDTO) {
        boardMapper.insertBoard(boardDTO);
        return Map.of();
    }
}
