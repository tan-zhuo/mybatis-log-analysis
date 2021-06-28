package com.yanuoer.action;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.yanuoer.enums.DataTypeEnums;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis日志解析
 *
 * @author 谭卓
 */
public class MyBatisLogAnalysisAction extends AnAction {

	private static final String TITLE = "the complete sql statement";

	/**
	 * 触发事件执行
	 * <p>
	 * 获取选中的文本框信息 只获取consoleView组件下鼠标选中的文本
	 * 格式必须为两行一行sql一行参数
	 * 解析日志 填充sql语句的参数 输出结果
	 *
	 * @param event 触发事件
	 * @author 谭卓
	 */
	@Override
	public void actionPerformed(@NotNull AnActionEvent event) {
		//获取事件发生源 并拿到历史日志输出文本编辑框
		final DataContext dataContext = event.getDataContext();
		final ConsoleViewImpl consoleView = (ConsoleViewImpl) dataContext.getData("consoleView");
		if (consoleView == null) return;
		final Editor editor = consoleView.getEditor();
		if (editor == null) return;
		//获取选中文本
		final String mybatisLogAll = editor.getSelectionModel().getSelectedText();
		if (mybatisLogAll == null || "".equals(mybatisLogAll) || !mybatisLogAll.contains("\n")) return;
		String[] mybatisLogs = mybatisLogAll.split("\n");
		//不满足日志规则 选中行数不符合指定行
		if (mybatisLogs.length != 2) return;
		final String sqlLog = mybatisLogs[0];
		final String parametersLog = mybatisLogs[1];
		if ("".equals(sqlLog) || "".equals(parametersLog)) return;
		final String preparingStr = "Preparing: ";
		final String parametersStr = "Parameters: ";
		if (!sqlLog.contains(preparingStr) || !parametersLog.contains(parametersStr)) return;
		//解析sql
		String preparatorySql = sqlLog.substring(sqlLog.indexOf(preparingStr) + preparingStr.length());
		String preparatoryParameters = parametersLog.substring(parametersLog.indexOf(parametersStr) + parametersStr.length());
		if (preparatoryParameters.length() != 0) {
			String[] parameters = preparatoryParameters.split(", ");
			List<Object> parameterObjectList = getObjectList(parameters);
			List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(preparatorySql, JdbcConstants.MYSQL);
			preparatorySql = SQLUtils.toSQLString(sqlStatementList, JdbcConstants.MYSQL, parameterObjectList);
		}
		preparatorySql = SQLUtils.format(preparatorySql, JdbcConstants.MYSQL, SQLUtils.DEFAULT_FORMAT_OPTION);
		Messages.showMessageDialog(preparatorySql, TITLE, Messages.getInformationIcon());
	}

	private List<Object> getObjectList(String[] parameters) {
		List<Object> parameterObjectList = new ArrayList<>(parameters.length);
		for (String parameter : parameters) {
			parameterObjectList.add(DataTypeEnums.getDataType(parameter));
		}
		return parameterObjectList;
	}

}
