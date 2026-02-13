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
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public final class CurrencyContextTestingTest implements CurrencyContextTesting,
    ClassTesting<CurrencyContextTesting> {

    // availableCurrencies..............................................................................................

    @Test
    public void testAvailableCurrencies() {
        final Currency currency1 = Currency.getInstance("AUD");
        final Currency currency2 = Currency.getInstance("NZD");

        this.availableCurrenciesAndCheck(
            new FakeCurrencyContext() {
                @Override
                public Set<Currency> availableCurrencies() {
                    return Sets.of(
                        currency1,
                        currency2
                    );
                }
            },
            currency1,
            currency2
        );
    }

    // currencyForLocale................................................................................................

    @Test
    public void testCurrencyForLocale() {
        final Locale locale = Locale.ENGLISH;
        final Currency currency1 = Currency.getInstance("AUD");
        final Currency currency2 = Currency.getInstance("NZD");

        this.currencyForLocaleAndCheck(
            new FakeCurrencyContext() {
                @Override
                public Set<Currency> currencyForLocale(final Locale l) {
                    checkEquals(locale, l, "locale");

                    return Sets.of(
                        currency1,
                        currency2
                    );
                }
            },
            locale,
            currency1,
            currency2
        );
    }

    // findByCurrencyText...............................................................................................

    @Test
    public void testFindByCurrencyText() {
        final String text = "text1";
        final int offset = 1;
        final int count = 23;
        final Currency currency1 = Currency.getInstance("AUD");
        final Currency currency2 = Currency.getInstance("NZD");

        this.findByCurrencyText(
            new FakeCurrencyContext() {
                @Override
                public Set<Currency> findByCurrencyText(final String t,
                                                        final int o,
                                                        final int c) {
                    checkEquals(text, t, "text");
                    checkEquals(offset, o, "offset");
                    checkEquals(count, c, "count");

                    return Sets.of(
                        currency1,
                        currency2
                    );
                }
            },
            text,
            offset,
            count,
            currency1,
            currency2
        );
    }

    // exchangeRate.....................................................................................................

    @Test
    public void testExchangeRate() {
        final Currency from = Currency.getInstance("AUD");
        final Currency to = Currency.getInstance("NZD");
        final Optional<LocalDateTime> dateTime = Optional.of(
            LocalDateTime.now()
        );
        final Number expected = BigDecimal.valueOf(12.5);

        this.exchangeRateAndCheck(
            new FakeCurrencyContext() {
                @Override
                public Number exchangeRate(final Currency f,
                                           final Currency t,
                                           final Optional<LocalDateTime> d) {
                    checkEquals(from, f, "from");
                    checkEquals(to, t, "to");
                    checkEquals(dateTime, d, "dateTime");

                    return expected;
                }
            },
            from,
            to,
            dateTime,
            expected
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyContextTesting> type() {
        return CurrencyContextTesting.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
