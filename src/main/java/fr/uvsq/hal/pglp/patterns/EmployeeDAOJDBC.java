package fr.uvsq.hal.pglp.patterns;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Implémentation du DAO pour la classe Employee utilisant JDBC avec Apache Derby.
 */
public class EmployeeDAOJDBC implements DAO<Employee> {

  // L'URL de connexion à la base de données Derby embarquée
  // "create=true" indique à Derby de créer la base si elle n'existe pas encore
  private static final String DB_URL = "jdbc:derby:data/pglp_db;create=true";

  public EmployeeDAOJDBC() {
    createTablesIfNotExists();
  }

  /**
   * Méthode utilitaire pour obtenir une connexion à la base de données.
   */
  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  /**
   * Crée les tables nécessaires si elles ne sont pas déjà présentes dans la base.
   */
  private void createTablesIfNotExists() {
    String createEmployeesTable = "CREATE TABLE EMPLOYEES ("
        + "id VARCHAR(255) PRIMARY KEY, "
        + "firstname VARCHAR(255), "
        + "lastname VARCHAR(255), "
        + "birth_date DATE)";

    // Pour simplifier cet exemple de base, on ne crée ici que la table EMPLOYEES.
    // Il faudrait faire de même pour FUNCTIONS et PHONES pour gérer les collections.

    try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
      // On essaie de créer la table. Si elle existe déjà, Derby lèvera une exception
      // SQL avec le code X0Y32, qu'on ignore simplement.
      stmt.executeUpdate(createEmployeesTable);
    } catch (SQLException e) {
      if (!"X0Y32".equals(e.getSQLState())) {
        e.printStackTrace(); // Affiche l'erreur si c'est autre chose que "table déjà existante"
      }
    }
  }

  /**
   * Génère l'identifiant (clé primaire) d'un employé.
   */
  private String getId(Employee employee) {
    return employee.getFirstname() + "_" + employee.getLastname();
  }

  @Override
  public Employee create(Employee obj) {
    String insertSQL = "INSERT INTO EMPLOYEES (id, firstname, lastname, birth_date) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
      pstmt.setString(1, getId(obj));
      pstmt.setString(2, obj.getFirstname());
      pstmt.setString(3, obj.getLastname());
      pstmt.setDate(4, Date.valueOf(obj.getBirthDate())); // Convertit LocalDate en java.sql.Date
      
      pstmt.executeUpdate();
      
      // Ici, il faudrait aussi insérer les fonctions et numéros dans les autres tables...
      
      return obj;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Optional<Employee> find(String id) {
    String selectSQL = "SELECT * FROM EMPLOYEES WHERE id = ?";
    
    try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
      pstmt.setString(1, id);
      
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) { // Si on trouve une ligne
          // On reconstruit l'objet Employee avec le Builder
          Employee emp = new Employee.Builder(
              rs.getString("firstname"), 
              rs.getString("lastname"), 
              rs.getDate("birth_date").toLocalDate()
          ).build();
          
          // Ici, il faudrait faire d'autres requêtes (SELECT) pour récupérer les fonctions
          // et numéros de téléphone et les ajouter au builder...
          
          return Optional.of(emp);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty(); // Retourne vide si non trouvé ou s'il y a eu une erreur
  }

  @Override
  public Employee update(Employee obj) {
    String updateSQL = "UPDATE EMPLOYEES SET firstname = ?, lastname = ?, birth_date = ? WHERE id = ?";
    
    try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
      pstmt.setString(1, obj.getFirstname());
      pstmt.setString(2, obj.getLastname());
      pstmt.setDate(3, Date.valueOf(obj.getBirthDate()));
      pstmt.setString(4, getId(obj));
      
      int rowsAffected = pstmt.executeUpdate();
      
      // Il faudrait aussi mettre à jour les tables FUNCTIONS et PHONES (le plus simple
      // est souvent de faire DELETE puis INSERT pour les collections).
      
      if (rowsAffected > 0) {
        return obj;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void delete(Employee obj) {
    String deleteSQL = "DELETE FROM EMPLOYEES WHERE id = ?";
    
    try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
      pstmt.setString(1, getId(obj));
      
      // Note : Il faudrait d'abord supprimer (DELETE) les lignes correspondantes 
      // dans les tables FUNCTIONS et PHONES (ou configurer un ON DELETE CASCADE sur la BDD).
      
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
