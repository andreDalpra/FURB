class Jogo {
  constructor(dados) {
    this.id = Number(dados.id);
    this.grupo = dados.grupo;
    this.rodada = dados.rodada;
    this.data = dados.data;
    this.hora = dados.hora;
    this.timeCasa = dados.timeCasa;
    this.codigoCasa = dados.codigoCasa;
    this.bandeiraCasa = dados.bandeiraCasa;
    this.timeFora = dados.timeFora;
    this.codigoFora = dados.codigoFora;
    this.bandeiraFora = dados.bandeiraFora;
    this.estadio = dados.estadio;
    this.cidade = dados.cidade;
    this.valor = Number(dados.valor);
    this.odd = Number(dados.odd);
    this.golsCasa = 0;
    this.golsFora = 0;
    this.encerrado = false;
    this.placarCasa = null;
    this.placarFora = null;
    this.gols = [];
    this.inicioApi = null;
    this.detalhesAbertos = false;
  }

  placar() {
    return `${this.golsCasa} x ${this.golsFora}`;
  }

  retornoPossivel() {
    return this.valor * this.odd;
  }

  bandeiraCasaUrl() {
    return criarUrlBandeira(this.codigoCasa);
  }

  bandeiraForaUrl() {
    return criarUrlBandeira(this.codigoFora);
  }

  dataFormatada() {
    const data = this.inicioApi ? new Date(this.inicioApi) : new Date(`${this.data}T${this.hora}:00`);

    return new Intl.DateTimeFormat("pt-BR", {
      day: "2-digit",
      month: "short",
      hour: "2-digit",
      minute: "2-digit"
    }).format(data);
  }

  apostasFechadas() {
    const inicio = this.inicioApi ? new Date(this.inicioApi) : new Date(`${this.data}T${this.hora}:00`);
    return this.encerrado || Date.now() >= inicio.getTime();
  }

  placarFinal() {
    return `${this.placarCasa} x ${this.placarFora}`;
  }

  resumoResultado() {
    if (this.placarCasa === this.placarFora) return "A partida terminou empatada";
    return `${this.placarCasa > this.placarFora ? this.timeCasa : this.timeFora} venceu`;
  }

  totalGolsTexto() {
    const total = this.placarCasa + this.placarFora;
    return `${total} ${total === 1 ? "gol" : "gols"} na partida`;
  }

  textoStatus() {
    if (this.encerrado) return "Encerrado";
    if (this.apostasFechadas()) return "Em andamento";
    return `Grupo ${this.grupo} · Rodada ${this.rodada}`;
  }

  aplicarResultado(evento) {
    const competicao = evento.competitions?.[0];
    const times = competicao?.competitors || [];
    const casa = times.find((time) => time.homeAway === "home");
    const fora = times.find((time) => time.homeAway === "away");

    this.inicioApi = evento.date || null;
    this.encerrado = Boolean(evento.status?.type?.completed);
    if (!this.encerrado || !casa || !fora) return;

    this.placarCasa = Number(casa.score);
    this.placarFora = Number(fora.score);
    this.gols = (competicao.details || [])
      .filter((detalhe) => detalhe.scoringPlay && !detalhe.shootout)
      .map((detalhe) => ({
        jogador: detalhe.athletesInvolved?.[0]?.displayName || "Autor não informado",
        minuto: detalhe.clock?.displayValue || "Minuto não informado",
        time: String(detalhe.team?.id) === String(casa.id) ? this.timeCasa : this.timeFora
      }));
  }
}

class ApiResultados {
  static url(data) {
    return `https://site.api.espn.com/apis/site/v2/sports/soccer/fifa.world/scoreboard?dates=${data.replaceAll("-", "")}`;
  }

  static async buscar(datas) {
    const respostas = await Promise.allSettled(datas.map(async (data) => {
      const resposta = await fetch(this.url(data));
      if (!resposta.ok) throw new Error(`Falha ao buscar resultados de ${data}`);
      return (await resposta.json()).events || [];
    }));

    return respostas.flatMap((resposta) => resposta.status === "fulfilled" ? resposta.value : []);
  }

  static encontrarEvento(jogo, eventos) {
    return eventos.find((evento) => {
      const times = evento.competitions?.[0]?.competitors || [];
      const casa = times.find((time) => time.homeAway === "home")?.team?.abbreviation;
      const fora = times.find((time) => time.homeAway === "away")?.team?.abbreviation;
      return casa === jogo.codigoCasa && fora === jogo.codigoFora;
    });
  }
}

