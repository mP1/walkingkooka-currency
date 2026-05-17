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
import walkingkooka.collect.set.Sets;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ReadOnlyCurrencyContextTest implements CurrencyContextTesting2<ReadOnlyCurrencyContext> {

    @Test
    public void testWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> ReadOnlyCurrencyContext.with(null)
        );
    }

    @Test
    public void testWithReadOnlyCurrencyContext() {
        final ReadOnlyCurrencyContext context = this.createContext();
        assertSame(
            context,
            ReadOnlyCurrencyContext.with(context)
        );
    }

    private final static Locale LOCALE = Locale.forLanguageTag("en-AU");
    private final static Currency CURRENCY = Currency.getInstance(LOCALE);

    @Test
    public void testCurrencyForCurrencyCode() {
        this.currencyForCurrencyCodeAndCheck(
            this.createContext(),
            CurrencyCode.fromCurrency(CURRENCY),
            CURRENCY
        );
    }

    @Test
    public void testCurrencyForLocale() {
        this.currencyForLocaleAndCheck(
            this.createContext(),
            LOCALE,
            CURRENCY
        );
    }

    @Test
    public void testCurrencyText() {
        this.currencyTextAndCheck(
            this.createContext(),
            CURRENCY.getCurrencyCode(),
            "***" + CURRENCY.getDisplayName()
        );
    }

    @Test
    public void testLocalesForCurrencyCode() {
        this.localesForCurrencyCodeAndCheck(
            this.createContext(),
            CurrencyCode.parse("AUD"),
            LOCALE
        );
    }

    @Test
    public void testFindByCurrencyText() {
        this.findByCurrencyTextAndCheck(
            this.createContext(),
            "",
            0,
            1,
            CURRENCY.getCurrencyCode()
        );
    }

    @Override
    public ReadOnlyCurrencyContext createContext() {
        return ReadOnlyCurrencyContext.with(
            new FakeCurrencyContext() {

            @Override
            public Optional<Currency> currencyForCurrencyCode(final CurrencyCode currencyCode) {
                Objects.requireNonNull(currencyCode, "currencyCode");

                return Optional.of(
                    Currency.getInstance(
                        currencyCode.value()
                    )
                );
            }

            @Override
            public Optional<Currency> currencyForLocale(final Locale locale) {
                Objects.requireNonNull(locale, "locale");

                return Optional.of(
                    Currency.getInstance(locale)
                );
            }

            @Override
            public Optional<String> currencyText(final CurrencyCode currencyCode) {
                Objects.requireNonNull(currencyCode, "currency");

                return Optional.of(
                    "***" +
                        Currency.getInstance(currencyCode.value())
                            .getDisplayName()
                );
            }

            @Override
            public Set<Locale> localesForCurrencyCode(final CurrencyCode currencyCode) {
                Objects.requireNonNull(currencyCode, "currencyCode");

                return currencyCode.value().equalsIgnoreCase("AUD") ?
                        Sets.of(LOCALE) :
                        Sets.empty();
            }

            @Override
            public Set<CurrencyCode> findByCurrencyText(final String text,
                                                        final int offset,
                                                        final int count) {
                Objects.requireNonNull(text, "text");
                if(offset < 0) {
                    throw new IllegalArgumentException("offset " + offset + " < 0");
                }
                if(count < 0) {
                    throw new IllegalArgumentException("count " + count + " < 0");
                }
                return Sets.of(
                    CurrencyCode.fromCurrency(CURRENCY)
                );
            }

                @Override
                public Optional<Number> currencyExchangeRate(final CurrencyExchange currencyExchange,
                                                             final Optional<LocalDateTime> dateTime) {
                    Objects.requireNonNull(currencyExchange, "currencyExchange");
                    Objects.requireNonNull(dateTime, "currency");

                    return Optional.of(2);
                }

            @Override
            public String toString() {
                return this.getClass().getSimpleName();
            }
        }
        );
    }

    // class............................................................................................................

    @Override
    public Class<ReadOnlyCurrencyContext> type() {
        return ReadOnlyCurrencyContext.class;
    }
}
