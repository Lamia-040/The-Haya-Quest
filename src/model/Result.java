package model;

import java.sql.Timestamp;

public class Result {
    private int id;
    private int userId;
    private int hadithId;
    private int score;
    private Timestamp submittedAt;

    public Result() {}

    public Result(int id, int userId, int hadithId, int score, Timestamp submittedAt) {
        this.id = id;
        this.userId = userId;
        this.hadithId = hadithId;
        this.score = score;
        this.submittedAt = submittedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHadithId() {
        return hadithId;
    }

    public void setHadithId(int hadithId) {
        this.hadithId = hadithId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }
}
