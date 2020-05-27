package com.caigen.graduationproject.model;

/**
 * Created by Yasin on 2020-03-18 20:13
 */
public class DividePageBean {
    private int startIndex;  //每页开始记录索引
    //private int endIndex;    //结束页数
    private int displayNum;//每页展示的记录个数
    private int total;       //总记录个数
    private String param;    //参数

    private static final int defaultDisplayNum = 5;
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public DividePageBean(){
        displayNum = defaultDisplayNum;
    }

    public DividePageBean(int startIndex,int displayNum){
        //this();
        this.startIndex = startIndex;
        this.displayNum = displayNum;
    }

    public int getTotalPage(){
        int totalPage;  //总页数
        if(0 == total % displayNum){    //若刚好总记录可整除总页面
            totalPage = total/displayNum;
        }else{                          //若不能整除则加一
            totalPage = total/displayNum +1;
        }
        if(0 == totalPage)
            totalPage = 1;  //若没有记录默认为1页
        return totalPage;
    }

    public int getLast(){
        int last;
        if(0 == total/displayNum){
            last = total-displayNum;
        }else{
            last = total-total%displayNum;
        }
        last = last<0?0:last;
        return last;
    }

    public boolean isHasPrevious(){
        if(startIndex == 0)
            return false;
        else
            return true;
    }

    public boolean isHasNext(){
        if (startIndex == getLast()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "DividePageBean{" +
                "startIndex=" + startIndex +
                ", displayNum=" + displayNum +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }
}
