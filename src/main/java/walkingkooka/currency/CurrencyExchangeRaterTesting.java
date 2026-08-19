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
import walkingkooka.datetime.HasNowTesting;
import walkingkooka.text.printer.TreePrintableTesting;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface CurrencyExchangeRaterTesting extends HasNowTesting,
    TreePrintableTesting {

    Optional<LocalDateTime> NO_DATE_TIME = Optional.empty();

    Optional<LocalDateTime> DATE_TIME = Optional.of(NOW);

    // currencyExchanges................................................................................................

    default <C extends CurrencyExchangeRaterContext> void currencyExchangesAndCheck(final CurrencyExchangeRater<C> rater,
                                                                                    final C context,
                                                                                    final CurrencyExchange... expected) {
        this.currencyExchangesAndCheck(
            rater,
            context,
            Sets.of(expected)
        );
    }

    default <C extends CurrencyExchangeRaterContext> void currencyExchangesAndCheck(final CurrencyExchangeRater<C> rater,
                                                                                    final C context,
                                                                                    final Set<CurrencyExchange> expected) {
        this.checkEquals(
            expected,
            rater.currencyExchanges(context),
            rater::toString
        );
    }

    // currencyExchangeRate.............................................................................................

    default <C extends CurrencyExchangeRaterContext> void currencyExchangeRateAndCheck(final CurrencyExchangeRater<C> rater,
                                                                                       final CurrencyExchange currencyExchange,
                                                                                       final C context) {
        this.currencyExchangeRateAndCheck(
            rater,
            currencyExchange,
            NO_DATE_TIME,
            context,
            Optional.empty()
        );
    }

    default <C extends CurrencyExchangeRaterContext> void currencyExchangeRateAndCheck(final CurrencyExchangeRater<C> rater,
                                                                                       final CurrencyExchange currencyExchange,
                                                                                       final C context,
                                                                                       final Number expected) {
        this.currencyExchangeRateAndCheck(
            rater,
            currencyExchange,
            NO_DATE_TIME,
            context,
            Optional.of(expected)
        );
    }

    default <C extends CurrencyExchangeRaterContext> void currencyExchangeRateAndCheck(final CurrencyExchangeRater<C> rater,
                                                                                       final CurrencyExchange currencyExchange,
                                                                                       final C context,
                                                                                       final LocalDateTime dateTime,
                                                                                       final Number expected) {
        this.currencyExchangeRateAndCheck(
            rater,
            currencyExchange,
            Optional.of(dateTime),
            context,
            Optional.of(expected)
        );
    }

    default <C extends CurrencyExchangeRaterContext> void currencyExchangeRateAndCheck(final CurrencyExchangeRater<C> rater,
                                                                                       final CurrencyExchange currencyExchange,
                                                                                       final Optional<LocalDateTime> dateTime,
                                                                                       final C context,
                                                                                       final Number expected) {
        this.currencyExchangeRateAndCheck(
            rater,
            currencyExchange,
            dateTime,
            context,
            Optional.of(expected)
        );
    }

    default <C extends CurrencyExchangeRaterContext> void currencyExchangeRateAndCheck(final CurrencyExchangeRater<C> rater,
                                                                                       final CurrencyExchange currencyExchange,
                                                                                       final Optional<LocalDateTime> dateTime,
                                                                                       final C context,
                                                                                       final Optional<Number> expected) {
        this.checkEquals(
            expected,
            rater.currencyExchangeRate(
                currencyExchange,
                dateTime,
                context
            ),
            () -> rater + " currencyExchangeRate " + currencyExchange + " " + dateTime.map(Object::toString)
        );
    }
}
