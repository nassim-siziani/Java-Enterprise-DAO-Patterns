package fr.uvsq.hal.pglp.patterns;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * La classe <code>Team</code> représente un groupe dans une organisation.
 *
 * @author hal
 * @version 2022
 */
public class Team implements OrganizationElement, Iterable<OrganizationElement> {
  private List<OrganizationElement> members;

  public Team() {
    members = new ArrayList<>();
  }

  public void add(OrganizationElement element) {
    members.add(element);
  }

  public boolean contains(OrganizationElement element) {
    return members.contains(element);
  }

  @Override
  public Iterator<OrganizationElement> iterator() {
    return new TeamIterator(members);
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
