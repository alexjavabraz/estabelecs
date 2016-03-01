package servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5926298133881470576L;
	private Logger logger = LoggerFactory.getLogger(BaseServlet.class);
	private WebApplicationContext appCtx;
	private boolean autowired = false;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (logger.isDebugEnabled()) {
			logger.debug("Initialize BaseServlet");
		}
		appCtx = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());

		if (!autowired) {
			appCtx.getAutowireCapableBeanFactory().autowireBean(this);
		}

	}

}
