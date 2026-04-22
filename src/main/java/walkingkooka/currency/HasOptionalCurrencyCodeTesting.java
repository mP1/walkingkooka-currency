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

import java.util.Optional;

public interface HasOptionalCurrencyCodeTesting extends TreePrintableTesting {

    default void currencyCodeAndCheck(final HasOptionalCurrencyCode has) {
        this.currencyCodeAndCheck(
            has,
            Optional.empty()
        );
    }

    default void currencyCodeAndCheck(final HasOptionalCurrencyCode has,
                                      final CurrencyCode expected) {
        this.currencyCodeAndCheck(
            has,
            Optional.of(expected)
        );
    }

    default void currencyCodeAndCheck(final HasOptionalCurrencyCode has,
                                      final Optional<CurrencyCode> expected) {
        this.checkEquals(
            expected,
            has.currencyCode(),
            () -> has + " currencyCode()"
        );
    }
}
