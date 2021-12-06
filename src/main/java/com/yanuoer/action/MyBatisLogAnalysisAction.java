package com.yanuoer.action;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.InsertPathAction;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.execution.ParametersListUtil;
import com.intellij.util.ui.UIUtil;
import com.yanuoer.enums.DataTypeEnums;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis日志解析
 *
 * @author tanzhuo
 * @version 1.0.3
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
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        //获取事件发生源 并拿到历史日志输出文本编辑框
        DataContext dataContext = event.getDataContext();
        ConsoleViewImpl consoleView = (ConsoleViewImpl) dataContext.getData("consoleView");
        if (consoleView == null) return;
        Editor editor = consoleView.getEditor();
        if (editor == null) return;
        //获取选中文本
        String mybatisLogAll = editor.getSelectionModel().getSelectedText();
        if (mybatisLogAll == null || "".equals(mybatisLogAll) || !mybatisLogAll.contains("\n")) return;
        String[] mybatisLogs = mybatisLogAll.split("\n");
        //不满足日志规则 选中行数不符合指定行
        if (mybatisLogs.length != 2) return;
        String sqlLog = mybatisLogs[0];
        String parametersLog = mybatisLogs[1];
        if ("".equals(sqlLog) || "".equals(parametersLog)) return;
        String preparingStr = "Preparing: ";
        String parametersStr = "Parameters: ";
        if (!sqlLog.contains(preparingStr) || !parametersLog.contains(parametersStr)) return;
        //解析sql
        String completeSql = sqlLog.substring(sqlLog.indexOf(preparingStr) + preparingStr.length());
        String preparatoryParameters = parametersLog.substring(parametersLog.indexOf(parametersStr) + parametersStr.length());
        if (preparatoryParameters.length() != 0) {
            String[] parameters = preparatoryParameters.split(", ");
            List<Object> parameterObjectList = getObjectList(parameters);
            List<SQLStatement> sqlStatementList = SQLUtils.parseStatements(completeSql, JdbcConstants.MYSQL);
            completeSql = SQLUtils.toSQLString(sqlStatementList, JdbcConstants.MYSQL, parameterObjectList);
        }
        openMessages(SQLUtils.format(completeSql, JdbcConstants.MYSQL, SQLUtils.DEFAULT_FORMAT_OPTION));
    }


    public void openMessages(String sql) {
        JTextArea textArea = new JTextArea(10, 50);
        UIUtil.addUndoRedoActions(textArea);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        Font font = new Font("JetBrains Mono", Font.BOLD, 14);
        textArea.setFont(font);
        textArea.setText(sql.replaceAll("\n\t\t", "\n").replaceAll("\n\t", "\n"));
        DialogBuilder builder = new DialogBuilder();
        builder.setDimensionServiceKey("");
        builder.setCenterPanel(ScrollPaneFactory.createScrollPane(textArea));
        builder.setPreferredFocusComponent(textArea);
        builder.setTitle(TITLE);
        builder.addOkAction();
        builder.addCancelAction();
        builder.show();
    }

    private List<Object> getObjectList(String[] parameters) {
        List<Object> parameterObjectList = new ArrayList<>(parameters.length);
        for (String parameter : parameters) {
            parameterObjectList.add(DataTypeEnums.getDataType(parameter));
        }
        return parameterObjectList;
    }

}
