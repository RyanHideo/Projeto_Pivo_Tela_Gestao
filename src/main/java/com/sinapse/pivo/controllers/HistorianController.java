package com.sinapse.pivo.controllers;

import com.sinapse.pivo.DTO.LineDTO;
import com.sinapse.pivo.DTO.ParameterDTO;
import com.sinapse.pivo.DTO.RecordDTO;
import com.sinapse.pivo.services.HistorianService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HistorianController {

    private final HistorianService service;

    @GetMapping("/parametros")
    public ParameterDTO parametros() {
        return service.parametros();
    }

    @GetMapping("/linhas")
    public List<LineDTO> linhas() {
        return service.listarLinhas();
    }

    @GetMapping("/leituras")
    public List<RecordDTO> leituras(
            @RequestParam(required=false) String table,      // "linha 1"…
            @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
            LocalDateTime startDate,
            @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
            LocalDateTime endDate,
            @RequestParam(required=false) Integer qtdBuscar) {

        LocalDateTime inicio = (startDate != null)
                ? startDate
                : LocalDateTime.now().minusMinutes(1);
        LocalDateTime fim    = (endDate   != null)
                ? endDate
                : LocalDateTime.now();

        return service.buscarLeituras(table, inicio, fim, qtdBuscar);
    }
}
