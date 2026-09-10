function loginApp() {
  return {
    mostrarSenha: false,
    aviso: "",
    usuarioLogado: null,
    totalApostas: 0,
    saldoInicial: Number(localStorage.getItem("bolaoSaldoInicial") || 500),

    form: {
      email: "",
      senha: "",
      lembrar: true
    },

    carregarUsuario() {
      this.totalApostas = this.apostasSalvas().length;
      const usuario = this.usuarioSalvo();
      this.usuarioLogado = localStorage.getItem("bolaoSessaoAtiva") === "true" ? usuario : null;

      if (usuario) {
        this.form.email = usuario.email;
      }
    },

    enviarFormulario() {
      if (!this.emailValido(this.form.email)) {
        this.mostrarAviso("Informe um e-mail válido.");
        return;
      }

      if (this.form.senha.length < 4) {
        this.mostrarAviso("A senha deve ter pelo menos 4 caracteres.");
        return;
      }

      const usuario = {
        nome: this.nomePeloEmail(this.form.email),
        email: this.form.email,
        lembrar: this.form.lembrar,
        ultimoAcesso: new Date().toISOString()
      };

      localStorage.setItem("bolaoUsuario", JSON.stringify(usuario));
      localStorage.setItem("bolaoSessaoAtiva", "true");
      this.usuarioLogado = usuario;

      this.mostrarAviso("Login realizado com sucesso.");

      setTimeout(() => {
        window.location.href = "index.html";
      }, 900);
    },

    recuperarSenha() {
      this.mostrarAviso("Recuperação simulada: informe qualquer senha com 4 ou mais caracteres.");
    },

    sair() {
      localStorage.removeItem("bolaoSessaoAtiva");
      this.usuarioLogado = null;
      this.form.senha = "";
      this.mostrarAviso("Você saiu da conta.");
    },

    usuarioSalvo() {
      try {
        return JSON.parse(localStorage.getItem("bolaoUsuario") || "null");
      } catch {
        return null;
      }
    },

    apostasSalvas() {
      try {
        return JSON.parse(localStorage.getItem("bolaoApostas") || "[]");
      } catch {
        return [];
      }
    },

    nomePeloEmail(email) {
      const usuario = this.usuarioSalvo();
      if (usuario?.email === email && usuario?.nome) return usuario.nome;

      return email.split("@")[0]
        .replace(/[._-]+/g, " ")
        .replace(/\b\w/g, (letra) => letra.toUpperCase());
    },

    emailValido(email) {
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
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
