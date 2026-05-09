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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.Optional;
import java.util.Set;

public interface CurrencyContextTesting extends CanCurrencyForCurrencyCodeTesting,
    CanCurrencyForLocaleTesting,
    CanLocalesForCurrencyCodeTesting,
    CurrencyExchangeRaterTesting,
    HasCurrencyTesting,
    TreePrintableTesting {

    // setCurrency....................................................................................................

    default void setCurrencyAndCheck(final CurrencyContext context,
                                     final Currency currency) {
        context.setCurrency(currency);

        this.currencyAndCheck(
            context,
            currency
        );
    }

    // availableCurrencies..............................................................................................

    default void availableCurrenciesAndCheck(final CurrencyContext context,
                                             final String... expected) {
        this.availableCurrenciesAndCheck(
            context,
            Arrays.stream(expected)
                .map(CurrencyCode::parse)
                .toArray(CurrencyCode[]::new)
        );
    }

    default void availableCurrenciesAndCheck(final CurrencyContext context,
                                             final CurrencyCode... expected) {
        this.availableCurrenciesAndCheck(
            context,
            Sets.of(expected)
        );
    }

    default void availableCurrenciesAndCheck(final CurrencyContext context,
                                             final Set<CurrencyCode> expected) {
        this.checkEquals(
            expected,
            context.availableCurrencies(),
            context::toString
        );
    }

    // currencyText.....................................................................................................

    default void currencyTextAndCheck(final CurrencyContext context,
                                      final String currency) {
        this.currencyTextAndCheck(
            context,
            CurrencyCode.parse(currency),
            Optional.empty()
        );
    }

    default void currencyTextAndCheck(final CurrencyContext context,
                                      final CurrencyCode currency) {
        this.currencyTextAndCheck(
            context,
            currency,
            Optional.empty()
        );
    }

    default void currencyTextAndCheck(final CurrencyContext context,
                                      final String currency,
                                      final String expected) {
        this.currencyTextAndCheck(
            context,
            CurrencyCode.parse(currency),
            Optional.of(expected)
        );
    }

    default void currencyTextAndCheck(final CurrencyContext context,
                                      final CurrencyCode currency,
                                      final String expected) {
        this.currencyTextAndCheck(
            context,
            currency,
            Optional.of(expected)
        );
    }

    default void currencyTextAndCheck(final CurrencyContext context,
                                      final CurrencyCode currency,
                                      final Optional<String> expected) {
        this.checkEquals(
            expected,
            context.currencyText(currency)
        );
    }
    
    // findByCurrencyText...............................................................................................

    default void findByCurrencyTextAndCheck(final CurrencyContext context,
                                            final String text,
                                            final int offset,
                                            final int count,
                                            final String... expected) {
        this.findByCurrencyTextAndCheck(
            context,
            text,
            offset,
            count,
            Arrays.stream(expected)
                .map(CurrencyCode::parse)
                .toArray(CurrencyCode[]::new)
        );
    }

    default void findByCurrencyTextAndCheck(final CurrencyContext context,
                                            final String text,
                                            final int offset,
                                            final int count,
                                            final CurrencyCode... expected) {
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
                                            final Set<CurrencyCode> expected) {
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
                                      final CurrencyExchange currencyExchange,
                                      final LocalDateTime dateTime,
                                      final Number expected) {
        this.exchangeRateAndCheck(
            context,
            currencyExchange,
            Optional.of(dateTime),
            Optional.of(expected)
        );
    }

    default void exchangeRateAndCheck(final CurrencyContext context,
                                      final CurrencyExchange currencyExchange,
                                      final Optional<LocalDateTime> dateTime,
                                      final Optional<Number> expected) {
        this.checkEquals(
            context.exchangeRate(
                currencyExchange,
                dateTime
            ),
            expected
        );
    }
}
