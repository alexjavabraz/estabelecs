package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.AbstractDAO;
import br.com.bjbraz.app.estabelecimentos.dao.EstabelecimentoDao;
import br.com.bjbraz.app.estabelecimentos.entity.Estabelecimento;

@Repository
public class EstabelecimentoDaoImpl extends AbstractDAO<Estabelecimento, Integer> implements EstabelecimentoDao {
	
	private static final Logger log = LoggerFactory.getLogger(EstabelecimentoDaoImpl.class);

    @Override
    public Estabelecimento salvar(Estabelecimento e) {
        return super.persist(e);
    }

    @Override
    public List<Estabelecimento> listarTodosEstabelecimentos() {
        return super.findAll(TAMANHO_MAXIMO_PESQUISA);
    }

	@Override
	public Estabelecimento listarEstabelecientoPorNome(String nomeFantasia) {
		try {
            List<Estabelecimento> itens = super.findByNamedQuery(Estabelecimento.BUSCAR_POR_DESCRICAO,
                createParams(new String[] { "descricao" }, new Serializable[] { nomeFantasia }));

            if(itens.size() > 0){
            	return itens.get(0);
            }
            
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
	}

}
