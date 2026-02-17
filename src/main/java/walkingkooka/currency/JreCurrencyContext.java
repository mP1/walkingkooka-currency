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
import walkingkooka.collect.set.Sets;
import walkingkooka.util.HasLocale;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

final class JreCurrencyContext implements CurrencyContext {

    static JreCurrencyContext with(final Currency currency,
                                   final BiFunction<Currency, Currency, Number> exchangeRates,
                                   final HasLocale hasLocale) {
        return new JreCurrencyContext(
            Objects.requireNonNull(currency, "currency"),
            Objects.requireNonNull(exchangeRates, "exchangeRates"),
            Objects.requireNonNull(hasLocale, "hasLocale")
        );
    }

    private JreCurrencyContext(final Currency currency,
                               final BiFunction<Currency, Currency, Number> exchangeRates,
                               final HasLocale hasLocale) {
        this.currency = currency;
        this.exchangeRates = exchangeRates;
        this.hasLocale = hasLocale;
    }

    @Override
    public Currency currency() {
        return this.currency;
    }

    @Override
    public void setCurrency(final Currency currency) {
        Objects.requireNonNull(currency, "currency");
        this.currency = currency;
    }

    private Currency currency;

    @Override
    public Set<Currency> availableCurrencies() {
        return Sets.readOnly(
            Currency.getAvailableCurrencies()
        );
    }

    @Override
    public Optional<Currency> currencyForCurrencyCode(final String currencyCode) {
        Objects.requireNonNull(currencyCode, "currencyCode");

        Currency currency;
        try {
            currency = Currency.getInstance(currencyCode);
        } catch (final RuntimeException exception) {
            currency = null;
        }

        return Optional.ofNullable(currency);
    }

    @Override
    public Optional<Currency> currencyForLocale(final Locale locale) {
        Objects.requireNonNull(locale, "locale");

        Currency currency;

        try {
            currency = Currency.getInstance(locale);
        } catch (final RuntimeException exception) {
            currency = null;
        }

        return Optional.ofNullable(currency);
    }

    @Override
    public Optional<String> currencyText(final Currency currency) {
        Objects.requireNonNull(currency, "currency");

        String text;

        try {
            text = JreCurrencyContextGetDisplayName.getDisplayName(
                currency,
                this.hasLocale.locale()
            );
        } catch (final RuntimeException exception) {
            text = null;
        }

        return Optional.ofNullable(text);
    }

    @Override
    public Set<Currency> findByCurrencyText(final String text,
                                            final int offset,
                                            final int count) {
        Objects.requireNonNull(text, "text");
        if (offset < 0) {
            throw new IllegalArgumentException("Invalid offset " + offset + " < 0");
        }
        if (count < 0) {
            throw new IllegalArgumentException("Invalid count " + count + " < 0");
        }

        final Locale locale = this.hasLocale.locale();

        return this.availableCurrencies()
            .stream()
            .filter(
                c -> JreCurrencyContextGetDisplayName.getDisplayName(
                    c,
                    locale
                ).startsWith(text)
            ).skip(offset)
            .limit(count)
            .collect(ImmutableSet.collector());
    }

    private final HasLocale hasLocale;

    @Override
    public Number exchangeRate(final Currency from,
                               final Currency to,
                               final Optional<LocalDateTime> dateTime) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(dateTime, "dateTime");

        return this.exchangeRates.apply(from, to);
    }

    private final BiFunction<Currency, Currency, Number> exchangeRates;

    @Override
    public String toString() {
        return "JRE";
    }
}
