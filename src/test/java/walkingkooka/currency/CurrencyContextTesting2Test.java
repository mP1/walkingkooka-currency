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
import walkingkooka.currency.CurrencyContextTesting2Test.TestCurrencyContext;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class CurrencyContextTesting2Test implements CurrencyContextTesting2<TestCurrencyContext>  {

    @Override
    public TestCurrencyContext createContext() {
        return new TestCurrencyContext();
    }

    @Override
    public Class<TestCurrencyContext> type() {
        return TestCurrencyContext.class;
    }

    final static class TestCurrencyContext extends FakeCurrencyContext {

        @Override
        public void setCurrency(final Currency currency) {
            Objects.requireNonNull(currency, "currency");
        }

        @Override
        public Optional<Currency> currencyForCurrencyCode(final String currencyCode) {
            Objects.requireNonNull(currencyCode, "currencyCode");

            return Optional.of(
                Currency.getInstance(
                    Locale.forLanguageTag("en-AU")
                )
            );
        }

        @Override
        public Set<Currency> currencyForLocale(final Locale locale) {
            Objects.requireNonNull(locale, "locale");

            return Sets.of(
                Currency.getInstance(
                    Locale.forLanguageTag("en-AU")
                )
            );
        }

        @Override
        public Set<Currency> findByCurrencyText(final String text,
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
                Currency.getInstance(
                    Locale.forLanguageTag("en-AU")
                )
            );
        }

        @Override
        public Number exchangeRate(final Currency from,
                                   final Currency to,
                                   final Optional<LocalDateTime> dateTime) {
            Objects.requireNonNull(from, "currency");
            Objects.requireNonNull(to, "currency");
            Objects.requireNonNull(dateTime, "currency");

            return 1;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
