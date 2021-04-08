package service;

import dao.ChessGameDao;
import domain.chessgame.ChessGame;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import dto.request.ChessGameRequestDto;
import dto.request.PiecesRequestDto;
import dto.request.ScoreRequestDto;
import dto.response.ChessGameResponseDto;
import dto.response.PieceResponseDto;
import dto.response.PiecesResponseDto;
import dto.response.ScoreResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChessGameService {

    private final ChessGameDao chessGameDao = new ChessGameDao();

    public ChessGameResponseDto putChessGame(ChessGameRequestDto chessGameRequestDto) {
        ChessGame chessGame = chessGameDao.selectByGameId(chessGameRequestDto.getGameId());
        chessGame.operate(chessGameRequestDto.isRestart(), chessGameRequestDto.isPlaying());
        chessGame = chessGameDao.updateChessGameByGameId(chessGameRequestDto.getGameId(), chessGame);

        return new ChessGameResponseDto(chessGame.isPlaying(), pieceResponseDtos(chessGame));
    }

    public List<PieceResponseDto> pieceResponseDtos(ChessGame chessGame) {
        List<PieceResponseDto> pieceResponseDtos = new ArrayList<>();
        Map<Position, Piece> pieces = chessGame.pieces();
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            pieceResponseDtos.add(new PieceResponseDto(entry.getValue(), entry.getKey()));
        }
        return pieceResponseDtos;
    }

    public PiecesResponseDto putPiece(PiecesRequestDto piecesRequestDto) {
        ChessGame chessGame = chessGameDao.selectByGameId(piecesRequestDto.getGameId());
        chessGame.move(new Position(piecesRequestDto.getSource()), new Position(piecesRequestDto.getTarget()));
        chessGame = chessGameDao.updateChessGameByGameId(piecesRequestDto.getGameId(), chessGame);

        return new PiecesResponseDto(chessGame, pieceResponseDtos(chessGame));
    }

    public ScoreResponseDto getScore(ScoreRequestDto scoreRequestDto) {
        ChessGame chessGame = chessGameDao.selectByGameId(scoreRequestDto.getGameId());

        return new ScoreResponseDto(chessGame.score(Color.BLACK), chessGame.score(Color.WHITE));
    }

}
