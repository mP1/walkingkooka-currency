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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public final class UnsupportedCurrencyExchangeException extends IllegalArgumentException {

    public static UnsupportedCurrencyExchangeException with(final CurrencyCode from,
                                                            final CurrencyCode to,
                                                            final Optional<LocalDateTime> dateTime) {
        return new UnsupportedCurrencyExchangeException(
            Objects.requireNonNull(from, "from"),
            Objects.requireNonNull(to, "to"),
            Objects.requireNonNull(dateTime, "dateTime")
        );
    }

    private UnsupportedCurrencyExchangeException(final CurrencyCode from,
                                                 final CurrencyCode to,
                                                 final Optional<LocalDateTime> dateTime) {
        super("");

        this.from = from;
        this.to = to;
        this.dateTime = dateTime;
    }

    @Override
    public String getMessage() {
        final LocalDateTime dateTime = this.dateTime
            .orElse(null);

        return "Unsupported currency exchange " +
            quote(this.from) +
            " to " +
            quote(this.to) +
            (null != dateTime ?
                " " + dateTime :
                ""
            );
    }

    private static CharSequence quote(final CurrencyCode currencyCode) {
        return CharSequences.quote(currencyCode.value());
    }

    public CurrencyCode from() {
        return this.from;
    }

    private final CurrencyCode from;

    public CurrencyCode to() {
        return this.to;
    }

    private final CurrencyCode to;

    public Optional<LocalDateTime> dateTime() {
        return this.dateTime;
    }

    private final Optional<LocalDateTime> dateTime;
}
