/*
 * Copyright 2026 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.currency;

import org.junit.jupiter.api.Test;
import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContexts;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class JreCurrencyContextTest implements CurrencyContextTesting2<JreCurrencyContext> {

    private final static Currency CURRENCY = Currency.getInstance("AUD");

    private final static CurrencyExchangeRater CURRENCY_EXCHANGE_RATER = (CurrencyCode from, CurrencyCode to, Optional<LocalDateTime> dateTime) -> {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(dateTime, "dateTime");

        return Optional.of(2);
    };

    private final static LocaleContext LOCALE_CONTEXT = LocaleContexts.jre(
        Locale.forLanguageTag("en-AU")
    );

    @Test
    public void testWithNullCurrencyFails() {
        assertThrows(
            NullPointerException.class,
            () -> JreCurrencyContext.with(
                null,
                CURRENCY_EXCHANGE_RATER,
                LOCALE_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullCurrencyExchangeRaterFails() {
        assertThrows(
            NullPointerException.class,
            () -> JreCurrencyContext.with(
                null,
                CURRENCY_EXCHANGE_RATER,
                LOCALE_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullLocaleContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> JreCurrencyContext.with(
                CURRENCY,
                CURRENCY_EXCHANGE_RATER,
                null
            )
        );
    }

    @Test
    public void testCurrency() {
        this.currencyAndCheck(
            this.createContext(),
            CURRENCY
        );
    }

    @Test
    public void testSetCurrency() {
        this.setCurrencyAndCheck(
            this.createContext(),
            CURRENCY
        );
    }

    @Test
    public void testAvailableCurrencies() {
        this.createContext()
            .availableCurrencies();
    }

    @Test
    public void testCurrencyForCurrencyCode() {
        this.currencyForCurrencyCodeAndCheck(
            this.createContext(),
            CurrencyCode.fromCurrency(CURRENCY),
            CURRENCY
        );
    }

    @Test
    public void testCurrencyForUnknownCurrencyCode() {
        this.currencyForCurrencyCodeAndCheck(
            this.createContext(),
            CurrencyCode.parse("BAD")
        );
    }

    @Test
    public void testCurrencyForLocale() {
        this.currencyForLocaleAndCheck(
            this.createContext(),
            Locale.forLanguageTag("en-AU"),
            CURRENCY
        );
    }

    @Test
    public void testCurrencyForInvalidLocale() {
        this.currencyForLocaleAndCheck(
            this.createContext(),
            Locale.ENGLISH
        );
    }

    @Test
    public void testCurrencyForText() {
        this.currencyTextAndCheck(
            this.createContext(),
            CURRENCY.getCurrencyCode(),
            CURRENCY.getDisplayName()
        );
    }

    @Test
    public void testLocalesForCurrencyCode() {
        this.localesForCurrencyCodeAndCheck(
            this.createContext(),
            CurrencyCode.parse("CAD"),
            "en-CA, fr-CA"
        );
    }

    @Test
    public void testLocalesForCurrencyCodeWithMany() {
        this.localesForCurrencyCodeAndCheck(
            this.createContext(),
            CurrencyCode.parse("AUD"),
            "en-CX, en-AU, en-NR, en-CC, en-TV, en-KI, en-NF"
        );
    }

    @Test
    public void testLocalesForCurrencyCodeWithUnknownCurrencyCode() {
        this.localesForCurrencyCodeAndCheck(
            this.createContext(),
            CurrencyCode.parse("ZZZ")
        );
    }

    @Test
    public void testFindByCurrencyText() {
        this.findByCurrencyTextAndCheck(
            this.createContext(),
            "Australia",
            0,
            2,
            CURRENCY
        );
    }

    @Test
    public void testFindByCurrencyTextDifferentCaseText() {
        this.findByCurrencyTextAndCheck(
            this.createContext(),
            "AUSTRalia",
            0,
            2,
            CURRENCY
        );
    }

    @Test
    public void testExchangeRate() {
        this.exchangeRateAndCheck(
            this.createContext(),
            CurrencyCode.parse("AUD"),
            CurrencyCode.parse("NZD"),
            LocalDateTime.MIN,
            2
        );
    }

    @Override
    public JreCurrencyContext createContext() {
        return JreCurrencyContext.with(
            CURRENCY,
            CURRENCY_EXCHANGE_RATER,
            LOCALE_CONTEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<JreCurrencyContext> type() {
        return JreCurrencyContext.class;
    }
}
