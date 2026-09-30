# Dashboard de Séries Estatísticas — Oktoberfest

Projeto acadêmico em Python com quatro séries estatísticas sobre a Oktoberfest. Cada série usa um único tipo de gráfico, sempre em duas dimensões, e a planilha Excel traz a tabela antes do respectivo gráfico.

## Associação das séries

| Série | Gráfico | Aba do Excel |
|---|---|---|
| Série Histórica — público por edição em anos selecionados | Linhas | Linhas |
| Série Geográfica — origem dos turistas em 2018 | Barras | Barras |
| Série Específica — cinco pratos mais vendidos em 2015 | Setores | Setores |
| Série Conjugada — turistas e moradores por edição | Colunas | Colunas |

Os valores foram obtidos em publicações oficiais da Prefeitura de Blumenau e da Oktoberfest Blumenau. Categorias residuais calculadas por diferença estão identificadas nas notas das tabelas.

## Como executar no Windows

```powershell
cd projeto_oktoberfest
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
streamlit run app.py
```

A planilha pronta está em `output/series_oktoberfest.xlsx`.

## Estrutura

- `app.py`: dashboard Streamlit.
- `dados.py`: dados usados pelo dashboard.
- `requirements.txt`: dependências Python.
- `scripts/gerar_planilha.mjs`: gerador reproduzível da planilha.
- `output/series_oktoberfest.xlsx`: entrega em Excel.
