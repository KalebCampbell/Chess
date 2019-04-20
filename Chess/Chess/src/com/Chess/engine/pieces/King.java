package com.Chess.engine.pieces;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.Chess.engine.Alliance;
import com.Chess.engine.board.Board;
import com.Chess.engine.board.BoardUtils;
import com.Chess.engine.board.Move;
import com.Chess.engine.board.Tile;
import com.Chess.engine.pieces.Piece.PieceType;

public class King extends Piece {

	private final static int[] CANDIDATE_MOVE_COORD = {-9, -8, -7, -1, 1, 7, 8, 9};

	public King(Alliance pieceAlliance, int piecePosition) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		
		for(final int currentCandidateOffset: CANDIDATE_MOVE_COORD) {
			
			final int candidateDestinationCoord;
			 candidateDestinationCoord = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
		
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || 
				   isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}
				
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
		return PieceType.KING.toString();
	}
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset ==-9|| candidateOffset == -1 || candidateOffset == 7);
	}
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == 1 || candidateOffset == -7);
	}
}
