package br.com.bjbraz.app.estabelecimentos.web.jsf;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.bjbraz.app.estabelecimentos.config.jsf.BasicBBean;

@Component
@SessionScoped
@ManagedBean
public class MeuManagedBBean extends BasicBBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6543348233516763545L;

	@PostConstruct
	public void init() {
		System.out.println(" Bean executado! ");
	}

	public String getMessage() {
		return "Hello World JSF!";
	}

}
