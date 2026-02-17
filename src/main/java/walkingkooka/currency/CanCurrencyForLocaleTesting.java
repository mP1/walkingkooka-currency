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
import java.util.Locale;
import java.util.Optional;

public interface CanCurrencyForLocaleTesting extends TreePrintableTesting {

    // currencyForLocale................................................................................................

    default void currencyForLocaleAndCheck(final CanCurrencyForLocale can,
                                           final Locale locale) {
        this.currencyForLocaleAndCheck(
            can,
            locale,
            Optional.empty()
        );
    }

    default void currencyForLocaleAndCheck(final CanCurrencyForLocale can,
                                           final Locale locale,
                                           final Currency expected) {
        this.currencyForLocaleAndCheck(
            can,
            locale,
            Optional.of(expected)
        );
    }

    default void currencyForLocaleAndCheck(final CanCurrencyForLocale can,
                                           final Locale locale,
                                           final Optional<Currency> expected) {
        this.checkEquals(
            expected,
            can.currencyForLocale(locale)
        );
    }
}
