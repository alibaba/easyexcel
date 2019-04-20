package com.alibaba.excel.support;


public abstract class LanguageAdapterHolder {

  private static final ThreadLocal<LanguageAdapter> LANGUAGE_ADAPTER_THREAD_LOCAL = new ThreadLocal<LanguageAdapter>();

  public static void setLanguageAdapter(LanguageAdapter languageAdapter) {
    LANGUAGE_ADAPTER_THREAD_LOCAL.set(languageAdapter);
  }

  public static LanguageAdapter getLanguageAdapter() {
    return LANGUAGE_ADAPTER_THREAD_LOCAL.get();
  }

  public static void remove() {
    LANGUAGE_ADAPTER_THREAD_LOCAL.remove();
  }
}
