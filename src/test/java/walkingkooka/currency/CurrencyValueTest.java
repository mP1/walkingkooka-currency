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
import walkingkooka.ValueTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.printer.TreePrintableTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyValueTest implements HashCodeEqualsDefinedTesting2<CurrencyValue>,
    TreePrintableTesting,
    ToStringTesting<CurrencyValue>,
    HasCurrencyCodeTesting,
    ClassTesting<CurrencyValue>,
    ValueTesting {

    private final static Number NUMBER = 1;

    private final static CurrencyCode CURRENCY_CODE = CurrencyCode.parse("AUD");

    // with.............................................................................................................

    @Test
    public void testWithNullNumberFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyValue.with(
                null,
                CURRENCY_CODE
            )
        );
    }

    @Test
    public void testWithNullCurrencyCodeFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyValue.with(
                NUMBER,
                null
            )
        );
    }

    @Test
    public void testWith() {
        final CurrencyValue value = CurrencyValue.with(
            NUMBER,
            CURRENCY_CODE
        );

        this.valueAndCheck(
            value,
            NUMBER
        );

        this.currencyCodeAndCheck(
            value,
            CURRENCY_CODE
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentNumber() {
        this.checkNotEquals(
            CurrencyValue.with(
                2,
                CURRENCY_CODE
            )
        );
    }

    @Test
    public void testEqualsDifferentCurrencyCode() {
        this.checkNotEquals(
            CurrencyValue.with(
                NUMBER,
                CurrencyCode.parse("NZD")
            )
        );
    }

    @Override
    public CurrencyValue createObject() {
        return CurrencyValue.with(
            NUMBER,
            CURRENCY_CODE
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            CurrencyValue.with(
                NUMBER,
                CURRENCY_CODE
            ),
            "1 AUD"
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testPrintTree() {
        this.treePrintAndCheck(
            CurrencyValue.with(
                NUMBER,
                CURRENCY_CODE
            ),
            "CurrencyValue\n" +
                "  1\n" +
                "  AUD\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyValue> type() {
        return CurrencyValue.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
