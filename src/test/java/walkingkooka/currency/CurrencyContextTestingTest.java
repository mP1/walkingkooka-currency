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
import walkingkooka.HasValueTesting;
import walkingkooka.collect.set.Sets;
import walkingkooka.reflect.PublicClassTesting;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class CurrencyContextTestingTest implements CurrencyContextTesting,
    PublicClassTesting<CurrencyContextTesting>,
    HasCurrencyCodeTesting,
    HasValueTesting {


    @Test
    public void testCurrencyContextConstantsToString() {
        this.checkEquals(
            CURRENCY_CONTEXT.toString(),
            "JRE"
        );
    }

    @Test
    public void testConstantsDifferent() {
        this.checkNotEquals(
            CURRENCY_CONTEXT,
            DIFFERENT_CURRENCY_CONTEXT
        );
    }

    // setCurrency......................................................................................................

    @Test
    public void testSetCurrency() {
        final CurrencyContext context = new FakeCurrencyContext() {
            @Override
            public Currency currency() {
                return this.currency;
            }

            @Override
            public void setCurrency(final Currency currency) {
                Objects.requireNonNull(currency, "currency");
                this.currency = currency;
            }

            private Currency currency = CURRENCY;
        };

        this.setCurrencyAndCheck(
            context,
            DIFFERENT_CURRENCY
        );
    }

    // availableCurrencies..............................................................................................

    @Test
    public void testAvailableCurrencies() {
        this.availableCurrenciesAndCheck(
            new FakeCurrencyContext() {
                @Override
                public Set<CurrencyCode> availableCurrencies() {
                    return Sets.of(
                        CURRENCY_CODE,
                        DIFFERENT_CURRENCY_CODE
                    );
                }
            },
            CURRENCY_CODE.value(),
            DIFFERENT_CURRENCY_CODE.value()
        );
    }

    // currencyForCurrencyCode..........................................................................................

    @Test
    public void testCurrencyForCurrencyCode() {
        this.currencyForCurrencyCodeAndCheck(
            new FakeCurrencyContext() {
                @Override
                public Optional<Currency> currencyForCurrencyCode(final CurrencyCode c) {
                    valueAndCheck(
                        c,
                        CURRENCY.getCurrencyCode()
                    );

                    return OPTIONAL_CURRENCY;
                }
            },
            CURRENCY_CODE,
            CURRENCY
        );
    }

    // currencyForLocale................................................................................................

    @Test
    public void testCurrencyForLocale() {
        this.currencyForLocaleAndCheck(
            new FakeCurrencyContext() {
                @Override
                public Optional<Currency> currencyForLocale(final Locale l) {
                    checkEquals(LOCALE, l, "locale");

                    return OPTIONAL_CURRENCY;
                }
            },
            LOCALE,
            CURRENCY
        );
    }

    // findCurrencyByText...............................................................................................

    @Test
    public void testFindCurrencyByTextAndCheck() {
        final String text = "text1";
        final int offset = 1;
        final int count = 23;

        this.findCurrencyByTextAndCheck(
            new FakeCurrencyContext() {
                @Override
                public Set<CurrencyCode> findCurrencyByText(final String t,
                                                            final int o,
                                                            final int c) {
                    checkEquals(text, t, "text");
                    checkEquals(offset, o, "offset");
                    checkEquals(count, c, "count");

                    return Sets.of(
                        CURRENCY_CODE,
                        DIFFERENT_CURRENCY_CODE
                    );
                }
            },
            text,
            offset,
            count,
            CURRENCY_CODE.value(),
            DIFFERENT_CURRENCY_CODE.value()
        );
    }

    // currencyExchangeRate.....................................................................................................

    @Test
    public void testCurrencyExchangeRate() {
        final Optional<LocalDateTime> dateTime = Optional.of(
            LocalDateTime.now()
        );
        final Number expected = BigDecimal.valueOf(12.5);

        this.currencyExchangeRateAndCheck(
            new FakeCurrencyContext() {
                @Override
                public Optional<Number> currencyExchangeRate(final CurrencyExchange ce,
                                                             final Optional<LocalDateTime> d) {
                    checkEquals(CURRENCY_EXCHANGE, ce, "currencyExchange");
                    checkEquals(dateTime, d, "dateTime");

                    return Optional.of(expected);
                }
            },
            CURRENCY_EXCHANGE,
            dateTime,
            expected
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyContextTesting> type() {
        return CurrencyContextTesting.class;
    }
}
