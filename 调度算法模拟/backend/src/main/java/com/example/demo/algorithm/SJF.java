package com.example.demo.algorithm;

import com.example.demo.domain.JCB;

import java.util.ArrayList;
import java.util.Comparator;

public class SJF {
    private ArrayList<JCB> jobsOfSJF;	//保存实参

    public SJF(ArrayList<JCB> jobs) {
        super();
        jobsOfSJF = jobs;
    }

    public ArrayList<JCB> SJFAlgorithm() {
        //没有作业
        if(jobsOfSJF.isEmpty()) {
            return jobsOfSJF;
        }
        //按照作业的到达时间排序
        jobsOfSJF.sort(new Comparator<JCB>() {
            @Override
            public int compare(JCB o1, JCB o2) {
                if(o1.getArriveTime() < o2.getArriveTime())
                    return -1;
                else if(o1.getArriveTime() > o2.getArriveTime())
                    return 1;
                else {
                    if(o1.getServeTime() < o2.getServeTime())
                        return -1;
                    else if(o1.getServeTime() > o2.getServeTime())
                        return 1;
                    else return 0;
                }
            }
        });
        //定义算法最终结果序列
        ArrayList<JCB> lastJobs = new ArrayList<>();
        //第一个作业，计算其各个时间并把其加到最终序列第一位，然后删除传参序列中的该元素
        jobsOfSJF.get(0).setStartTime(jobsOfSJF.get(0).getArriveTime());
        jobsOfSJF.get(0).setWaitTime(0);
        jobsOfSJF.get(0).setCompleteTime(jobsOfSJF.get(0).getStartTime() + jobsOfSJF.get(0).getServeTime());
        jobsOfSJF.get(0).setCyclingTime(jobsOfSJF.get(0).getCompleteTime() - jobsOfSJF.get(0).getArriveTime());
        lastJobs.add(jobsOfSJF.get(0));
        jobsOfSJF.remove(0);

        //定义当前到达的作业序列
        ArrayList<JCB> nowQueue = new ArrayList<>();

        //短作业优先作业调度算法
        while(!jobsOfSJF.isEmpty()) {
            boolean tag = false;	//false表示当前作业执行过程中没有新到达的作业
            for(int i = 0;i < jobsOfSJF.size();i++) {
                //在当前作业执行过程中，新到达的作业加入到当前到达序列中，并删除传参序列中的该元素
                if(jobsOfSJF.get(i).getArriveTime() <= lastJobs.get(lastJobs.size() - 1).getCompleteTime()) {
                    nowQueue.add(jobsOfSJF.get(i));
                    jobsOfSJF.remove(i);
                    i--;		//删除元素后下标左移
                    tag = true;	//表示当前作业执行过程中有新到达的作业
                } else {
                    break;		//传参序列是按照到达时间排序的，没有能加入的新元素就可以立即结束查找
                }
            }
            //没有新的作业到达，并且当前到达作业序列中也没有作业，就把传参序列中的第一个（最快到达）作业加入当前到达序列
            //否则，当前到达作业序列中还有作业，就按照作业需要的服务时间（短作业优先原则）排序
            if(tag == false && nowQueue.isEmpty()) {
                nowQueue.add(jobsOfSJF.get(0));
                jobsOfSJF.remove(0);
            } else {
                nowQueue.sort(new Comparator<JCB>() {
                    @Override
                    public int compare(JCB o1, JCB o2) {
                        if(o1.getServeTime() < o2.getServeTime())
                            return -1;
                        else if(o1.getServeTime() > o2.getServeTime())
                            return 1;
                        else return 0;
                    }
                });
            }
            //计算下一个执行作业（当前已到达作业序列优先级最高（即第一个））的各个时间
            //下一个作业的到达时间小于上一个作业的完成时间（即该作业已到达），其开始时间为上一个作业的完成时间
            //否则，该作业未在上一个作业结束前到达，即是新到达作业，处理机早已空闲，其开始时间为其到达时间
            if(lastJobs.get(lastJobs.size() - 1).getCompleteTime() >= nowQueue.get(0).getArriveTime()) {
                nowQueue.get(0).setStartTime(lastJobs.get(lastJobs.size() - 1).getCompleteTime());
            } else {
                nowQueue.get(0).setStartTime(nowQueue.get(0).getArriveTime());
            }
            //等待时间为其开始时间减去到达时间
            nowQueue.get(0).setWaitTime(nowQueue.get(0).getStartTime() - nowQueue.get(0).getArriveTime());
            //完成时间为其开始时间加上服务时间
            nowQueue.get(0).setCompleteTime(nowQueue.get(0).getStartTime() + nowQueue.get(0).getServeTime());
            //周转时间为其完成时间减去到达时间
            nowQueue.get(0).setCyclingTime(nowQueue.get(0).getCompleteTime() - nowQueue.get(0).getArriveTime());
            //加入该作业到最终结果序列（即其为下一个执行作业）
            lastJobs.add(nowQueue.get(0));
            //在当前已到达作业序列中删除该作业（由于需要服务时间最短，所以其在首位）
            nowQueue.remove(0);
            if(jobsOfSJF.size() == 0)
                break;
        }
        //把剩下的已到达的作业按照其优先级（短作业优先原则）加入到最终结果序列（即按其优先级执行）
        for(int i = 0;i < nowQueue.size();i++) {
            if(lastJobs.get(lastJobs.size() - 1).getCompleteTime() >= nowQueue.get(i).getArriveTime()) {
                nowQueue.get(i).setStartTime(lastJobs.get(lastJobs.size() - 1).getCompleteTime());
            } else {
                nowQueue.get(i).setStartTime(nowQueue.get(i).getArriveTime());
            }
            nowQueue.get(i).setWaitTime(nowQueue.get(i).getStartTime() - nowQueue.get(i).getArriveTime());
            nowQueue.get(i).setCompleteTime(nowQueue.get(i).getStartTime() + nowQueue.get(i).getServeTime());
            nowQueue.get(i).setCyclingTime(nowQueue.get(i).getCompleteTime() - nowQueue.get(i).getArriveTime());
            lastJobs.add(nowQueue.get(i));
        }
        return lastJobs;
    }
}
