"""Dados acadêmicos simulados para as séries da Oktoberfest."""

import pandas as pd


def serie_historica() -> pd.DataFrame:
    return pd.DataFrame(
        {
            "Ano": [2015, 2017, 2019, 2022, 2023, 2024],
            "Público total": [473196, 568000, 576500, 634701, 454285, 579222],
        }
    )


def serie_geografica() -> pd.DataFrame:
    return pd.DataFrame(
        {
            "Origem dos turistas": ["Santa Catarina", "São Paulo", "Paraná", "Outros estados/países"],
            "Participação (%)": [40.0, 19.0, 11.3, 29.7],
        }
    )


def serie_especifica() -> pd.DataFrame:
    return pd.DataFrame(
        {
            "Prato": ["Batata recheada", "Espetinho", "Bratwurst", "Cachorro-quente", "Waffel"],
            "Unidades vendidas": [73664, 31362, 20647, 20563, 13862],
        }
    )


def serie_conjugada() -> pd.DataFrame:
    return pd.DataFrame(
        {
            "Ano": [2022, 2023, 2024],
            "Turistas": [396688, 233502, 304671],
            "Moradores de Blumenau": [238013, 220783, 274551],
        }
    )
