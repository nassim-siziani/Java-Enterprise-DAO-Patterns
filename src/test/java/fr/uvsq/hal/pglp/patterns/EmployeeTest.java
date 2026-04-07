package fr.uvsq.hal.pglp.patterns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static fr.uvsq.hal.pglp.patterns.PhoneNumberType.MOBILE;
import static fr.uvsq.hal.pglp.patterns.PhoneNumberType.PRO;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
  Employee frodon;

  @BeforeEach
  public void setup() {
    frodon = new Employee.Builder("Frodon", "Sacquet", LocalDate.of(1987, 6, 12)).build();
  }

  @Test
  public void unPersonnelPossedeLesCaracteristiquesObligatoires() {
    Employee employee = new Employee.Builder("Frodon", "Sacquet", LocalDate.of(1987, 6, 12)).build();
    assertEquals("Frodon", employee.getFirstname());
    assertEquals("Sacquet", employee.getLastname());
    assertEquals(LocalDate.of(1987, 6, 12), employee.getBirthDate());
    assertFalse(employee.hasFunction("Quelconque"));
    for (PhoneNumberType phoneNumberType : PhoneNumberType.values()) {
      assertTrue(employee.getPhoneNumber(phoneNumberType).isEmpty());
    }
  }

  @Test
  public void unPersonnelAvecDesFonctions() {
    Employee employee = new Employee.Builder("Frodon", "Sacquet", LocalDate.of(1987, 6, 12))
      .function("Chef de service")
      .function("Président du CE")
      .build();
    assertEquals("Frodon", employee.getFirstname());
    assertEquals("Sacquet", employee.getLastname());
    assertEquals(LocalDate.of(1987, 6, 12), employee.getBirthDate());
    assertTrue(employee.hasFunction("Chef de service"));
    assertTrue(employee.hasFunction("Président du CE"));
  }

  @Test
  public void unPersonnelAvecDesNumerosDeTelephone() {
    PhoneNumber pro = new PhoneNumber("0123456789", PRO);
    PhoneNumber mobile = new PhoneNumber("0213456789", MOBILE);
    Employee employee = new Employee.Builder("Frodon", "Sacquet", LocalDate.of(1987, 6, 12))
      .phoneNumber(pro.getPhoneNumber(), pro.getType())
      .phoneNumber(mobile.getPhoneNumber(), mobile.getType())
      .build();
    assertEquals("Frodon", employee.getFirstname());
    assertEquals("Sacquet", employee.getLastname());
    assertEquals(LocalDate.of(1987, 6, 12), employee.getBirthDate());
    assertEquals(Optional.of(pro), employee.getPhoneNumber(PRO));
    assertEquals(Optional.of(mobile), employee.getPhoneNumber(MOBILE));
  }

  @Test
  public void unPersonnelEstSerializable() throws IOException, ClassNotFoundException {
    byte[] frodonAsByteArray = SerializationUtils.serialize(frodon);
    Employee deserializedFrodon = SerializationUtils.deserialize(frodonAsByteArray, Employee.class);
    assertEquals(frodon, deserializedFrodon);
  }
}
