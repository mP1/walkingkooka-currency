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
import walkingkooka.reflect.PublicClassTesting;

public final class CanHaveCurrencyValueTest implements PublicClassTesting<CanHaveCurrencyValue> {

    @Test
    public void testMaybeShouldHaveCurrencyValueWithNull() {
        this.maybeShouldHaveCurrencyValueAndCheck(
            null,
            false
        );
    }


    @Test
    public void testMaybeShouldHaveCurrencyValueWithCanHaveCurrencyValueTrue() {
        this.maybeShouldHaveCurrencyValueAndCheck(
            new CanHaveCurrencyValue() {
                @Override
                public boolean maybeShouldHaveCurrencyValue() {
                    return true;
                }
            },
            true
        );
    }

    @Test
    public void testMaybeShouldHaveCurrencyValueWithCanHaveCurrencyValueFalse() {
        this.maybeShouldHaveCurrencyValueAndCheck(
            new CanHaveCurrencyValue() {
                @Override
                public boolean maybeShouldHaveCurrencyValue() {
                    return false;
                }
            },
            false
        );
    }

    private void maybeShouldHaveCurrencyValueAndCheck(final CanHaveCurrencyValue can,
                                                      final boolean expected) {
        this.checkEquals(
            expected,
            can
        );
    }

    // class............................................................................................................

    @Override
    public Class<CanHaveCurrencyValue> type() {
        return CanHaveCurrencyValue.class;
    }
}
