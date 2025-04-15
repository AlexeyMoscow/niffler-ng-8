package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.dataUtils.Databases;

public class DatabasesExtension implements SuiteExtension {
  @Override
  public void afterSuite() {
    Databases.closeAllConnections();
  }
}
