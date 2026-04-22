package fr.uvsq.hal.pglp.patterns;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.Optional;

/**
 * Implémentation du DAO pour la classe Employee utilisant JPA (Hibernate).
 */
public class EmployeeDAOJPA implements DAO<Employee> {

  // L'usine à "gestionnaires d'entités" de JPA
  private final EntityManagerFactory emf;

  public EmployeeDAOJPA() {
    // On charge la configuration "pglp_pu" définie dans notre fichier persistence.xml
    this.emf = Persistence.createEntityManagerFactory("pglp_pu");
  }

  @Override
  public Employee create(Employee obj) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    try {
      transaction.begin(); // On démarre la transaction
      em.persist(obj);     // MAGIE : JPA génère le INSERT SQL et sauvegarde l'objet, y compris ses listes !
      transaction.commit(); // On valide
      return obj;
    } catch (Exception e) {
      if (transaction.isActive()) transaction.rollback(); // En cas d'erreur, on annule tout
      e.printStackTrace();
      return null;
    } finally {
      em.close();
    }
  }

  @Override
  public Optional<Employee> find(String id) {
    EntityManager em = emf.createEntityManager();
    try {
      // MAGIE : JPA génère le SELECT SQL et reconstruit l'objet Employee !
      Employee employee = em.find(Employee.class, id);
      return Optional.ofNullable(employee);
    } finally {
      em.close();
    }
  }

  @Override
  public Employee update(Employee obj) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    try {
      transaction.begin();
      // "merge" met à jour l'entité en base avec les valeurs de notre objet
      Employee updatedEmployee = em.merge(obj);
      transaction.commit();
      return updatedEmployee;
    } catch (Exception e) {
      if (transaction.isActive()) transaction.rollback();
      e.printStackTrace();
      return null;
    } finally {
      em.close();
    }
  }

  @Override
  public void delete(Employee obj) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    try {
      transaction.begin();
      // Pour supprimer, il faut d'abord dire à JPA de "suivre" l'objet avec merge
      Employee managedEmployee = em.merge(obj); 
      em.remove(managedEmployee); // MAGIE : JPA génère le DELETE
      transaction.commit();
    } catch (Exception e) {
      if (transaction.isActive()) transaction.rollback();
      e.printStackTrace();
    } finally {
      em.close();
    }
  }
}