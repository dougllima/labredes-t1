package base;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Host {
    private int qntJogadores; //Quantidade de jogadores.
    private int playerAtual; //Quantidade de jogadores.

    private int chances; //Quantidade de erros permitidos.
    private int tamanho; //Tamanho da resposta.

    private int erros; //Quantidade de palpites errados.
    private int acertos; //Quantidade de palpites certos.

    private String resposta; //Resposta da forca.
    private char[] palpites = new char[26]; //Letras ditas até o momento.

    private ServerSocket server;
    private Socket client; //Socket que será instanciado com os players;

    private PrintWriter out;
    private Scanner in;

    public void Host(int qntJogadores) {
        try {
            this.qntJogadores = qntJogadores;
            this.server = new ServerSocket(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void IniciaServer(int chances, String resposta) {
        this.chances = chances;
        this.resposta = resposta;

        while (true) {
            try {
                //Cria o socket que será utilizado para comunicar com o cliente
                client = this.server.accept();
                //Entrada do cliente
                in = new Scanner(client.getInputStream());
                //Saida para o cliente
                out = new PrintWriter(client.getOutputStream(), true);

                //Verifica se o cliente está tentando iniciar uma jogada.
                if (in.nextInt() == Codigos.INICIO_JOGADA.getValue()) {

                    //Pega a ID do jogador que está iniciando a jogada.
                    int idPlayer = in.nextInt();
                    //Verifica se é a vez dele.
                    if (idPlayer == playerAtual) {
                        //Avisa que está esperando ele enviar a letra.
                        out.println(Codigos.ESPERANDO_LETRA);

                        String entrada = in.nextLine();

                        //Verifica se a entrada enviada corresponde a uma letra apenas
                        if (entrada.length() == 1) {
                            //Trata a resposta do jogador
                            ReceberLetra(entrada.charAt(0));

                            playerAtual++;
                            if (playerAtual > qntJogadores)
                                playerAtual = 1;
                        } else {
                            out.println(Codigos.ERRO.getValue());
                        }
                    } else {
                        out.println(Codigos.ESPERAR_VEZ.getValue());
                    }
                } else {
                    out.println(Codigos.ERRO.getValue());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ReceberLetra(char letra) {
        //Adiciona a letra a lista de palpites realizados;
        palpites[erros + acertos] = letra;

        boolean contains = false;

        //Verifica se existe a letra na palavra;
        contains = this.resposta.indexOf(letra) > -1;

        //Se existe
        if (contains) {
            //Avisa o player que ele acertou a letra;
            out.println(Codigos.ACERTOU_LETRA.getValue());

            //Soma um a lista de letras acertadas;
            this.acertos++;

            //Posição da letra na palavra
            int index = -1;

            //Verifica todas as posições da palavra tras das ocorrencias da letra
            do {
                //Acha o index da letra dentro da resposta
                index = this.resposta.indexOf(letra, index + 1);

                //Envia ao usuário o index da letra que ele acertou
                if(index > -1){
                    out.println(Codigos.INDICE_LETRA.getValue());
                    out.println(index);
                }

            } while (index > -1 || index == (this.tamanho - 1));
            VerificaVitoria();
        } else {
            //Avisa o player que ele errou a letra;
            out.println(Codigos.ERROU_LETRA.getValue());

            //Soma um a lista de letras erradas;
            this.erros++;
        }
        //Avisa que vai enviar a lista de letras ditas;
        out.println(Codigos.PALPITES.getValue());

        //Envia a lista com todas letras que foram ditas até o momento
        out.println(palpites);

        out.println(Codigos.FIM_JOGADA.getValue());
    }

    public void VerificaVitoria(){
        //Verifica se a quantidade de acertos é igual a quantidade de caracteres unicos da resposta;
        if(resposta.chars().distinct().count() == acertos){
            //Avisa ao jogador que o jogo já acabou e ele venceu
            out.println(Codigos.VITORIA.getValue());
        }

    }
}
