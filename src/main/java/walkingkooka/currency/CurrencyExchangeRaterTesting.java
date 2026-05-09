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
import walkingkooka.text.printer.TreePrintableTesting;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface CurrencyExchangeRaterTesting extends TreePrintableTesting {

    // currencyExchanges................................................................................................

    default void currencyExchangesAndCheck(final CurrencyExchangeRater rater,
                                           final CurrencyExchange... expected) {
        this.currencyExchangesAndCheck(
            rater,
            Sets.of(expected)
        );
    }

    default void currencyExchangesAndCheck(final CurrencyExchangeRater rater,
                                           final Set<CurrencyExchange> expected) {
        this.checkEquals(
            expected,
            rater.currencyExchanges(),
            rater::toString
        );
    }

    // exchangeRate.....................................................................................................

    default void exchangeRateAndCheck(final CurrencyExchangeRater rater,
                                      final CurrencyExchange currencyExchange) {
        this.exchangeRateAndCheck(
            rater,
            currencyExchange,
            Optional.empty(),
            Optional.empty()
        );
    }

    default void exchangeRateAndCheck(final CurrencyExchangeRater rater,
                                      final CurrencyExchange currencyExchange,
                                      final Number expected) {
        this.exchangeRateAndCheck(
            rater,
            currencyExchange,
            Optional.empty(),
            Optional.of(expected)
        );
    }

    default void exchangeRateAndCheck(final CurrencyExchangeRater rater,
                                      final CurrencyExchange currencyExchange,
                                      final LocalDateTime dateTime,
                                      final Number expected) {
        this.exchangeRateAndCheck(
            rater,
            currencyExchange,
            Optional.of(dateTime),
            Optional.of(expected)
        );
    }

    default void exchangeRateAndCheck(final CurrencyExchangeRater rater,
                                      final CurrencyExchange currencyExchange,
                                      final Optional<LocalDateTime> dateTime,
                                      final Number expected) {
        this.exchangeRateAndCheck(
            rater,
            currencyExchange,
            dateTime,
            Optional.of(expected)
        );
    }

    default void exchangeRateAndCheck(final CurrencyExchangeRater rater,
                                      final CurrencyExchange currencyExchange,
                                      final Optional<LocalDateTime> dateTime,
                                      final Optional<Number> expected) {
        this.checkEquals(
            expected,
            rater.exchangeRate(
                currencyExchange,
                dateTime
            ),
            () -> rater + " exchangeRate " + currencyExchange + " " + dateTime.map(Object::toString)
        );
    }
}
