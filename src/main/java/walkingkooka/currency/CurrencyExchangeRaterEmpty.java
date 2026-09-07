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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link CurrencyExchangeRater} that has no exchange rates and always fails to return {@link CurrencyExchange}.
 */
final class CurrencyExchangeRaterEmpty<C extends CurrencyExchangeRaterContext> implements CurrencyExchangeRater<C> {

    static <C extends CurrencyExchangeRaterContext> CurrencyExchangeRaterEmpty<C> instance() {
        return INSTANCE;
    }

    private final static CurrencyExchangeRaterEmpty INSTANCE = new CurrencyExchangeRaterEmpty<>();

    private CurrencyExchangeRaterEmpty() {
        super();
    }

    // CurrencyExchangeRater............................................................................................

    @Override
    public Set<CurrencyExchange> currencyExchanges(final C context) {
        Objects.requireNonNull(context, "context");

        return Sets.empty();
    }

    @Override
    public Optional<Number> currencyExchangeRate(final CurrencyExchange currencyExchange,
                                                 final Optional<LocalDateTime> dateTime,
                                                 final C context) {
        Objects.requireNonNull(currencyExchange, "currencyExchange");
        Objects.requireNonNull(dateTime, "dateTime");
        Objects.requireNonNull(context, "context");

        return Optional.empty();
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "";
    }
}
