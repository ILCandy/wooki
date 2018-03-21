package com.wooki.util.common.create;

import com.wooki.util.common.date.DateUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 17/10/25
 * Time : 下午12:01
 * desc : 通用控制器生成
 */
public class CommonController {

    public static String moduleName = "taskUserPartTime"; // 名称
    public static String packageName = "task";  // 所属模块
//    public static Object hasCompanyId = true;   // 是否有公司id
    public static Object hasCompanyId = null;   // 是否有公司id

    public static String author = "wuhaonan";
    public static String basePath = "com.aims.system";
    public static String date = DateUtil.getCurrDate();
    public static String ModuleName = moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1);
    public static String parentPath = basePath+"."+packageName;
    public static String controllerPath = parentPath+"."+"controller";
    public static String controllerName = ModuleName+"Controller";
    public static String serviceName = moduleName+"Service";
    public static String ServiceName = ModuleName+"Service";
    public static String Entity = ModuleName;
    public static String entity = moduleName;

    //项目路径
    private static String projectPath = getRootPath().substring(0,getRootPath().length()-1);
    // bean ,service 等生成路径
    private static String productPath = projectPath+"/HWork/src/main/java/"+basePath.replace(".","/")+"/"+packageName+"/"+"controller"+"/";
    // 生成路径
//    public static String productPath = "/users/user/Desktop/";

//    DateUtil.get


    public static void main(String[] args) {
        createFile();
    }


    public static void createFile(){
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();

        Template t = ve.getTemplate("/hellovelocity.vm");

        // 设置内容
        VelocityContext ctx = new VelocityContext();
        // =================== 设置内容
        ctx.put("author",author);
        ctx.put("parentPath",parentPath);
        ctx.put("basePath",basePath);
        ctx.put("date",date);
        ctx.put("entity",entity);
        ctx.put("Entity",Entity);
        ctx.put("serviceName",serviceName);
        ctx.put("ServiceName",ServiceName);
        ctx.put("controllerName",controllerName);
        ctx.put("controllerPath",controllerPath);
        ctx.put("moduleName",moduleName);
        ctx.put("hasCompanyId",hasCompanyId);

        // ===================
        StringWriter sw = new StringWriter();

        t.merge(ctx, sw);

        File file = null ;
        FileOutputStream out = null;
        try {
            file = new File(productPath+controllerName+".java");
            out=new FileOutputStream(file);
            out.write(sw.toString().getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(out!=null)
                try {
                    out.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public static String getRootPath(){
        String rootPath ="";
        try{
            File file = new File(CommonPageParser.class.getResource("/").getFile());
            rootPath = file.getParentFile().getParentFile().getParent()+"\\";
            rootPath = java.net.URLDecoder.decode(rootPath,"utf-8");
            return rootPath;
        }catch(Exception e){
            e.printStackTrace();
        }
        return rootPath;
    }
}