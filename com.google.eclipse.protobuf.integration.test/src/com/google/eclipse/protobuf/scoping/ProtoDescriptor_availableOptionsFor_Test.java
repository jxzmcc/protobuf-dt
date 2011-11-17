/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.scoping;

import static com.google.eclipse.protobuf.junit.core.Setups.integrationTestSetup;
import static com.google.eclipse.protobuf.junit.core.XtextRule.createWith;
import static com.google.eclipse.protobuf.junit.matchers.FieldHasType.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.*;

import org.eclipse.emf.ecore.EObject;
import org.junit.*;

import com.google.eclipse.protobuf.junit.core.XtextRule;
import com.google.eclipse.protobuf.protobuf.*;

/**
 * Tests for <code>{@link ProtoDescriptor#availableOptionsFor(EObject)}</code>.
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
public class ProtoDescriptor_availableOptionsFor_Test {

  @Rule public XtextRule xtext = createWith(integrationTestSetup());

  private Options options;
  private ProtoDescriptor descriptor;

  @Before public void setUp() {
    options = new Options();
    ProtoDescriptorProvider descriptorProvider = xtext.getInstanceOf(ProtoDescriptorProvider.class);
    descriptor = descriptorProvider.primaryDescriptor();
  }

  @Test public void should_return_all_file_options() {
    Protobuf optionContainer = mock(Protobuf.class);
    options.mapByName(descriptor.availableOptionsFor(optionContainer));
    assertThat(options.option("java_package"), isString());
    assertThat(options.option("java_outer_classname"), isString());
    assertThat(options.option("java_multiple_files"), isBool());
    assertThat(options.option("java_generate_equals_and_hash"), isBool());
    assertNotNull(options.option("optimize_for"));
    assertThat(options.option("cc_generic_services"), isBool());
    assertThat(options.option("java_generic_services"), isBool());
    assertThat(options.option("py_generic_services"), isBool());
  }

  @Test public void should_return_all_message_options() {
    Message optionContainer = mock(Message.class);
    options.mapByName(descriptor.availableOptionsFor(optionContainer));
    assertThat(options.option("message_set_wire_format"), isBool());
    assertThat(options.option("no_standard_descriptor_accessor"), isBool());
  }

  @Test public void should_return_all_field_options() {
    MessageField optionContainer = mock(MessageField.class);
    options.mapByName(descriptor.availableOptionsFor(optionContainer));
    assertNotNull(options.option("ctype"));
    assertThat(options.option("packed"), isBool());
    assertThat(options.option("deprecated"), isBool());
    assertThat(options.option("experimental_map_key"), isString());
  }

  @Test public void should_return_empty_List_if_given_model_does_not_have_options() {
    Import optionContainer = mock(Import.class);
    Collection<MessageField> foundOptions = descriptor.availableOptionsFor(optionContainer);
    assertThat(foundOptions.size(), equalTo(0));
  }

  private static class Options {
    private final Map<String, MessageField> optionsByName = new HashMap<String, MessageField>();

    void mapByName(Collection<MessageField> options) {
      optionsByName.clear();
      for (MessageField option : options) {
        Name name = option.getName();
        optionsByName.put(name.getValue(), option);
      }
    }

    MessageField option(String name) {
      return optionsByName.get(name);
    }
  }
}
