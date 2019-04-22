package com.Chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.Chess.engine.Alliance;
import com.Chess.engine.board.Board;
import com.Chess.engine.board.Move;
import com.Chess.engine.pieces.King;
import com.Chess.engine.pieces.Piece;

public abstract class Player {

	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isInCheck;
	
	public Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
		
		this.board = board;
		this.legalMoves = legalMoves;
		this.playerKing = establishKing();
		this.isInCheck = !Player.calculateAttacksOntile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
		
	}
	public King getPlayerKing() {
		return this.playerKing;
	}
	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
	}

	private static Collection<Move> calculateAttacksOntile(int piecePosition, Collection<Move> moves) {
		final List<Move> attackMoves = new ArrayList<>();
		for(final Move move: moves) {
			if(piecePosition == move.getDestinationCoord()) {
				attackMoves.add(move);
			}
		}
		return Collections.unmodifiableList(attackMoves);	
	}

	private King establishKing() {
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here!, Invalid Board!");
	}
	
	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	public boolean isInCheck() {
		return this.isInCheck;
	}
	
	public boolean isInCheckmate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isInStalemate() {
		return !this.isInCheck && !this.hasEscapeMoves();
	}
	
	protected boolean hasEscapeMoves() {
		for(final Move move: this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}

	public boolean isCastled() {
		return false;
	}
	
	public MoveTransition makeMove(final Move move) {
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		final Board transitionBoard = move.execute();
		final Collection<Move> kingAttacks = Player.calculateAttacksOntile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
				transitionBoard.currentPlayer().getLegalMoves());
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	
	
}
