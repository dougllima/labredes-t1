package base;

public enum Codigos {
    DERROTA(-2),
    VITORIA(-1),
    ERRO(0),
    ESPERAR_VEZ(1),
    ESPERANDO_LETRA(1),
    INDICE_LETRA(2),
    INICIO_JOGADA(3),
    FIM_JOGADA(4),
    FIM_JOGO(5),
    ACERTOU_LETRA(8),
    ERROU_LETRA(9),
    PALPITES(10),
    RESPOSTA(11),
    ENTRANDO_JOGO(12);

    private final int value;

    Codigos(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
