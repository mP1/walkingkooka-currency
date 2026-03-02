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

import java.util.Locale;
import java.util.Optional;

/**
 * Supports querying the {@link Locale} for a given {@link String currencyCode}
 */
public interface CanLocaleForCurrencyCode {

    /**
     * Returns the available {@link Locale} for the given code. Not all currency codes have a single {@link Locale}, eg EURO.
     */
    Optional<Locale> localeForCurrencyCode(final String currencyCode);

    /**
     * If the currency code is unknown or invalid an {@link IllegalArgumentException} will be thrown.
     */
    default Locale localeForCurrencyCodeOrFail(final String currencyCode) {
        return this.localeForCurrencyCode(currencyCode)
            .orElseThrow(() -> new MissingCurrencyException(currencyCode));
    }
}
