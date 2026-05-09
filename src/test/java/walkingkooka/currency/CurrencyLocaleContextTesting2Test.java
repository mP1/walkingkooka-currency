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
import walkingkooka.currency.CurrencyLocaleContextTesting2Test.TestCurrencyLocaleContext;
import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContextDelegator;
import walkingkooka.locale.LocaleContexts;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class CurrencyLocaleContextTesting2Test implements CurrencyLocaleContextTesting2<TestCurrencyLocaleContext> {

    @Test
    public void testCurrencyText() {
        final Currency currency = Currency.getInstance("AUD");

        this.currencyTextAndCheck(
            this.createContext(),
            currency.getCurrencyCode(),
            currency.getDisplayName()
        );
    }

    @Override
    public TestCurrencyLocaleContext createContext() {
        return new TestCurrencyLocaleContext();
    }

    @Override
    public Class<TestCurrencyLocaleContext> type() {
        return TestCurrencyLocaleContext.class;
    }

    final static class TestCurrencyLocaleContext implements CurrencyLocaleContext,
        CurrencyContextDelegator,
        LocaleContextDelegator {

        @Override
        public CurrencyContext currencyContext() {
            return CurrencyContexts.jre(
                Currency.getInstance("AUD"),
                new FakeCurrencyExchangeRater() {
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
                    public Optional<Number> exchangeRate(final CurrencyExchange currencyExchange,
                                                         final Optional<LocalDateTime> dateTime) {
                        Objects.requireNonNull(currencyExchange, "currencyExchange");
                        Objects.requireNonNull(dateTime, "dateTime");

                        return Optional.of(2);
                    }
                },
                this.localeContext()
            );
        }

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
