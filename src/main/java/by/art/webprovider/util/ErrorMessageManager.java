package by.art.webprovider.util;

import java.util.ResourceBundle;

public class ErrorMessageManager {
  protected static final String BUNDLE_NAME = "message";
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private ErrorMessageManager() {
  }

  public static String getMessage(String key) {
    return RESOURCE_BUNDLE.getString(key);
  }
}
