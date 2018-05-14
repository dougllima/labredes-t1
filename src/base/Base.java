package base;

public class Base {

    private int chances; //Quantidade de erros permitidos.
    private int tamanho; //Tamanho da resposta.
    private String resposta; //Resposta da forca.

    private int erros; //Quantidade de palpites errados.
    private int acertos; //Quantidade de palpites certos.
    private String palavra; //Palavra formada até o momento.
    private char[] palpites = new char[26]; //Letras ditas até o momento.

    public void Boss(int chances, String resposta) {
        this.chances = chances;
        this.tamanho = resposta.length();
        this.resposta = resposta;

        //Aguardar conexões
    }

    public void Player(int chances, int tamanho) {
        this.tamanho = tamanho;
        this.chances = chances;

        StringBuilder palavra = new StringBuilder();

        //Adicionando um placeholder para as letras que não foram acertadas ainda.
        for (int i = 0; i < tamanho; i++) {
            palavra.append("_");
        }

        this.palavra = palavra.toString();

    }

    public void EnviarLetra(char letra) {
    //Envia a letra e pega a resposta dos index que contem ela.

    //Simulando retorno;
    }

}

