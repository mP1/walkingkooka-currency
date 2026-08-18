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
import walkingkooka.locale.LocaleContextTesting;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyContextJreTest implements CurrencyContextTesting2<CurrencyContextJre>,
    LocaleContextTesting {

    private final static CurrencyExchangeRater CURRENCY_EXCHANGE_RATER = new CurrencyExchangeRater() {
        @Override
        public Set<CurrencyExchange> currencyExchanges() {
            return Set.of(
                CurrencyExchange.with(
                    CurrencyCode.parse("AUD"),
                    CurrencyCode.parse("NZD")
                )
            );
        }

        @Override
        public Optional<Number> currencyExchangeRate(final CurrencyExchange currencyExchange,
                                                     final Optional<LocalDateTime> dateTime) {
            Objects.requireNonNull(currencyExchange, "currencyExchange");
            Objects.requireNonNull(dateTime, "dateTime");

            return java.util.Optional.of(2);
        }
    };

    @Test
    public void testWithNullCurrencyFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyContextJre.with(
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
            () -> CurrencyContextJre.with(
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
            () -> CurrencyContextJre.with(
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
            CURRENCY.getCurrencyCode()
        );
    }

    @Test
    public void testFindByCurrencyTextDifferentCaseText() {
        this.findByCurrencyTextAndCheck(
            this.createContext(),
            "AUSTRalia",
            0,
            2,
            CURRENCY.getCurrencyCode()
        );
    }

    @Test
    public void testCurrencyExchangeRate() {
        this.currencyExchangeRateAndCheck(
            this.createContext(),
            CurrencyExchange.with(
                CurrencyCode.parse("AUD"),
                CurrencyCode.parse("NZD")
            ),
            LocalDateTime.MIN,
            2
        );
    }

    @Override
    public CurrencyContextJre createContext() {
        return CurrencyContextJre.with(
            CURRENCY,
            CURRENCY_EXCHANGE_RATER,
            LOCALE_CONTEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyContextJre> type() {
        return CurrencyContextJre.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
