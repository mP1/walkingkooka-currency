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
import walkingkooka.currency.CurrencyLocaleContextDelegatorTest.TestCurrencyLocaleContextDelegator;
import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContextDelegator;
import walkingkooka.locale.LocaleContexts;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class CurrencyLocaleContextDelegatorTest implements CurrencyLocaleContextTesting2<TestCurrencyLocaleContextDelegator> {

    private final static Locale LOCALE = Locale.forLanguageTag("en-AU");

    private final static String CURRENCY_CODE = "AUD";

    @Test
    public void testCurrencyText() {
        final Currency currency = Currency.getInstance(CURRENCY_CODE);

        this.currencyTextAndCheck(
            this.createContext(),
            CURRENCY_CODE,
            "***" + currency.getDisplayName()
        );
    }

    @Test
    public void testLocaleForCurrencyCode() {
        this.localesForCurrencyCodeAndCheck(
            this.createContext(),
            CurrencyCode.parse("AUD"),
            LOCALE
        );
    }

    @Override
    public TestCurrencyLocaleContextDelegator createContext() {
        return new TestCurrencyLocaleContextDelegator();
    }

    @Override
    public Class<TestCurrencyLocaleContextDelegator> type() {
        return TestCurrencyLocaleContextDelegator.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    final static class TestCurrencyLocaleContextDelegator implements CurrencyLocaleContextDelegator {

        TestCurrencyLocaleContextDelegator() {
            super();
        }

        @Override
        public CurrencyLocaleContext currencyLocaleContext() {
            return this.context;
        }

        private final TestCurrencyLocaleContext context = new TestCurrencyLocaleContext();

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    final static class TestCurrencyLocaleContext extends FakeCurrencyContext implements CurrencyLocaleContext, LocaleContextDelegator {

        @Override
        public void setCurrency(final Currency currency) {
            Objects.requireNonNull(currency, "currency");
        }

        @Override
        public Optional<Currency> currencyForCurrencyCode(final CurrencyCode currencyCode) {
            Objects.requireNonNull(currencyCode, "currencyCode");

            return Optional.of(
                Currency.getInstance(LOCALE)
            );
        }

        @Override
        public Optional<Currency> currencyForLocale(final Locale locale) {
            Objects.requireNonNull(locale, "locale");

            return Optional.of(
                Currency.getInstance(
                    Locale.forLanguageTag("en-AU")
                )
            );
        }

        @Override
        public Optional<String> currencyText(final CurrencyCode currency) {
            Objects.requireNonNull(currency, "currency");

            return Optional.of(
                "***" +
                    Currency.getInstance(currency.value())
                        .getDisplayName()
            );
        }

        @Override
        public Set<Locale> localesForCurrencyCode(final CurrencyCode currencyCode) {
            Objects.requireNonNull(currencyCode, "currencyCode");

            return currencyCode.value().equalsIgnoreCase(CURRENCY_CODE) ?
                Sets.of(LOCALE) :
                Sets.empty();
        }

        @Override
        public Set<CurrencyCode> findByCurrencyText(final String text,
                                                    final int offset,
                                                    final int count) {
            Objects.requireNonNull(text, "text");
            if (offset < 0) {
                throw new IllegalArgumentException("offset " + offset + " < 0");
            }
            if (count < 0) {
                throw new IllegalArgumentException("count " + count + " < 0");
            }
            return Sets.of(
                CurrencyCode.fromCurrency(
                    Currency.getInstance(
                        Locale.forLanguageTag("en-AU")
                    )
                )
            );
        }

        @Override
        public Optional<Number> exchangeRate(final CurrencyCode from,
                                             final CurrencyCode to,
                                             final Optional<LocalDateTime> dateTime) {
            Objects.requireNonNull(from, "currency");
            Objects.requireNonNull(to, "currency");
            Objects.requireNonNull(dateTime, "currency");

            return Optional.of(1);
        }

        // LocaleContext................................................................................................

        @Override
        public LocaleContext localeContext() {
            return LocaleContexts.jre(
                Locale.forLanguageTag("en-AU")
            );
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
