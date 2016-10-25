package com.capping.xinran.cappingnews.utils;

/**
 * Created by qixinh on 16/10/12.
 */
public class StracktraceUitils {

    public static String methodCallStackTrace(){
        StackTraceElement[] traces= Thread.currentThread().getStackTrace();
        StringBuilder sb=new StringBuilder();
        for(StackTraceElement trace: traces){
            sb.append(trace.getFileName()).append("\n")
                    .append(trace.getClassName()).append("\n")
                    .append(trace.getMethodName()).append("\n")
                    .append(trace.getLineNumber()).append("\n");
        }
        return sb.toString();
    }
}
