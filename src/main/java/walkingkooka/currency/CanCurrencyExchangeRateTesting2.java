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

import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface CanCurrencyExchangeRateTesting2<C extends CanCurrencyExchangeRate> extends CanCurrencyExchangeRateTesting {

    // currencyExchangeRate.............................................................................................

    @Test
    default void testExchangeRateWithNullFromFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCanCurrencyExchangeRate()
                .exchangeRate(
                    null,
                    Currency.getInstance("NZD"),
                    Optional.empty() // dateTime
                )
        );
    }

    @Test
    default void testExchangeRateWithNullToFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCanCurrencyExchangeRate()
                .exchangeRate(
                    Currency.getInstance("AUD"),
                    null,
                    Optional.empty() // dateTime
                )
        );
    }

    @Test
    default void testExchangeRateWithNullDateTimeFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCanCurrencyExchangeRate()
                .exchangeRate(
                    Currency.getInstance("AUD"),
                    Currency.getInstance("NZD"),
                    null // dateTime
                )
        );
    }

    C createCanCurrencyExchangeRate();
}
