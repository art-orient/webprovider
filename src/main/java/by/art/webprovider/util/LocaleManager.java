package by.art.webprovider.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {
  private static final String MESSAGE_BUNDLE = "message";
  private static ResourceBundle resourceBundle = ResourceBundle.getBundle(MESSAGE_BUNDLE);

  private LocaleManager() {
  }

  public static void setLocale(String language) {
    Locale locale = new Locale(language);
    Locale.setDefault(locale);
    resourceBundle = ResourceBundle.getBundle(MESSAGE_BUNDLE, locale);
  }

  public static String getMessage(String key) {
    return resourceBundle.getString(key);
  }
}
