package com.prime.bigpipe.web.component;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prime.bigpipe.web.component.handler.ComponentHandler;

@Service
public class DefaultComponentHandlerExecutor implements ComponentHandlerExecutor {

	@Autowired
	private ComponentRenderer componentRenderer;

	public String execute(ComponentHandler handler, String componentName, Map<String, Object> params, HttpServletRequest request) {
		ComponentViewModel viewModel = handler.handle(params, request);
		ComponentResponse componentResponse = ComponentResponse.withStringWriter();
		componentRenderer.render(viewModel, request, componentResponse);

		String content = componentResponse.getContent();
		if (StringUtils.isNotEmpty(content)) {
			content = content.replaceFirst("<!DOCTYPE\\s+html\\s*>", "");
		}

		return content;

	}
}
