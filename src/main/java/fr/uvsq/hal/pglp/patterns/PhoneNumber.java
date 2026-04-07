package fr.uvsq.hal.pglp.patterns;

import java.util.Objects;

/**
 * La classe <code>PhoneNumber</code> représente un numéro de téléphone.
 *
 * @author hal
 * @version 2022
 */
public class PhoneNumber {
  private final String phoneNumber;
  private final PhoneNumberType phoneNumberType;

  public PhoneNumber(String phoneNumber, PhoneNumberType phoneNumberType) {
    this.phoneNumber = Objects.requireNonNull(phoneNumber);
    this.phoneNumberType = phoneNumberType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
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
