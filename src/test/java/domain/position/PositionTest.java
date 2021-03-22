package domain.position;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("포지션을 생성한다.")
    @Test
    void createTest() {
        Position position = new Position(1, 2);

        assertThat(position.getRowDegree()).isEqualTo(1);
        assertThat(position.getColumnDegree()).isEqualTo(2);
    }

    @DisplayName("체스 좌표계를 사용하여 포지션을 생성한다.")
    @Test
    void chessPositionCreate() {
        Position position = new Position("d5");

        assertThat(position.getRowDegree()).isEqualTo(3);
        assertThat(position.getColumnDegree()).isEqualTo(3);
    }

    @DisplayName("포지션이 체스판 범위에 들지 않는다.")
    @Test
    void isNotChessBoardPositionTest() {
        Position position = new Position("k1");
        assertThat(position.isChessBoardPosition()).isFalse();
    }

    @DisplayName("포지션이 체스판 범위에 든다.")
    @Test
    void isChessBoardPositionTest() {
        Position position = new Position("h1");
        assertThat(position.isChessBoardPosition()).isTrue();
    }

    @DisplayName("두 포지션은 수평 또는 수직이 아니다.")
    @Test
    void isNotLinearPositionTest() {
        Position source = new Position("d5");
        Position target = new Position("e6");

        assertThat(source.isNotLinearPosition(target)).isTrue();
    }

    @DisplayName("두 포지션은 수평 또는 수직이다.")
    @Test
    void isLinearPositionTest() {
        Position source = new Position("d5");
        Position target = new Position("h5");

        assertThat(source.isNotLinearPosition(target)).isFalse();
    }

    @DisplayName("두 포지션은 대각선 상에 있지 않다.")
    @Test
    void isNotDiagonalPosition() {
        Position source = new Position("d5");
        Position target = new Position("e5");

        assertThat(source.isNotDiagonalPosition(target)).isTrue();
    }

    @DisplayName("두 포지션은 대각선 상에 있다.")
    @Test
    void isDiagonalPosition() {
        Position source = new Position("d5");
        Position target = new Position("e6");

        assertThat(source.isNotDiagonalPosition(target)).isFalse();
    }

    @DisplayName("특정 방향으로 이동한 포지션을 생성한다.")
    @Test
    void sumTest() {
        Position source = new Position(1, 3);
        Position result = source.sum(Direction.EAST);

        assertThat(result.getRowDegree()).isEqualTo(1);
        assertThat(result.getColumnDegree()).isEqualTo(4);
    }

    @DisplayName("두 포지션의 차이를 갖는 포지션을 생성한다.")
    @Test
    void diffTest() {
        Position source = new Position(3, 3);
        Position target = new Position(5, 7);
        Position diff = source.diff(target);

        assertThat(diff.getRowDegree()).isEqualTo(2);
        assertThat(diff.getColumnDegree()).isEqualTo(4);
    }
}