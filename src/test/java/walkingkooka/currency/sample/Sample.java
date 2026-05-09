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

package walkingkooka.currency.sample;

import org.junit.jupiter.api.Test;
import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyExchange;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.props.Properties;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Sample {

    public static void main(final String[] args) {
        final Sample sample = new Sample();
        sample.testExchangeRate();
    }

    @Test
    public void testExchangeRate() {
        checkEquals(
            Optional.of(
                new BigDecimal("1.1")
            ),
            CurrencyExchangeRaters.properties(
                Properties.parse("AUD-NZD=1.1"),
                (String text, Boolean invert) -> {
                    final BigDecimal value = new BigDecimal(text);
                    return invert ?
                        BigDecimal.ONE.divide(
                            value,
                            2,
                            BigDecimal.ROUND_HALF_UP
                        ) :
                        value;
                }
            ).exchangeRate(
                CurrencyExchange.with(
                    CurrencyCode.parse("AUD"),
                    CurrencyCode.parse("NZD")
                ),
                Optional.empty()
            ),
            "AUD-NZD"
        );
    }

    private static void checkEquals(final Object expected,
                                    final Object actual,
                                    final String message) {
        assertEquals(
            expected,
            actual,
            message
        );
    }
}
