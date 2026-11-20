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
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.Lists;

import java.util.Currency;

public final class CurrencyListTest implements ImmutableListTesting<CurrencyList, Currency>,
    HasCurrencyTesting{

    @Test
    public void testSetElementsWithDifferent() {
        this.setElementsAndCheck(
            new CurrencyList(
                Lists.of(CURRENCY)
            ),
            Lists.of(DIFFERENT_CURRENCY),
            new CurrencyList(
                Lists.of(DIFFERENT_CURRENCY)
            )
        );
    }

    @Test
    public void testSetElementsWithDifferentEmpty() {
        this.setElementsAndCheck(
            new CurrencyList(
                Lists.of(CURRENCY)
            ),
            Lists.empty(),
            CurrencyList.EMPTY
        );
    }

    @Override
    public CurrencyList createList() {
        return CurrencyList.EMPTY;
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyList> type() {
        return CurrencyList.class;
    }
}
