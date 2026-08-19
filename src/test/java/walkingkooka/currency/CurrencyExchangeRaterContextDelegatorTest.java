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

import walkingkooka.currency.CurrencyExchangeRaterContextDelegatorTest.TestCurrencyExchangeRaterContextDelegator;

public final class CurrencyExchangeRaterContextDelegatorTest implements CurrencyExchangeRaterContextTesting2<TestCurrencyExchangeRaterContextDelegator> {

    @Override
    public TestCurrencyExchangeRaterContextDelegator createContext() {
        return new TestCurrencyExchangeRaterContextDelegator();
    }

    final static class TestCurrencyExchangeRaterContextDelegator implements CurrencyExchangeRaterContextDelegator {

        @Override
        public CurrencyExchangeRaterContext currencyExchangeRaterContext() {
            return new FakeCurrencyExchangeRaterContext();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    @Override
    public Class<TestCurrencyExchangeRaterContextDelegator> type() {
        return TestCurrencyExchangeRaterContextDelegator.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
