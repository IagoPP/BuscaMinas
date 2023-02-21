import java.util.Random;

public class BuscaMinas {
    private int x;
    private int y;
    private int tMinas;
    private char mina;
    private char blank;
    private int minesLeft;
    private char reveal;
    private char flagin;
    private char questionin;
    private char flagout;
    private char questionout;
    private char action;
    private char[][] gameBoard;
    private char[][] userBoard;

    BuscaMinas() {
        x = 9;
        y = 7;
        tMinas = 14;
        mina = '*';
        blank = ' ';
        minesLeft = tMinas;
        reveal = 'r';
        flagin = 'f';
        questionin = '?';
        action = reveal;
        flagout = '¶';
        questionout= '?';
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

        while (count < tMinas) {
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
                byte nMinas = 0;
                if (gameBoard[i][j] == blank) {
                    if (i > 0) {
                        if (gameBoard[i - 1][j] == mina) {
                            nMinas++;
                        }
                    }
                    if (i < gameBoard.length - 1) {
                        if (gameBoard[i + 1][j] == mina) {
                            nMinas++;
                        }
                    }
                    if (j > 0) {
                        if (gameBoard[i][j - 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (j < gameBoard[0].length - 1) {
                        if (gameBoard[i][j + 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (i > 0 && j > 0) {
                        if (gameBoard[i - 1][j - 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (i < gameBoard.length - 1 && j < gameBoard[0].length - 1) {
                        if (gameBoard[i + 1][j + 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (i > 0 && j < gameBoard[0].length - 1) {
                        if (gameBoard[i - 1][j + 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (i < gameBoard.length - 1 && j > 0) {
                        if (gameBoard[i + 1][j - 1] == mina) {
                            nMinas++;
                        }
                    }
                    gameBoard[i][j] = Integer.toString(nMinas).charAt(0);
                }
            }
        }
    }

    // Gameplay loop
    

    public void chooseAction(char action, int cx, int cy){
        if (action==reveal){
            if (userBoard[cy][cx]==gameBoard[cy][cx]){
                revealAround(cy, cx);
            }
            userBoard[cy][cx]=gameBoard[cy][cx];         
            if (gameBoard[cy][cx]=='0'){
                revealZeroes(cx, cy);
            }
        }else if (action==flagin){
            if (userBoard[cy][cx]==flagout){
                userBoard[cy][cx]=blank;
                minesLeft++;
            }else if (Character.isDigit(userBoard[cy][cx])==false){
                userBoard[cy][cx]=flagout;
                minesLeft--;
            }
        }else if (action==questionin){
            if (userBoard[cy][cx]==questionout){
                userBoard[cy][cx]=blank;
            }else if (Character.isDigit(userBoard[cy][cx])==false){
                userBoard[cy][cx]=questionout;
            }
        }
    }

    //maybe include start if statement?
    public void revealZeroes(int cx, int cy) {

        int sx = cx;
        int sy = cy;
        checkTouching(sy, sx);
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
                gameBoard[sy][sx]='X';
                userBoard[sy][sx]='0';
                sy++;
            } else if (sx < gameBoard[0].length - 1 && gameBoard[sy][sx] == '→') {
                gameBoard[sy][sx]='X';
                userBoard[sy][sx]='0';
                sx++;
            } else if (sy > 0 && gameBoard[sy][sx] == '↑') {
                gameBoard[sy][sx]='X';
                userBoard[sy][sx]='0';
                sy--;
            } else if (sx > 0 && gameBoard[sy][sx] == '←') {
                gameBoard[sy][sx]='X';
                userBoard[sy][sx]='0';
                sx--;
            } else {
                break;
            }
        }
    }

    //add conditions for revealed cell counting (exclude 'X' '0' and arleady filled cells)
    public void checkTouching(int sy, int sx){
        if (sy < gameBoard.length - 1 && Character.isDigit(gameBoard[sy+1][sx]) && Character.getNumericValue(gameBoard[sy+1][sx])>0) {userBoard[sy+1][sx]=gameBoard[sy+1][sx];}
        if (sx < gameBoard[0].length - 1  && Character.isDigit(gameBoard[sy][sx+1]) && Character.getNumericValue(gameBoard[sy][sx+1])>0) {userBoard[sy][sx+1]=gameBoard[sy][sx+1]; }
        if (sy > 0  && Character.isDigit(gameBoard[sy-1][sx]) && Character.getNumericValue(gameBoard[sy-1][sx])>0) {userBoard[sy-1][sx]=gameBoard[sy-1][sx]; }
        if (sx > 0  && Character.isDigit(gameBoard[sy][sx-1]) && Character.getNumericValue(gameBoard[sy][sx-1])>0) {userBoard[sy][sx-1]=gameBoard[sy][sx-1]; }
        if (sy < gameBoard.length - 1 && sx < gameBoard[0].length - 1  && Character.isDigit(gameBoard[sy+1][sx+1]) && Character.getNumericValue(gameBoard[sy+1][sx+1])>0) {userBoard[sy+1][sx+1]=gameBoard[sy+1][sx+1]; }
        if (sy > 0 && sx > 0  && Character.isDigit(gameBoard[sy-1][sx-1]) && Character.getNumericValue(gameBoard[sy-1][sx-1])>0) {userBoard[sy-1][sx-1]=gameBoard[sy-1][sx-1]; }
        if (sy < gameBoard.length - 1 && sx > 0 && Character.isDigit(gameBoard[sy+1][sx-1]) && Character.getNumericValue(gameBoard[sy+1][sx-1])>0) {userBoard[sy+1][sx-1]=gameBoard[sy+1][sx-1]; }
        if (sy > 0 && sx < gameBoard[0].length - 1 && Character.isDigit(gameBoard[sy-1][sx+1]) && Character.getNumericValue(gameBoard[sy-1][sx+1])>0) {userBoard[sy-1][sx+1]=gameBoard[sy-1][sx+1]; }
    }

    public void revealAround(int sy, int sx){
        if (sy < gameBoard.length - 1 && (Character.isDigit(gameBoard[sy+1][sx]) || (gameBoard[sy+1][sx]==mina && userBoard[sy+1][sx]!=flagout))) {
            userBoard[sy+1][sx]=gameBoard[sy+1][sx];
            if (userBoard[sy+1][sx]=='0') {revealZeroes(sx, sy+1);}
        }
        if (sx < gameBoard[0].length - 1  && (Character.isDigit(gameBoard[sy][sx+1]) || (gameBoard[sy][sx+1]==mina && userBoard[sy][sx+1]!=flagout))) {
            userBoard[sy][sx+1]=gameBoard[sy][sx+1];
            if (userBoard[sy][sx+1]=='0') {revealZeroes(sx+1, sy);}
        }
        if (sy > 0  && (Character.isDigit(gameBoard[sy-1][sx]) || (gameBoard[sy-1][sx]==mina && userBoard[sy-1][sx]!=flagout))) {
            userBoard[sy-1][sx]=gameBoard[sy-1][sx];
            if (userBoard[sy-1][sx]=='0') {revealZeroes(sx, sy-1);}
        }
        if (sx > 0  && (Character.isDigit(gameBoard[sy][sx-1]) || (gameBoard[sy][sx-1]==mina && userBoard[sy][sx-1]!=flagout))) {
            userBoard[sy][sx-1]=gameBoard[sy][sx-1];
            if (userBoard[sy][sx-1]=='0') {revealZeroes(sx-1, sy);}
        }
        if (sy < gameBoard.length - 1 && sx < gameBoard[0].length - 1  && (Character.isDigit(gameBoard[sy+1][sx+1]) || (gameBoard[sy+1][sx+1]==mina && userBoard[sy+1][sx+1]!=flagout))) {
            userBoard[sy+1][sx+1]=gameBoard[sy+1][sx+1];
            if (userBoard[sy+1][sx+1]=='0') {revealZeroes(sx+1, sy+1);}
        }
        if (sy > 0 && sx > 0  && (Character.isDigit(gameBoard[sy-1][sx-1]) || (gameBoard[sy-1][sx-1]==mina && userBoard[sy-1][sx-1]!=flagout))) {
            userBoard[sy-1][sx-1]=gameBoard[sy-1][sx-1];
            if (userBoard[sy-1][sx-1]=='0') {revealZeroes(sx-1, sy-1);}
        }
        if (sy < gameBoard.length - 1 && sx>0 && (Character.isDigit(gameBoard[sy+1][sx-1]) || (gameBoard[sy+1][sx-1]==mina && userBoard[sy+1][sx-1]!=flagout))) {
            userBoard[sy+1][sx-1]=gameBoard[sy+1][sx-1];
            if (userBoard[sy+1][sx-1]=='0') {revealZeroes(sx-1, sy+1);}
        }
        if (sy > 0 && sx < gameBoard[0].length - 1 && (Character.isDigit(gameBoard[sy-1][sx+1]) || (gameBoard[sy-1][sx+1]==mina && userBoard[sy-1][sx+1]!=flagout))) {
            userBoard[sy-1][sx+1]=gameBoard[sy-1][sx+1];
            if (userBoard[sy-1][sx]+1=='0') {revealZeroes(sx+1, sy-1);}
        }
    }

    public boolean winCon(){
        int numCells=0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (Character.isDigit(userBoard[i][j]) || userBoard[i][j]=='X') {
                    numCells++;
                }
            }
        }
        if (numCells==(userBoard.length*userBoard[0].length)-tMinas) {
            return true;
        }else{
            return false;
        }
    }

    //kill screen
    public void loseBoard(char failedMine){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (userBoard[i][j]==flagout && gameBoard[i][j]!=mina) {
                    userBoard[i][j]=failedMine;
                }
                if (userBoard[i][j]!=flagout && gameBoard[i][j]==mina) {
                    userBoard[i][j]=mina;
                }
            }
        }
    }

    // getters e setters
    public char[][] getUserBoard() {
        return this.userBoard;
    }
    public char[][] getGameBoard() {
        return this.gameBoard;
    }
    public int getMinesLeft() {
        return minesLeft;
    }
    public char getMina() {
        return mina;
    }
    public char getReveal() {
        return reveal;
    }
    public char getAction() {
        return action;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setAction(char action) {
        this.action = action;
    }
}