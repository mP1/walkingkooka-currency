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
import org.opentest4j.AssertionFailedError;
import walkingkooka.util.HasLocaleTesting;

import java.util.Currency;

public final class HasCurrencyTestingTest implements HasCurrencyTesting,
    HasLocaleTesting {

    @Test
    public void testCurrencyAndCheck() {
        final Currency currency = Currency.getInstance(LOCALE);
        this.currencyAndCheck(() -> currency, currency);
    }

    @Test
    public void testCurrencyAndCheckFails() {
        boolean failed = false;
        try {
            this.currencyAndCheck(
                () -> Currency.getInstance(LOCALE),
                Currency.getInstance(DIFFERENT_LOCALE)
            );
        } catch (final AssertionFailedError expected) {
            failed = true;
        }
        this.checkEquals(
            true,
            failed
        );
    }
}
