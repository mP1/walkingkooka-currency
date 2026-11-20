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

import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.HasCsvStringList;
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.list.Lists;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

/**
 * A {@link List} that holds nullable {@link Currency currencies}.
 */
public final class CurrencyList extends AbstractList<Currency>
    implements ImmutableListDefaults<CurrencyList, Currency>,
    HasCsvStringList {

    public final static CurrencyList EMPTY = new CurrencyList(Lists.empty());

    // @VisibleForTesting
    CurrencyList(final List<Currency> list) {
        super();
        this.list = list;
    }

    @Override
    public Currency get(final int index) {
        return this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public CurrencyList setElements(final Collection<Currency> collection) {
        CurrencyList currencyList;

        if (collection instanceof CurrencyList) {
            currencyList = (CurrencyList) collection;
        } else {
            final List<Currency> copy = Lists.array();
            copy.addAll(collection);

            if (copy.equals(this)) {
                currencyList = this;
            } else {
                currencyList = copy.isEmpty() ?
                    EMPTY :
                    new CurrencyList(copy);
            }
        }

        return currencyList;
    }

    @Override
    public void elementCheck(final Currency currency) {
        // null is allowed
    }

    private final List<Currency> list;

    // HasCsvStringList.................................................................................................

    @Override
    public CsvStringList csvStringList() {
        return CsvStringList.makeCsvStringList(
            this.list,
            (Currency currency) -> null == currency ?
                "" :
                currency.getCurrencyCode()
        );
    }
}
