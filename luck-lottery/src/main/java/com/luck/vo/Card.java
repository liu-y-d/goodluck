package com.luck.vo;

import lombok.Data;

/**
 * @author Liuyunda
 * @date 2022/6/8 0:17
 * @email man021436@163.com
 */
@Data
public class Card {
    private String num,chr;
    public Card(String chr,String num){
        this.num=num;
        this.chr=chr;
    }

    public Card getCard(){
        return this;
    }

    public void setCard(Card a){
        this.num=a.num;
        this.chr=a.chr;
    }

    public Card(Card a){
        this(a.chr,a.num);
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public String toString(){
        return chr+" "+num;
    }
}
