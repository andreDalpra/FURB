import fs from "node:fs/promises";
import path from "node:path";
import { fileURLToPath } from "node:url";
import { SpreadsheetFile, Workbook } from "@oai/artifact-tool";

const here = path.dirname(fileURLToPath(import.meta.url));
const root = path.resolve(here, "..");
const outputDir = path.join(root, "output");
const previewDir = path.join(root, "previews");
const font = "Arial";
const palette = {
  vinho: "#7B1E2B",
  dourado: "#E49B0F",
  verde: "#1F5D42",
  azul: "#3A6EA5",
  creme: "#FFF5DE",
  texto: "#2E2E2E",
  grade: "#D9CDB7",
};

const workbook = Workbook.create();

const specs = [
  {
    sheet: "Linhas",
    series: "Série Histórica",
    title: "Público da Oktoberfest por edição",
    description: "Evolução do público total ao longo das edições.",
    headers: ["Ano", "Público total"],
    rows: [[2015,473196],[2017,568000],[2019,576500],[2022,634701],[2023,454285],[2024,579222]],
    source: "Fonte: Prefeitura de Blumenau e Oktoberfest Blumenau, balanços oficiais de 2015, 2022, 2023 e 2024.",
    chartType: "line",
    chartTitle: "Evolução do público da Oktoberfest",
    color: palette.vinho,
  },
  {
    sheet: "Barras",
    series: "Série Geográfica",
    title: "Origem dos turistas da Oktoberfest em 2018",
    description: "Distribuição geográfica dos turistas entrevistados.",
    headers: ["Origem dos turistas", "Participação (%)"],
    rows: [["Santa Catarina",40.0],["São Paulo",19.0],["Paraná",11.3],["Outros estados/países",29.7]],
    source: "Fonte: Prefeitura de Blumenau, pesquisa de público da Oktoberfest 2018.",
    chartType: "bar",
    chartTitle: "Origem geográfica dos turistas em 2018",
    color: palette.verde,
  },
  {
    sheet: "Setores",
    series: "Série Específica",
    title: "Cinco pratos mais vendidos na Oktoberfest em 2015",
    description: "Distribuição específica das vendas por tipo de prato.",
    headers: ["Prato", "Unidades vendidas"],
    rows: [["Batata recheada",73664],["Espetinho",31362],["Bratwurst",20647],["Cachorro-quente",20563],["Waffel",13862]],
    source: "Fonte: Prefeitura de Blumenau, balanço da Oktoberfest 2015.",
    chartType: "pie",
    chartTitle: "Participação dos cinco pratos mais vendidos",
    color: palette.dourado,
  },
  {
    sheet: "Colunas",
    series: "Série Conjugada",
    title: "Turistas e moradores na Oktoberfest por edição",
    description: "Combinação de período e categoria de residência.",
    headers: ["Ano", "Turistas", "Moradores de Blumenau"],
    rows: [[2022,396688,238013],[2023,233502,220783],[2024,304671,274551]],
    source: "Fonte: Oktoberfest Blumenau (2022–2023) e Prefeitura de Blumenau (2024).",
    chartType: "bar",
    chartTitle: "Turistas e moradores por edição",
    color: palette.dourado,
    columnDirection: true,
  },
];

for (const spec of specs) workbook.worksheets.add(spec.sheet);

