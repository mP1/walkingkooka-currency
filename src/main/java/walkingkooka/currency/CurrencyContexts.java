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

import walkingkooka.reflect.PublicStaticHelper;

import java.util.Currency;
import java.util.function.BiFunction;

public final class CurrencyContexts implements PublicStaticHelper {

    /**
     * {@see FakeCurrencyContext}
     */
    public static FakeCurrencyContext fake() {
        return new FakeCurrencyContext();
    }

    /**
     * {@see JreCurrencyContext}
     */
    public static CurrencyContext jre(final Currency currency,
                                      final BiFunction<Currency, Currency, Number> exchangeRates) {
        return JreCurrencyContext.with(
            currency,
            exchangeRates
        );
    }

    /**
     * {@see ReadOnlyCurrencyContext}
     */
    public static CurrencyContext readOnly(final CurrencyContext context) {
        return ReadOnlyCurrencyContext.with(context);
    }

    /**
     * Stop creation
     */
    private CurrencyContexts() {
        throw new UnsupportedOperationException();
    }
}
