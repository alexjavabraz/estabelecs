package br.com.bjbraz.app.estabelecimentos.web.jsf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.bjbraz.app.estabelecimentos.config.jsf.BasicBBean;
import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoCategoria;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoGrupo;
import br.com.bjbraz.app.estabelecimentos.entity.EstabelecimentoSubGrupo;
import br.com.bjbraz.app.estabelecimentos.entity.Estado;
import br.com.bjbraz.app.estabelecimentos.service.CadastroEstabelecimentoService;
import br.com.bjbraz.app.estabelecimentos.service.EstadoService;
import br.com.bjbraz.app.estabelecimentos.validator.Email;

@Component
@ViewScoped
@ManagedBean
public class MeuManagedBBean extends BasicBBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6543348233516763545L;

	private List<SelectItem> categories;
	private String selection;
	private String option;

	private UploadedFile file;
	private UploadedFile file2;
	private UploadedFile file3;
	private UploadedFile file4;
	private boolean destaque = false;
	private String phone;
	private String celPhone;
	private String fileName;

	@Email(message = "Favor informar um email válido")
	private String email;

	private Estabelecimento estabelecimento = new Estabelecimento();

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CadastroEstabelecimentoService service;

	private boolean temImagem = false;
	private boolean temImagem2 = false;
	private boolean temImagem3 = false;
	private boolean temImagem4 = false;

	private StreamedContent imagem;
	private StreamedContent imagem2;
	private StreamedContent imagem3;
	private StreamedContent imagem4;

	public void inicializar() {
		estabelecimento = new Estabelecimento();
		temImagem = false;
		destaque = false;
		selection = "";
		option = "";
		file = null;
		fileName = null;
		email = null;
		imagem = null;
	}

	@PostConstruct
	public void init() {
		System.out.println(" Bean executado! ");
		montaSelectGrupos();
		montaSelectCidades();
	}

	private void montaSelectCidades() {
		List<Estado> estados = estadoService.mostrarEstados();

	}

	private void montaSelectGrupos() {
		categories = new ArrayList<SelectItem>();

		List<EstabelecimentoCategoria> categorias = service.listarTodasCategorias();

		for (int i = 0; i < categorias.size(); i++) {
			EstabelecimentoCategoria categoria = categorias.get(i);

			SelectItemGroup group1 = new SelectItemGroup(categoria.getNomeCat());

			List<EstabelecimentoGrupo> grupos = service.listarGruposPorCategoria(categoria.getIdCat());

			/**
			 * 
			 */
			group1.setSelectItems(new SelectItem[grupos.size()]);

			for (int x = 0; x < grupos.size(); x++) {

				EstabelecimentoGrupo grupo = grupos.get(x);

				SelectItemGroup group11 = new SelectItemGroup(grupo.getNomeGrupo());

				List<EstabelecimentoSubGrupo> subGrupos = service.listarSubGrupoPorGrupo(grupo.getIdGrupo());

				group11.setSelectItems(new SelectItem[subGrupos.size()]);

				for (int y = 0; y < subGrupos.size(); y++) {

					EstabelecimentoSubGrupo subGrupo = subGrupos.get(y);

					SelectItem option111 = new SelectItem(subGrupo.getNomeSubGrupo());

					group11.getSelectItems()[y] = option111;
				}

				group1.getSelectItems()[x] = group11;

			}

			categories.add(group1);
		}

	}

	public String getMessage() {
		return "Hello World JSF!";
	}

	public List<SelectItem> getCategories() {
		return categories;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	public String next() {
		if (file != null && (null != selection) && ("".equals(selection)) ) {
			
			fileName = file.getFileName();
			FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);

			preparaImagem();
			preparaImagem2();
			preparaImagem3();
			preparaImagem4();
			
			return "index2";
		}else{
			FacesMessage message = new FacesMessage("Favor informar Categoria => Grupo => SubGrupo");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

		return null;
	}
	
	public String voltar() {
		return "index";
	}
	
	public String limpar() {
		
		inicializar();
		
		return "index";
	}

	public String upload() {
		if (file != null && (null != selection) && (!"".equals(selection))) {
			fileName = file.getFileName();
			FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			preparaImagem();
			preparaImagem2();
			preparaImagem3();
			preparaImagem4();
			
			
		}else{
			FacesMessage message = new FacesMessage("Favor informar Categoria => Grupo => SubGrupo");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		
		return "index2";
		
	}

	public String gravar() {
		
		EstabelecimentoSubGrupo esg = service.listarSubGrupoPorNome(selection);
		getEstabelecimento().setIdSubGrupo(esg.getIdSg());
		getEstabelecimento().setEstabelecimentoSugGrupo(esg);
		
		Estabelecimento estabelecimentoComMesmoNome = service.listarEstabelecimentoPorNome(getEstabelecimento().getNomeFantasia());
		
		if(estabelecimentoComMesmoNome != null){
			FacesMessage message = new FacesMessage("J� existe um Estabelecimento com este nome");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		
		
		if (esg != null ) {
			try{
				getEstabelecimento().setImagem1(IOUtils.toByteArray(file.getInputstream()));
				
				if(file2 != null)
					getEstabelecimento().setImagem2(IOUtils.toByteArray(file2.getInputstream()));
				
				if(file3 != null)
					getEstabelecimento().setImagem3(IOUtils.toByteArray(file3.getInputstream()));
				
				if(file4 != null)
					getEstabelecimento().setImagem4(IOUtils.toByteArray(file4.getInputstream()));
				
				getEstabelecimento().setDhInclusao(new Date());
				service.salvarEstabelecimento(getEstabelecimento());
				
				FacesMessage message = new FacesMessage(getEstabelecimento().getNomeFantasia() + " salvo com sucesso !");
				FacesContext.getCurrentInstance().addMessage(null, message);
				
				inicializar();
				return "index";
			}catch(Exception e){
				logger.error("Erro ao salvar estabelecimento ", e);
				FacesMessage message = new FacesMessage("Ocorreu um erro ao salvar Estabelecimento, tente novamente");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		
		return null;
	}

	private void preparaImagem() {
		try {
			BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImg.createGraphics();
			g2.drawString("This is a text", 0, 10);
			
			byte[] bytes = IOUtils.toByteArray(file.getInputstream());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "png", os);
			imagem = new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image/png");
			
			temImagem = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void preparaImagem2() {
		try {
			BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImg.createGraphics();
			g2.drawString("This is a text", 0, 10);
			
			byte[] bytes = IOUtils.toByteArray(file2.getInputstream());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "png", os);
			imagem2 = new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image/png");
			
			temImagem2 = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void preparaImagem3() {
		try {
			BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImg.createGraphics();
			g2.drawString("This is a text", 0, 10);
			
			byte[] bytes = IOUtils.toByteArray(file3.getInputstream());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "png", os);
			imagem3 = new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image/png");
			
			temImagem3 = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void preparaImagem4() {
		try {
			BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bufferedImg.createGraphics();
			g2.drawString("This is a text", 0, 10);
			
			byte[] bytes = IOUtils.toByteArray(file4.getInputstream());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImg, "png", os);
			imagem4 = new DefaultStreamedContent(new ByteArrayInputStream(bytes), "image/png");
			
			temImagem4 = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCelPhone() {
		return celPhone;
	}

	public void setCelPhone(String celPhone) {
		this.celPhone = celPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isTemImagem() {
		return temImagem;
	}

	public void setTemImagem(boolean temImagem) {
		this.temImagem = temImagem;
	}

	public StreamedContent getImagem() {
		return imagem;
	}

	public void setImagem(StreamedContent imagem) {
		this.imagem = imagem;
	}

	public UploadedFile getFile2() {
		return file2;
	}

	public void setFile2(UploadedFile file2) {
		this.file2 = file2;
	}

	public UploadedFile getFile3() {
		return file3;
	}

	public void setFile3(UploadedFile file3) {
		this.file3 = file3;
	}

	public UploadedFile getFile4() {
		return file4;
	}

	public void setFile4(UploadedFile file4) {
		this.file4 = file4;
	}

	public StreamedContent getImagem2() {
		return imagem2;
	}

	public void setImagem2(StreamedContent imagem2) {
		this.imagem2 = imagem2;
	}

	public StreamedContent getImagem3() {
		return imagem3;
	}

	public void setImagem3(StreamedContent imagem3) {
		this.imagem3 = imagem3;
	}

	public StreamedContent getImagem4() {
		return imagem4;
	}

	public void setImagem4(StreamedContent imagem4) {
		this.imagem4 = imagem4;
	}

	public boolean isTemImagem2() {
		return temImagem2;
	}

	public void setTemImagem2(boolean temImagem2) {
		this.temImagem2 = temImagem2;
	}

	public boolean isTemImagem3() {
		return temImagem3;
	}

	public void setTemImagem3(boolean temImagem3) {
		this.temImagem3 = temImagem3;
	}

	public boolean isTemImagem4() {
		return temImagem4;
	}

	public void setTemImagem4(boolean temImagem4) {
		this.temImagem4 = temImagem4;
	}

}