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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicCurrencyLocaleContextTest implements CurrencyLocaleContextTesting2<BasicCurrencyLocaleContext>,
    HashCodeEqualsDefinedTesting2<BasicCurrencyLocaleContext>,
    ClassTesting2<BasicCurrencyLocaleContext>,
    ToStringTesting<BasicCurrencyLocaleContext> {

    @Test
    public void testWithNullCurrencyContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicCurrencyLocaleContext.with(
                null,
                LOCALE_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullLocaleContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicCurrencyLocaleContext.with(
                CURRENCY_CONTEXT,
                null
            )
        );
    }

    @Test
    public void testWithCurrencyLocaleContext() {
        final BasicCurrencyLocaleContext context = this.createContext();
        assertSame(
            context,
            BasicCurrencyLocaleContext.with(
                context,
                context
            )
        );
    }

    @Override
    public BasicCurrencyLocaleContext createContext() {
        return (BasicCurrencyLocaleContext)
            BasicCurrencyLocaleContext.with(
                CURRENCY_CONTEXT,
                LOCALE_CONTEXT
            );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentCurrencyContext() {
        this.checkNotEquals(
            BasicCurrencyLocaleContext.with(
                CurrencyContexts.fake(),
                LOCALE_CONTEXT
            )
        );
    }

    @Test
    public void testEqualsDifferentLocaleContext() {
        this.checkNotEquals(
            BasicCurrencyLocaleContext.with(
                CURRENCY_CONTEXT,
                LocaleContexts.fake()
            )
        );
    }

    @Override
    public BasicCurrencyLocaleContext createObject() {
        return this.createContext();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            CURRENCY_CONTEXT + " " + LOCALE_CONTEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicCurrencyLocaleContext> type() {
        return BasicCurrencyLocaleContext.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
