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
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;

public final class CurrencyExchangeRaterEmptyTest implements CurrencyExchangeRaterTesting2<CurrencyExchangeRaterEmpty<CurrencyExchangeRaterContext>, CurrencyExchangeRaterContext>,
    ToStringTesting<CurrencyExchangeRaterEmpty<CurrencyExchangeRaterContext>>,
    ClassTesting<CurrencyExchangeRaterEmpty<CurrencyExchangeRaterContext>> {

    private final static CurrencyExchangeRaterContext CONTEXT = new FakeCurrencyExchangeRaterContext();

    // currencyExchanges................................................................................................

    @Test
    public void testCurrencyExchanges() {
        this.currencyExchangesAndCheck(
            this.createCurrencyExchangeRater(),
            CONTEXT
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
            CONTEXT
        );
    }

    @Override
    public CurrencyExchangeRaterEmpty<CurrencyExchangeRaterContext> createCurrencyExchangeRater() {
        return CurrencyExchangeRaterEmpty.instance();
    }

    @Override
    public CurrencyExchangeRaterContext createContext() {
        return CONTEXT;
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createCurrencyExchangeRater(),
            ""
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyExchangeRaterEmpty<CurrencyExchangeRaterContext>> type() {
        return Cast.to(CurrencyExchangeRaterEmpty.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
