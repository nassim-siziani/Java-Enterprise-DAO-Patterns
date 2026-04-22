package fr.uvsq.hal.pglp.patterns;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * La classe <code>Employee</code> représente un personnel d'une organisation.
 */
@Entity // Indique à JPA que cette classe correspond à une table en BDD
@Table(name = "EMPLOYEES") // Optionnel : force le nom de la table
public class Employee implements OrganizationElement, Serializable {

  @Id // Indique que ce champ est la clé primaire
  private String id;

  private String firstname;
  private String lastname;
  private LocalDate birthDate;

  // @ElementCollection dit à JPA : "Ceci est une liste, crée une table séparée pour la stocker"
  @ElementCollection
  @CollectionTable(name = "EMPLOYEE_FUNCTIONS", joinColumns = @JoinColumn(name = "employee_id"))
  @Column(name = "function_name")
  private List<String> functions;

  @ElementCollection
  @CollectionTable(name = "EMPLOYEE_PHONES", joinColumns = @JoinColumn(name = "employee_id"))
  @MapKeyEnumerated(EnumType.STRING) // La clé de la map sera stockée en texte
  @MapKeyColumn(name = "phone_type")
  private Map<PhoneNumberType, PhoneNumber> phoneNumbers;

  // Constructeur vide obligatoire pour JPA
  protected Employee() {}

  // Modification du constructeur privé pour générer l'ID
  private Employee(Builder builder) {
    this.firstname = builder.firstname;
    this.lastname = builder.lastname;
    this.birthDate = builder.birthDate;
    this.functions = builder.functions;
    this.phoneNumbers = builder.phoneNumbers;
    // On génère l'ID comme on le faisait manuellement dans JDBC
    this.id = this.firstname + "_" + this.lastname; 
  }

  public String getId() { return id; }
  public String getFirstname() { return firstname; }
  public String getLastname() { return lastname; }
  public LocalDate getBirthDate() { return birthDate; }
  public boolean hasFunction(String function) { return functions.contains(function); }
  
  public Optional<PhoneNumber> getPhoneNumber(PhoneNumberType phoneNumberType) {
    return Optional.ofNullable(phoneNumbers.get(phoneNumberType));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return Objects.equals(id, employee.id); // L'égalité se base maintenant sur l'ID !
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  /**
   * Le Builder reste inchangé ! Ton code existant n'est pas cassé.
   */
  public static class Builder {
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private List<String> functions;
    private Map<PhoneNumberType, PhoneNumber> phoneNumbers;

    public Builder(String firstname, String lastname, LocalDate birthDate) {
      this.firstname = firstname;
      this.lastname = lastname;
      this.birthDate = birthDate;
      functions = new ArrayList<>();
      phoneNumbers = new HashMap<>();
    }

    public Employee build() {
      return new Employee(this);
    }

    public Builder function(String function) {
      functions.add(function);
      return this;
    }

    public Builder phoneNumber(String phoneNumber, PhoneNumberType phoneNumberType) {
      phoneNumbers.put(phoneNumberType, new PhoneNumber(phoneNumber, phoneNumberType));
      return this;
    }
  }
}