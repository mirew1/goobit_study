package com.gbs.app.nss.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public Map<String, String> selectBoardList() {
        return boardMapper.selectBoardList();
    }
}
