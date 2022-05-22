package com.example.demo.algorithm;

import com.example.demo.domain.JCB;

import java.util.ArrayList;
import java.util.Comparator;

public class FCFS {
    private ArrayList<JCB> jobsOfFCFS;

    public FCFS(ArrayList<JCB> jobsOfFCFS) {
        this.jobsOfFCFS = jobsOfFCFS;
    }

    public ArrayList<JCB> FCFSAlgorithm(){
        if(jobsOfFCFS.isEmpty()) return jobsOfFCFS;
        jobsOfFCFS.sort(new Comparator<JCB>() {
            @Override
            public int compare(JCB o1, JCB o2) {
                if(o1.getArriveTime() < o2.getArriveTime()){
                    return -1;
                }
                else if(o1.getArriveTime() > o2.getArriveTime()){
                    return 1;
                }
                else return 0;
            }
        });
        for(int i = 0;i<jobsOfFCFS.size();i++){
            if(i == 0){
                jobsOfFCFS.get(0).setStartTime(jobsOfFCFS.get(0).getArriveTime());
                jobsOfFCFS.get(0).setWaitTime(0);
            }
            else{
                if(jobsOfFCFS.get(i-1).getCompleteTime() < jobsOfFCFS.get(i).getArriveTime()){
                    jobsOfFCFS.get(i).setStartTime(jobsOfFCFS.get(i).getArriveTime());
                }
                else{
                    jobsOfFCFS.get(i).setStartTime(jobsOfFCFS.get(i-1).getCompleteTime());
                }
                jobsOfFCFS.get(i).setWaitTime(jobsOfFCFS.get(i).getStartTime() - jobsOfFCFS.get(i).getArriveTime());
            }
            jobsOfFCFS.get(i).setCompleteTime(jobsOfFCFS.get(i).getStartTime()+ jobsOfFCFS.get(i).getServeTime());
            jobsOfFCFS.get(i).setCyclingTime(jobsOfFCFS.get(i).getCompleteTime() - jobsOfFCFS.get(i).getArriveTime());
        }
        return jobsOfFCFS;
    }
}
