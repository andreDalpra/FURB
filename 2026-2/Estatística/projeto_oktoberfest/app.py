from pathlib import Path
from html import escape
from importlib import reload

import pandas as pd
import streamlit as st

import dados


# O servidor do Streamlit preserva módulos importados durante o hot reload.
# Recarregar os dados evita que o app novo use colunas de uma versão anterior.
dados = reload(dados)


ROOT = Path(__file__).resolve().parent
EXCEL_PATH = ROOT / "output" / "series_oktoberfest.xlsx"
VINHO = "#7B1E2B"
DOURADO = "#E49B0F"
VERDE = "#1F5D42"
AZUL = "#3A6EA5"
CORES = [VINHO, DOURADO, VERDE, AZUL, "#9B5DE5", "#607D8B"]

st.set_page_config(page_title="Séries da Oktoberfest", page_icon="🍺", layout="wide")

st.markdown(
    """
    <style>
    .stApp { background: #ffffff; }
    .block-container { max-width: 1420px; padding-top: 2rem; padding-bottom: 3rem; }
    [data-testid="stMetric"] {
        background: #ffffff; border: 1px solid #eadfc8; border-left: 5px solid #7B1E2B;
        border-radius: 10px; padding: 14px 16px; box-shadow: 0 3px 12px rgba(80,45,20,.06);
    }
    [data-testid="stDataFrame"] {
        border: 1px solid #d8ccb4; border-radius: 10px; overflow: hidden;
        box-shadow: 0 3px 12px rgba(80,45,20,.06);
    }
    h1, h2, h3 { color: #5f1721; }
    </style>
    """,
    unsafe_allow_html=True,
)


def formatar_valor(valor, formato: str) -> str:
    if formato == "ano":
        return str(int(valor))
    if formato == "inteiro":
        return f"{int(valor):,}".replace(",", ".")
    if formato == "percentual":
        return f"{float(valor):.1f}%".replace(".", ",")
    return escape(str(valor))


def tabela_formatada(
    df: pd.DataFrame,
    formatos: dict[str, str],
    fontes: list[tuple[str, str]],
    observacao: str,
    numero: int,
    titulo: str,
    unidade: str,
) -> None:
    """Renderiza uma tabela estatística conforme a estrutura recomendada pelo IBGE."""
    cabecalho = "".join(f"<th>{escape(str(coluna))}</th>" for coluna in df.columns)
    linhas = []
    for _, registro in df.iterrows():
        celulas = []
        for coluna in df.columns:
            numerica = pd.api.types.is_numeric_dtype(df[coluna])
            classe = "numero" if numerica else "texto"
            valor = formatar_valor(registro[coluna], formatos.get(coluna, "texto"))
            celulas.append(f'<td class="{classe}">{valor}</td>')
        linhas.append(f"<tr>{''.join(celulas)}</tr>")

    links = " · ".join(
        f'<a href="{escape(url)}" target="_blank" rel="noopener noreferrer">{escape(nome)}</a>'
        for nome, url in fontes
    )
    st.html(
        f"""
        <figure class="okt-table-wrap" aria-label="{escape(titulo)}">
          <div class="okt-table-unit">Unidade: {escape(unidade)}</div>
          <table class="okt-table">
            <caption><strong>Tabela {numero}</strong> – {escape(titulo)}</caption>
            <thead><tr>{cabecalho}</tr></thead>
            <tbody>{''.join(linhas)}</tbody>
          </table>
          <div class="okt-source">
            <strong>Fonte:</strong> {links}.<br>
            <span>{escape(observacao)}</span>
          </div>
        </figure>
        <style>
          .okt-table-wrap {{
            width: min(100%, 840px); margin: 1rem auto 2rem; background: transparent;
            font-family: Arial, sans-serif; color: #222;
          }}
          .okt-table-unit {{ color: #444; font-size: 12px; text-align: right; margin-bottom: 3px; }}
          .okt-table {{ width: 100%; border-collapse: collapse; table-layout: auto; border-top: 2px solid #6d1724; border-bottom: 2px solid #6d1724; }}
          .okt-table caption {{
            caption-side: top; color: #222; font-size: 15px; line-height: 1.4;
            font-weight: 400; text-align: left; padding: 0 0 8px;
          }}
          .okt-table th {{
            background: #eee9df; color: #222; padding: 9px 12px; text-align: center;
            font-size: 13px; font-weight: 700; border-bottom: 1px solid #8b7a60;
          }}
          .okt-table th:first-child, .okt-table td:first-child {{ text-align: left; width: 42%; }}
          .okt-table td {{ padding: 8px 12px; border-bottom: 1px solid #ddd7cd; color: #222; font-size: 13px; }}
          .okt-table tbody tr:last-child td {{ border-bottom: 0; }}
          .okt-table td.texto {{ text-align: left; }}
          .okt-table td.numero {{ text-align: right; font-variant-numeric: tabular-nums; font-weight: 400; }}
          .okt-source {{ padding-top: 7px; color: #45413b; font-size: 11px; line-height: 1.45; }}
          .okt-source a {{ color: #6d1724; font-weight: 700; text-decoration: none; }}
          .okt-source a:hover {{ text-decoration: underline; }}
          .okt-source span {{ color: #6c6254; }}
          @media (max-width: 720px) {{
            .okt-table-wrap {{ width: 100%; }}
            .okt-table th, .okt-table td {{ padding: 8px 7px; font-size: 12px; overflow-wrap: anywhere; }}
          }}
        </style>
        """
    )


