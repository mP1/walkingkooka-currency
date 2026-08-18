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

import java.util.Set;

public interface CanCurrencyExchangesTesting extends TreePrintableTesting {

    default void currencyExchangesAndCheck(final CanCurrencyExchanges can,
                                           final CurrencyExchange... expected) {
        this.currencyExchangesAndCheck(
            can,
            Sets.of(expected)
        );
    }

    default void currencyExchangesAndCheck(final CanCurrencyExchanges can,
                                           final Set<CurrencyExchange> expected) {
        this.checkEquals(
            expected,
            can.currencyExchanges(),
            can::toString
        );
    }
}
