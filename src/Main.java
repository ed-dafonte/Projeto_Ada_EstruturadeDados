import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {

        // Fonte: Módulo Técnica Programação I
        String nomeArquivo = "BaseDados/TransacaoBancaria.csv";

        try {

            //Lendo o .CSV / Cada elemento do Stream representa uma linha do arquivo.
            Stream<String> stream = Files.lines(Paths.get(nomeArquivo));


            // Set para eliminar linhas duplicadas
            Set<String> dados = stream.collect(Collectors.toSet());


            // Cria uma List para guardar os objetos

            // List para guardar os objetos da classe TransaçõesBancarias.
            List<TransacoesBancarias> transacoes = new ArrayList<>();


            // Separando a linha nos atributos da classe TransaçoesBancarias
            for (String linha : dados) {

                if (linha.contains("VALOR")) { // se encontrar "Valor" no atributo double pula para o próximo. Evitar o cabeçalho.
                    continue;
                }

                String[] campos = linha.split(","); //Array dos atributos. Separa por separação de virgula


                // Cria um objeto da classe TransacoesBancarias. Os dados que estavam separados no array "campos"
                TransacoesBancarias transacao = new TransacoesBancarias(
                        campos[0],
                        campos[1],
                        campos[2],
                        campos[3],
                        campos[4],
                        campos[5],
                        Double.parseDouble(campos[6]));

                // Coloca o objeto dentro da List.
                transacoes.add(transacao);
            }

            // MAP para agrupar por titular

            Map<String, List<TransacoesBancarias>> mapaDeTransacoes = new HashMap<>();


            // Percorre todas as transações que estão na List.
            for (TransacoesBancarias transacao : transacoes) { // Para cada TransacoesBancarias transacao dentro de transacoes

                // Pega o titular da transação atual.
                String titular = transacao.getTitular();


                // Verifica se esse titular ainda não existe no Map.
                if (!mapaDeTransacoes.containsKey(titular)) {

                    // Se não existe, cria uma nova List, para guardar as transações desse titular.
                    List<TransacoesBancarias> listaDoTitular = new ArrayList<>();

                    // Coloca essa List dentro do Map.

                    mapaDeTransacoes.put(titular, listaDoTitular);

                }

                // Recupera do Map a List daquele titular.
                List<TransacoesBancarias> listaDoTitular = mapaDeTransacoes.get(titular);

                // Adiciona a transação na List daquele titular.
                listaDoTitular.add(transacao);
            }


            // Ordenando - INSERTION SORT

            for (List<TransacoesBancarias> lista : mapaDeTransacoes.values()) {

                // Começamos pelo segundo elemento.
                // O primeiro elemento, sozinho, já está "ordenado".
                for (int i = 1; i < lista.size(); i++) {

                    // Guarda a transação que queremos colocar
                    // na posição correta.
                    TransacoesBancarias atual = lista.get(i);

                    // Começamos a comparar com o elemento
                    // imediatamente anterior.
                    int j = i - 1;

                    // Enquanto ainda houver elementos para trás
                    // E a data anterior for maior que a data atual...
                    while (j >= 0
                            && lista.get(j).getDataHora()
                            .compareTo(atual.getDataHora()) > 0) {

                        // Empurra o elemento anterior uma posição
                        // para a direita.
                        lista.set(j + 1, lista.get(j));

                        // Anda uma posição para trás
                        // para continuar procurando o lugar correto.
                        j--;
                    }

                    // Quando encontramos a posição correta,
                    // colocamos a transação nela.
                    lista.set(j + 1, atual);
                }
            }


            // Saldo

            // Percorre todos os titulares existentes no Map.
            for (String titular : mapaDeTransacoes.keySet()) {


                // Pega a List de transações daquele titular.
                List<TransacoesBancarias> lista = mapaDeTransacoes.get(titular);


                // saldo 0
                double saldo = 0;


                // Percorre todas as transações daquele titular.

                for (TransacoesBancarias transacao : lista) { // "Para cada objeto TransacoesBancarias que existe dentro da lista, coloque sua referência na variável transacao."


                    // Se a operação for um depósito, o valor é somado ao saldo.
                    if (transacao.getOperacao()
                            .equalsIgnoreCase("Deposito")) {

                        saldo = saldo + transacao.getValor();
                    }


                    // Se a operação for um saque, o valor é subtraido do saldo.
                    else if (transacao.getOperacao()
                            .equalsIgnoreCase("Saque")) {

                        saldo = saldo - transacao.getValor();
                    }
                }

                //Print
                // Mostra o saldo final daquele titular.
                System.out.println(
                        "Titular: " + titular
                                + " | Saldo final: R$ " + saldo
                );
            }


            // Mostra quantas transações foram transformadas em objetos

            System.out.println(
                    "Quantidade de transações sem duplicidade: "
                            + transacoes.size()
            );


        } catch (IOException e) {

            // Se ocorrer algum problema ao ler o arquivo, mostra a mensagem do erro.

            System.out.println(e);
        }
    }
}