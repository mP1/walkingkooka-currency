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
import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyContextCurrencyLocaleContextTest implements CurrencyLocaleContextTesting2<CurrencyContextCurrencyLocaleContext>,
    ClassTesting2<CurrencyContextCurrencyLocaleContext>,
    ToStringTesting<CurrencyContextCurrencyLocaleContext> {

    private final static LocaleContext LOCALE_CONTEXT = LocaleContexts.jre(
        Locale.forLanguageTag("en-AU")
    );

    private final static CurrencyContext CURRENCY_CONTEXT = CurrencyContexts.jre(
        Currency.getInstance(
            LOCALE_CONTEXT.locale()
        ),
        (Currency from, Currency to) -> {
            Objects.requireNonNull(from, "from");
            Objects.requireNonNull(to, "to");

            return 2;
        },
        LOCALE_CONTEXT
    );

    @Test
    public void testWithNullCurrencyContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyContextCurrencyLocaleContext.with(
                null,
                LOCALE_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullLocaleContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyContextCurrencyLocaleContext.with(
                CURRENCY_CONTEXT,
                null
            )
        );
    }

    @Test
    public void testWithCurrencyLocaleContext() {
        final CurrencyContextCurrencyLocaleContext context = this.createContext();
        assertSame(
            context,
            CurrencyContextCurrencyLocaleContext.with(
                context,
                context
            )
        );
    }

    @Override
    public CurrencyContextCurrencyLocaleContext createContext() {
        return (CurrencyContextCurrencyLocaleContext)
            CurrencyContextCurrencyLocaleContext.with(
                CURRENCY_CONTEXT,
                LOCALE_CONTEXT
            );
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
    public Class<CurrencyContextCurrencyLocaleContext> type() {
        return CurrencyContextCurrencyLocaleContext.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
