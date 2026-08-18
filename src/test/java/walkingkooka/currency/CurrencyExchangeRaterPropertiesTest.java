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
import walkingkooka.props.HasPropertiesTesting;
import walkingkooka.props.Properties;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyExchangeRaterPropertiesTest implements CurrencyExchangeRaterTesting2<CurrencyExchangeRaterProperties>,
    HasPropertiesTesting,
    HashCodeEqualsDefinedTesting2<CurrencyExchangeRaterProperties>,
    ToStringTesting<CurrencyExchangeRaterProperties>,
    ClassTesting<CurrencyExchangeRaterProperties> {

    private final static Properties PROPERTIES = Properties.parse("AUD-NZD=1.1\nAUD-CAD=1.2\n");

    private final BiFunction<String, Boolean, Number> NUMBER_PARSER = (String text, Boolean invert) -> {
        final BigDecimal value = new BigDecimal(text);
        return invert ?
            BigDecimal.ONE.divide(
                value,
                2,
                BigDecimal.ROUND_HALF_UP
            ) :
            value;
    };

    // with.............................................................................................................

    @Test
    public void testWithNullPropertiesFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchangeRaterProperties.with(
                null,
                NUMBER_PARSER
            )
        );
    }

    @Test
    public void testWithNullNumberParserFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchangeRaterProperties.with(
                Properties.EMPTY,
                null
            )
        );
    }

    // HasProperties....................................................................................................

    @Test
    public void testProperties() {
        this.propertiesAndCheck(
            this.createCurrencyExchangeRater(),
            PROPERTIES
        );
    }

    // currencyExchanges................................................................................................

    @Test
    public void testCurrencyExchanges() {
        final CurrencyCode aud = CurrencyCode.parse("AUD");
        final CurrencyCode nzd = CurrencyCode.parse("NZD");
        final CurrencyCode cad = CurrencyCode.parse("CAD");

        this.currencyExchangesAndCheck(
            this.createCurrencyExchangeRater(),
            CurrencyExchange.with(
                aud,
                nzd
            ),
            CurrencyExchange.with(
                aud,
                cad
            ),
            CurrencyExchange.with(
                nzd,
                aud
            ),
            CurrencyExchange.with(
                cad,
                aud
            )
        );
    }

    // currencyExchangeRate.............................................................................................

    @Test
    public void testCurrencyExchangeRate() {
        this.currencyExchangeRateAndCheck(
            this.createCurrencyExchangeRater(),
            CurrencyExchange.with(
                CurrencyCode.parse("AUD"),
                CurrencyCode.parse("NZD")
            ),
            new BigDecimal("1.1")
        );
    }

    @Test
    public void testCurrencyExchangeRateInverted() {
        this.currencyExchangeRateAndCheck(
            this.createCurrencyExchangeRater(),
            CurrencyExchange.with(
                CurrencyCode.parse("NZD"),
                CurrencyCode.parse("AUD")
            ),
            new BigDecimal("0.91")
        );
    }

    @Override
    public CurrencyExchangeRaterProperties createCurrencyExchangeRater() {
        return CurrencyExchangeRaterProperties.with(
            PROPERTIES,
            NUMBER_PARSER
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentProperties() {
        this.checkNotEquals(
            CurrencyExchangeRaterProperties.with(
                Properties.EMPTY,
                NUMBER_PARSER
            )
        );
    }

    @Test
    public void testEqualsDifferentNumberParser() {
        this.checkNotEquals(
            CurrencyExchangeRaterProperties.with(
                PROPERTIES,
                (String text, Boolean invert) -> {
                    throw new UnsupportedOperationException();
                }
            )
        );
    }

    @Override
    public CurrencyExchangeRaterProperties createObject() {
        return this.createCurrencyExchangeRater();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createCurrencyExchangeRater(),
            PROPERTIES.toString()
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyExchangeRaterProperties> type() {
        return CurrencyExchangeRaterProperties.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
