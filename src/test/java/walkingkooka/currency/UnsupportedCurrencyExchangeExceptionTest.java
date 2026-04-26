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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class UnsupportedCurrencyExchangeExceptionTest implements ThrowableTesting2<UnsupportedCurrencyExchangeException> {

    // with.............................................................................................................

    @Test
    public void testWithNullFromFails() {
        assertThrows(
            NullPointerException.class,
            () -> UnsupportedCurrencyExchangeException.with(
                null,
                CurrencyCode.parse("NZD"),
                Optional.empty()
            )
        );
    }

    @Test
    public void testWithNullToFails() {
        assertThrows(
            NullPointerException.class,
            () -> UnsupportedCurrencyExchangeException.with(
                CurrencyCode.parse("AUD"),
                null,
                Optional.empty()
            )
        );
    }

    @Test
    public void testWithNullDateTimeFails() {
        assertThrows(
            NullPointerException.class,
            () -> UnsupportedCurrencyExchangeException.with(
                CurrencyCode.parse("AUD"),
                CurrencyCode.parse("NZD"),
                null
            )
        );
    }

    // getMessage.......................................................................................................

    @Test
    public void testGetMessage() {
        this.getMessageAndCheck(
            UnsupportedCurrencyExchangeException.with(
                CurrencyCode.parse("AUD"),
                CurrencyCode.parse("NZD"),
                Optional.empty()
            ),
            "Unsupported currency exchange \"AUD\" to \"NZD\""
        );
    }

    @Test
    public void testGetMessageWithDateTime() {
        this.getMessageAndCheck(
            UnsupportedCurrencyExchangeException.with(
                CurrencyCode.parse("AUD"),
                CurrencyCode.parse("NZD"),
                Optional.of(
                    LocalDateTime.of(
                        1999,
                        12,
                        31,
                        12,
                        58,
                        59
                    )
                )
            ),
            "Unsupported currency exchange \"AUD\" to \"NZD\" 1999-12-31T12:58:59"
        );
    }

    // class............................................................................................................

    @Override
    public Class<UnsupportedCurrencyExchangeException> type() {
        return UnsupportedCurrencyExchangeException.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    @Override
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testAllConstructorsVisibility() {
        throw new UnsupportedOperationException();
    }
}
