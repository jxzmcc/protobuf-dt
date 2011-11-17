/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.junit.matchers;

import static org.eclipse.xtext.EcoreUtil2.getAllContentsOfType;

import java.util.*;

import org.eclipse.emf.ecore.EObject;
import org.hamcrest.*;

import com.google.eclipse.protobuf.junit.IEObjectDescriptions;
import com.google.eclipse.protobuf.protobuf.*;
import com.google.eclipse.protobuf.protobuf.Enum;

/**
 * @author alruiz@google.com (Alex Ruiz)
 */
public class ContainAllLiteralsInEnum extends BaseMatcher<IEObjectDescriptions> {

  private final Enum anEnum;

  public static ContainAllLiteralsInEnum containAllLiteralsIn(Enum anEnum) {
    return new ContainAllLiteralsInEnum(anEnum);
  }
  
  private ContainAllLiteralsInEnum(Enum anEnum) {
    this.anEnum = anEnum;
  }
  
  @Override public boolean matches(Object arg) {
    if (!(arg instanceof IEObjectDescriptions)) return false;
    IEObjectDescriptions descriptions = (IEObjectDescriptions) arg;
    List<Literal> literals = allLiterals();
    if (descriptions.size() != literals.size()) return false;
    for (Literal literal : literals) {
      Name name = literal.getName();
      EObject described = descriptions.objectDescribedAs(name.getValue());
      if (described != literal) return false;
    }
    return true;
  }

  @Override public void describeTo(Description description) {
    List<String> names = new ArrayList<String>();
    for (Literal literal : allLiterals()) {
      Name name = literal.getName();
      names.add(name.getValue());
    }
    description.appendValue(names);
  }

  private List<Literal> allLiterals() {
    return getAllContentsOfType(anEnum, Literal.class);
  }
}
