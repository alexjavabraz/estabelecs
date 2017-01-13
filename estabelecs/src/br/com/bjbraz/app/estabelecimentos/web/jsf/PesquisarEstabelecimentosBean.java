package br.com.bjbraz.app.estabelecimentos.web.jsf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.bjbraz.app.estabelecimentos.config.jsf.BasicBBean;
import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;
import br.com.bjbraz.app.estabelecimentos.service.CadastroEstabelecimentoService;

@ManagedBean
@Component
@SessionScoped
public class PesquisarEstabelecimentosBean extends BasicBBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6286193401944423527L;
	
	private List<Estabelecimento> estabelecimentos;
	private Estabelecimento estabelecimento;
	private Estabelecimento filtro;
	
	@Autowired
	private CadastroEstabelecimentoService service;	

	@PostConstruct
	public void init(){
		filtro = new Estabelecimento();
		estabelecimentos = new ArrayList<Estabelecimento>();
	}
	
	public void pesquisar(){
		estabelecimentos = service.listarTodosEstabelecimentos(filtro);
	}
	
	public void salvar(){
		try{
			
			if(getEstabelecimento().getFile() != null)
				getEstabelecimento().setImagem1(IOUtils.toByteArray(getEstabelecimento().getFile().getInputstream()));
			
			if(getEstabelecimento().getFile2() != null)
				getEstabelecimento().setImagem2(IOUtils.toByteArray(getEstabelecimento().getFile2().getInputstream()));
			
			if(getEstabelecimento().getFile3() != null)
				getEstabelecimento().setImagem3(IOUtils.toByteArray(getEstabelecimento().getFile3().getInputstream()));
			
			if(getEstabelecimento().getFile4() != null)
				getEstabelecimento().setImagem4(IOUtils.toByteArray(getEstabelecimento().getFile4().getInputstream()));
			
			
			service.salvarEstabelecimento(getEstabelecimento());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estabelecimento SALVO com sucesso", "Estabelecimento SALVO com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch(Exception e){
			logger.error("Erro salvando o estabelecimento", e);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar estabelecimento");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void preparaImagem() {
		try {
			BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImg.createGraphics();
			g2.drawString("This is a text", 0, 10);
			
			byte[] bytes = IOUtils.toByteArray(estabelecimento.getFile().getInputstream());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "png", os);
			estabelecimento.setImagem(new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image/png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void preparaImagem2() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void preparaImagem3() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void preparaImagem4() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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


	public Estabelecimento getFiltro() {
		return filtro;
	}


	public void setFiltro(Estabelecimento filtro) {
		this.filtro = filtro;
	}
	
	public StreamedContent getImagem() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("image_id");
        if (id != null) {
            Integer imagemId = Integer.parseInt(id);
            for (Estabelecimento c : getEstabelecimentos()) {
                if (c.getId().intValue() == imagemId.intValue()) {
                    return c.getImagem();
                }
            }
        }
        
        return new DefaultStreamedContent();
    }
	
	public StreamedContent getImagem2() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("image_id");
        if (id != null) {
            Integer imagemId = Integer.parseInt(id);
            for (Estabelecimento c : getEstabelecimentos()) {
                if (c.getId().intValue() == imagemId.intValue()) {
                    return c.getStram2();
                }
            }
        }
        
        return new DefaultStreamedContent();
    }	
	
	public StreamedContent getImagem3() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("image_id");
        if (id != null) {
            Integer imagemId = Integer.parseInt(id);
            for (Estabelecimento c : getEstabelecimentos()) {
                if (c.getId().intValue() == imagemId.intValue()) {
                    return c.getStram3();
                }
            }
        }
        
        return new DefaultStreamedContent();
    }
	
	public StreamedContent getImagem4() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("image_id");
        if (id != null) {
            Integer imagemId = Integer.parseInt(id);
            for (Estabelecimento c : getEstabelecimentos()) {
                if (c.getId().intValue() == imagemId.intValue()) {
                    return c.getStram4();
                }
            }
        }
        
        return new DefaultStreamedContent();
    }	
	

}
