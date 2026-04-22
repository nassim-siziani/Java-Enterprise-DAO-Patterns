package fr.uvsq.hal.pglp.patterns;

/**
 * Usine concrète qui fabrique des DAO fonctionnant avec la Sérialisation Java.
 */
public class DAOFactorySerialize extends DAOFactory {

  @Override
  public DAO<Employee> getEmployeeDAO() {
    return new EmployeeDAO(); // Retourne l'ancien DAO (celui des fichiers)
  }
}