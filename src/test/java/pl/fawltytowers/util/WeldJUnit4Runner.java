package pl.fawltytowers.util;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * 
 * {@link http://memorynotfound.com/java-se-unit-testing-cdi-junit-jboss-weld-se/}
 *
 */
public class WeldJUnit4Runner extends BlockJUnit4ClassRunner {
	 public WeldJUnit4Runner(Class<Object> clazz) throws InitializationError {
	        super(clazz);
	    }

	    @Override
	    protected Object createTest() {
	        final Class<?> test = getTestClass().getJavaClass();
	        return WeldContext.INSTANCE.getBeanNewRequestContext(test);
	    }
}
