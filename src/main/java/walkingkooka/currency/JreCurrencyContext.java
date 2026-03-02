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
import walkingkooka.collect.set.ImmutableSortedSet;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.locale.LocaleContext;
import walkingkooka.text.CharSequences;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;

final class JreCurrencyContext implements CurrencyContext {

    static JreCurrencyContext with(final Currency currency,
                                   final BiFunction<Currency, Currency, Number> exchangeRates,
                                   final LocaleContext localeContext) {
        return new JreCurrencyContext(
            Objects.requireNonNull(currency, "currency"),
            Objects.requireNonNull(exchangeRates, "exchangeRates"),
            Objects.requireNonNull(localeContext, "localeContext")
        );
    }

    private JreCurrencyContext(final Currency currency,
                               final BiFunction<Currency, Currency, Number> exchangeRates,
                               final LocaleContext localeContext) {
        this.currency = currency;
        this.exchangeRates = exchangeRates;
        this.localeContext = localeContext;
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
        if (null == this.availableCurrencies) {
            final SortedSet<Currency> currencies = SortedSets.tree(CurrencyContexts.CURRENCY_CODE_COMPARATOR);
            currencies.addAll(
                Currency.getAvailableCurrencies()
            );

            this.availableCurrencies = SortedSets.immutable(currencies);
        }
        return this.availableCurrencies;
    }

    private Set<Currency> availableCurrencies;

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
                this.localeContext.locale()
            );
        } catch (final RuntimeException exception) {
            text = null;
        }

        return Optional.ofNullable(text);
    }

    @Override
    public Optional<Locale> localeForCurrencyCode(final String currencyCode) {
        Objects.requireNonNull(currencyCode, "currencyCode");

        if (null == this.currencyCodeToLocale) {
            final Map<String, Locale> currencyCodeToLocale = Maps.sorted(
                CurrencyContexts.CASE_SENSITIVITY.comparator()
            );

            for (final Locale locale : this.localeContext.availableLocales()) {
                try {
                    currencyCodeToLocale.put(
                        Currency.getInstance(locale)
                            .getCurrencyCode(),
                        locale
                    );
                } catch (final UnsupportedOperationException rethrow) {
                    throw rethrow;
                } catch (final RuntimeException exception) {

                }
            }

            this.currencyCodeToLocale = currencyCodeToLocale;
        }

        return Optional.ofNullable(
            this.currencyCodeToLocale.get(currencyCode)
        );
    }

    private Map<String, Locale> currencyCodeToLocale;

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

        return this.availableCurrencies()
            .stream()
            .filter(currency -> {
                final String currencyText = this.currencyText(currency)
                    .orElse(null);
                return false == CharSequences.isNullOrEmpty(currencyText) &&
                    CurrencyContexts.CASE_SENSITIVITY.startsWith(
                        currencyText,
                        text
                    );
            })
            .skip(offset)
            .limit(count)
            .collect(
                ImmutableSortedSet.collector(CurrencyContexts.CURRENCY_CODE_COMPARATOR)
            );
    }

    private final LocaleContext localeContext;

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
