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

import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContextDelegator;

import java.util.Objects;

final class CurrencyContextCurrencyLocaleContext implements CurrencyLocaleContext,
    CurrencyContextDelegator, 
    LocaleContextDelegator {

    static CurrencyLocaleContext with(final CurrencyContext currencyContext,
                                      final LocaleContext localeContext) {
        return currencyContext == localeContext ?
            (CurrencyLocaleContext) currencyContext :
            new CurrencyContextCurrencyLocaleContext(
                Objects.requireNonNull(currencyContext, "currencyContext"),
                Objects.requireNonNull(localeContext, "localeContext")
            );
    }

    private CurrencyContextCurrencyLocaleContext(final CurrencyContext currencyContext,
                                                 final LocaleContext localeContext) {
        super();

        this.currencyContext = currencyContext;
        this.localeContext = localeContext;
    }

    // CurrencyContextDelegator.........................................................................................
    
    @Override
    public CurrencyContext currencyContext() {
        return this.currencyContext;
    }
    
    private final CurrencyContext currencyContext;

    // LocaleContextDelegator...........................................................................................

    @Override
    public LocaleContext localeContext() {
        return this.localeContext;
    }

    private final LocaleContext localeContext;

    // toString.........................................................................................................

    @Override
    public String toString() {
        return this.currencyContext + " " + this.localeContext;
    }
}
