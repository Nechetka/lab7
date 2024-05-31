package com.nechet.common.util.model.checkers;

public class ChapterMarineCountChecker implements Checked<Integer>{
    @Override
    public boolean check(Integer obj){
        return (obj != null && obj > 0 && obj<=1000);
    }
}
