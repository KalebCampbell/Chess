package com.Chess.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.Chess.engine.pieces.Piece;

public abstract class Tile {
	
	//to make this immutable
	protected final int tileCoord;
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
	
		final  Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		return Collections.unmodifiableMap(emptyTileMap);
	}
	
	public static Tile createTile(final int tileCoord, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoord, piece) : EMPTY_TILES_CACHE.get(tileCoord);
	}
	
	private Tile(final int tileCoord){
		this.tileCoord = tileCoord;
	}
	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile{
		
		EmptyTile(final int coord){
			super(coord);
		}
		
		@Override
		public String toString() {
			return "-";
		}


		@Override
		public boolean isTileOccupied() {
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
public static final class OccupiedTile extends Tile{
	
	//no way to refrence this variable without calling get piece
	private final Piece pieceOnTile;
	
	OccupiedTile(int tileCoord, Piece pieceOnTile) {
		super(tileCoord);
		this.pieceOnTile = pieceOnTile;
	}
	
	@Override
	public String toString() {
		return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
			getPiece().toString();
	}
	
	@Override
	public boolean isTileOccupied() {
		return true;
	}

	@Override
	public Piece getPiece() {
		return this.pieceOnTile;
	}
	
}
}