def base_echarts() -> dict:
    return {
        "animationDuration": 700,
        "color": CORES,
        "textStyle": {"fontFamily": "Arial", "color": "#333333"},
        "toolbox": {
            "right": 18,
            "feature": {
                "saveAsImage": {"title": "Salvar imagem", "pixelRatio": 2},
                "restore": {"title": "Restaurar"},
            },
        },
    }


def grafico_linhas(df: pd.DataFrame) -> dict:
    opcao = base_echarts()
    opcao.update({
        "tooltip": {"trigger": "axis"},
        "grid": {"left": 75, "right": 35, "top": 55, "bottom": 55},
        "xAxis": {
            "type": "category", "name": "Ano", "nameLocation": "middle", "nameGap": 34,
            "boundaryGap": False, "data": df["Ano"].astype(str).tolist(),
            "axisLine": {"lineStyle": {"color": "#9c8c75"}},
        },
        "yAxis": {
            "type": "value", "name": "Visitantes", "nameGap": 45,
            "splitLine": {"lineStyle": {"type": "dashed", "color": "#ddd4c4"}},
        },
        "series": [{
            "name": "Público total", "type": "line",
            "data": df["Público total"].astype(int).tolist(),
            "symbol": "circle", "symbolSize": 10,
            "lineStyle": {"width": 4, "color": VINHO},
            "itemStyle": {"color": VINHO, "borderColor": "#ffffff", "borderWidth": 2},
            "areaStyle": {"color": "rgba(123,30,43,0.10)"},
            "emphasis": {"focus": "series"},
        }],
    })
    return opcao


def grafico_barras(df: pd.DataFrame) -> dict:
    dados = df.sort_values("Participação (%)", ascending=True)
    opcao = base_echarts()
    opcao.update({
        "tooltip": {"trigger": "axis", "axisPointer": {"type": "shadow"}},
        "grid": {"left": 155, "right": 75, "top": 55, "bottom": 45},
        "xAxis": {
            "type": "value", "name": "Participação (%)", "max": 45,
            "splitLine": {"lineStyle": {"type": "dashed", "color": "#ddd4c4"}},
        },
        "yAxis": {"type": "category", "data": dados["Origem dos turistas"].tolist(), "axisTick": {"show": False}},
        "series": [{
            "name": "Participação", "type": "bar", "data": dados["Participação (%)"].astype(float).tolist(),
            "barMaxWidth": 42, "itemStyle": {"color": VERDE, "borderRadius": [0, 7, 7, 0]},
            "label": {"show": True, "position": "right", "formatter": "{c}%"},
            "emphasis": {"focus": "series"},
        }],
    })
    return opcao


