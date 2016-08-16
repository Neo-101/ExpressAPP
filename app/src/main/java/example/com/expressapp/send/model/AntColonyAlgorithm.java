package example.com.expressapp.send.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by lxs on 2016/8/15.
 */
public class AntColonyAlgorithm {
    static public double disSum=0;
    //设定保存最邻近点的最大数量
    static private int nearMaxNum=3;

    /*
     * 分别传入点号顺序对应数组、连点间距离数组（乱序均可）、首点号对应索引
     * 返回值为epList数字的顺序索引
     * 比如第一个点是“5”，点号顺序是“3”、“5”、“2”，“8”，返回结果为“2-3-5-8”；
     * 则epList=[3,5,2,8],startIndex=1;return [2,0,1,3];
     * 此外，此函数仅返回一次“概率最近”的结果，若要获取最终结果，需要用到一个类内全局变量来多次比较。
     * */
    static public int[] AntMethod(int[] epList,ExpressDistance[] edList,int startIndex ){
        //全局变量记录总距离
        disSum=0;
        //设定保存最邻近点的最大数量
        int nearMaxNum=3;
        //计算点的总个数
        int num=epList.length;
        //计算距离的总个数
        int numOfedList=edList.length;
        //用点的索引记录路线
        int order[]=new int[num];
        //与“epList”共用索引，记录是否被使用
        boolean isSelect[]=new boolean[num];
        //初始化为FALSE，所有点均未使用
        for(int i=0;i<num;i++){
            isSelect[i]=false;
        }
        //startIndex，开始索引点为起点
        isSelect[startIndex]=true;
        order[0]=startIndex;
        //已使用点的个数
        int inPointNum=1;
        //当前用于遍历计算距离的起始点的索引
        int currentIndex=startIndex;
        for(;inPointNum<num;inPointNum++){
            double dis=9999999;
            //从各临近点中选取的“概率最近点”
            int shortIndex=0;
            //各临近点的索引
            int[] nearIndex=new int[nearMaxNum];
            //各临近点距目标点的距离
            double[] neardis=new double[nearMaxNum];
            //初始化各临近点的距离，索引值越大，距离越近
            for(int i=0;i<nearMaxNum;i++){
                neardis[i]=dis-i;
                nearIndex[i]=-1;
            }
            for(int i=0;i<num;i++){
                if(!isSelect[i]){
                    //遍历寻找距离
                    double distmp=0;
                    for(int indexOfedList=0;indexOfedList<numOfedList;indexOfedList++){
                        if(edList[indexOfedList].strPoint==epList[currentIndex]&&edList[indexOfedList].endPoint==epList[i]){
                            distmp=edList[indexOfedList].dis;;
                            break;
                        }
                        if(edList[indexOfedList].endPoint==epList[currentIndex]&&edList[indexOfedList].strPoint==epList[i]){
                            distmp=edList[indexOfedList].dis;;
                            break;
                        }
                    }
                    //记录插入的位置
                    int insertIndex=0;
                    //遍历，得到插入的位置
                    for(;insertIndex<nearMaxNum;insertIndex++){
                        if(distmp>neardis[insertIndex])
                            break;
                    }
                    if(insertIndex!=0){
                        //其余被插入临近点前推
                        for(int j=0;j<insertIndex-1;j++){
                            nearIndex[j]=nearIndex[j+1];
                            neardis[j]=neardis[j+1];
                        }
                        //插入最新点，保存其索引和距离
                        nearIndex[insertIndex-1]=i;
                        neardis[insertIndex-1]=distmp;
                    }

                }
            }
            //计算临近点距离总和
            double neardisSum=0;
            for(int i=0;i<nearMaxNum;i++){
                if(nearIndex[i]!=-1)
                    neardisSum+=neardis[i];
            }
            //依照距离计算权重，越近，权重越大
            double[] disPow=new double[nearMaxNum];
            for(int i=0;i<nearMaxNum;i++){
                if(nearIndex[i]!=-1)
                    disPow[i]=neardisSum/neardis[i];
            }
            //计算总权重
            double disPowSum=0;
            for(int i=0;i<nearMaxNum;i++){
                if(nearIndex[i]!=-1)
                    disPowSum+=disPow[i];
            }
            //按照产生的随机数，参照权重，概率得到“最近点”
            double powValueAdd=0;
            double powValue=(double)Math.random()*disPowSum;
            for(int i=0;i<nearMaxNum;i++){
                if(nearIndex[i]!=-1){
                    powValueAdd+=disPow[i];
                    if(powValueAdd>powValue){
                        //保存“概率最近点”的索引
                        shortIndex=nearIndex[i];
                        break;
                    }
                }
            }
            //总距离增加
            for(int indexOfedList=0;indexOfedList<numOfedList;indexOfedList++){
                if(edList[indexOfedList].strPoint==epList[currentIndex]&&edList[indexOfedList].endPoint==epList[shortIndex]){
                    disSum+=edList[indexOfedList].dis;
                    //System.out.println("disNow:"+edList[indexOfedList].dis+"\tdisSum:"+disSum+"\tinPointNum:"+inPointNum);
                    break;
                }
                if(edList[indexOfedList].endPoint==epList[currentIndex]&&edList[indexOfedList].strPoint==epList[shortIndex]){
                    disSum+=edList[indexOfedList].dis;
                    //System.out.println("disNow:"+edList[indexOfedList].dis+"\tdisSum:"+disSum+"\tinPointNum:"+inPointNum);
                    break;
                }
            }
            //该点已被使用
            isSelect[shortIndex]=true;
            //记录其索引
            order[inPointNum]=shortIndex;
            //改变“当前点”
            currentIndex=shortIndex;
        }
        //返回点号顺序
        return order;
    }

    /*
     * 在“AntMethod”的基础上多一个参数“proTimes”（尝试次数），建议不少于“距离”的个数；
     */
    static public int[] AntsMethod(int[] epList,ExpressDistance[] edList,int startIndex,int proTimes){
        int orderIndex[]=new int[epList.length];
        int orderIndexTmp[]=new int[epList.length];
        double disSumTmp=99999999;
        for(int i=0;i<proTimes;i++){
            orderIndexTmp=AntMethod(epList,edList,startIndex);
            if(disSumTmp>disSum){
                disSumTmp=disSum;
                orderIndex=orderIndexTmp;
            }
        }
        return orderIndex;
    }
}
