
public class Funcionario {

    private String nome;
    private double salario;

    public double calculaIRPF(){
        double l_aliipo;
        if (salario > 1903.98 && salario <= 2826.65){
            l_aliipo = 0.075;
        }else if(salario >2826.65 && salario <= 3751.05){//REVER
                l_aliipo = 0.15;
        }else if(salario > 3751.05 && salario <= 4664.68){
                l_aliipo = 0.225;
        }else if(salario > 4664.68){
                l_aliipo = 0.275;
        }else{
            l_aliipo = 0;
        }
        return salario * l_aliipo;
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
