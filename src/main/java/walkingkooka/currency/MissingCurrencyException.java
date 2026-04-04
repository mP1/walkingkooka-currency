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

import walkingkooka.text.CharSequences;

import java.util.Objects;

/**
 * The exception that should be thrown when a {@link CurrencyCode} currency code is not found by all {@link CanCurrencyForCurrencyCode}.
 */
public final class MissingCurrencyException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public MissingCurrencyException(final CurrencyCode currencyCode) {
        super(
            "Missing currency code " +
                CharSequences.quoteAndEscape(
                    Objects.requireNonNull(currencyCode, "currencyCode")
                        .value()
                )
        );
        this.currencyCode = currencyCode;
    }

    public CurrencyCode currencyCode() {
        return this.currencyCode;
    }

    private final CurrencyCode currencyCode;

    // hashCode/equals..................................................................................................

    @Override
    public int hashCode() {
        return this.currencyCode.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof MissingCurrencyException && this.equals0((MissingCurrencyException) other);
    }

    private boolean equals0(final MissingCurrencyException other) {
        return this.currencyCode.equals(other.currencyCode);
    }
}
