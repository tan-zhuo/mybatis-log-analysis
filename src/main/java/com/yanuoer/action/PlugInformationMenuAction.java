package com.yanuoer.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * mybatis日志解析信息介绍
 *
 * @author 谭卓
 * @version 1.0.2
 * @Date 2020-8-11 16:19:06
 */
public class PlugInformationMenuAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent event) {
		Messages.showMessageDialog("welcome to use mybatis log analysis plugin, this plugin is permanently free.", "welcome", Messages.getInformationIcon());
	}

}
