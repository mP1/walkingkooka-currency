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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.PublicStaticHelperTesting;

import java.lang.reflect.Method;
import java.util.Currency;
import java.util.Set;

public final class CurrencyContextsTest implements PublicStaticHelperTesting<CurrencyContexts> {

    // CURRENCY_CODE_COMPARATOR..........................................................................................

    @Test
    public void testCurrencyCodeComparatorSort() {
        final Currency aud = Currency.getInstance("AUD");
        final Currency nzd = Currency.getInstance("NZD");
        final Currency usd = Currency.getInstance("USD");

        final Set<Currency> sortedByCurrencyCode = SortedSets.tree(CurrencyContexts.CURRENCY_CODE_COMPARATOR);
        sortedByCurrencyCode.add(usd);
        sortedByCurrencyCode.add(aud);
        sortedByCurrencyCode.add(nzd);

        this.checkEquals(
            Lists.of(
                aud,
                nzd,
                usd
            ),
            Lists.of(
                sortedByCurrencyCode.toArray()
            )
        );
    }
    
    @Override
    public boolean canHavePublicTypes(final Method method) {
        return false;
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyContexts> type() {
        return CurrencyContexts.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
