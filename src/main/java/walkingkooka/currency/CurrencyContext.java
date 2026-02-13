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

import walkingkooka.util.HasCurrency;

import javax.naming.Context;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link Context} with some {@link Currency} operations.
 */
public interface CurrencyContext extends Context,
    HasCurrency {

    /**
     * Sets or replaces the current {@link Currency}
     */
    void setCurrency(final Currency currency);

    /**
     * Returns all available {@link Currency currencies}.
     */
    Set<Locale> availableCurrencies();

    /**
     * Returns the {@link Currency currencies} for the given {@link Locale}. Note this response may be zero or many.
     */
    Set<Currency> currencyForLocale(final Locale locale);

    /**
     * Returns all {@link Currency} that have display or local text beginning with the given search text.
     */
    Set<Currency> findByCurrencyText(final String text,
                                     final int offset,
                                     final int count);

    /**
     * Queries the exchange rate between the two currencies at the given {@link LocalDateTime}.
     */
    Number exchangeRate(final Currency from,
                        final Currency to,
                        final Optional<LocalDateTime> dateTime);
}
