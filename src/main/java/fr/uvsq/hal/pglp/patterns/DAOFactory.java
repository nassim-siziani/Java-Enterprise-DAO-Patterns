package fr.uvsq.hal.pglp.patterns;

/**
 * Fabrique pour l'instanciation des DAO.
 * Implémente le pattern Factory pour centraliser la création des objets d'accès aux données.
 *
 * @author hal
 * @version 2022
 */
public class DAOFactory {

  /**
   * Récupère le DAO pour gérer la persistance des employés.
   *
   * @return une instance concrète de DAO pour Employee
   */
  public static DAO<Employee> getEmployeeDAO() {
    return new EmployeeDAO();
  }

  /* * Note : Si tu décides de faire la Question 6 (optionnelle) plus tard 
   * en créant un fichier TeamDAO.java, il te suffira de décommenter la méthode ci-dessous !
   */
  
  /*
  public static DAO<Team> getTeamDAO() {
    return new TeamDAO();
  }
  */
}