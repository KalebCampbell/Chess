package com.Chess.engine.pieces;

import java.util.Collection;

import com.Chess.engine.Alliance;
import com.Chess.engine.board.Board;
import com.Chess.engine.board.Move;

public abstract class Piece {

	protected final PieceType pieceType;
	protected final int piecePosition; 
	protected final Alliance pieceAlliance; 
	protected final boolean isFirstMove;
	
	Piece(final int piecePosition, final Alliance pieceAlliance, final PieceType pieceType){
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.isFirstMove = false;
		this.pieceType = pieceType;
	}
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}
	public boolean isFirstMove() {
		return this.isFirstMove;
		
	}
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	public enum PieceType {
	
		PAWN("P") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		private String pieceName;
		
		PieceType(final String  pieceName){
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName;
		}

		public abstract boolean isKing();
	}

	public PieceType getPieceType() {
		return this.pieceType;
	}
	
}
