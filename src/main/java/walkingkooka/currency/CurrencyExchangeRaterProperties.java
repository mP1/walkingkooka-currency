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

import walkingkooka.collect.set.ImmutableSet;
import walkingkooka.props.HasProperties;
import walkingkooka.props.Properties;
import walkingkooka.props.PropertiesPath;
import walkingkooka.text.CharSequences;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Wraps a {@link Properties} where multiple exchange rates are defined.
 * <pre>
 * AUD-NZD=1.1
 * </pre>
 */
final class CurrencyExchangeRaterProperties implements CurrencyExchangeRater,
    HasProperties {

    static CurrencyExchangeRaterProperties with(final Properties properties,
                                                final BiFunction<String, Boolean, Number> numberParser) {
        return new CurrencyExchangeRaterProperties(
            Objects.requireNonNull(properties, "properties"),
            Objects.requireNonNull(numberParser, "numberParser")
        );
    }

    private CurrencyExchangeRaterProperties(final Properties properties,
                                            final BiFunction<String, Boolean, Number> numberParser) {
        super();
        this.properties = properties;
        this.numberParser = numberParser;

        this.currencyExchanges = properties.keys()
            .stream()
            .flatMap((PropertiesPath propertiesPath) -> {
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

                final CurrencyExchange currencyExchange = CurrencyExchange.with(
                    from,
                    to
                );

                return Stream.of(
                    currencyExchange,
                    currencyExchange.swap()
                );
            }).collect(
                ImmutableSet.collector()
            );
    }

    private final Properties properties;

    // HasProperties....................................................................................................

    @Override
    public Properties properties() {
        return this.properties;
    }

    // CurrencyExchangeRater............................................................................................

    @Override
    public Set<CurrencyExchange> currencyExchanges() {
        return this.currencyExchanges;
    }

    private final Set<CurrencyExchange> currencyExchanges;

    @Override
    public Optional<Number> currencyExchangeRate(final CurrencyExchange currencyExchange,
                                                 final Optional<LocalDateTime> dateTime) {
        Objects.requireNonNull(currencyExchange, "currencyExchange");
        Objects.requireNonNull(dateTime, "dateTime");

        final String fromCurrencyCode = currencyExchange.from()
            .value();
        final String toCurrencyCode = currencyExchange.to()
            .value();

        final Properties properties = this.properties;

        boolean invert = false;

        PropertiesPath key = PropertiesPath.parse(
            fromCurrencyCode +
                "-" +
                toCurrencyCode
        );

        String valueOrNull = properties.get(key)
            .orElse(null);

        if (null == valueOrNull) {
            key = PropertiesPath.parse(
                toCurrencyCode +
                    "-" +
                    fromCurrencyCode
            );

            valueOrNull = properties.get(key)
                .orElse(null);

            invert = true;
        }

        final Number number;

        if (null != valueOrNull) {
            try {
                number = this.numberParser.apply(
                    valueOrNull,
                    invert
                );
            } catch (final RuntimeException invalid) {
                throw new IllegalArgumentException("Invalid exchange rate " + CharSequences.quoteAndEscape(valueOrNull), invalid);
            }
        } else {
            number = null;
        }

        return Optional.ofNullable(number);
    }

    private final BiFunction<String, Boolean, Number> numberParser;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.properties,
            this.numberParser
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            (other instanceof CurrencyExchangeRaterProperties &&
                this.equals0((CurrencyExchangeRaterProperties) other));
    }

    private boolean equals0(final CurrencyExchangeRaterProperties other) {
        return this.properties.equals(other.properties) &&
            this.numberParser.equals(other.numberParser);
    }

    @Override
    public String toString() {
        return this.properties.toString();
    }
}
