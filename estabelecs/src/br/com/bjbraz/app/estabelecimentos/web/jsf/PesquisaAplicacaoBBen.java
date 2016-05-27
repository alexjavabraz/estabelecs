package br.com.bjbraz.app.estabelecimentos.web.jsf;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;
import br.com.bjbraz.app.estabelecimentos.service.CadastroEstabelecimentoService;

@Component
@ViewScoped
@ManagedBean
public class PesquisaAplicacaoBBen {

	private static final String URL = "http://www.melhorarminhacidade.com.br/hipservices/consulta/estabelecimento/listarTodosSubGrupoJson?key=1234&idSubGrupo=11";
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private List<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();

	@Autowired
	CadastroEstabelecimentoService service;

	private Estabelecimento estabelecimento;
	
	public void buscarPorGrupo(ActionEvent event) {
		String idGrupo = (String) event.getComponent().getAttributes().get("idGrupo");
		estabelecimentos = service.listarPorGrupo(Integer.parseInt(idGrupo));
	}

	public void buscarPorSubGrupo(ActionEvent event) {
		String idSubGrupo = (String) event.getComponent().getAttributes().get("idSubGrupo");
		estabelecimentos = service.listarPorSubGrupo(Integer.parseInt(idSubGrupo));
	}

	public void salvar() {
		try {
			service.salvarEstabelecimento(getEstabelecimento());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estabelecimento SALVO com sucesso",
					"Estabelecimento SALVO com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			logger.error("Erro salvando o estabelecimento", e);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Erro ao salvar estabelecimento");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public String pesquisar() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = null;
		HttpGet get = new HttpGet(URL);
		HttpResponse resposta;

		try {
			resposta = httpclient.execute(get); // Getting the web (HTML)
												// response from the URL
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			responseBody = httpclient.execute(get, responseHandler);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(false);

			DocumentBuilder builder = factory.newDocumentBuilder();

			InputSource is = new InputSource(new StringReader(responseBody));
			Document documento = builder.parse(is);

			Element elemento = documento.getDocumentElement();

			Node node = elemento.getFirstChild();

			elemento.getAttribute("estabelecimentoes");

			elemento.getNextSibling();

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, " Retorno da pesquisa  ",
					responseBody);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

		} catch (Exception e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, " Erro ao efetuar a busca ",
					e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}

		return null;
	}

	public List<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

}
