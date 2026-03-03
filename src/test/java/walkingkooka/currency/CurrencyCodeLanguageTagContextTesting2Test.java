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

import walkingkooka.currency.CurrencyCodeLanguageTagContextTesting2Test.TestCurrencyCodeLanguageTagContext;

import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class CurrencyCodeLanguageTagContextTesting2Test implements CurrencyCodeLanguageTagContextTesting2<TestCurrencyCodeLanguageTagContext> {

    @Override
    public TestCurrencyCodeLanguageTagContext createContext() {
        return new TestCurrencyCodeLanguageTagContext();
    }

    @Override
    public Class<TestCurrencyCodeLanguageTagContext> type() {
        return TestCurrencyCodeLanguageTagContext.class;
    }

    static final class TestCurrencyCodeLanguageTagContext implements CurrencyCodeLanguageTagContext{
        @Override
        public Optional<Currency> currencyForCurrencyCode(final String currencyCode) {
            Objects.requireNonNull(currencyCode, "currencyCode");
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Locale> localeForLanguageTag(final String languageTag) {
            Objects.requireNonNull(languageTag, "languageTag");
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
