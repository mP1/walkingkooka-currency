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
import walkingkooka.ContextTesting;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface CurrencyContextTesting2<C extends CurrencyContext> extends CurrencyContextTesting,
    CanCurrencyForCurrencyCodeTesting2<C>,
    CanCurrencyForLocaleTesting2<C>,
    CanLocalesForCurrencyCodeTesting2<C>,
    ContextTesting<C>,
    CurrencyExchangeRaterTesting2<C> {

    // setCurrency......................................................................................................

    @Test
    default void testSetCurrencyWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .setCurrency(null)
        );
    }

    // currencyText.....................................................................................................

    @Test
    default void testCurrencyTextWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .currencyText(null)
        );
    }

    // findByCurrencyText...............................................................................................

    @Test
    default void testFindByCurrencyTextWithNullTextAndCheckFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .findByCurrencyText(
                    null,
                    0,
                    1
                )
        );
    }

    @Test
    default void testFindByCurrencyTextAndCheckWithNegativeOffsetFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> this.createContext()
                .findByCurrencyText(
                    "",
                    -1,
                    1
                )
        );
    }

    @Test
    default void testFindByCurrencyTextAndCheckWithNegativeCountFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> this.createContext()
                .findByCurrencyText(
                    "",
                    0,
                    -1
                )
        );
    }

    // exchangeRate.....................................................................................................

    @Test
    default void testExchangeRateWithNullFromFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .exchangeRate(
                    null,
                    CurrencyCode.parse("AUD"),
                    Optional.empty()
                )
        );
    }

    @Test
    default void testExchangeRateWithNullToFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .exchangeRate(
                    CurrencyCode.parse("AUD"),
                    null,
                    Optional.empty()
                )
        );
    }

    @Test
    default void testExchangeRateWithNullDateTimeFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .exchangeRate(
                    CurrencyCode.parse("AUD"),
                    CurrencyCode.parse("NZD"),
                    null
                )
        );
    }

    @Override
    default C createCurrencyExchangeRater() {
        return this.createContext();
    }

    @Override
    default C createCanCurrencyForCurrencyCode() {
        return this.createContext();
    }

    @Override
    default C createCanCurrencyForLocale() {
        return this.createContext();
    }

    @Override
    default C createCanLocalesForCurrencyCode() {
        return this.createContext();
    }

    // class............................................................................................................

    @Override
    default String typeNameSuffix() {
        return CurrencyContext.class.getSimpleName();
    }
}
