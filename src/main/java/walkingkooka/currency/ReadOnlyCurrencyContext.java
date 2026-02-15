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

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link CurrencyContext} where {@link #setCurrency(Currency)} throws {@link UnsupportedOperationException}, all
 * other methods delegate to the wrapped {@link CurrencyContext}.
 */
final class ReadOnlyCurrencyContext implements CurrencyContext {

    static ReadOnlyCurrencyContext with(final CurrencyContext context) {
        Objects.requireNonNull(context);

        final ReadOnlyCurrencyContext readOnlyCurrencyContext;

        if (context instanceof ReadOnlyCurrencyContext) {
            readOnlyCurrencyContext = (ReadOnlyCurrencyContext) context;
        } else {
            readOnlyCurrencyContext = new ReadOnlyCurrencyContext(context);
        }

        return readOnlyCurrencyContext;
    }

    private ReadOnlyCurrencyContext(final CurrencyContext context) {
        super();
        this.context = context;
    }

    @Override
    public Currency currency() {
        return context.currency();
    }

    @Override
    public void setCurrency(final Currency currency) {
        Objects.requireNonNull(currency);
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Currency> availableCurrencies() {
        return this.context.availableCurrencies();
    }

    @Override
    public Set<Currency> currencyForLocale(final Locale locale) {
        return this.context.currencyForLocale(locale);
    }

    @Override
    public Optional<String> currencyText(final Currency currency) {
        return this.context.currencyText(currency);
    }

    @Override
    public Set<Currency> findByCurrencyText(final String text,
                                            final int offset,
                                            final int count) {
        return this.context.findByCurrencyText(
            text,
            offset,
            count
        );
    }

    @Override
    public Number exchangeRate(final Currency from,
                               final Currency to,
                               final Optional<LocalDateTime> dateTime) {
        return this.context.exchangeRate(
            from,
            to,
            dateTime
        );
    }

    @Override
    public Optional<Currency> currencyForCurrencyCode(String currencyCode) {
        return this.context.currencyForCurrencyCode(currencyCode);
    }

    @Override
    public Currency currencyForCurrencyCodeOrFail(String currencyCode) {
        return this.context.currencyForCurrencyCodeOrFail(currencyCode);
    }

    private final CurrencyContext context;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.context.toString();
    }
}
