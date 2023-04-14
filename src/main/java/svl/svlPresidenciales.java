package svl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class svlPresidenciales extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public svlPresidenciales() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		dao.OnpeDAO daoOnpe =new dao.OnpeDAO();
		
		String id = request.getParameter("id"); 
		String nroMesa = request.getParameter("nroMesa");
		
		String idDepartamento = request.getParameter("cboDepartamento");
		String idProvincia = request.getParameter("cboProvincia");
		String idDistrito = request.getParameter("cboDistrito");
		String idLocalVotacion = request.getParameter("cboLocalVotacion");
		
		Object DPD = session.getAttribute("DPD");
		String sDPD="";
		Object data= null, departamentos=null,provincias=null,distritos=null, localvotacion=null;
		
		if (idDepartamento == null) idDepartamento = "-1";
		if (idProvincia == null) 	idProvincia = "-1";
		if (idDistrito == null) 	idDistrito = "-1";
		if (idLocalVotacion == null)idLocalVotacion = "-1";
		
		
		if ( id == null && session.getAttribute("departamentos")==null) {
			session.setAttribute("departamentos", departamentos=daoOnpe.getDepartamentos(1,25));
		}
		
		if ( idDepartamento != null && !idDepartamento.equals("-1")) {
			session.setAttribute("provincias",  provincias = daoOnpe.getProvincias(idDepartamento));
		}
		
		if ( idProvincia != null && !idProvincia.equals("1")) {
			session.setAttribute("distritos",  distritos = daoOnpe.getDistritos(idProvincia));
		}
		
		if ( idDistrito != null && !idDistrito.equals("1")) {
			session.setAttribute("localvotacion",  localvotacion = daoOnpe.getLocalesVotacion(idDistrito));
		}
		
		if ( nroMesa != null) {
			data = daoOnpe.getGrupoVotacion(nroMesa);
		}
		
		if (idDepartamento != null) sDPD = idDepartamento;
		if (idProvincia != null) 	sDPD += "," + idProvincia;
		if (idDistrito != null) 	sDPD += "," + idDistrito;
		
		
		session.setAttribute("id", id);
		session.setAttribute("data", data);
		session.setAttribute("dpd", sDPD);
		
		response.sendRedirect("presidenciales.jsp");
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
