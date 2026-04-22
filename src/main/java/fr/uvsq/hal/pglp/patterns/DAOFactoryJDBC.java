package fr.uvsq.hal.pglp.patterns;

/**
 * Usine concrète qui fabrique des DAO fonctionnant avec JDBC.
 */
public class DAOFactoryJDBC extends DAOFactory {

  @Override
  public DAO<Employee> getEmployeeDAO() {
    return new EmployeeDAOJDBC(); // Retourne le nouveau DAO (celui de la BDD)
  }
}