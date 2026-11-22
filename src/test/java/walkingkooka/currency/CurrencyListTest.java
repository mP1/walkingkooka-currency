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
import walkingkooka.collect.list.HasCsvStringListTsvStringListTesting2;
import walkingkooka.collect.list.ImmutableListTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
import walkingkooka.collect.list.StringList;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyListTest implements ImmutableListTesting<CurrencyList, Currency>,
    HasCurrencyTesting,
    HasCsvStringListTsvStringListTesting2<CurrencyList>,
    HashCodeEqualsDefinedTesting2<CurrencyList> {

    @Test
    public void testFromStringLikeListWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyList.fromStringLikeList(null)
        );
    }

    @Test
    public void testFromStringLikeListWithEmpty() {
        this.fromStringLikeList(
            StringList.EMPTY,
            CurrencyList.EMPTY
        );
    }

    @Test
    public void testFromStringLikeListWithNotEmpty() {
        this.fromStringLikeList(
            StringList.EMPTY.concat(CURRENCY.getCurrencyCode())
                .concat(DIFFERENT_CURRENCY.getCurrencyCode()),
            CurrencyList.EMPTY.concat(CURRENCY)
                .concat(DIFFERENT_CURRENCY)
        );
    }

    @Test
    public void testFromStringLikeListWithNotEmptyWithNull() {
        this.fromStringLikeList(
            StringList.EMPTY.concat(CURRENCY.getCurrencyCode())
                .concat(null)
                .concat(DIFFERENT_CURRENCY.getCurrencyCode()),
            CurrencyList.EMPTY.concat(CURRENCY)
                .concat(null)
                .concat(DIFFERENT_CURRENCY)
        );
    }

    @Test
    public void testFromStringLikeListWithNotEmptyWithEmpty() {
        this.fromStringLikeList(
            StringList.EMPTY.concat(CURRENCY.getCurrencyCode())
                .concat("")
                .concat(DIFFERENT_CURRENCY.getCurrencyCode()),
            CurrencyList.EMPTY.concat(CURRENCY)
                .concat(null)
                .concat(DIFFERENT_CURRENCY)
        );
    }

    private void fromStringLikeList(final StringLikeList strings,
                                    final CurrencyList expected) {
        this.checkEquals(
            expected,
            CurrencyList.fromStringLikeList(strings),
            strings::toString
        );
    }

    // setElements......................................................................................................

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

    @Override
    public CurrencyList createHasCsvStringListTsvStringList() {
        return this.createList();
    }

    // HasCsvStringList.................................................................................................

    @Test
    public void testCsvStringList() {
        this.csvStringListAndCheck(
            CurrencyList.EMPTY.concat(CURRENCY)
                .concat(DIFFERENT_CURRENCY),
            "AUD,NZD"
        );
    }

    @Test
    public void testCsvStringListWithNull() {
        this.csvStringListAndCheck(
            CurrencyList.EMPTY.concat(CURRENCY)
                .concat(null)
                .concat(DIFFERENT_CURRENCY),
            "AUD,,NZD"
        );
    }

    // HasTsvStringList.................................................................................................

    @Test
    public void testTsvStringList() {
        this.tsvStringListAndCheck(
            CurrencyList.EMPTY.concat(CURRENCY)
                .concat(null)
                .concat(DIFFERENT_CURRENCY),
            "AUD\t\tNZD"
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsNotEmpty() {
        this.checkEquals(
            CurrencyList.EMPTY.concat(CURRENCY)
                .concat(DIFFERENT_CURRENCY),
            CurrencyList.EMPTY.concat(CURRENCY)
                .concat(DIFFERENT_CURRENCY)
        );
    }

    @Test
    public void testEqualsNotEmptyDifferent() {
        this.checkNotEquals(
            CurrencyList.EMPTY.concat(CURRENCY),
            CurrencyList.EMPTY.concat(DIFFERENT_CURRENCY)
        );
    }

    @Override
    public CurrencyList createObject() {
        return this.createList();
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyList> type() {
        return CurrencyList.class;
    }
}
