package com.sinapse.pivo.DTO;

import java.time.LocalDate;

public record ParameterDTO(int tempoPoolingSegundos,
                           int qtdPadraoRegistrosCarregados,
                           LocalDate calendarioMinDate) {}
