package base;

public enum Codigos {
    ERRO(0),
    ESPERAR_VEZ(1),
    ESPERANDO_LETRA(1),
    INDICE_LETRA(2),
    INICIO_JOGADA(3),
    FIM_JOGADA(4),
    FIM_JOGO(5),
    DERROTA(6),
    VITORIA(7),
    ACERTOU_LETRA(8),
    ERROU_LETRA(9),
    PALPITES(10),
    RESPOSTA(11);

    private final int value;

    Codigos(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
