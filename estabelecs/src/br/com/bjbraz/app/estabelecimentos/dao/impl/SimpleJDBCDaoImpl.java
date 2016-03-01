package br.com.bjbraz.app.estabelecimentos.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import br.com.bjbraz.app.estabelecimentos.dao.GenericJdbcDao;
import br.com.bjbraz.app.estabelecimentos.dao.SimpleJDBCDao;
import br.com.bjbraz.app.estabelecimentos.exception.ErrorMessage;
import br.com.bjbraz.app.estabelecimentos.exception.SystemException;

@Repository
public class SimpleJDBCDaoImpl extends GenericJdbcDao implements SimpleJDBCDao {

    private static final Logger log = LoggerFactory.getLogger(SimpleJDBCDaoImpl.class);

    @Autowired
    public void initDataSource(@Qualifier("dataSource1") DataSource dataSource) {
        setDataSource(dataSource);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> listarTodosInsulmosDaOs(Integer idOs){
        List<java.util.Map<String, Object>> mapaRetorno = null;

        try {
            StringBuilder sql = new StringBuilder(" select  " +
                 " vi.id_produto,   " +
                " vi.id_insulmo,  " +
                " sum(vi.qtd_total_insulmo) as total_insulmo,  " +
                " sum(vi.qtd_total_produto) as total_produto,  " +
                " p.ds_produto,  " +
                " p1.ds_produto as ds_insulmo,  " +
                " lp.nm_linha_produto  " +
                " from    " +
                " tb_venda_insulmos vi,   " +
                " bjbraz.tb_produto p ,  " +
                " bjbraz.tb_produto p1,  " +
                " bjbraz.tb_linha_produto lp  " +
                " where vi.id_produto = p.id_produto  " +
                " and   vi.id_venda   in (  " +
                " select id_venda from bjbraz.tb_ordem_servico_vendas where id_ordem_servico = " + idOs+
                "                              )  " +
                " and   vi.id_insulmo = p1.id_produto " +
                " and   p.id_linha_produto = lp.id_linha_produto " +
                " group by  " +
                " vi.id_produto, vi.id_insulmo,  " +
                " p.ds_produto, " +
                " p1.ds_produto, " +
                " lp.nm_linha_produto ");

                mapaRetorno = getJdbcTemplate().queryForList(sql.toString());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SystemException(ErrorMessage.UNEXPECTED_ERROR);
        }

        return mapaRetorno;        
    }

	@Override
	public List<Map<String, Object>> teste(Integer cdPais, Integer cdEmpr) {
		 List<java.util.Map<String, Object>> mapaRetorno = null;

	        try {
	            	StringBuilder sql = new StringBuilder(" select * from tb_demanda  ");

	                mapaRetorno = getJdbcTemplate().queryForList(sql.toString());
	        }
	        catch (Exception e) {
	            log.error(e.getMessage(), e);
	            throw new SystemException(ErrorMessage.UNEXPECTED_ERROR);
	        }
	        
	        return mapaRetorno;
	}
    


}
