package domain.board;

import domain.chessgame.Score;
import domain.piece.Bishop;
import domain.piece.EmptyPiece;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Queen;
import domain.piece.Rook;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Board {

    private static final int CHESS_BOARD_SIZE = 8;
    private static final int PAWN_ALLY_COUNT_CONDITION = 2;
    private final Map<Position, Piece> board;

    public Board() {
        board = emptyBoard();
    }

    private Map<Position, Piece> emptyBoard() {
        Map<Position, Piece> emptyBoard = new HashMap<>();
        for (int row = 0; row < CHESS_BOARD_SIZE; row++) {
            putInitialPiece(emptyBoard, row);
        }
        return emptyBoard;
    }

    private void putInitialPiece(Map<Position, Piece> emptyBoard, int row) {
        for (int column = 0; column < CHESS_BOARD_SIZE; column++) {
            emptyBoard.put(new Position(row, column), new EmptyPiece());
        }
    }

    public void initChessPieces() {
        board.putAll(King.createInitialKings());
        board.putAll(Queen.createInitialQueens());
        board.putAll(Bishop.createInitialBishops());
        board.putAll(Knight.createInitialKnights());
        board.putAll(Rook.createInitialRooks());
        board.putAll(Pawn.createInitialPawns());
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public Piece piece(Position position) {
        return board.getOrDefault(position, new EmptyPiece());
    }

    public boolean move(Position source, Position target) {
        Piece SourcePiece = piece(source);
        if (canMove(source, target) && SourcePiece.canMove(this, source, target)) {
            put(source, new EmptyPiece());
            put(target, SourcePiece);
            return true;
        }
        return false;
    }

    public boolean canMove(Position source, Position target) {
        Piece sourcePiece = piece(source);
        Piece targetPiece = piece(target);
        return sourcePiece.isNotEmpty()
            && target.isChessBoardPosition()
            && !sourcePiece.isSameColor(targetPiece);
    }

    public void put(Position position, Piece piece) {
        board.put(position, piece);
    }

    public Map<Position, Piece> pieces(boolean isBlack) {
        return board.entrySet()
            .stream()
            .filter(entry -> entry.getValue().isNotEmpty() && entry.getValue().isBlack() == isBlack)
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    public Score piecesScore(boolean isBlack) {
        Score score = new Score();
        Map<Position, Piece> pieces = pieces(isBlack);

        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            score = score.sum(entry.getValue().getScore());
        }
        score = minusPawnScore(score, isBlack);
        return score;
    }

    private Score minusPawnScore(Score score, boolean isBlack) {
        int minusCount = 0;
        for (int row = 0; row < CHESS_BOARD_SIZE; row++) {
            minusCount += rowAllyPawnCount(row, isBlack);
        }
        return score.minusPawnScore(minusCount);
    }

    private int rowAllyPawnCount(int column, boolean isBlack) {
        int count = (int) pieces(isBlack).entrySet()
            .stream()
            .filter(entry -> entry.getValue().isPawn()
                && column == entry.getKey().getColumnDegree())
            .count();

        if (count >= PAWN_ALLY_COUNT_CONDITION) {
            return count;
        }
        return 0;
    }

}