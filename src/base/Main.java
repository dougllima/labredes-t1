package base;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Bem vindo! \nInforme se deseja criar um novo jogo ou se juntar a um já existente. ");
        System.out.println("1 - Novo jogo.");
        System.out.println("2 - Jogo existente.");

        int resp = in.nextInt();

        if(resp == 1){
            System.out.println("\n");
            System.out.println("Informe a quantidade de jogadores máximos na sala.");

            int qntJogadores = in.nextInt();

            Host host = new Host(qntJogadores);

            System.out.println("\n");
            System.out.println("Informe a quantidade de chances que os jogadores terão.");

            int chances = in.nextInt();

            System.out.println("\n");
            System.out.println("Informe a palavra que será usada na forca.");

            String palavra = in.next();

            System.out.println("Agora é só aguardar os jogadores.");

            host.IniciaServer(chances, palavra);
        }else if(resp == 2){
            Player player = new Player();
            player.IniciarPlayer();
        }
    }
}
