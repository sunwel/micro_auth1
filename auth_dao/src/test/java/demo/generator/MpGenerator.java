package demo.generator;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.MyAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.config.rules.QuerySQL;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MpGenerator {

	private static final ResourceBundle rb = ResourceBundle.getBundle("mp_generator");
	// 目标项目相关配置
	// 目标项目根路径
	private static final String PROJECT_DIR = rb.getString("mpg.project_dir");
	// 目标项目作者
	private static final String PROJECT_AUTHOR = "idea";
	// 目标项目的基础包名
	private static final String PROJECT_PACKAGE = rb.getString("mpg.project_package");

	// 数据库相关配置
	// 数据库类型
	private static final DbType DB_TYPE = DbType.MYSQL;
	// 数据库连接驱动类
	private static final String DB_DRIVER_NAME = rb.getString("mpg.db_driver_name");
	// 数据库连接用户名
	private static final String DB_USERNAME = rb.getString("mpg.db_username");
	// 数据库连接密码
	private static final String DB_PASSWORD = rb.getString("mpg.db_password");
	// 数据库连接URL
	private static final String DB_URL = rb.getString("mpg.db_url");
	// 需要代码生成的表
	private static final String DB_TBLS_STR = rb.getString("mpg.db_tables");

	/**
	 * 获取代码生成的目标路径
	 * @return 代码生成的目标路径
	 */
	private static String getOutputDir() {
		String outputDir = PROJECT_DIR + "/src/main/java";
		return outputDir;
	}

	private static QuerySQL getQuerySQL(DbType dbType) {
		for (QuerySQL qs : QuerySQL.values()) {
			if (qs.getDbType().equals(dbType.getValue())) {
				return qs;
			}
		}
		return QuerySQL.MYSQL;
	}

	private static boolean isTblMatch(String tableName, List<String> tbMatchlList) {
		for (String tbMatch : tbMatchlList) {
			if (tableName.matches(tbMatch)) {
				return true;
			}
		}
		return false;
	}

	private static String[] getDbTblOrView(DataSourceConfig config, String tblOrViewStr, boolean isTbl) {
		if (org.apache.commons.lang.StringUtils.isBlank(tblOrViewStr)) {
			return null;
		}
		String[] temp = tblOrViewStr.split(",");
		// 获取用于表匹配的数据集合
		List<String> tbMatchlList = Arrays.stream(temp).map(String::trim).collect(Collectors.toList());
		// 初始化最终的表数据集合
		List<String> finalTbls = new ArrayList<>();
		Connection connection = config.getConn();
		QuerySQL querySQL = getQuerySQL(config.getDbType());
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(querySQL.getTableCommentsSql());
			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				String tableName = results.getString(querySQL.getTableName());
				String comment = results.getString(querySQL.getFieldComment());
				if (StringUtils.isNotEmpty(tableName)) {
					if(isTbl) {
//						 忽略掉mysql的视图
						if (!"VIEW".equalsIgnoreCase(comment) && isTblMatch(tableName, tbMatchlList)) {
							finalTbls.add(tableName);
						}
					}else{
						if(isTblMatch(tableName, tbMatchlList)) {
							finalTbls.add(tableName);
						}
					}
				} else {
					System.err.println("当前数据库为空！！！");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			 释放资源
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("符合要求的表名：" + finalTbls);
		String[] finalTblArr = new String[finalTbls.size()];
		return finalTbls.toArray(finalTblArr);
	}

	private static String[] getDbTbls(DataSourceConfig config) {
		return getDbTblOrView(config, DB_TBLS_STR, true);
	}

//	private static String[] getDbViews(DataSourceConfig config) {
//		return getDbTblOrView(config, DB_VIEWS_STR, false);
//	}

	/**
	 * <p>
	 * MySQL 生成演示<br/>
	 * 运行脚本：mvn exec:java -Dexec.mainClass="test2.mybatis.MpGenerator"
	 * </p>
	 */
	public static void main(String[] args) {
		MyAutoGenerator mpg = new MyAutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(getOutputDir());
		gc.setFileOverride(true);
		gc.setActiveRecord(true);
		gc.setEnableCache(false); //XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor(PROJECT_AUTHOR);

		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		// gc.setMapperName("%sDao");
		// gc.setXmlName("%sDao");
		// gc.setServiceName("MP%sService");
		// gc.setServiceImplName("%sServiceDiy");
		// gc.setControllerName("%sAction");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DB_TYPE);
		dsc.setDriverName(DB_DRIVER_NAME);
		dsc.setUsername(DB_USERNAME);
		dsc.setPassword(DB_PASSWORD);
		dsc.setUrl(DB_URL);
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// String[] tablePrefix = { "bmd_" };
		// strategy.setTablePrefix(tablePrefix);// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(getDbTbls(dsc)); // 可以匹配表名，不一定是具体的表名
		//strategy.setViewInclude(getDbViews(dsc)); // 需要代码生成的视图，有可能返回null
		// strategy.setInclude(new String[] { "user" }); // 需要生成的表
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 字段名生成策略
		// strategy.setFieldNaming(NamingStrategy.underline_to_camel);
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
		strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		strategy.setEntityBuilderModel(true);
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(PROJECT_PACKAGE);
		// pc.setModuleName("test");
		mpg.setPackageInfo(pc);

		// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
				this.setMap(map);
			}
		};
		// 自定义 xxList.jsp 生成
		// List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		// focList.add(new FileOutConfig("/template/list.jsp.vm") {
		// @Override
		// public String outputFile(TableInfo tableInfo) {
		// // 自定义输入文件名称
		// return "D://my_" + tableInfo.getEntityName() + ".jsp";
		// }
		// });
		// cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
		// 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
		// TemplateConfig tc = new TemplateConfig();
		// tc.setController("...");
		// tc.setEntity("...");
		// tc.setMapper("...");
		// tc.setXml("...");
		// tc.setService("...");
		// tc.setServiceImpl("...");
		// mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();

		// 打印注入设置
		System.err.println(mpg.getCfg().getMap().get("abc"));
	}

}
