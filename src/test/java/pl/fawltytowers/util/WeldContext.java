package pl.fawltytowers.util;

import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * {@link http://memorynotfound.com/java-se-unit-testing-cdi-junit-jboss-weld-se/}
 *
 */
public class WeldContext {

	public static final WeldContext INSTANCE = new WeldContext();

	private final Weld weld;
	private final WeldContainer container;

	private WeldContext() {
		this.weld = new Weld();
		this.container = weld.initialize();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				weld.shutdown();
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getBean(Class type) {
        
		return container.instance().select(type).get();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getBeanNewRequestContext(Class type) {
		RequestContext requestContext= container.instance().select(RequestContext.class, UnboundLiteral.INSTANCE).get();
        requestContext.activate();
        
		return container.instance().select(type).get();
	}
}
