public class TransacoesBancarias {

    private String agencia;
    private String conta;
    private String banco;
    private String titular;
    private String operacao;
    private String dataHora;
    private double valor;

    public TransacoesBancarias(String agencia, String conta, String banco,
                               String titular, String operacao,
                               String dataHora, double valor) {

        this.agencia = agencia;
        this.conta = conta;
        this.banco = banco;
        this.titular = titular;
        this.operacao = operacao;
        this.dataHora = dataHora;
        this.valor = valor;
    }

    public String getConta() {
        return conta;
    }

    public String getBanco() {
        return banco;
    }

    public String getTitular() {
        return titular;
    }

    public String getOperacao() {
        return operacao;
    }


    public String getDataHora() {
        return dataHora;
    }


    public double getValor() {
        return valor;
    }



    @Override
    public String toString() {

        return titular
                + " | "
                + operacao
                + " | "
                + dataHora
                + " | R$ "
                + valor;
    }

}