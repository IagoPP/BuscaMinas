import java.util.Random;

public class BuscaMinas {
    private int x;
    private int y;
    private int nminas;
    private char mina;
    private char blank;
    private byte numCell;
    private char[][] gameBoard;
    private char[][] userBoard;

    BuscaMinas() {
        x = 8;
        y = 6;
        nminas = 5;
        mina = '*';
        blank = ' ';
        numCell = 0;
        userBoard = new char[y][x];
        gameBoard = new char[y][x];
        createBlankBoard(userBoard);
        createBlankBoard(gameBoard);
    }

    // Base Boards Setup
    public void createBlankBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = blank;
            }
        }
    }

    public void placeMines() {
        Random rng = new Random();
        int count = 0;

        while (count < nminas) {
            int miney = rng.nextInt(0, gameBoard.length);
            int minex = rng.nextInt(0, gameBoard[0].length);

            if (gameBoard[miney][minex] != mina) {
                gameBoard[miney][minex] = mina;
                count++;
            }
        }
    }

    public void placeNums() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                byte minen = 0;
                if (gameBoard[i][j] == blank) {
                    if (i > 0) {
                        if (gameBoard[i - 1][j] == mina) {
                            minen++;
                        }
                    }
                    if (i < gameBoard.length - 1) {
                        if (gameBoard[i + 1][j] == mina) {
                            minen++;
                        }
                    }
                    if (j > 0) {
                        if (gameBoard[i][j - 1] == mina) {
                            minen++;
                        }
                    }
                    if (j < gameBoard.length - 1) {
                        if (gameBoard[i][j + 1] == mina) {
                            minen++;
                        }
                    }
                    if (i > 0 && j > 0) {
                        if (gameBoard[i - 1][j - 1] == mina) {
                            minen++;
                        }
                    }
                    if (i < gameBoard.length - 1 && j < gameBoard[0].length - 1) {
                        if (gameBoard[i + 1][j + 1] == mina) {
                            minen++;
                        }
                    }
                    if (i > 0 && j < gameBoard[0].length - 1) {
                        if (gameBoard[i - 1][j + 1] == mina) {
                            minen++;
                        }
                    }
                    if (i < gameBoard.length - 1 && j > 0) {
                        if (gameBoard[i + 1][j - 1] == mina) {
                            minen++;
                        }
                    }
                    numCell = minen;
                    gameBoard[i][j] = Integer.toString(numCell).charAt(0);
                }
            }
        }
    }

    // Gameplay loop

    public void revealZeroes(int cx, int cy) {

        int sx = cx;
        int sy = cy;

        while (true) {
            if (sy < gameBoard.length - 1 && (gameBoard[sy + 1][sx] == '0')) {  
                userBoard[sy][sx]='0';
                sy++;
                checkTouching(sy, sx);
                gameBoard[sy][sx] = '↑';
            } else if (sx < gameBoard[0].length - 1 && (gameBoard[sy][sx + 1] == '0')) {
                userBoard[sy][sx]='0';
                sx++;
                checkTouching(sy, sx);
                gameBoard[sy][sx] = '←';
            } else if (sy > 0 && (gameBoard[sy - 1][sx] == '0')) {
                userBoard[sy][sx]='0';
                sy--;
                checkTouching(sy, sx);
                gameBoard[sy][sx] = '↓';
            } else if (sx > 0 && (gameBoard[sy][sx - 1] == '0')) {
                userBoard[sy][sx]='0';
                sx--;
                checkTouching(sy, sx);
                gameBoard[sy][sx] = '→';
            } else if (sy < gameBoard.length - 1 && gameBoard[sy][sx] == '↓') {
                userBoard[sy][sx]='0';
                gameBoard[sy][sx] = 'X';
                sy++;
            } else if (sx < gameBoard[0].length - 1 && gameBoard[sy][sx] == '→') {
                userBoard[sy][sx]='0';
                gameBoard[sy][sx] = 'X';
                sx++;
            } else if (sy > 0 && gameBoard[sy][sx] == '↑') {
                userBoard[sy][sx]='0';
                gameBoard[sy][sx] = 'X';
                sy--;
            } else if (sx > 0 && gameBoard[sy][sx] == '←') {
                userBoard[sy][sx]='0';
                gameBoard[sy][sx] = 'X';
                sx--;
            } else {
                break;
            }
        }
    }

    public void checkTouching(int sx, int sy){
        if (sy < gameBoard.length - 1) {userBoard[sy+1][sx]=gameBoard[sy+1][sx];}
        if (sx < gameBoard[0].length - 1) {userBoard[sy][sx+1]=gameBoard[sy][sx+1];}
        if (sy > 0) {userBoard[sy-1][sx]=gameBoard[sy-1][sx];}
        if (sx > 0) {userBoard[sy][sx-1]=gameBoard[sy][sx-1];}
        if (sy < gameBoard.length - 1 && sx < gameBoard[0].length - 1) {userBoard[sy+1][sx+1]=gameBoard[sy+1][sx+1];}
        if (sy > 0 && sx > 0) {userBoard[sy-1][sx-1]=gameBoard[sy-1][sx-1];}
        if (sy < gameBoard.length - 1 && sx > 0) {userBoard[sy+1][sx-1]=gameBoard[sy+1][sx-1];}
        if (sy > 0 && sx < gameBoard[0].length - 1) {userBoard[sy-1][sx+1]=gameBoard[sy-1][sx+1];}
    }

    // getters e setters
    public char[][] getUserBoard() {
        return this.userBoard;
    }

    public char[][] getGameBoard() {
        return this.gameBoard;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}