package com.sinapse.pivo.DTO;

import java.time.LocalDateTime;

public record RecordDTO(String nomeAtributoCompleto,
                        String nomeAtributoSimplificado,
                        double valorSalvo,
                        double valorConvertido,
                        LocalDateTime dataHoraLeitura,
                        String unidadeMedida) {}
