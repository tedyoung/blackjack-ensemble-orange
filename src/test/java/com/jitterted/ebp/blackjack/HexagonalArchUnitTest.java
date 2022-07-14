package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleGame;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

public class HexagonalArchUnitTest {

    @Test
    public void hexagonalArchitecture() throws Exception {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.jitterted.ebp.blackjack");

        Architectures.onionArchitecture()
                     .domainModels("com.jitterted.ebp.blackjack.domain..")
                     .domainServices("com.jitterted.ebp.blackjack.domain..")
                     .applicationServices("com.jitterted.ebp.blackjack.application..")
                     .adapter("persistence", "com.jitterted.ebp.blackjack.adapter.out.jdbc..")
                     .adapter("notifier", "com.jitterted.ebp.blackjack.adapter.out.gamemonitor..")
                     .adapter("web", "com.jitterted.ebp.blackjack.adapter.in.web..")
                     .adapter("console", "com.jitterted.ebp.blackjack.adapter.in.console..")
                     .adapter("bootstrap", "com.jitterted.ebp.blackjack")
                     .ignoreDependency(Blackjack.class, ConsoleGame.class)
                     .ignoreDependency(getClass(), ConsoleGame.class)
                     .check(importedClasses);
    }
}
