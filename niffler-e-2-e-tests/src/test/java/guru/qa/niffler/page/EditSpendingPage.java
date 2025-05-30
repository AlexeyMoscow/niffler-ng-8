package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class EditSpendingPage {

  private final SelenideElement descriptionInput = $("#description");
  private final SelenideElement sumInput = $("#amount");
  private final SelenideElement submitBtn = $("#save");

  public void editDescription(String description) {
    descriptionInput.clear();
    descriptionInput.setValue(description);
    submitBtn.click();
  }

  public MainPage editSum(String sum) {
    sumInput.clear();
    sumInput.setValue(sum);
    submitBtn.click();
    return new MainPage();
  }
}
