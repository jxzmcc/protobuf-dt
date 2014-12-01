/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.grammar;

/**
 * @author alruiz@google.com (Alex Ruiz)
 */
public final class Syntaxes {
  /**
   * Returns the value to use to in the "syntax" element if this is a proto2 file.
   * @return the {@code String} "proto2".
   */
  public static String proto2() {
    return "proto2";
  }

  /**
   * Returns the value to use to in the "syntax" element if this is a proto3 file.
   * @return the {@code String} "proto3".
   */
  public static String proto3() {
    return "proto3";
  }

  /**
   * Indicates whether the given {@code String} is equal to <code>{@link #proto2()}</code>.
   * @param s the {@code String} to check.
   * @return {@code true} if the given {@code String} is equal to "proto2," {@code false} otherwise.
   */
  public static boolean isSpecifyingProto2Syntax(String s) {
    return proto2().equals(s);
  }

  /**
   * Indicates whether the given {@code String} is equal to <code>{@link #proto3()}</code>.
   * @param s the {@code String} to check.
   * @return {@code true} if the given {@code String} is equal to "proto3," {@code false} otherwise.
   */
  public static boolean isSpecifyingProto3Syntax(String s) {
    return proto3().equals(s);
  }

  private Syntaxes() {}
}
