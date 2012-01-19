/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.model.util;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableList;
import static org.eclipse.xtext.EcoreUtil2.getAllContentsOfType;

import com.google.eclipse.protobuf.protobuf.*;
import com.google.inject.*;

import java.util.*;

/**
 * Utility methods related to <code>{@link Message}</code>s.
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
@Singleton public class Messages {
  @Inject private ModelObjects modelObjects;
  @Inject private TypeExtensions typeExtensions;

  /**
   * Returns all the extensions of the given message declared in the same file as the message.
   * @param message the given message.
   * @return all the extensions of the given message declared in the same file as the message, or an empty collection if
   * none are found.
   */
  public Collection<TypeExtension> localExtensionsOf(Message message) {
    return extensionsOf(message, modelObjects.rootOf(message));
  }

  public Collection<TypeExtension> extensionsOf(Message message, Protobuf root) {
    Set<TypeExtension> extensions = newHashSet();
    for (TypeExtension extension : getAllContentsOfType(root, TypeExtension.class)) {
      Message referred = typeExtensions.messageFrom(extension);
      if (message.equals(referred)) {
        extensions.add(extension);
      }
    }
    return extensions;
  }

  /**
   * Returns all the fields of the given <code>{@link Message}</code>.
   * @param message the given message.
   * @return all the fields of the given {@code Message}.
   */
  public Collection<MessageField> fieldsOf(Message message) {
    List<MessageField> fields = newArrayList();
    for (MessageElement e : message.getElements()) {
      if (e instanceof MessageField) {
        fields.add((MessageField) e);
      }
    }
    return unmodifiableList(fields);
  }
}