package fr.uvsq.hal.pglp.patterns;

import java.util.Optional;

/**
 * Interface générique pour le pattern DAO (Data Access Object).
 * Définit les opérations CRUD (Create, Read, Update, Delete).
 *
 * @param <T> le type de l'objet métier manipulé par le DAO
 * @author hal
 * @version 2022
 */
public interface DAO<T> {

  /**
   * Crée (sauvegarde) un nouvel objet dans la source de données.
   *
   * @param obj l'objet à créer
   * @return l'objet créé
   */
  T create(T obj);

  /**
   * Recherche un objet à partir de son identifiant.
   * On utilise Optional car l'objet peut ne pas exister.
   *
   * @param id l'identifiant de l'objet recherché (ex: un nom)
   * @return un Optional contenant l'objet s'il est trouvé, sinon un Optional vide
   */
  Optional<T> find(String id);

  /**
   * Met à jour un objet existant dans la source de données.
   *
   * @param obj l'objet avec les nouvelles valeurs
   * @return l'objet mis à jour
   */
  T update(T obj);

  /**
   * Supprime un objet de la source de données.
   *
   * @param obj l'objet à supprimer
   */
  void delete(T obj);
}