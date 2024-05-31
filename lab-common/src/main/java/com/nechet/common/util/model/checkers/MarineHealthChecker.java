package com.nechet.common.util.model.checkers;

public class MarineHealthChecker implements Checked<Double>{
    @Override
    public boolean check(Double obj){
        return (obj != null && obj>0);
    }
}