const codigosBandeiras = {
  ARG: "ar",
  ALG: "dz",
  AUS: "au",
  AUT: "at",
  BEL: "be",
  BIH: "ba",
  BRA: "br",
  CAN: "ca",
  CIV: "ci",
  COD: "cd",
  COL: "co",
  CPV: "cv",
  CRO: "hr",
  CUW: "cw",
  CZE: "cz",
  ECU: "ec",
  EGY: "eg",
  ENG: "gb-eng",
  ESP: "es",
  FRA: "fr",
  GER: "de",
  GHA: "gh",
  HAI: "ht",
  IRN: "ir",
  IRQ: "iq",
  JOR: "jo",
  JPN: "jp",
  KOR: "kr",
  KSA: "sa",
  MAR: "ma",
  MEX: "mx",
  NED: "nl",
  NOR: "no",
  NZL: "nz",
  PAN: "pa",
  PAR: "py",
  POR: "pt",
  QAT: "qa",
  RSA: "za",
  SCO: "gb-sct",
  SEN: "sn",
  SUI: "ch",
  SWE: "se",
  TUN: "tn",
  TUR: "tr",
  URU: "uy",
  USA: "us",
  UZB: "uz"
};

function criarUrlBandeira(codigoFifa) {
  const codigo = codigosBandeiras[codigoFifa];
  return `https://flagcdn.com/w80/${codigo}.png`;
}

class Aposta {
  constructor(jogo) {
    this.id = Date.now();
    this.jogoId = jogo.id;
    this.grupo = jogo.grupo;
    this.rodada = jogo.rodada;
    this.timeCasa = jogo.timeCasa;
    this.timeFora = jogo.timeFora;
    this.bandeiraCasa = jogo.bandeiraCasaUrl();
    this.bandeiraFora = jogo.bandeiraForaUrl();
    this.data = jogo.dataFormatada();
    this.placar = jogo.placar();
    this.palpite = jogo.placar();
    this.valor = jogo.valor;
    this.retornoPossivel = jogo.retornoPossivel();
    this.status = "carrinho";
    this.pagamentoId = null;
  }

  resumo() {
    return `${this.timeCasa} ${this.placar} ${this.timeFora}`;
  }
}

class LeitorCsv {
  static converter(textoCsv) {
    const linhas = textoCsv
      .trim()
      .split(/\r?\n/)
      .filter(Boolean);

    const cabecalhos = linhas.shift().split(",");

    return linhas.map((linha) => {
      const valores = linha.split(",");

      return cabecalhos.reduce((dados, cabecalho, indice) => {
        dados[cabecalho] = valores[indice];
        return dados;
      }, {});
    });
  }
}

