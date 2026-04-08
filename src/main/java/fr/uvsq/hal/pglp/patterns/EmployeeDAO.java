package fr.uvsq.hal.pglp.patterns;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

/**
 * Implémentation du DAO pour la classe Employee utilisant la sérialisation.
 * Les employés sont sauvegardés dans des fichiers .ser dans un dossier dédié.
 *
 * @author hal
 * @version 2022
 */
public class EmployeeDAO implements DAO<Employee> {

  private final String dirPath = "data/employees/";

  /**
   * Constructeur qui s'assure que le répertoire de sauvegarde existe.
   */
  public EmployeeDAO() {
    File dir = new File(dirPath);
    if (!dir.exists()) {
      dir.mkdirs(); // Crée le dossier "data/employees/" s'il n'existe pas
    }
  }

  /**
   * Génère l'identifiant unique d'un employé basé sur son prénom et son nom.
   */
  private String getId(Employee employee) {
    return employee.getFirstname() + "_" + employee.getLastname();
  }

  /**
   * Génère le chemin complet du fichier de sauvegarde.
   */
  private String getFileName(String id) {
    return dirPath + id + ".ser";
  }

  @Override
  public Employee create(Employee obj) {
    try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream(getFileName(getId(obj))))) {
      oos.writeObject(obj);
      return obj;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Optional<Employee> find(String id) {
    File file = new File(getFileName(id));
    if (!file.exists()) {
      return Optional.empty(); // Fichier introuvable, on renvoie un Optional vide
    }

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
      Employee employee = (Employee) ois.readObject();
      return Optional.of(employee);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  @Override
  public Employee update(Employee obj) {
    // Pour la sérialisation par fichier, mettre à jour revient à écraser l'ancien fichier.
    // On réutilise donc simplement la méthode create.
    return create(obj);
  }

  @Override
  public void delete(Employee obj) {
    File file = new File(getFileName(getId(obj)));
    if (file.exists()) {
      file.delete(); // Supprime le fichier physique
    }
  }
}