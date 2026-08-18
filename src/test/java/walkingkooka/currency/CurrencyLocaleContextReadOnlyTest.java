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
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyLocaleContextReadOnlyTest implements CurrencyLocaleContextTesting2<CurrencyLocaleContextReadOnly>,
    ClassTesting2<CurrencyLocaleContextReadOnly>,
    ToStringTesting<CurrencyLocaleContextReadOnly> {

    @Test
    public void testWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyLocaleContextReadOnly.with(null)
        );
    }

    @Test
    public void testSetCurrencyFails() {
        assertThrows(
            UnsupportedOperationException.class,
            () -> this.createContext()
                .setCurrency(
                    Currency.getInstance("NZD")
                )
        );
    }

    @Test
    public void testSetLocaleFails() {
        assertThrows(
            UnsupportedOperationException.class,
            () -> this.createContext()
                .setLocale(
                    Locale.forLanguageTag("en-NZ")
                )
        );
    }

    @Override
    public CurrencyLocaleContextReadOnly createContext() {
        return CurrencyLocaleContextReadOnly.with(CURRENCY_LOCALE_CONTEXT);
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            CURRENCY_LOCALE_CONTEXT.toString()
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyLocaleContextReadOnly> type() {
        return CurrencyLocaleContextReadOnly.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
