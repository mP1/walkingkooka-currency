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

import walkingkooka.collect.set.Sets;
import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.util.HasCurrencyTesting;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public interface CurrencyContextTesting extends CanCurrencyForCurrencyCodeTesting,
    HasCurrencyTesting,
    TreePrintableTesting {

    // availableCurrencies..............................................................................................

    default void availableCurrenciesAndCheck(final CurrencyContext context,
                                             final Currency... expected) {
        this.availableCurrenciesAndCheck(
            context,
            Sets.of(expected)
        );
    }

    default void availableCurrenciesAndCheck(final CurrencyContext context,
                                             final Set<Currency> expected) {
        this.checkEquals(
            expected,
            context.availableCurrencies(),
            context::toString
        );
    }

    // currencyForLocale................................................................................................

    default void currencyForLocaleAndCheck(final CurrencyContext context,
                                           final Locale locale,
                                           final Currency... expected) {
        this.currencyForLocaleAndCheck(
            context,
            locale,
            Sets.of(expected)
        );
    }

    default void currencyForLocaleAndCheck(final CurrencyContext context,
                                           final Locale locale,
                                           final Set<Currency> expected) {
        this.checkEquals(
            expected,
            context.currencyForLocale(locale)
        );
    }

    // findByCurrencyText...............................................................................................

    default void findByCurrencyTextAndCheck(final CurrencyContext context,
                                            final String text,
                                            final int offset,
                                            final int count,
                                            final Currency... expected) {
        this.findByCurrencyTextAndCheck(
            context,
            text,
            offset,
            count,
            Sets.of(expected)
        );
    }

    default void findByCurrencyTextAndCheck(final CurrencyContext context,
                                            final String text,
                                            final int offset,
                                            final int count,
                                            final Set<Currency> expected) {
        this.checkEquals(
            expected,
            context.findByCurrencyText(
                text,
                offset,
                count
            ),
            () -> "findByCurrencyText text: " + CharSequences.quoteAndEscape(text) + " offset: " + offset + " count: " + count
        );
    }

    // exchangeRateAndCheck.............................................................................................

    default void exchangeRateAndCheck(final CurrencyContext context,
                                      final Currency from,
                                      final Currency to,
                                      final LocalDateTime dateTime,
                                      final Number expected) {
        this.exchangeRateAndCheck(
            context,
            from,
            to,
            Optional.of(dateTime),
            expected
        );
    }

    default void exchangeRateAndCheck(final CurrencyContext context,
                                      final Currency from,
                                      final Currency to,
                                      final Optional<LocalDateTime> dateTime,
                                      final Number expected) {
        this.checkEquals(
            context.exchangeRate(
                from,
                to,
                dateTime
            ),
            expected
        );
    }
}
