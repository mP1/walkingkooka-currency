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

import walkingkooka.ContextTesting;
import walkingkooka.locale.CanLocaleForLanguageTagTesting2;

public interface CurrencyCodeLanguageTagContextTesting2<C extends CurrencyCodeLanguageTagContext> extends CanCurrencyForCurrencyCodeTesting2<C>,
    CanLocaleForLanguageTagTesting2<C>,
    ContextTesting<C> {

    @Override
    default C createCanCurrencyForCurrencyCode() {
        return this.createContext();
    }

    @Override
    default C createCanLocaleForLanguageTag() {
        return this.createContext();
    }

    @Override
    default String typeNameSuffix() {
        return CurrencyCodeLanguageTagContext.class.getSimpleName();
    }
}
