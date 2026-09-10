function carrinhoApp() {
  return {
    carteiraAberta: false,
    mostrarAdicionarSaldo: false,
    valorNovoSaldo: null,
    saldoInicial: Number(localStorage.getItem("bolaoSaldoInicial") || 500),
    filtro: "todas",
    pagamentoSelecionadoId: null,
    mostrarFormularioPagamento: false,
    apostaDetalhada: null,
    aviso: "",

    novoPagamento: {
      nome: "",
      detalhe: "",
      tipo: "cartao"
    },

    formasPagamento: [],
    apostas: JSON.parse(localStorage.getItem("bolaoApostas") || "[]"),

    apostasFiltradas() {
      if (this.filtro === "todas") {
        return this.apostas;
      }

      return this.apostas.filter((aposta) => aposta.status === this.filtro);
    },

    adicionarFormaPagamento() {
      const icones = {
        cartao: "credit_card",
        pix: "qr_code_2",
        carteira: "account_balance_wallet"
      };

      const formaPagamento = {
        id: Date.now(),
        nome: this.novoPagamento.nome,
        detalhe: this.novoPagamento.detalhe,
        tipo: this.novoPagamento.tipo,
        icone: icones[this.novoPagamento.tipo]
      };

      this.formasPagamento.push(formaPagamento);
      this.pagamentoSelecionadoId = formaPagamento.id;

      this.novoPagamento = {
        nome: "",
        detalhe: "",
        tipo: "cartao"
      };

      this.mostrarFormularioPagamento = false;
      this.mostrarAviso("Forma de pagamento adicionada.");
    },

    finalizarAposta(apostaId) {
      if (!this.pagamentoSelecionadoId) {
        this.mostrarAviso("Cadastre e selecione uma forma de pagamento.");
        return;
      }

      const aposta = this.apostas.find((item) => item.id === apostaId);

      if (!aposta) {
        return;
      }

      aposta.status = "finalizada";
      aposta.pagamentoId = this.pagamentoSelecionadoId;
      this.salvarApostas();

      this.mostrarAviso("Aposta finalizada com sucesso.");
    },

    salvarApostas() {
      localStorage.setItem("bolaoApostas", JSON.stringify(this.apostas));
    },

    abrirDetalhes(aposta) {
      this.apostaDetalhada = aposta;
    },

    nomePagamento(pagamentoId) {
      const forma = this.formasPagamento.find((item) => item.id === pagamentoId);
      return forma ? `${forma.nome} - ${forma.detalhe}` : "Nao informado";
    },

    textoPagamento(aposta) {
      if (!aposta.pagamentoId) {
        return "Aguardando finalizacao";
      }

      return `Pago com ${this.nomePagamento(aposta.pagamentoId)}`;
    },

    textoStatus(status) {
      const textos = {
        carrinho: "No carrinho",
        finalizada: "Finalizada",
        venceu: "Venceu",
        perdeu: "Perdeu"
      };

      return textos[status] || "Nao informado";
    },

    totalCarrinho() {
      return this.apostas
        .filter((aposta) => aposta.status === "carrinho")
        .reduce((total, aposta) => total + aposta.valor, 0);
    },

    totalPago() {
      return this.apostas
        .filter((aposta) => aposta.status !== "carrinho")
        .reduce((total, aposta) => total + aposta.valor, 0);
    },

    saldoApostado() {
      return this.apostas.reduce((total, aposta) => total + Number(aposta.valor || 0), 0);
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
