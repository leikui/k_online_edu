package com.oyyo.excel;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class DemoDataListener extends AnalysisEventListener<Test> {


    @Override
    public void invoke(Test test, AnalysisContext analysisContext) {
        System.out.println("xxx" + test);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}