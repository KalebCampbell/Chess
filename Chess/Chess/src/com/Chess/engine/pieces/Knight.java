package com.Chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.Chess.engine.Alliance;
import com.Chess.engine.board.Board;
import com.Chess.engine.board.BoardUtils;
import com.Chess.engine.board.Move;
import com.Chess.engine.board.Tile;
import com.Chess.engine.pieces.Piece.PieceType;

public class Knight extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDS = { -17, -15, -10, -6, 6, 10, 15, 17};
	
	public Knight(final Alliance pieceAlliance, final int piecePosition) {
		super(piecePosition, pieceAlliance, PieceType.KNIGHT);
	}

	/**
	 * calculates the moves possible for the piece
	 */
	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		int candidateDestinationCoord;
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDS) {
			
			candidateDestinationCoord = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) || isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) || isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
			
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));
				}else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					
					if(this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoord, pieceAtDestination));
					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}
	@Override
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset ==-17|| candidateOffset == -10 || candidateOffset == -6 || candidateOffset == 15);
	}
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset ==-10 || candidateOffset == 6);
	}
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset ==-6 || candidateOffset == 10);
	}
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset ==-15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17);
	}
}
