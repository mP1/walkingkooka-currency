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

import walkingkooka.collect.iterator.Iterators;
import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.set.ImmutableSortedSetDefaults;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.text.CharacterConstant;
import walkingkooka.text.HasText;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * An immutable {@link Set} containing unique {@link CurrencyExchange}.
 */
public final class CurrencyExchangeSet extends AbstractSet<CurrencyExchange>
    implements ImmutableSortedSetDefaults<CurrencyExchangeSet, CurrencyExchange>,
    HasText,
    TreePrintable {

    /**
     * An empty {@link CurrencyExchangeSet}.
     */
    public static final CurrencyExchangeSet EMPTY = new CurrencyExchangeSet(SortedSets.empty());

    /**
     * The comma which separates the CSV text representation.
     */
    public static final CharacterConstant SEPARATOR = CharacterConstant.COMMA;

    // @VisibleForTesting
    static CurrencyExchangeSet with(final SortedSet<CurrencyExchange> currencies) {
        return currencies.isEmpty() ?
            EMPTY :
            new CurrencyExchangeSet(currencies);
    }

    private CurrencyExchangeSet(final SortedSet<CurrencyExchange> currencyExchanges) {
        super();
        this.currencyExchanges = currencyExchanges;
    }

    // ImmutableSortedSet...............................................................................................

    @Override
    public Iterator<CurrencyExchange> iterator() {
        return Iterators.readOnly(
            this.currencyExchanges.iterator()
        );
    }

    @Override
    public int size() {
        return this.currencyExchanges.size();
    }

    @Override
    public Comparator<CurrencyExchange> comparator() {
        return null;
    }

    @Override
    public CurrencyExchangeSet subSet(final CurrencyExchange from,
                                      final CurrencyExchange to) {
        return with(
            this.currencyExchanges.subSet(
                from,
                to
            )
        );
    }

    @Override
    public CurrencyExchangeSet headSet(final CurrencyExchange locale) {
        return with(
            this.currencyExchanges.headSet(locale)
        );
    }

    @Override
    public CurrencyExchangeSet tailSet(final CurrencyExchange locale) {
        return with(
            this.currencyExchanges.tailSet(locale)
        );
    }

    @Override
    public CurrencyExchange first() {
        return this.currencyExchanges.first();
    }

    @Override
    public CurrencyExchange last() {
        return this.currencyExchanges.last();
    }

    @Override
    public SortedSet<CurrencyExchange> toSet() {
        return new TreeSet<>(this.currencyExchanges);
    }

    @Override
    public CurrencyExchangeSet setElements(final Collection<CurrencyExchange> currencyExchanges) {
        final CurrencyExchangeSet currencyExchangeSet;

        if (currencyExchanges instanceof CurrencyExchangeSet) {
            currencyExchangeSet = (CurrencyExchangeSet) currencyExchanges;
        } else {
            final TreeSet<CurrencyExchange> copy = new TreeSet<>(
                Objects.requireNonNull(currencyExchanges, "currencies")
            );
            currencyExchangeSet = this.currencyExchanges.equals(copy) ?
                this :
                with(copy);
        }

        return currencyExchangeSet;
    }

    private final SortedSet<CurrencyExchange> currencyExchanges;

    @Override
    public void elementCheck(final CurrencyExchange currencyExchange) {
        Objects.requireNonNull(currencyExchange, "currencyExchange");
    }

    // parse............................................................................................................

    public static CurrencyExchangeSet parse(final String text) {
        final SortedSet<CurrencyExchange> currencyExchanges = SortedSets.tree();

        SEPARATOR.parse(
            text,
            (final String currencyExchange) -> currencyExchanges.add(
                CurrencyExchange.parse(
                    currencyExchange.trim()
                )
            )
        );

        return with(currencyExchanges);
    }

    // HasText..........................................................................................................

    @Override
    public String text() {
        return CsvStringList.EMPTY.setElements(
            this.currencyExchanges.stream()
                .map(CurrencyExchange::text)
                .collect(Collectors.toList())
        ).text();
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
        printer.indent();
        {
            for (final CurrencyExchange currencyExchange : this) {
                printer.println(currencyExchange.text());
            }
        }
        printer.outdent();
    }
}
