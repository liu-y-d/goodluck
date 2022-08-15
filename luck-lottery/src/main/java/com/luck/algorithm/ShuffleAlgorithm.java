package com.luck.algorithm;


import com.luck.vo.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 洗牌算法
 * @author Liuyunda
 * @date 2022/6/8 0:01
 * @email man021436@163.com
 */
public class ShuffleAlgorithm {
    private String numList[]={"A","2","3","4","5","6","7","8","9","10",
            "J","Q","K"};
    private String chrList[]={"*","&","^","%"};
    //cardList表示全部牌，curList表示当前的牌
    private Card cardList[],curList[];
    private int curSize;
    private static Random rdm=new Random();

    public static final int CARDSIZE=13*4;

    public Card[] getCardList() {
        return cardList;
    }

    public Card[] getCurList() {
        return curList;
    }

    public void setCurList(Card[] curList) {
        for(int i=0;i<curList.length;i++){
            this.curList[i]=new Card(curList[i]);
        }
    }

    public ShuffleAlgorithm(){
        //this.size=numList.length*chrList.length;
        cardList=new Card[this.CARDSIZE];
        curList=new Card[this.CARDSIZE];
        for(int i=0;i<numList.length;i++){
            for(int j=0;j<chrList.length;j++){
                cardList[i*chrList.length+j]=new Card(chrList[j],numList[i]);
            }
        }
        this.IOShuffle(CARDSIZE);
        this.KDShuffle(this.CARDSIZE);
    }

    //从全部牌列表中第i张后面的牌中随机选出一张到做为前牌中的第i张
    //改变了牌列表的顺序。
    public void FYShuffle(int size){
        int tempInd;
        for(int i=0;i<size;i++){
            tempInd=i+rdm.nextInt(size-i);
            curList[i]=new Card(cardList[tempInd]);
            this.swap(cardList[i],cardList[tempInd]);
        }
    }
    public static List<Long> FYShuffle(List<Long> array){
        Long[] original = Arrays.copyOf(array.toArray(new Long[array.size()]), array.size());
        int tempInd;
        for(int i=0;i<original.length;i++){
            tempInd=i+rdm.nextInt(original.length-i);
            Long temp;
            temp = original[i];
            original[i] = original[tempInd];
            original[tempInd] = temp;
        }
        return new ArrayList<>(Arrays.asList(original));
    }

    //将前面的i张牌中随机选出一张和第i张交换位置
    //节省了空间
    public void KDShuffle(int size){
        int tempInd;
        this.setCurList(this.getCardList());
        for(int i=size-1;i>0;i--){
            tempInd=rdm.nextInt(i);
            this.swap(curList[i], curList[tempInd]);
        }
    }

    //在前面的i-1张牌中随机选出一张和第i张牌交换。
    public void IOShuffle(int size){
        int tempInd;
        this.setCurList(this.getCardList());
        for(int i=1;i<size;i++){
            tempInd=rdm.nextInt(i);
            this.swap(curList[i], curList[tempInd]);
        }
    }

    private void swap(Card card, Card card2) {
        // TODO Auto-generated method stub
        Card temp=new Card(card);
        card.setCard(card2);
        card2.setCard(temp);
    }

    public void printCard(Card[] a){
        for(int i=0;i<a.length;i++){
            if(i%4==0){
                System.out.printf("\n");
            }
            System.out.printf("%-9s  ",a[i].toString());
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ShuffleAlgorithm cg=new ShuffleAlgorithm();
        //cg.printCard(cg.getCardList());
        cg.printCard(cg.getCardList());
        System.out.println();
        cg.printCard(cg.getCurList());
    }

}
