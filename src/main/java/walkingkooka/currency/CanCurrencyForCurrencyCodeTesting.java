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

import java.util.Currency;
import java.util.Optional;

public interface CanCurrencyForCurrencyCodeTesting extends TreePrintableTesting {

    // currencyForCurrencyCode..........................................................................................

    default void currencyForCurrencyCodeAndCheck(final CanCurrencyForCurrencyCode can,
                                                 final CurrencyCode currencyCode) {
        this.currencyForCurrencyCodeAndCheck(
            can,
            currencyCode,
            Optional.empty()
        );
    }

    default void currencyForCurrencyCodeAndCheck(final CanCurrencyForCurrencyCode can,
                                                 final CurrencyCode currencyCode,
                                                 final Currency expected) {
        this.currencyForCurrencyCodeAndCheck(
            can,
            currencyCode,
            Optional.of(expected)
        );
    }

    default void currencyForCurrencyCodeAndCheck(final CanCurrencyForCurrencyCode can,
                                                 final CurrencyCode currencyCode,
                                                 final Optional<Currency> expected) {
        this.checkEquals(
            expected,
            can.currencyForCurrencyCode(currencyCode)
        );
    }
}