function bolaoApp() {
  return {
    carteiraAberta: false,
    mostrarAdicionarSaldo: false,
    valorNovoSaldo: null,
    saldoInicial: Number(localStorage.getItem("bolaoSaldoInicial") || 500),
    grupoSelecionado: "todos",
    rodadaSelecionada: "todas",
    situacaoSelecionada: "todos",
    carregando: true,
    erroCarregamento: "",
    aviso: "",
    apostas: [],
    jogos: [],

    async carregarJogos() {
      try {
        localStorage.setItem("bolaoSaldoInicial", String(this.saldoInicial));
        const resposta = await fetch("jogos-grupos.csv");

        if (!resposta.ok) {
          throw new Error("Arquivo jogos-grupos.csv não encontrado.");
        }

        const textoCsv = await resposta.text();
        this.jogos = LeitorCsv.converter(textoCsv).map((dados) => new Jogo(dados));
        await this.carregarResultados();
      } catch (erro) {
        this.erroCarregamento = "Abra a página pelo servidor local para permitir a leitura do arquivo CSV.";
      } finally {
        this.carregando = false;
      }
    },

    async carregarResultados() {
      const hoje = new Date();
      hoje.setHours(23, 59, 59, 999);
      const datas = [...new Set(this.jogos
        .filter((jogo) => new Date(`${jogo.data}T${jogo.hora}:00`) <= hoje)
        .map((jogo) => jogo.data))];

      const eventos = await ApiResultados.buscar(datas);
      this.jogos.forEach((jogo) => {
        const evento = ApiResultados.encontrarEvento(jogo, eventos);
        if (evento) jogo.aplicarResultado(evento);
      });
    },

    grupos() {
      return [...new Set(this.jogos.map((jogo) => jogo.grupo))].sort();
    },

    jogosFiltrados() {
      return this.jogos.filter((jogo) => {
        const passouGrupo = this.grupoSelecionado === "todos" || jogo.grupo === this.grupoSelecionado;
        const passouRodada = this.rodadaSelecionada === "todas" || jogo.rodada === this.rodadaSelecionada;
        const passouSituacao = this.situacaoSelecionada === "todos"
          || (this.situacaoSelecionada === "abertos" && !jogo.apostasFechadas())
          || (this.situacaoSelecionada === "encerrados" && jogo.encerrado);

        return passouGrupo && passouRodada && passouSituacao;
      });
    },

    quantidadeAbertos() {
      return this.jogos.filter((jogo) => !jogo.apostasFechadas()).length;
    },

    quantidadeEncerrados() {
      return this.jogos.filter((jogo) => jogo.encerrado).length;
    },

    apostasSalvas() {
      try {
        return JSON.parse(localStorage.getItem("bolaoApostas") || "[]");
      } catch {
        return [];
      }
    },

    saldoApostado() {
      this.apostas.length;
      return this.apostasSalvas().reduce((total, aposta) => total + Number(aposta.valor || 0), 0);
    },

    saldoDisponivel() {
      return Math.max(0, this.saldoInicial - this.saldoApostado());
    },

    adicionarSaldo() {
      const valor = Math.round(Number(this.valorNovoSaldo) * 100) / 100;

      if (!Number.isFinite(valor) || valor <= 0) {
        this.mostrarAviso("Informe um valor válido maior que zero.");
        return;
      }

      this.saldoInicial = Math.round((this.saldoInicial + valor) * 100) / 100;
      localStorage.setItem("bolaoSaldoInicial", String(this.saldoInicial));
      this.valorNovoSaldo = null;
      this.mostrarAdicionarSaldo = false;
      this.mostrarAviso(`Saldo de ${this.formatarMoeda(valor)} adicionado com sucesso.`);
    },

    apostar(jogo) {
      if (jogo.apostasFechadas()) {
        this.mostrarAviso("Esta partida já começou e não aceita mais apostas.");
        return;
      }

      if (!Number.isInteger(jogo.golsCasa) || !Number.isInteger(jogo.golsFora)) {
        this.mostrarAviso("Informe um placar válido para apostar.");
        return;
      }

      if (jogo.golsCasa < 0 || jogo.golsFora < 0) {
        this.mostrarAviso("O placar não pode ter número negativo.");
        return;
      }

      if (!Number.isFinite(jogo.valor) || jogo.valor <= 0) {
        this.mostrarAviso("Informe um valor de aposta maior que zero.");
        return;
      }

      jogo.valor = Math.round(jogo.valor * 100) / 100;

      if (jogo.valor > this.saldoDisponivel()) {
        this.mostrarAviso(`Saldo insuficiente. Disponível: ${this.formatarMoeda(this.saldoDisponivel())}.`);
        setTimeout(() => { this.carteiraAberta = true; }, 0);
        return;
      }

      const aposta = new Aposta(jogo);
      this.apostas.unshift(aposta);
      this.salvarApostaNoCarrinho(aposta);
      this.mostrarAviso(`Aposta registrada: ${aposta.resumo()}`);
    },

    salvarApostaNoCarrinho(aposta) {
      const apostasSalvas = JSON.parse(localStorage.getItem("bolaoApostas") || "[]");
      apostasSalvas.unshift(aposta);
      localStorage.setItem("bolaoApostas", JSON.stringify(apostasSalvas));
    },

    ultimaApostaTexto() {
      if (this.apostas.length === 0) {
        return "";
      }

      return this.apostas[0].resumo();
    },

    formatarMoeda(valor) {
      return new Intl.NumberFormat("pt-BR", {
        style: "currency",
        currency: "BRL"
      }).format(valor);
    },

    mostrarAviso(mensagem) {
      this.aviso = mensagem;

      setTimeout(() => {
        this.aviso = "";
      }, 2600);
    }
  };
}
