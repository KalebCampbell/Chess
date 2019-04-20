package com.Chess.engine.board;

import com.Chess.engine.pieces.Piece;

public class Move {

	final Board board;
	final Piece movedPiece; 
	final int destionationCoord;
	
	private Move(final Board board, final Piece movedPiece, final int destinationCoord){
		this.board = board;
		this.movedPiece = movedPiece;
		this.destionationCoord = destinationCoord;
	}
	
	public static final class MajorMove extends Move{

		public MajorMove(final Board board, final Piece movedPiece, final int destinationCoord) {
			super(board, movedPiece, destinationCoord);
		}
	}
	public static final class AttackMove extends Move{

		final Piece attackedPiece;
		
		public AttackMove(final Board board, final Piece movedPiece, final int destinationCoord, final Piece attackedPiece) {
			super(board, movedPiece, destinationCoord);
			this.attackedPiece = attackedPiece;
		}
	}
	
}
