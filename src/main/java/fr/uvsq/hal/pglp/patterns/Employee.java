package fr.uvsq.hal.pglp.patterns;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * La classe <code>Employee</code> représente un personnel d'une organisation.
 *
 * @author hal
 * @version 2022
 */
public class Employee implements OrganizationElement, Serializable {
  private final String firstname;

  private final String lastname;
  private final LocalDate birthDate;
  private List<String> functions;
  private Map<PhoneNumberType, PhoneNumber> phoneNumbers;

  private Employee(Builder builder) {
    firstname = builder.firstname;
    lastname = builder.lastname;
    birthDate = builder.birthDate;
    functions = builder.functions;
    phoneNumbers = builder.phoneNumbers;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public boolean hasFunction(String function) {
    return functions.contains(function);
  }

  public Optional<PhoneNumber> getPhoneNumber(PhoneNumberType phoneNumberType) {
    PhoneNumber phoneNumber = phoneNumbers.get(phoneNumberType);
    return Optional.ofNullable(phoneNumber);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return firstname.equals(employee.firstname) && lastname.equals(employee.lastname) && birthDate.equals(employee.birthDate) && functions.equals(employee.functions) && phoneNumbers.equals(employee.phoneNumbers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname, birthDate, functions, phoneNumbers);
  }

  /**
   * Permet l'initialisation d'un personnel selon le pattern Builder.
   */
  public static class Builder {
    // Attributs obligatoires
    private final String firstname;
    private final String lastname;
    private final LocalDate birthDate;

    // Attributs optionnels
    private List<String> functions;
    private Map<PhoneNumberType, PhoneNumber> phoneNumbers;

    /**
     * Fournit les attributs obligatoires pour un personnel.
     *
     * @param firstname prénom
     * @param lastname nom
     * @param birthDate date de naissance
     */
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
