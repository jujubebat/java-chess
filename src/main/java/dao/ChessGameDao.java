package dao;

import domain.chessgame.ChessGame;
import exception.DataBaseException;
import exception.DeserializeException;
import exception.SerializeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class ChessGameDao {

    private static final String SERVER_PORT_NUMBER = "localhost:13306";
    private static final String DATABASE_NAME = "chess_db";
    private static final String DATABASE_OPTION = "?useSSL=false&serverTimezone=UTC";
    private static final String DATABASE_USER_NAME = "root";
    private static final String DATABASE_USER_PASSWORD = "root";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(" !! JDBC Driver load 오류: " + e.getMessage());
        }

        return DriverManager
            .getConnection(
                "jdbc:mysql://" + SERVER_PORT_NUMBER + "/" + DATABASE_NAME + DATABASE_OPTION,
                DATABASE_USER_NAME,
                DATABASE_USER_PASSWORD);
    }

    public ChessGame selectByGameId(int gameId) {
        String query = "SELECT * FROM chessGame WHERE game_id = ?";

        try (Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, String.valueOf(gameId));
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return new ChessGame();
            }

            return chessGame(rs.getString("serialized_base64_chess_game"));
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public ChessGame updateChessGameByGameId(int gameId, ChessGame chessGame) {
        String query = "INSERT INTO chessGame (game_id, serialized_base64_chess_game) "
            + "VALUES (?, ?) ON DUPLICATE KEY UPDATE serialized_base64_chess_game = ?";

        try (Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, String.valueOf(gameId));
            pstmt.setString(2, serializedChessGame(chessGame));
            pstmt.setString(3, serializedChessGame(chessGame));
            pstmt.executeUpdate();
            return selectByGameId(gameId);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public String serializedChessGame(ChessGame chessGame) {
        byte[] serializedMember;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(chessGame);
                serializedMember = baos.toByteArray();
            }
        } catch (IOException e) {
            throw new SerializeException(e);
        }
        return Base64.getEncoder().encodeToString(serializedMember);
    }

    public ChessGame chessGame(String serializedMember) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(
            Base64.getDecoder().decode(serializedMember))) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object objectMember = ois.readObject();
                return (ChessGame) objectMember;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new DeserializeException(e);
        }
    }

}
