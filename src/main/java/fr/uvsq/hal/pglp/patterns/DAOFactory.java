package fr.uvsq.hal.pglp.patterns;

/**
 * Fabrique Abstraite pour l'instanciation des DAO.
 * Elle permet de choisir la technologie de persistance (Fichier ou BDD).
 */
public abstract class DAOFactory {

  // Enumération pour lister nos types de stockage disponibles
  public enum DAOType {
    SERIALIZATION,
    JDBC
  }

  // Les méthodes abstraites que les sous-usines devront implémenter
  public abstract DAO<Employee> getEmployeeDAO();
  
  // public abstract DAO<Team> getTeamDAO(); // A décommenter si tu fais la classe Team

  /**
   * Le "Producteur" de fabrique : c'est lui qui choisit quelle usine instancier.
   * * @param type Le type de persistance souhaité
   * @return L'usine correspondante
   */
  public static DAOFactory getDAOFactory(DAOType type) {
    if (type == DAOType.JDBC) {
      return new DAOFactoryJDBC();
    }
    // Par défaut, on retourne la sérialisation
    return new DAOFactorySerialize();
  }
}