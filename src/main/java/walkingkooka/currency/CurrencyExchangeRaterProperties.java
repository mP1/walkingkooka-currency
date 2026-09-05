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

import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.Sets;
import walkingkooka.props.HasProperties;
import walkingkooka.props.Properties;
import walkingkooka.props.PropertiesPath;
import walkingkooka.text.CharSequences;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * Wraps a {@link Properties} where multiple exchange rates are defined.
 * <pre>
 * AUD-NZD=1.1
 * NZD-AUD=0.9
 * </pre>
 */
final class CurrencyExchangeRaterProperties<C extends CurrencyExchangeRaterContext> implements CurrencyExchangeRater<C>,
    HasProperties {

    static <C extends CurrencyExchangeRaterContext> CurrencyExchangeRaterProperties<C> with(final Properties properties,
                                                                                            final Function<String, Number> numberParser) {
        return new CurrencyExchangeRaterProperties<>(
            Objects.requireNonNull(properties, "properties"),
            Objects.requireNonNull(numberParser, "numberParser")
        );
    }

    private CurrencyExchangeRaterProperties(final Properties properties,
                                            final Function<String, Number> numberParser) {
        super();
        this.properties = properties;

        final Map<CurrencyExchange, Number> currencyExchangeToRate = Maps.hash();

        for (final Entry<PropertiesPath, String> entry : properties.entries()) {
            PropertiesPath propertiesPath = entry.getKey();

            final String value = propertiesPath.value();

            final int separator = value.indexOf('-');
            if (-1 == separator) {
                throw new IllegalArgumentException("Properties: Invalid currency exchange rate " + CharSequences.quoteIfChars(value));
            }

            final CurrencyCode from = CurrencyCode.parse(
                value.substring(
                    0,
                    separator
                )
            );

            final CurrencyCode to = CurrencyCode.parse(
                value.substring(
                    separator + 1
                )
            );

            currencyExchangeToRate.put(
                CurrencyExchange.with(
                    from,
                    to
                ),
                numberParser.apply(
                    entry.getValue()
                )
            );
        }

        this.currencyExchangeToRate = currencyExchangeToRate;
    }

    private final Properties properties;

    // HasProperties....................................................................................................

    @Override
    public Properties properties() {
        return this.properties;
    }

    // CurrencyExchangeRater............................................................................................

    @Override
    public Set<CurrencyExchange> currencyExchanges(final C context) {
        Objects.requireNonNull(context, "context");

        return Sets.readOnly(
            this.currencyExchangeToRate.keySet()
        );
    }

    @Override
    public Optional<Number> currencyExchangeRate(final CurrencyExchange currencyExchange,
                                                 final Optional<LocalDateTime> dateTime,
                                                 final C context) {
        Objects.requireNonNull(currencyExchange, "currencyExchange");
        Objects.requireNonNull(dateTime, "dateTime");
        Objects.requireNonNull(context, "context");

        return Optional.ofNullable(
            this.currencyExchangeToRate.get(currencyExchange)
        );
    }

    private final Map<CurrencyExchange, Number> currencyExchangeToRate;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.currencyExchangeToRate.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            (other instanceof CurrencyExchangeRaterProperties &&
                this.equals0((CurrencyExchangeRaterProperties<?>) other));
    }

    private boolean equals0(final CurrencyExchangeRaterProperties<?> other) {
        return this.currencyExchangeToRate.equals(other.currencyExchangeToRate);
    }

    @Override
    public String toString() {
        return this.properties.toString();
    }
}
