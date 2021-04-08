package dto.response;

import domain.chessgame.ChessGame;
import domain.piece.Color;
import java.util.List;

public class PiecesResponseDto {

    private final boolean isPlaying;
    private final List<PieceResponseDto> pieces;
    private final String winner;

    public PiecesResponseDto(ChessGame chessGame, List<PieceResponseDto> pieces) {
        this.isPlaying = chessGame.isPlaying();
        this.pieces = pieces;
        this.winner = winner(chessGame);
    }

    private String winner(ChessGame chessGame) {
        if (chessGame.isKingAlive(Color.BLACK) && chessGame.isKingAlive(Color.WHITE)) {
            return "무승부";
        }
        if (chessGame.isKingAlive(Color.BLACK)) {
            return "흑색 승리";
        }
        return "백색 승리";
    }

}
