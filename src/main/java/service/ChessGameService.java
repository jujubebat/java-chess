package service;

import domain.chessgame.ChessGame;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import dto.request.ChessGameRequestDto;
import dto.request.PiecesRequestDto;
import dto.request.ScoreResponseDto;
import dto.response.ChessGameResponseDto;
import dto.response.PieceResponseDto;
import dto.response.PiecesResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChessGameService {

    private ChessGame chessGame = new ChessGame();

    public ChessGameResponseDto putChessGame(ChessGameRequestDto chessGameRequestDto) {
        chessGame = chessGame.of(chessGameRequestDto.isPlaying());
        List<PieceResponseDto> pieceResponseDtos = pieceResponseDtos();
        return new ChessGameResponseDto(chessGame.isPlaying(), pieceResponseDtos);
    }

    public List<PieceResponseDto> pieceResponseDtos() {
        List<PieceResponseDto> pieceResponseDtos = new ArrayList<>();
        Map<Position, Piece> pieces = chessGame.pieces();
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            pieceResponseDtos.add(new PieceResponseDto(entry.getValue(), entry.getKey()));
        }
        return pieceResponseDtos;
    }

    public PiecesResponseDto putPiece(PiecesRequestDto piecesRequestDto) {
        try {
            chessGame.move(new Position(piecesRequestDto.getSource()),
                new Position(piecesRequestDto.getTarget()));
        } catch (Exception e) {
            return new PiecesResponseDto(chessGame, pieceResponseDtos(), e.getMessage());
        }
        return new PiecesResponseDto(chessGame, pieceResponseDtos());
    }

    public ScoreResponseDto getScore() {
        return new ScoreResponseDto(chessGame.score(Color.BLACK), chessGame.score(Color.WHITE));
    }

}
