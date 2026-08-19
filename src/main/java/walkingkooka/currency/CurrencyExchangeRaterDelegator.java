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
import java.util.Optional;
import java.util.Set;

public interface CurrencyExchangeRaterDelegator<C extends CurrencyExchangeRaterContext> extends CurrencyExchangeRater<C> {

    @Override
    default Set<CurrencyExchange> currencyExchanges(final C context) {
        return this.currencyExchangeRater()
            .currencyExchanges(context);
    }

    @Override
    default Optional<Number> currencyExchangeRate(final CurrencyExchange currencyExchange,
                                                  final Optional<LocalDateTime> dateTime,
                                                  final C context) {
        return this.currencyExchangeRater()
            .currencyExchangeRate(
                currencyExchange,
                dateTime,
                context
            );
    }

    CurrencyExchangeRater<C> currencyExchangeRater();
}
