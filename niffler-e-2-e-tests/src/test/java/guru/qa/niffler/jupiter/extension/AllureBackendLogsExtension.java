package guru.qa.niffler.jupiter.extension;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.TestResult;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class AllureBackendLogsExtension implements SuiteExtension{

    private static final String caseName = "Niffler backend logs";

    @SneakyThrows
    @Override
    public void afterSuite() {
        final AllureLifecycle allureLifecycle = Allure.getLifecycle();
        final String caseId = UUID.randomUUID().toString();
        allureLifecycle.scheduleTestCase(new TestResult().setUuid(caseId).setName(caseName));
        allureLifecycle.startTestCase(caseId);
        attachLogForService(allureLifecycle, "Niffler-auth log", ".logs/niffler-auth/app.log");
        attachLogForService(allureLifecycle, "Niffler-currency log", ".logs/niffler-currency/app.log");
        attachLogForService(allureLifecycle, "Niffler-gateway log", ".logs/niffler-gateway/app.log");
        attachLogForService(allureLifecycle,"Niffler-spend log", ".logs/niffler-spend/app.log");
        attachLogForService(allureLifecycle,"Niffler-userdata log", ".logs/niffler-userdata/app.log");

        allureLifecycle.stopTestCase(caseId);
        allureLifecycle.writeTestCase(caseId);
    }

    @SneakyThrows
    private static void attachLogForService(AllureLifecycle allureLifecycle, String name, String path){
        allureLifecycle.addAttachment(
                name,
                "text/html",
                ".log",
                Files.newInputStream(Path.of(path))
        );
    }
}