def grafico_setores(df: pd.DataFrame) -> dict:
    opcao = base_echarts()
    opcao.update({
        "tooltip": {"trigger": "item", "formatter": "{b}<br/>{c} unidades ({d}%)"},
        "legend": {"type": "scroll", "orient": "horizontal", "bottom": 5},
        "series": [{
            "name": "Pratos vendidos", "type": "pie", "radius": [0, "68%"],
            "center": ["50%", "46%"], "avoidLabelOverlap": True,
            "data": [
                {"name": linha["Prato"], "value": int(linha["Unidades vendidas"])}
                for _, linha in df.iterrows()
            ],
            "label": {"show": True, "position": "inside", "formatter": "{d}%", "fontSize": 13, "fontWeight": "bold", "color": "#ffffff"},
            "labelLine": {"show": False},
            "itemStyle": {"borderColor": "#ffffff", "borderWidth": 3},
            "emphasis": {
                "scale": True, "scaleSize": 8,
                "itemStyle": {"shadowBlur": 15, "shadowColor": "rgba(0,0,0,0.22)"},
            },
        }],
    })
    return opcao


def grafico_colunas(df: pd.DataFrame) -> dict:
    opcao = base_echarts()
    opcao.update({
        "tooltip": {"trigger": "axis", "axisPointer": {"type": "shadow"}},
        "legend": {"top": 10, "left": "center"},
        "grid": {"left": 75, "right": 35, "top": 65, "bottom": 55},
        "xAxis": {
            "type": "category", "name": "Ano", "nameLocation": "middle", "nameGap": 34,
            "data": df["Ano"].astype(str).tolist(),
        },
        "yAxis": {
            "type": "value", "name": "Pessoas",
            "splitLine": {"lineStyle": {"type": "dashed", "color": "#ddd4c4"}},
        },
        "series": [
            {
                "name": "Turistas", "type": "bar", "data": df["Turistas"].astype(int).tolist(),
                "barMaxWidth": 55, "itemStyle": {"color": DOURADO, "borderRadius": [6, 6, 0, 0]},
                "emphasis": {"focus": "series"},
            },
            {
                "name": "Moradores de Blumenau", "type": "bar", "data": df["Moradores de Blumenau"].astype(int).tolist(),
                "barMaxWidth": 55, "itemStyle": {"color": VINHO, "borderRadius": [6, 6, 0, 0]},
                "emphasis": {"focus": "series"},
            },
        ],
    })
    return opcao


st.title("Séries Estatísticas da Oktoberfest")
st.caption("Quatro séries, quatro gráficos 2D distintos. Dados acadêmicos simulados.")

hist = dados.serie_historica()
geo = dados.serie_geografica()
esp = dados.serie_especifica()
conj = dados.serie_conjugada()

c1, c2, c3, c4 = st.columns(4)
c1.metric("Maior público anual", f"{hist['Público total'].max():,.0f}".replace(",", "."))
c2.metric("Origens analisadas", len(geo))
c3.metric("Prato mais vendido", esp.loc[esp["Unidades vendidas"].idxmax(), "Prato"])
c4.metric(
    "Turistas em 2024",
    f"{conj.loc[conj['Ano'] == 2024, 'Turistas'].iloc[0]:,.0f}".replace(",", "."),
)

if EXCEL_PATH.exists():
    st.download_button(
        "Baixar planilha Excel", data=EXCEL_PATH.read_bytes(), file_name=EXCEL_PATH.name,
        mime="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", type="primary",
    )

abas = st.tabs(["Histórica · Linhas", "Geográfica · Barras", "Específica · Setores", "Conjugada · Colunas"])

