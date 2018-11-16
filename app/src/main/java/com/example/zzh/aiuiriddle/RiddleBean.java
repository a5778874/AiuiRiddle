package com.example.zzh.aiuiriddle;

public class RiddleBean {
    private String leftLine;  //上联
    private String rightLine;  //下联
    private String tips;        //提示

    public RiddleBean() {
    }

    public RiddleBean(String leftLine, String rightLine, String tips) {
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.tips = tips;
    }

    public String getLeftLine() {
        return leftLine;
    }

    public void setLeftLine(String leftLine) {
        this.leftLine = leftLine;
    }

    public String getRightLine() {
        return rightLine;
    }

    public void setRightLine(String rightLine) {
        this.rightLine = rightLine;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "RiddleBean{" +
                "leftLine='" + leftLine + '\'' +
                ", rightLine='" + rightLine + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
