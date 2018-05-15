package base;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Player {
    private Scanner teclado = new Scanner(System.in);

    private boolean avisar = true;
    private int idPlayer; //Id do jogador
    private int chances; //Chances restantes
    private int tamanho; //Tamanho da palavra

    private String palavra; //Palavra final conforme for se formando

    private Socket socket; //Socket utilizado na comunicação

    private PrintWriter out;
    private Scanner in;

    private boolean jogando;

    public Player() {
        try {
            socket = new Socket("", 12345);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void IniciarPlayer() {
        //Entrada do server
        try {
            in = new Scanner(socket.getInputStream());

            //Saida para o server
            out = new PrintWriter(socket.getOutputStream(), true);
            //Informa ao server que quer se juntar ao jogo
            out.println(Codigos.ENTRANDO_JOGO.getValue());

            //Verifica o retorno recebido
            int resp = in.nextInt();

            //Verifica se ocorreu erro ao entrar na sala
            if (resp == Codigos.ERRO.getValue()) {
                System.out.println("Sala cheia");
            } else {
                //Se o retorno não era erro, então é o ID do player
                idPlayer = resp;

                //Proxima mensagem é o tamanho da respota
                tamanho = in.nextInt();

                //Proxima mensagem é a quantidade de chances
                chances = in.nextInt();

                jogando = true;

                palavra = "";

                for (int i = 0; i < tamanho; i++) {
                    palavra += "_";
                }

                //Inicia a jogada
                IniciarJogada();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void IniciarJogada() {
        String letra;
        while (jogando) {
            //Avisa que vai iniciar uma jogada
            out.println(Codigos.INICIO_JOGADA.getValue());

            //Passa seu ID para verificar se esta no seu turno
            out.println(idPlayer);

            int resp = in.nextInt();

            //Se for sua vez de jogar
            if (resp == Codigos.ESPERANDO_LETRA.getValue()) {
                System.out.println("Informe a letra que deseja jogar.");

                letra = teclado.nextLine();
                //Envia a letra para o servidor
                out.println(letra);

                //Pega o resultado da letra enviada
                resp = in.nextInt();

                //Se acertou a letra
                if (resp == Codigos.ACERTOU_LETRA.getValue()) {
                    //pega o proximo código que será informado
                    resp = in.nextInt();

                    //Pega todos os indeces da letra informada na palavra
                    while (resp == Codigos.INDICE_LETRA.getValue()) {
                        int index = in.nextInt();

                        var chars = palavra.toCharArray();

                        chars[index] = letra.toCharArray()[0];

                        palavra = Arrays.toString(chars);

                        //busca o proximo código para ver se ainda estão informando indices de letras
                        resp = in.nextInt();
                    }

                    //Depois de substituir os caracteres pelas letras corretas, segue com o que deve fazer.
                    if (resp != Codigos.PALPITES.getValue())
                        FimDeJogo(resp);
                    else {
                        System.out.println("Letras ditas até o momento: " + in.nextLine());

                        resp = in.nextInt();

                        if (resp == Codigos.FIM_JOGADA.getValue())
                            System.out.println("Fim da sua rodada.");
                    }
                } else if (resp == Codigos.ERROU_LETRA.getValue()) {
                    System.out.println("Ops, letra errada!");

                    System.out.println("Letras ditas até o momento: " + in.nextLine());

                    FimDeJogo(resp);
                }
                avisar = true;
            } else {
                FimDeJogo(resp);
                avisar = false;
            }
        }
    }

    private void FimDeJogo(int resp) {
        if (resp == Codigos.VITORIA.getValue()) {
            System.out.println("Parabéns! Vocês conseguiram descobrir a palavra.");
            jogando = false;
        } else if (resp == Codigos.DERROTA.getValue()) {
            System.out.println("Que pena! Vocês não conseguiram descobrir a palavra a tempo.");

            resp = in.nextInt();
            if (resp == Codigos.RESPOSTA.getValue())
                System.out.println("A resposta era: " + in.nextLine());

            jogando = false;
        } else if (resp == Codigos.ESPERAR_VEZ.getValue()) {
            if (avisar) {
                System.out.println("Outro jogador ainda está jogando, aguarde sua vez.");
            }
        }
    }
}
