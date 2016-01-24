package pl.fawltytowers.servlet;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import pl.fawltytowers.beans.InitData;
import pl.fawltytowers.model.Hotel;

/**
 * Initialize base hotel data after jetty start. 
 *  
 * @author krzysztof bujanecki
 *
 */
public class InitFoltyServlet extends HttpServlet {

	@Inject
	private InitData init;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7654774560030895717L;

	@Override
	public void init() throws ServletException {
		init.createFawltyTowersIfNotExists();
	}
}
