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

import walkingkooka.props.HasProperties;
import walkingkooka.props.Properties;
import walkingkooka.props.PropertiesPath;
import walkingkooka.text.CharSequences;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Wraps a {@link Properties} where multiple exchange rates are defined.
 * <pre>
 * AUD-NZD=1.1
 * </pre>
 */
public final class ExchangeRate implements CurrencyExchangeRater,
    HasProperties {

    public static ExchangeRate fromProperties(final Properties properties,
                                              final BiFunction<String, Boolean, Number> numberParser) {
        return new ExchangeRate(
            Objects.requireNonNull(properties, "properties"),
            Objects.requireNonNull(numberParser, "numberParser")
        );
    }

    private ExchangeRate(final Properties properties,
                         final BiFunction<String, Boolean, Number> numberParser) {
        super();
        this.properties = properties;
        this.numberParser = numberParser;
    }

    private final Properties properties;

    // HasProperties....................................................................................................

    @Override
    public Properties properties() {
        return this.properties;
    }

    // CurrencyExchangeRater..........................................................................................

    @Override
    public Number exchangeRate(final CurrencyCode from,
                               final CurrencyCode to,
                               final Optional<LocalDateTime> dateTime) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(dateTime, "dateTime");

        final String fromCurrencyCode = from.value();
        final String toCurrencyCode = to.value();

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
                .orElseThrow(() -> UnsupportedCurrencyExchangeException.with(
                        from,
                        to,
                        dateTime
                    )
                );

            invert = true;
        }

        try {
            return this.numberParser.apply(
                valueOrNull,
                invert
            );
        } catch (final RuntimeException invalid) {
            throw new IllegalArgumentException("Invalid exchange rate " + CharSequences.quoteAndEscape(valueOrNull), invalid);
        }
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
            (other instanceof ExchangeRate &&
                this.equals0((ExchangeRate) other));
    }

    private boolean equals0(final ExchangeRate other) {
        return this.properties.equals(other.properties) &&
            this.numberParser.equals(other.numberParser);
    }

    @Override
    public String toString() {
        return this.properties.toString();
    }
}
