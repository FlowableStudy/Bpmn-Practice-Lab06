package com.study.demo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:flowable-context.xml")
public class CustomUuidGeneratorTest {

    private ProcessEngine processEngine;
    private TaskService taskService;
    private RuntimeService runtimeService;
    private RepositoryService repositoryService;
    private HistoryService historyService;
    private DynamicBpmnService dynamicBpmnService;
    private FormService formService;
    private IdentityService identityService;
    private ManagementService managementService;
    private ProcessEngineConfiguration processEngineConfiguration;

    @Before
    public void testProcessEngine() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("流程引擎类：" + processEngine);

        taskService = processEngine.getTaskService();
        System.out.println(taskService);

        runtimeService = processEngine.getRuntimeService();
        System.out.println(taskService);

        repositoryService = processEngine.getRepositoryService();
        System.out.println(repositoryService);

        historyService = processEngine.getHistoryService();
        System.out.println(historyService);

        dynamicBpmnService = processEngine.getDynamicBpmnService();
        System.out.println(dynamicBpmnService);

        formService = processEngine.getFormService();
        System.out.println(formService);

        identityService = processEngine.getIdentityService();
        System.out.println(identityService);

        managementService = processEngine.getManagementService();
        System.out.println(managementService);

        String name = processEngine.getName();
        System.out.println("流程引擎的名称： " + name);

        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        System.out.println(processEngineConfiguration);

    }

    /**
     * 关闭流程引擎
     */
    @After
    public void close(){
        processEngine.close();
    }
    /**
     * 流程部署
     */
    @Test
    public void deploymentBuild(){
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("流程部署分类")
                .name("流程部署名称");
        System.out.println("流程部署: " + deploymentBuilder);
    }
    /**
     * classpath方式部署
     * 涉及三张表：ACT_RE_PROCDEF,ACT_RE_DEPLOYMENT,ACT_GE_BYTEARRAY
     */
    @Test
    public void deploymentByClasspath(){
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("classpath方式部署分类")
                .name("classpath方式部署名称")
                .addClasspathResource("classpath_deploy.bpmn");
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("classpath方式部署,流程ID: " + deploy.getId());
    }
    /**
     * 文本方式部署:默认是UTF-8
     * 涉及三张表：ACT_RE_PROCDEF,ACT_RE_DEPLOYMENT,ACT_GE_BYTEARRAY
     */
    @Test
    public void deploymentByText(){

        String text = IoUtil.readFileAsString("classpath_deploy.bpmn");
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("文本方式部署分类")
                .name("文本方式部署名称")
                .key("文本方式部署测试的key")
                .addString("text_deploy.bpmn",text);
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("文本方式部署,流程ID: " + deploy.getId());
    }

    /**
     * 流的方式方式部署
     * 涉及三张表：ACT_RE_PROCDEF,ACT_RE_DEPLOYMENT,ACT_GE_BYTEARRAY
     */
    @Test
    public void addInputStream(){

        InputStream inputStream = CustomUuidGeneratorTest.class.getClassLoader().getResourceAsStream("classpath_deploy.bpmn");
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("流的方式方式部署分类")
                .name("流的方式方式部署名称")
                .key("流的方式方式部署测试的key")
                .addInputStream("inputstream_deploy.bpmn",inputStream);
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("流的方式部署,流程ID: " + deploy.getId());
    }

    /**
     * 压缩包的方式方式部署
     * 涉及三张表：ACT_RE_PROCDEF,ACT_RE_DEPLOYMENT,ACT_GE_BYTEARRAY
     */
    @Test
    public void addZipInputStream(){

        InputStream inputStream = CustomUuidGeneratorTest.class.getClassLoader().getResourceAsStream("1.zip");

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("压缩包的方式部署分类")
                .name("压缩包的方式部署名称")
                .key("测试的key")
                .addZipInputStream(zipInputStream);
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("压缩包的方式部署,流程ID: " + deploy.getId());
    }

    /**
     * 字节的方式方式部署
     * 涉及三张表：ACT_RE_PROCDEF,ACT_RE_DEPLOYMENT,ACT_GE_BYTEARRAY
     */
    @Test
    public void addBytes(){
        String inputStreamName = "测试字节方式部署流程";

        InputStream inputStream = CustomUuidGeneratorTest.class.getClassLoader().getResourceAsStream("classpath_deploy.bpmn");

        byte[] bytes = IoUtil.readInputStream(inputStream,inputStreamName) ;
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("字节的方式分类")
                .name("字节的方式方式部署名称")
                .key("测试的key")
                .addBytes("bytes_deploy.bpmn",bytes);
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("字节的方式部署,流程ID: " + deploy.getId());
    }





}
