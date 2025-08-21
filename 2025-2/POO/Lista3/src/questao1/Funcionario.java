
public class Funcionario {

    private String nome;
    private double salario;

    public double calculaIRPF(){
        double vlripo = 0.0;
        double l_vlripo = 0.0;
        double l_aliipo = 0.0;
        double l_faixa = 0.0;
        int qtdFaixa = 0;

        if (salario > 1903.98 && salario <= 2826.65){
            l_aliipo = 0.075;
            l_faixa = 1903.98;
            qtdFaixa = 1;
            l_vlripo = ((salario - l_faixa) * l_aliipo);
        }else if(salario >2826.65 && salario <= 3751.05){//REVER
                l_aliipo = 0.15;
                l_faixa = 2826.65;
                qtdFaixa = 2;
        }else if(salario > 3751.05 && salario <= 4664.68){
                l_aliipo = 0.225;
                l_faixa = 3751.05;
                qtdFaixa = 3;
        }else if(salario > 4664.68){
                l_aliipo = 0.275;
                qtdFaixa = 4;
        }else{
            l_aliipo = 0;
            qtdFaixa = 0;
        }

        
        return vlripo;
    }
    

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
