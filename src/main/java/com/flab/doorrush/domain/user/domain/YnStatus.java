package com.flab.doorrush.domain.user.domain;

public enum YnStatus {
  Y(true), N(false);

  private final boolean ynValue;

  YnStatus(boolean ynValue) {
    this.ynValue = ynValue;
  }

  public boolean getYnValue() {
    return ynValue;
  }
}
