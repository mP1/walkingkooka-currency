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
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.ThrowableTesting2;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MissingCurrencyExceptionTest implements ThrowableTesting2<MissingCurrencyException> {

    // with.............................................................................................................

    @Test
    public void testWithNullCurrencyCodeFails() {
        assertThrows(
            NullPointerException.class,
            () -> new MissingCurrencyException(null)
        );
    }

    @Test
    public void testWith() {
        final CurrencyCode currencyCode = CurrencyCode.parse("AUD");

        this.checkEquals(
            currencyCode,
            new MissingCurrencyException(currencyCode)
                .currencyCode()
        );
    }

    // with.............................................................................................................

    @Test
    public void testGetMessage() {
        this.getMessageAndCheck(
            new MissingCurrencyException(
                CurrencyCode.parse("AUD")
            ),
            "Missing currency code \"AUD\""
        );
    }

    // class............................................................................................................

    @Override
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<MissingCurrencyException> type() {
        return MissingCurrencyException.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
