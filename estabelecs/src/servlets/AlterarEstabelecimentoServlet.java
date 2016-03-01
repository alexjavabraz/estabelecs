package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bjbraz.app.estabelecimentos.dao.TesteHibernate;
import br.com.bjbraz.app.estabelecimentos.service.DummyService;


@WebServlet("/alterar")
public class AlterarEstabelecimentoServlet extends BaseServlet {
	
	@Autowired
	private DummyService service;

    /**
     * 
     */
    private static final long serialVersionUID = 3513339749797971724L;

    private static final String PAGINA = "./alterar.jsp";
    private static final String INICIO = "./consulta.jsp";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	service.listAll();
    	
    	service.listAll2();
    	
    	doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        


        request.getRequestDispatcher(PAGINA).forward(request, response);
    }
     

}
