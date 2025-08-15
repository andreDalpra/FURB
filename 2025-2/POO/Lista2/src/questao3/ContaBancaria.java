package questao3;

public class ContaBancaria {

    private String numero;
    private String titular;
    private double saldo;
    
    public void depositar(double valor){
        if(valor<0){
            System.out.println("Valor não pode ser negativo");
        }
        else{
            valor+=saldo;
            System.out.println("Deposito de: "+valor+" realizado com sucesso");
        }
    }

    public void sacar(double valor){
        if(valor > saldo){
            System.out.println("O valor do saque não pode ser maior que o saldo da conta");
        }
        else if(valor <= 0) {
            System.out.println("Valor inválido para transferência.");
        }
        else{
            valor -= saldo;
            System.out.println("Saque de: "+valor+" realizado com sucesso");
        }  
    }

    public boolean transferir(ContaBancaria conta , double valor){
      if(valor <= 0) {
            System.out.println("Valor inválido para transferência.");
            return false;
      }
      if(valor > saldo){
            System.out.println("O valor do saque não pode ser maior que o saldo da conta");
      }
        this.saldo -= valor;
        conta.saldo += valor;
        System.out.println("Transferência de "+valor+" realizada com sucesso.");
        return true;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
