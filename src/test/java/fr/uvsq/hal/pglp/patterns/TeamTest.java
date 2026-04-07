package fr.uvsq.hal.pglp.patterns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
  private Employee frodon;
  private Employee gandalf;
  private Team communaute;

  @BeforeEach
  public void setup() {
    frodon = new Employee.Builder("Frodon", "Sacquet", LocalDate.of(1987, 6, 12)).build();
    gandalf = new Employee.Builder("Gandalf", "Le Gris", LocalDate.of(1987, 6, 12)).build();
    communaute = new Team();
    communaute.add(frodon);
    communaute.add(gandalf);
  }

  @Test
  public void unGroupeContientDesEmployes() {
    assertTrue(communaute.contains(frodon));
    assertTrue(communaute.contains(gandalf));
  }

  @Test
  public void unGroupeContientDesEmployesEtDesGroupes() {
    Team team1 = new Team();
    communaute.add(team1);
    assertTrue(communaute.contains(team1));
  }

  @Test
  public void containsFaitUneRechercheRecursive() {
    Team team1 = new Team();
    team1.add(frodon);
    Team team2 = new Team();
    team2.add(gandalf);
    assertTrue(team1.contains(gandalf));
  }

  @Test
  public void unGroupeNeSeContientPasLuiMemeDirectement() {
    communaute.add(communaute);
    assertFalse(communaute.contains(communaute));
  }

  @Test
  public void unGroupeNeSeContientPasLuiMemeMemeIndirectement() {
    Team team1 = new Team();
    team1.add(frodon);
    Team team2 = new Team();
    team2.add(gandalf);
    team2.add(team1);
    assertFalse(team1.contains(team1));
  }

  @Test
  public void unGroupePeutEtreParcourus() {
    final List<OrganizationElement> expectedEmployees = List.of(frodon, gandalf);
    List<OrganizationElement> visitedEmployees = new ArrayList<>();
    for (OrganizationElement element : communaute) {
      visitedEmployees.add(element);
    }
    assertEquals(expectedEmployees, visitedEmployees);
  }

  @Test
  public void unGroupeImbriquePeutEtreParcourus() {
    final List<OrganizationElement> expectedEmployees = List.of(frodon, gandalf);
    List<OrganizationElement> visitedEmployees = new ArrayList<>();
    Team team1 = new Team();
    team1.add(frodon);
    Team team2 = new Team();
    team2.add(gandalf);
    team1.add(team2);
    for (OrganizationElement element : team1) {
      visitedEmployees.add(element);
    }
    assertEquals(expectedEmployees, visitedEmployees);
  }

  @Test
  public void unAutreGroupeImbriquePeutEtreParcourus() {
    final List<OrganizationElement> expectedEmployees = List.of(frodon, gandalf, frodon, gandalf, frodon, gandalf, gandalf, frodon, gandalf, frodon);
    List<OrganizationElement> visitedEmployees = new ArrayList<>();
    Team team1 = new Team();
    team1.add(frodon);
    Team team2 = new Team();
    team2.add(gandalf);
    Team team3 = new Team();
    team3.add(frodon); team3.add(gandalf);
    team2.add(team3);
    team2.add(frodon);
    team1.add(team2);
    team1.add(gandalf);
    team1.add(team2);
    for (OrganizationElement element : team1) {
      visitedEmployees.add(element);
    }
    assertEquals(expectedEmployees, visitedEmployees);
  }
}
