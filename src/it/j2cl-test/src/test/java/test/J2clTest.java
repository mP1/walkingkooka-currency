package test;


import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Assert;
import org.junit.Test;

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.Sets;
import walkingkooka.color.Color;
import walkingkooka.convert.Converters;
import walkingkooka.convert.provider.ConverterProvider;
import walkingkooka.convert.provider.ConverterSelector;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.environment.AuditInfo;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.locale.LocaleContext;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberSymbols;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.spreadsheet.SpreadsheetStrings;
import walkingkooka.spreadsheet.color.SpreadsheetColors;
import walkingkooka.spreadsheet.compare.provider.SpreadsheetComparatorProviders;
import walkingkooka.spreadsheet.convert.SpreadsheetConverterContext;
import walkingkooka.spreadsheet.convert.provider.SpreadsheetConvertersConverterProviders;
import walkingkooka.spreadsheet.engine.FakeSpreadsheetEngineContext;
import walkingkooka.spreadsheet.engine.SpreadsheetDelta;
import walkingkooka.spreadsheet.engine.SpreadsheetEngine;
import walkingkooka.spreadsheet.engine.SpreadsheetEngineContext;
import walkingkooka.spreadsheet.engine.SpreadsheetEngines;
import walkingkooka.spreadsheet.engine.SpreadsheetMetadataMode;
import walkingkooka.spreadsheet.export.provider.SpreadsheetExporterProviders;
import walkingkooka.spreadsheet.expression.FakeSpreadsheetExpressionEvaluationContext;
import walkingkooka.spreadsheet.expression.SpreadsheetExpressionEvaluationContext;
import walkingkooka.spreadsheet.expression.SpreadsheetExpressionEvaluationContexts;
import walkingkooka.spreadsheet.format.pattern.SpreadsheetPattern;
import walkingkooka.spreadsheet.format.provider.SpreadsheetFormatterProvider;
import walkingkooka.spreadsheet.format.provider.SpreadsheetFormatterProviders;
import walkingkooka.spreadsheet.format.provider.SpreadsheetFormatterSelector;
import walkingkooka.spreadsheet.formula.SpreadsheetFormula;
import walkingkooka.spreadsheet.formula.SpreadsheetFormulaParsers;
import walkingkooka.spreadsheet.formula.parser.SpreadsheetFormulaParserToken;
import walkingkooka.spreadsheet.importer.provider.SpreadsheetImporterProviders;
import walkingkooka.spreadsheet.meta.SpreadsheetId;
import walkingkooka.spreadsheet.meta.SpreadsheetMetadata;
import walkingkooka.spreadsheet.meta.SpreadsheetMetadataPropertyName;
import walkingkooka.spreadsheet.meta.store.SpreadsheetMetadataStores;
import walkingkooka.spreadsheet.parser.SpreadsheetParser;
import walkingkooka.spreadsheet.parser.provider.SpreadsheetParserProvider;
import walkingkooka.spreadsheet.parser.provider.SpreadsheetParserProviders;
import walkingkooka.spreadsheet.parser.provider.SpreadsheetParserSelector;
import walkingkooka.spreadsheet.provider.SpreadsheetProviders;
import walkingkooka.spreadsheet.reference.SpreadsheetExpressionReferenceLoader;
import walkingkooka.spreadsheet.reference.SpreadsheetLabelNameResolver;
import walkingkooka.spreadsheet.reference.SpreadsheetLabelNameResolvers;
import walkingkooka.spreadsheet.reference.SpreadsheetSelection;
import walkingkooka.spreadsheet.store.repo.SpreadsheetStoreRepositories;
import walkingkooka.spreadsheet.store.repo.SpreadsheetStoreRepository;
import walkingkooka.spreadsheet.value.SpreadsheetCell;
import walkingkooka.storage.HasUserDirectorieses;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.text.cursor.parser.Parsers;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionEvaluationContexts;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;
import walkingkooka.tree.expression.function.provider.ExpressionFunctionProviders;
import walkingkooka.tree.text.Length;
import walkingkooka.tree.text.TextNode;
import walkingkooka.tree.text.TextStyle;
import walkingkooka.tree.text.TextStylePropertyName;
import walkingkooka.validation.form.provider.FormHandlerProviders;
import walkingkooka.validation.provider.ValidatorProviders;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

// copied parse Sample
@J2clTestInput(J2clTest.class)
public class J2clTest {

    @Test
    public void testAssertEquals() {
        checkEquals(
                "",
                ""
        );
    }
}


