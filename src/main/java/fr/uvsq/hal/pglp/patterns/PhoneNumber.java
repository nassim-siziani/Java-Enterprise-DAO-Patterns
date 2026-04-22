package fr.uvsq.hal.pglp.patterns;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

/**
 * La classe <code>PhoneNumber</code> représente un numéro de téléphone.
 */
@Embeddable // Indique à JPA que cet objet sera "embarqué" dans la table liée à Employee
public class PhoneNumber implements Serializable {
  
  private String phoneNumber;
  
  @Enumerated(EnumType.STRING) // Sauvegarde le type sous forme de texte ("PRO", "MOBILE") au lieu d'un chiffre
  private PhoneNumberType phoneNumberType;

  // JPA EXIGE toujours un constructeur vide (sans arguments). 
  // On le met en 'protected' pour qu'il ne pollue pas l'utilisation normale.
  protected PhoneNumber() {}

  public PhoneNumber(String phoneNumber, PhoneNumberType phoneNumberType) {
    this.phoneNumber = Objects.requireNonNull(phoneNumber);
    this.phoneNumberType = phoneNumberType;
  }

  // ... (Garde tes méthodes equals, hashCode, getType et getPhoneNumber exactement comme avant) ...
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PhoneNumber that = (PhoneNumber) o;
    return Objects.equals(phoneNumber, that.phoneNumber) && phoneNumberType == that.phoneNumberType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(phoneNumber, phoneNumberType);
  }

  public PhoneNumberType getType() {
    return phoneNumberType;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}