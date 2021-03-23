# 체스 1단계 - 체스판 초기화

### 기능 요구 사항

- 체스 말 도메인
    - [x] `Pawn`
        - `Pawn`은 앞으로 한 칸 또는 두 칸만 이동 가능하다. (후퇴가 불가능)
        - `Pawn`은 **처음 시작할 때만** 2칸 움직일 수 있다.
        - `Pawn`은 전방 대각선 한 칸으로만 잡을 수 있다.
            - 상대편 말이 존재하는 경우에만 이동 가능하다.

    - [x] `Bishop`
        - `Bishop`은 대각선 방향으로 칸수 제한 없이 이동 가능하다.
            - 목적지까지의 이동 경로 중간에 말이 존재할 경우 목적지까지 이동이 불가능하다.

    - [x] `Knight`
        - 두 칸 전진한 상태에서 좌우로 한 칸 움직일 수 있다.
        - 유일하게 다른 피스를 뛰어넘을 수 있다.

    - [x] `King`
        - 전후좌우, 대각선으로 한칸씩 움직일 수 있다.

    - [x] `Queen`
        - 전후좌우, 대각선으로 칸수 제한없이 움직일 수 있다.

    - [x] `Rook`
        - 전후좌우로 칸 수 제한없이 움직일 수 있다.

    - 공통 이동 로직
        - [x] 현재 위치로 이동할 수 없다.
        - [ ] 체스판을 벗어나는 곳으로 이동할 수 없다.(Position 객체에서 예외 발생하도록 구현한다.)
        - [x] 같은 편 말이 존재하는 위치로 이동할 수 없다.
        - [x] 이동하려는 위치에 상대편 말이 있으면, 상대편 말을 먹고 해당위치로 이동한다.
            - 단, Pawn은 예외적으로 이동가능 위치와 말을 먹을 수 있는 위치가 구분된다.

### TODO

- 이동할 위치에 만약 말이 존재하는 경우, 이동 여부 판별 로직 구현
- Piece의 null들을 empty 클래스로 바꿔준다.
- 캐슬링, 양파상, 프로모션 등의 규칙을 구현한다.