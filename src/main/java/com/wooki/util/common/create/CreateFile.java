package com.wooki.util.common.create;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class CreateFile {

    private static String auth = "whn";

    public static String prpojectName = "Wooki";
    //项目路径
    private static String projectPath = getRootPath().substring(0,getRootPath().length()-1);
//    private static String projectPath = "/Users/user/Desktop";
    // bean ,service 等生成路径
    private static String javaFilePath = projectPath+"/"+prpojectName+"/src/main/java/";
    // xml 文件生成路径
    private static String mapperPath = projectPath+"/"+prpojectName+"/src/main/resources/mapper/";
    // xml 模板
    private static String xmlTemplate = "/template/mapper.xml.vm";
    // sql
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://127.0.0.1:3306/wookitech?characterEncoding=utf8";
    private static String username = "root";
    private static String password = "Ugrtbx*6$#";

    // 上层路径
    private static String parentPath = "com.wooki.system.tbl";
    // controller
    private static String controller = "controller";
    // 模块名
    private static String moduleName = "metting";
    // 需要生成的表getLastDay
//    private static String[] tables = {"task_user_daily","task_user_temp","task_user_part_time","task_user_job"};
    private static String[] tables = {"tbl_metting_reserve"};

    public static void main(String[] args) {

        create();
        //System.out.println(DateUtil.getLastDayStr());

        Integer a = 5;
        //System.out.println(a.toString());
//        for(int i =0 ;i<100;i++){
//            //System.out.println(ProductCode.product());
//        }
//        Calendar calendarNew = Calendar.getInstance();
//        calendarNew.add(Calendar.MONTH,1);
//        //System.out.println(calendarNew.getTime());
//        calendarNew.add(Calendar.MONTH,-1);
//        //System.out.println(calendarNew.getTime());
//        //System.out.println(calendarNew.get(Calendar.MONTH));
//          create();

//        Date a = DateUtil.getCurrentDayZero();
//        //System.out.println(a);
//        String str = "2017-11";
//        Date date = DateUtil.getDateByString(str);
//        //System.out.println(date);
//        //System.out.println(next);
//        //System.out.println(a==0);
//        RecordMonth recordMonth = new RecordMonth(DateUtil.getLastMonthDay1TimeZero(), DateUtil.getCurrentMonthDay1TimeZero());
//        //System.out.println(recordMonth);

//        try {
//            String str = "http://www.baidu.com/search?s=百度一下你就知道";
//            String encode = URLEncoder.encode(str,"UTF-8");
//            //System.out.println(encode);
//            String decode = URLDecoder.decode(encode,"UTF-8");
//            //System.out.println(decode);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        //System.out.println(DateUtil.getLastMonthDay1TimeZero());
//        //System.out.println(DateUtil.getLastYearDay1TimeZero());
//        //System.out.println(DateUtil.getCurrentYearDay1TimeZero());
//        //System.out.println(DateUtil.getCurrentDayZero());
//        //System.out.println(DateUtil.getNextDayZero());

//        Date currentMonthDay1  = DateUtil.getCurrentMonthDay1();    // 当前月一号
//        //System.out.println(currentMonthDay1);
//        Date nextMonthDay1 = DateUtil.getNextMonthDay1TimeZero();           // 下个月一号

//        int a = 0;
//        //System.out.println(a=geta());

//        Team team = new Team();
//        //System.out.println((team=null)==null);

//        String a = "1";
//        show(a);
//        //System.out.println("a = "+a);

//        Team team = new Team();
//        //System.out.println("1 :"+team.hashCode());
//        change(team);
//        //System.out.println("3 :"+team.hashCode());

//        Long a = 432432L;
//        Long b = 432432L;
//        //System.out.println(b.toString());
    }

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void create() {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(javaFilePath);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(auth);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
         gc.setServiceName("%sService");
         gc.setServiceImplName("%sServiceImpl");

        // gc.setControllerName("%sAction");

//        mpg.setConfig()
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                //System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName(driver);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setUrl(url);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[] { "tlog_", "tsys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tables); // 需要生成的表
        // strategy.setExclude(new String[]{"ProductController"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPath);
        pc.setModuleName(moduleName);
        pc.setController(controller);
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
//        focList.add(new FileOutConfig("/template/list.jsp.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                 自定义输入文件名称
//                return "D://my_" + tableInfo.getEntityName() + ".jsp";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 调整 xml 生成目录演示
        focList.add(new FileOutConfig(xmlTemplate) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return mapperPath + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);


        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
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