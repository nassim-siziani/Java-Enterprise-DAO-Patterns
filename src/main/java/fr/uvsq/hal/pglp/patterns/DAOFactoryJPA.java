package fr.uvsq.hal.pglp.patterns;

/**
 * Usine concrète qui fabrique des DAO fonctionnant avec JPA (Hibernate).
 */
public class DAOFactoryJPA extends DAOFactory {

  @Override
  public DAO<Employee> getEmployeeDAO() {
    return new EmployeeDAOJPA();
  }
}