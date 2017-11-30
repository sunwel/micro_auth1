package com.enlink.authcenter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

/**
 * 转换navicat导出的mysql的建表语句为h2的语法<br>
 * 主要的要注意的点是:<br>
 * 1.设置H2为mysql模式, 可以通过 SET MODE MYSQL;语句来实现<br>
 * 2.'`'全部要去掉<br>
 * 3.字段的字符集设置'COLLATE utf8_bin'不支持, 需要删除, 如这样的'`operator` varchar(10) COLLATE utf8_bin NOT NULL'<br>
 * 4.注释按道理也没问题的, 但是没有用, 所以删除了.<br>
 * 5.'ENGINE=InnoDB'设置不支持, 删掉<br>
 * 6.'DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP'不支持, 修改为H2类似的'AS CURRENT_TIMESTAMP'br>
 * 7.H2的索引名必须要全局唯一, 所以需要替换所有的索引名为全局唯一br>
 * @author tudesheng
 * @since 2016年6月20日 下午8:37:52
 */
public class TransformMysqlToH2 {

	public static void main(String[] args) throws Exception {
		String microAuthDir = System.getProperty("user.dir");
		String mysqlFilePath = microAuthDir + "/auth_center/src/scripts/micro_auth_createtable.sql";
		String outFilePath = microAuthDir + "/auth_center/src/test/resources/h2/schema-h2.sql";
		File file = new File(mysqlFilePath);
		File outFile = new File(outFilePath);
		String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		content = "SET MODE MYSQL;\n\n" + content;

		content = content.replaceAll("`", "");
		content = content.replaceAll("COLLATE.*(?=D)", "");
		content = content.replaceAll("COMMENT.*'(?=,)", "");
		content = content.replaceAll("\\).*ENGINE.*(?=;)", ")");
		content = content.replaceAll("DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", " AS CURRENT_TIMESTAMP");
		content = content.replaceAll("alter table.*(?=;)", "");

		content = uniqueKey(content);
		FileUtils.writeStringToFile(outFile, content, "UTF-8");
		System.out.println(content);
	}

	/**
	 * h2的索引名必须全局唯一
	 * @param content sql建表脚本
	 * @return 替换索引名为全局唯一
	 */
	private static String uniqueKey(String content) {
		int inc = 0;
		Pattern pattern = Pattern.compile("(?<=KEY )(.*?)(?= \\()");
		Matcher matcher = pattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group() + inc++);
		}
		matcher.appendTail(sb);
		content = sb.toString();
		return content;
	}

}