package com.sudoku.logicmaths.Model;

public class modelanswer {
    int levelid;
    int answer;

    public modelanswer(int levelid, int answer) {
        this.levelid = levelid;
        this.answer = answer;
    }

    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
