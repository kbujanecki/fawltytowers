package pl.fawltytowers.beans;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * Begin and commit transaction. Used because there is no {@link Transactional} support in weld library for non Jee container environment.
 *   
 * 
 * @author krzysztof bujanecki
 *
 */
@Tx
@Interceptor
public class TxInterceptor implements Serializable {

	private static final long serialVersionUID = -2019240634188419271L;
	
	final static Logger logger = Logger.getLogger(TxInterceptor.class);

	@Inject
	@EManager
	EntityManager em;

	public TxInterceptor() {
	}

	@AroundInvoke
	public Object arounInvoke(InvocationContext invocationContext) throws Exception {
		
		
		try {
			logger.info("<=========== Begin tx for: " + invocationContext.getMethod().getName() + " em: "  + em);
			this.em.getTransaction().begin();
			return invocationContext.proceed();
		} finally {
			logger.info("<=========== Commit tx for: " + em);
			this.em.getTransaction().commit();
		}
	}
}