for (const spec of specs) {
  const sheet = workbook.worksheets.getItem(spec.sheet);
  sheet.showGridLines = false;
  sheet.tabColor = spec.color;

  sheet.getRange("A2:H2").merge();
  sheet.getRange("A2").values = [[spec.title]];
  sheet.getRange("A2:H2").format = { font: { name: font, size: 15, bold: true, color: palette.vinho }, verticalAlignment: "center" };
  sheet.getRange("A3:H3").merge();
  sheet.getRange("A3").values = [[`${spec.series} · ${spec.description}`]];
  sheet.getRange("A3:H3").format = { font: { name: font, size: 10, italic: true, color: "#666666" } };
  sheet.getRange("A5:B5").values = [["Classificação", spec.series]];
  sheet.getRange("A5:B5").format.font = { name: font, size: 10, bold: true, color: palette.texto };

  const rowCount = spec.rows.length + 1;
  const colCount = spec.headers.length;
  const endRow = 7 + spec.rows.length;
  const endCol = String.fromCharCode(64 + colCount);
  const matrix = [spec.headers, ...spec.rows];
  sheet.getRange(`A7:${endCol}${endRow}`).values = matrix;
  const table = sheet.tables.add(`A7:${endCol}${endRow}`, true, `${spec.sheet}Tabela`);
  table.style = "TableStyleMedium2";
  table.showBandedRows = true;

  sheet.getRange(`A7:${endCol}${endRow}`).format.font = { name: font, size: 10, color: palette.texto };
  sheet.getRange(`A7:${endCol}7`).format = {
    fill: palette.vinho,
    font: { name: font, size: 10, bold: true, color: "#FFFFFF" },
    horizontalAlignment: "center",
    verticalAlignment: "center",
  };
  sheet.getRange(`B8:${endCol}${endRow}`).format.numberFormat = spec.sheet === "Barras" ? "0.0\"%\"" : "#,##0";
  sheet.getRange(`A7:${endCol}${endRow}`).format.borders = { preset: "outside", style: "thin", color: palette.grade };

  const chart = sheet.charts.add(spec.chartType, sheet.getRange(`A7:${endCol}${endRow}`));
  chart.title = spec.chartTitle;
  chart.titleTextStyle.fontSize = 12;
  chart.titleTextStyle.typeface = font;
  chart.legend = { position: "top", textStyle: { typeface: font } };
  chart.setPosition("A16", "H34");
  if (spec.chartType !== "pie" && chart.series.items.length > 0) chart.series.items[0].fill = spec.color;
  if (spec.chartType === "line" && chart.series.items.length > 0) {
    chart.series.items[0].line = { fill: spec.color, style: "solid", width: 2 };
    chart.hasLegend = false;
  }
  if (spec.sheet === "Barras") {
    chart.hasLegend = false;
    chart.barOptions.direction = "bar";
    chart.barOptions.grouping = "clustered";
  }
  if (spec.columnDirection) {
    chart.barOptions.direction = "column";
    chart.barOptions.grouping = "clustered";
    chart.barOptions.gapWidth = 90;
    if (chart.series.items[0]) chart.series.items[0].fill = palette.dourado;
    if (chart.series.items[1]) chart.series.items[1].fill = palette.vinho;
  }
  if (spec.chartType !== "pie") {
    chart.xAxis = { textStyle: { typeface: font, fontSize: 10 } };
    chart.yAxis = { numberFormatCode: spec.sheet === "Barras" ? "0.0\"%\"" : "#,##0", numberFormatSourceLinked: false, textStyle: { typeface: font, fontSize: 10 } };
  }

  sheet.getRange("A36:H36").merge();
  sheet.getRange("A36").values = [[spec.source]];
  sheet.getRange("A36:H36").format.font = { name: font, size: 9, italic: true, color: "#777777" };
  sheet.getRange("A1:H36").format.verticalAlignment = "center";
  sheet.getRange("A:A").format.columnWidth = 24;
  sheet.getRange("B:B").format.columnWidth = 18;
  sheet.getRange("C:C").format.columnWidth = 28;
  sheet.getRange("D:H").format.columnWidth = 14;
  sheet.getRange("2:2").format.rowHeight = 26;
}

workbook.recalculate();
await fs.mkdir(outputDir, { recursive: true });
await fs.mkdir(previewDir, { recursive: true });

for (const spec of specs) {
  const preview = await workbook.render({ sheetName: spec.sheet, range: "A1:H36", scale: 1, format: "png" });
  await fs.writeFile(path.join(previewDir, `${spec.sheet.toLowerCase()}.png`), new Uint8Array(await preview.arrayBuffer()));
}

const inspect = await workbook.inspect({ kind: "sheet,table,drawing", maxChars: 10000, tableMaxRows: 12, tableMaxCols: 6 });
console.log(inspect.ndjson);
const errors = await workbook.inspect({ kind: "match", searchTerm: "#REF!|#DIV/0!|#VALUE!|#NAME\\?|#N/A|#NUM!|#NULL!|#SPILL!|#CALC!", options: { useRegex: true, maxResults: 100 }, summary: "verificação final de fórmulas" });
console.log(errors.ndjson);

const output = await SpreadsheetFile.exportXlsx(workbook);
await output.save(path.join(outputDir, "series_oktoberfest.xlsx"));
console.log(`Planilha criada em ${path.join(outputDir, "series_oktoberfest.xlsx")}`);
