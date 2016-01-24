
package pl.fawltytowers.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
public class EntityManagerProvider {
	 
	private EntityManagerFactory emf;
	
	@PostConstruct
    public void init() {
		emf = Persistence.createEntityManagerFactory("objectdb:hotel.odb");
    }
	
	@PreDestroy
    public void close() {
		emf.close();
    }
	
	@RequestScoped
	@Produces
	@EManager
	public EntityManager getEntityManager() {
		return this.emf.createEntityManager();
	}
	
	public EntityManagerFactory getEmf(){
		return this.emf;
	}
	
	public void closeLogHandler(@Disposes @EManager EntityManager handler) {
		handler.close();
	}
}
