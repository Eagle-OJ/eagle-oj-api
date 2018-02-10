package com.eagleoj.web.data.status;

/**
 * @author Smith
 **/
public enum ContestTypeStatus {
    OI_CONTEST_NORMAL_TIME(0), OI_CONTEST_LIMIT_TIME(1),
    ACM_CONTEST_NORMAL_TIME(2), ACM_CONTEST_LIMIT_TIME(3);

    private int number;

    private ContestTypeStatus(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
