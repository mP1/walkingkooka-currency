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
import walkingkooka.collect.list.HasCsvStringListTsvStringList;
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
import walkingkooka.text.CharSequences;
import walkingkooka.text.HasMultiLineText;
import walkingkooka.text.MultiLineText;
import walkingkooka.text.TextContext;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A {@link List} that holds nullable {@link Currency currencies}.
 */
public final class CurrencyList extends AbstractList<Currency>
    implements ImmutableListDefaults<CurrencyList, Currency>,
    HasCsvStringListTsvStringList,
    HasMultiLineText {

    public final static CurrencyList EMPTY = new CurrencyList(Lists.empty());

    /**
     * Accepts a {@link StringLikeList} converting non null and non empty into a {@link Currency}. Null or empty strings
     * will become null {@link Currency}.
     */
    public static CurrencyList fromStringLikeList(final StringLikeList strings) {
        Objects.requireNonNull(strings, "strings");

        return EMPTY.setElements(
            strings.stream()
                .map(
                    (final String string) -> CharSequences.isNullOrEmpty(string) ?
                        null :
                        Currency.getInstance(string)
                ).collect(Collectors.toList())
        );
    }

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
        final CurrencyList currencyList;

        if (collection instanceof CurrencyList) {
            currencyList = (CurrencyList) collection;
        } else {
            final List<Currency> copy = Lists.array();
            copy.addAll(collection);

            currencyList = this.equals(copy) ?
                this :
                this.replaceCurrency(copy);
        }

        return currencyList;
    }

    @Override
    public CurrencyList subList(final int from,
                              final int to) {
        return 0 == from && this.size() == to ?
            this :
            this.replaceCurrency(
                this.list.subList(
                    from,
                    to
                )
            );
    }

    private CurrencyList replaceCurrency(final List<Currency> currencys) {
        return currencys.isEmpty() ?
            EMPTY :
            new CurrencyList(currencys);
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

    // HasMultiLineText.................................................................................................

    @Override
    public MultiLineText multiLineText(final TextContext textContext) {
        return null;
    }
}
