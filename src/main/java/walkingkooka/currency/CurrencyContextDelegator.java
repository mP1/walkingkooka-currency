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
import java.util.Optional;
import java.util.Set;

public interface CurrencyContextDelegator extends CurrencyContext {

    @Override
    default Currency currency() {
        return this.currencyContext()
            .currency();
    }

    @Override
    default void setCurrency(final Currency currency) {
        this.currencyContext()
            .setCurrency(currency);
    }

    @Override
    default CurrencyCode currencyCode() {
        return this.currencyContext()
            .currencyCode();
    }

    @Override
    default Set<CurrencyCode> availableCurrencies() {
        return this.currencyContext()
            .availableCurrencies();
    }

    @Override
    default Optional<Currency> currencyForCurrencyCode(final CurrencyCode currencyCode) {
        return this.currencyContext()
            .currencyForCurrencyCode(currencyCode);
    }

    @Override
    default Optional<Currency> currencyForLocale(final Locale locale) {
        return this.currencyContext()
            .currencyForLocale(locale);
    }

    @Override
    default Optional<String> currencyText(final CurrencyCode currencyCode) {
        return this.currencyContext()
            .currencyText(currencyCode);
    }

    @Override
    default Set<Locale> localesForCurrencyCode(final CurrencyCode currencyCode) {
        return this.currencyContext()
            .localesForCurrencyCode(currencyCode);
    }

    @Override
    default Set<CurrencyCode> findByCurrencyText(final String text,
                                                 final int offset,
                                                 final int count) {
        return this.currencyContext()
            .findByCurrencyText(
                text,
                offset,
                count
            );
    }

    @Override
    default Optional<Number> exchangeRate(final CurrencyExchange currencyExchange,
                                          final Optional<LocalDateTime> dateTime) {
        return this.currencyContext()
            .exchangeRate(
                currencyExchange,
                dateTime
            );
    }

    CurrencyContext currencyContext();
}
