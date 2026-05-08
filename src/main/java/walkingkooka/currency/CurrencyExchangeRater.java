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
import java.util.Optional;

public interface CurrencyExchangeRater {

    Optional<Number> exchangeRate(final CurrencyCode from,
                                  final CurrencyCode to,
                                  final Optional<LocalDateTime> dateTime);

    default Number exchangeRateOrFail(final CurrencyCode from,
                                      final CurrencyCode to,
                                      final Optional<LocalDateTime> dateTime) {
        return this.exchangeRate(
            from,
            to,
            dateTime
        ).orElseThrow(() -> new IllegalArgumentException(
            "Invalid exchange rate " +
                CharSequences.quote(from.value()) +
                " to " +
                CharSequences.quote(from.value()) +
                (
                    dateTime.map(dt -> " " + dt)
                )
            )
        );
    }
}
