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
import walkingkooka.collect.set.Sets;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.locale.LocaleContext;
import walkingkooka.text.CharSequences;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

final class JreCurrencyContext implements CurrencyContext {

    static JreCurrencyContext with(final Currency currency,
                                   final CurrencyExchangeRater currencyExchangeRater,
                                   final LocaleContext localeContext) {
        return new JreCurrencyContext(
            Objects.requireNonNull(currency, "currency"),
            Objects.requireNonNull(currencyExchangeRater, "currencyExchangeRater"),
            Objects.requireNonNull(localeContext, "localeContext")
        );
    }

    private JreCurrencyContext(final Currency currency,
                               final CurrencyExchangeRater currencyExchangeRater,
                               final LocaleContext localeContext) {
        this.currency = currency;
        this.currencyExchangeRater = currencyExchangeRater;
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
    public Set<CurrencyCode> availableCurrencies() {
        if (null == this.availableCurrencies) {
            final SortedSet<CurrencyCode> currencies = SortedSets.tree();

            for (final Currency currency : Currency.getAvailableCurrencies()) {
                currencies.add(
                    CurrencyCode.fromCurrency(currency)
                );
            }

            this.availableCurrencies = SortedSets.immutable(currencies);
        }
        return this.availableCurrencies;
    }

    private Set<CurrencyCode> availableCurrencies;

    @Override
    public Optional<Currency> currencyForCurrencyCode(final CurrencyCode currencyCode) {
        Objects.requireNonNull(currencyCode, "currencyCode");

        Currency currency;
        try {
            currency = Currency.getInstance(
                currencyCode.value()
            );
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
    public Optional<String> currencyText(final CurrencyCode currencyCode) {
        Objects.requireNonNull(currencyCode, "currencyCode");

        String text;

        try {
            text = JreCurrencyContextGetDisplayName.getDisplayName(
                Currency.getInstance(
                    currencyCode.value()
                ),
                this.localeContext.locale()
            );
        } catch (final RuntimeException exception) {
            text = null;
        }

        return Optional.ofNullable(text);
    }

    @Override
    public Set<Locale> localesForCurrencyCode(final CurrencyCode currencyCode) {
        Objects.requireNonNull(currencyCode, "currencyCode");

        if (null == this.currencyCodeToLocales) {
            final Map<String, Set<Locale>> currencyCodeToLocales = Maps.sorted(
                CurrencyContexts.CASE_SENSITIVITY.comparator()
            );

            for (final Locale locale : this.localeContext.availableLocales()) {
                try {
                    final Currency currency = Currency.getInstance(locale);
                    final String currencyCurrencyCode = currency.getCurrencyCode();

                    Set<Locale> locales = currencyCodeToLocales.get(currencyCurrencyCode);
                    if (null == locales) {
                        locales = Sets.of(locale);
                    } else {
                        locales = Sets.immutable(locales)
                            .concat(locale);
                    }

                    currencyCodeToLocales.put(
                        currencyCurrencyCode,
                        locales
                    );
                } catch (final UnsupportedOperationException rethrow) {
                    throw rethrow;
                } catch (final RuntimeException exception) {

                }
            }

            this.currencyCodeToLocales = currencyCodeToLocales;
        }

        Set<Locale> locales = this.currencyCodeToLocales.get(
            currencyCode.value()
        );
        if (null == locales) {
            locales = Sets.empty();
        }
        return locales;
    }

    private Map<String, Set<Locale>> currencyCodeToLocales;

    @Override
    public Set<CurrencyCode> findByCurrencyText(final String text,
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
                    ImmutableSortedSet.collector(Comparator.naturalOrder())
            );
    }

    private final LocaleContext localeContext;

    @Override
    public Optional<Number> exchangeRate(final CurrencyCode from,
                                         final CurrencyCode to,
                                         final Optional<LocalDateTime> dateTime) {
        return this.currencyExchangeRater.exchangeRate(
            from,
            to,
            dateTime
        );
    }

    private final CurrencyExchangeRater currencyExchangeRater;

    @Override
    public String toString() {
        return "JRE";
    }
}