with abas[0]:
    st.subheader("Série Histórica — público por edição")
    tabela_formatada(
        hist,
        {"Ano": "ano", "Público total": "inteiro"},
        [
            ("Prefeitura de Blumenau — balanço de 2015", "https://www.blumenau.sc.gov.br/secao/noticias/16734"),
            ("Oktoberfest Blumenau — balanço de 2022", "https://oktoberfestblumenau.com.br/2022/10/28/37a-oktoberfest-blumenau-supera-expectativas/"),
            ("Balanço oficial de 2023", "https://oktoberfestblumenau.com.br/2023/12/18/oktoberfest-2023-apresenta-receita-recorde-e-segundo-maior-superavit-da-historia/"),
            ("Prefeitura de Blumenau — balanço de 2024", "https://www.blumenau.sc.gov.br/secao/noticias/108809"),
        ],
        "Anos selecionados. Em 2020 e 2021 não houve edição por causa da pandemia. Os valores de 2017 e 2019 são apresentados com o arredondamento publicado pelas fontes oficiais.",
        1,
        "Público da Oktoberfest Blumenau, em anos selecionados — Blumenau — 2015–2024",
        "pessoas",
    )
    st.markdown("#### Gráfico de linhas")
    st.echarts_chart(grafico_linhas(hist), height=520, key="grafico_historico")

with abas[1]:
    st.subheader("Série Geográfica — origem dos turistas")
    tabela_formatada(
        geo,
        {"Participação (%)": "percentual"},
        [("Prefeitura de Blumenau — pesquisa de público de 2018", "https://www.blumenau.sc.gov.br/secao/noticias/44497")],
        "Percentuais referentes somente aos turistas entrevistados. A categoria residual foi calculada por diferença para completar 100%.",
        2,
        "Distribuição dos turistas da Oktoberfest segundo a origem — Blumenau — 2018",
        "% dos turistas entrevistados",
    )
    st.markdown("#### Gráfico de barras")
    st.echarts_chart(grafico_barras(geo), height=520, key="grafico_geografico")

with abas[2]:
    st.subheader("Série Específica — pratos mais vendidos")
    tabela_formatada(
        esp,
        {"Unidades vendidas": "inteiro"},
        [("Prefeitura de Blumenau — balanço da Oktoberfest 2015", "https://www.blumenau.sc.gov.br/secao/noticias/16734")],
        "A tabela apresenta quantidades absolutas. As participações percentuais são calculadas e exibidas somente no gráfico de setores.",
        3,
        "Cinco pratos mais vendidos na Oktoberfest — Blumenau — 2015",
        "unidades vendidas",
    )
    st.markdown("#### Gráfico de setores")
    st.echarts_chart(grafico_setores(esp), height=560, key="grafico_especifico")

with abas[3]:
    st.subheader("Série Conjugada — turistas e moradores por edição")
    tabela_formatada(
        conj,
        {"Ano": "ano", "Turistas": "inteiro", "Moradores de Blumenau": "inteiro"},
        [
            ("Oktoberfest Blumenau — pesquisas de 2022 e 2023", "https://oktoberfestblumenau.com.br/2023/12/18/oktoberfest-2023-apresenta-receita-recorde-e-segundo-maior-superavit-da-historia/"),
            ("Prefeitura de Blumenau — pesquisa de 2024", "https://www.blumenau.sc.gov.br/secao/noticias/108809"),
        ],
        "Valores estimados pela aplicação dos percentuais oficiais de turistas e moradores ao público total de cada edição; diferenças eventuais decorrem do arredondamento.",
        4,
        "Distribuição do público da Oktoberfest por residência — Blumenau — 2022–2024",
        "pessoas (estimativa)",
    )
    st.markdown("#### Gráfico de colunas")
    st.echarts_chart(grafico_colunas(conj), height=520, key="grafico_conjugado")

st.caption("Tema: Oktoberfest · Gráficos planificados (2D) · Cada tipo de gráfico utilizado uma única vez")
