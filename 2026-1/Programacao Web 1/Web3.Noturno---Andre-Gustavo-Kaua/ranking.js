function rankingApp() {
  return {
    carteiraAberta: false,
    mostrarAdicionarSaldo: false,
    valorNovoSaldo: null,
    saldoInicial: Number(localStorage.getItem("bolaoSaldoInicial") || 500),
    aviso: "",
    filtro: "maisApostou",
    busca: "",

    usuarios: [
      {
        id: 1,
        nome: "Você",
        totalApostado: 0,
        valorAcertos: 0,
        valorErros: 0,
        acertos: 0,
        erros: 0,
        atual: true
      },
      {
        id: 2,
        nome: "Lucas Ferreira",
        totalApostado: 420,
        valorAcertos: 260,
        valorErros: 160,
        acertos: 8,
        erros: 5,
        atual: false
      },
      {
        id: 3,
        nome: "Marina Costa",
        totalApostado: 360,
        valorAcertos: 300,
        valorErros: 60,
        acertos: 10,
        erros: 2,
        atual: false
      },
      {
        id: 4,
        nome: "Rafael Nunes",
        totalApostado: 290,
        valorAcertos: 110,
        valorErros: 180,
        acertos: 4,
        erros: 7,
        atual: false
      },
      {
        id: 5,
        nome: "Bianca Almeida",
        totalApostado: 180,
        valorAcertos: 90,
        valorErros: 90,
        acertos: 3,
        erros: 3,
        atual: false
      }
    ],

    rankingOrdenado() {
      const criterio = {
        maisApostou: "totalApostado",
        maisAcertou: "valorAcertos",
        maisErrou: "valorErros"
      }[this.filtro];

      return [...this.usuarios]
        .sort((a, b) => b[criterio] - a[criterio])
        .map((usuario, index) => ({
          ...usuario,
          posicao: index + 1
        }));
    },

    rankingFiltrado() {
      const termo = this.busca.trim().toLowerCase();

      if (!termo) {
        return this.rankingOrdenado();
      }

      return this.rankingOrdenado().filter((usuario) =>
        usuario.nome.toLowerCase().includes(termo)
      );
    },

    podio() {
      return this.rankingOrdenado().slice(0, 3);
    },

    posicaoUsuario() {
      const usuario = this.rankingOrdenado().find((item) => item.atual);
      return usuario ? usuario.posicao : "-";
    },

    resumoUsuario() {
      const usuario = this.usuarios.find((item) => item.atual);

      if (!usuario || usuario.totalApostado === 0) {
        return "Você ainda não possui apostas finalizadas.";
      }

      return `${this.formatarMoeda(usuario.totalApostado)} apostados nesta semana`;
    },

    apostasSalvas() {
      try {
        return JSON.parse(localStorage.getItem("bolaoApostas") || "[]");
      } catch {
        return [];
      }
    },

    saldoApostado() {
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

    descricaoFiltro() {
      const textos = {
        maisApostou: "Classificação por maior valor total apostado.",
        maisAcertou: "Classificação por maior valor apostado em palpites vencedores.",
        maisErrou: "Classificação por maior valor apostado em palpites perdedores."
      };

      return textos[this.filtro];
    },

    textoPrincipal(usuario) {
      const textos = {
        maisApostou: `${this.formatarMoeda(usuario.totalApostado)} apostados`,
        maisAcertou: `${this.formatarMoeda(usuario.valorAcertos)} em acertos`,
        maisErrou: `${this.formatarMoeda(usuario.valorErros)} em erros`
      };

      return textos[this.filtro];
    },

    medalha(posicao) {
      const medalhas = {
        1: "🥇",
        2: "🥈",
        3: "🥉"
      };

      return medalhas[posicao] || "";
    },

    iniciais(nome) {
      return nome
        .split(" ")
        .map((parte) => parte[0])
        .join("")
        .slice(0, 2)
        .toUpperCase();
    },

    formatarMoeda(valor) {
      return new Intl.NumberFormat("pt-BR", {
        style: "currency",
        currency: "BRL"
      }).format(valor);
    },

    mostrarAviso(mensagem) {
      this.aviso = mensagem;
      setTimeout(() => { this.aviso = ""; }, 2600);
    }
  };
}
