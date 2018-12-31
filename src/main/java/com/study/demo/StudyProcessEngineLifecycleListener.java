package com.study.demo;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineLifecycleListener;

public class StudyProcessEngineLifecycleListener implements ProcessEngineLifecycleListener {

    public void onProcessEngineBuilt(ProcessEngine processEngine) {
        System.out.println("流程引擎开始: " + processEngine);

    }

    public void onProcessEngineClosed(ProcessEngine processEngine) {
        System.out.println("流程引擎关闭: " + processEngine);

    }
}
