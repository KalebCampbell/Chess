package com.Chess.engine;

import com.Chess.engine.board.Board;

public class JChess {
	
	public static void main(String[] args) {
		
		Board board = Board.createStandardBoard();
		
		System.out.println(board);
	}
}
