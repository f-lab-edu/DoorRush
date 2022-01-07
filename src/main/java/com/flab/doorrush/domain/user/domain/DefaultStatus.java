package com.flab.doorrush.domain.user.domain;

public enum DefaultStatus {
  Y(true), N(false);

  private final boolean isDefault;

  DefaultStatus(boolean isDefault) {
    this.isDefault = isDefault;
  }

  public boolean getDefaultValue() {
    return isDefault;
  }
}
