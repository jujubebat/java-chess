package domain.piece;

import domain.board.Board;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class KnightTest {

    @DisplayName("나이트는 두 칸 전진한 상태에서 좌우로 한 칸 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"d5,e7", "d5,f6", "d5,f4", "d5,e3", "d5,c3","d5,b4", "d5,b6", "d5,c7"}, delimiter = ',')
    void testMoveEmptyPlace(String source, String target) {
        Board board = new Board();
        Position sourcePosition = new Position(source);
        Position targetPosition = new Position(target);
        Knight blackKnight = new Knight(true);

        board.put(sourcePosition, blackKnight);

        assertThat(blackKnight.canMove(board, sourcePosition, targetPosition)).isTrue();
    }

    @DisplayName("나이트는 이동 가능 범위가 아닌 위치로 이동 할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"d5,a5", "d5,h5", "d5,d8", "d5,d1"}, delimiter = ',')
    void testNotMoveEmptyPlace(String source, String target) {
        Board board = new Board();
        Position sourcePosition = new Position(source);
        Position targetPosition = new Position(target);
        Knight blackKnight = new Knight(true);

        board.put(sourcePosition, blackKnight);

        assertThat(blackKnight.canMove(board, sourcePosition, targetPosition)).isFalse();
    }

    @DisplayName("나이트는 같은 편 기물이 있는 위치로 이동할 수 없다.")
    @Test
    void testMoveSameColorPiecePlace() {
        Board board = new Board();
        Position sourcePosition = new Position("d5");
        Position targetPosition = new Position("e7");
        Knight blackKnight = new Knight(true);
        Rook blackRook = new Rook(true);

        board.put(sourcePosition, blackKnight);
        board.put(targetPosition, blackRook);

        assertThat(blackKnight.canMove(board, sourcePosition, targetPosition)).isFalse();
    }

    @DisplayName("킹은 다른 편 기물이 있는 위치로 이동할 수 있다.")
    @Test
    void testMoveEnemyPiecePlace() {
        Board board = new Board();
        Position sourcePosition = new Position("d5");
        Position targetPosition = new Position("e7");
        Knight blackKnight = new Knight(true);
        Rook blackRook = new Rook(false);

        board.put(sourcePosition, blackKnight);
        board.put(targetPosition, blackRook);

        assertThat(blackKnight.canMove(board, sourcePosition, targetPosition)).isTrue();
    }

}