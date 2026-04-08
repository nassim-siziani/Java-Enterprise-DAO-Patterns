package fr.uvsq.hal.pglp.patterns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * La classe <code>Team</code> représente un groupe dans une organisation.
 *
 * @author hal
 * @version 2022
 */
public class Team implements OrganizationElement, Iterable<OrganizationElement>, Serializable {
  private List<OrganizationElement> members;

  public Team() {
    members = new ArrayList<>();
  }

  /**
   * Ajoute un élément à l'équipe.
   * Empêche une équipe de s'ajouter elle-même directement.
   */
  public void add(OrganizationElement element) {
    if (element != this) {
      members.add(element);
    }
  }

  /**
   * Vérifie si un élément est présent dans l'équipe ou ses sous-équipes.
   */
  public boolean contains(OrganizationElement element) {
    // Règle stricte : un groupe ne se contient jamais lui-même
    if (this.equals(element)) {
      return false;
    }

    for (OrganizationElement member : members) {
      // 1. Est-ce que l'élément recherché est directement là ?
      if (member.equals(element)) {
        return true;
      }
      // 2. Si c'est une sous-équipe, on fouille à l'intérieur (récursivité) !
      if (member instanceof Team && ((Team) member).contains(element)) {
        return true;
      }
    }
    
    return false;
  }

  @Override
  public Iterator<OrganizationElement> iterator() {
    return new TeamIterator(members);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Team team = (Team) o;
    return Objects.equals(members, team.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(members);
  }

  private static class TeamIterator implements Iterator<OrganizationElement> {
    private Stack<Iterator<OrganizationElement>> iteratorStack;

    public TeamIterator(List<OrganizationElement> members) {
      iteratorStack = new Stack<>();
      iteratorStack.push(members.iterator());
    }

    @Override
    public boolean hasNext() {
      boolean hasNext = iteratorStack.peek().hasNext();
      while (!hasNext) {
        iteratorStack.pop();
        if (iteratorStack.isEmpty()) {
          return false;
        }
        hasNext = iteratorStack.peek().hasNext();
      }
      return hasNext;
    }

    @Override
    public OrganizationElement next() {
      OrganizationElement nextElement = iteratorStack.peek().next();
      while (nextElement instanceof Team) {
        Team team = (Team) nextElement;
        Iterator<OrganizationElement> newIterator = team.iterator();
        iteratorStack.push(newIterator);
        nextElement = iteratorStack.peek().next();
      }
      return nextElement;
    }
  }
}