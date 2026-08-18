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

import walkingkooka.text.printer.TreePrintableTesting;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CanCurrencyExchangeRateTesting extends TreePrintableTesting {

    default void currencyExchangeRateAndCheck(final CanCurrencyExchangeRate can,
                                              final CurrencyExchange currencyExchange) {
        this.currencyExchangeRateAndCheck(
            can,
            currencyExchange,
            Optional.empty(),
            Optional.empty()
        );
    }

    default void currencyExchangeRateAndCheck(final CanCurrencyExchangeRate can,
                                              final CurrencyExchange currencyExchange,
                                              final Number expected) {
        this.currencyExchangeRateAndCheck(
            can,
            currencyExchange,
            Optional.empty(),
            Optional.of(expected)
        );
    }

    default void currencyExchangeRateAndCheck(final CanCurrencyExchangeRate can,
                                              final CurrencyExchange currencyExchange,
                                              final LocalDateTime dateTime,
                                              final Number expected) {
        this.currencyExchangeRateAndCheck(
            can,
            currencyExchange,
            Optional.of(dateTime),
            Optional.of(expected)
        );
    }

    default void currencyExchangeRateAndCheck(final CanCurrencyExchangeRate can,
                                              final CurrencyExchange currencyExchange,
                                              final Optional<LocalDateTime> dateTime,
                                              final Number expected) {
        this.currencyExchangeRateAndCheck(
            can,
            currencyExchange,
            dateTime,
            Optional.of(expected)
        );
    }

    default void currencyExchangeRateAndCheck(final CanCurrencyExchangeRate can,
                                              final CurrencyExchange currencyExchange,
                                              final Optional<LocalDateTime> dateTime,
                                              final Optional<Number> expected) {
        this.checkEquals(
            expected,
            can.currencyExchangeRate(
                currencyExchange,
                dateTime
            ),
            () -> can + " currencyExchangeRate " + currencyExchange + " " + dateTime.map(Object::toString)
        );
    }
}
