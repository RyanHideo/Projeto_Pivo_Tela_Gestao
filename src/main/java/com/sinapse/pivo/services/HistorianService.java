package com.sinapse.pivo.services;

import com.sinapse.pivo.DTO.LineDTO;
import com.sinapse.pivo.DTO.ParameterDTO;
import com.sinapse.pivo.DTO.RecordDTO;
import com.sinapse.pivo.repositories.TagHistorianRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorianService {

    private final TagHistorianRepository histRepo;
    private final EntityManager em;

    public List<LineDTO> listarLinhas() {
        return histRepo.findDistinctLines()
                .stream()
                .map(l -> new LineDTO("Linha " + l,
                        histRepo.findAttributesByLine(l)))
                .toList();
    }

    public List<RecordDTO> buscarLeituras(String linha,
                                          LocalDateTime inicio,
                                          LocalDateTime fim,
                                          Integer qtd) {

        StringBuilder sql = new StringBuilder("""
            SELECT name, SUBSTRING_INDEX(name,' ',1), value,
                   (value/100) AS val_conv,
                   dateOfRecord, engineUnits
            FROM tag_records
            WHERE dateOfRecord BETWEEN :ini AND :fim
            """);

        if (linha != null) {
            String ln = linha.replace("linha ", "").toUpperCase();
            sql.append("""
                AND REGEXP_REPLACE(name,' +',' ')
                    IN ('Potência %1$s','Pressão %1$s',
                        'Volume %1$s ','Volume %1$s')
                """.formatted(ln));
        }
        sql.append(" ORDER BY dateOfRecord DESC");
        if (qtd != null) sql.append(" LIMIT ").append(qtd);

        Query q = em.createNativeQuery(sql.toString(), "RecordMapping");
        q.setParameter("ini", inicio);
        q.setParameter("fim", fim);
        // mapping registrado em orm.xml ou via ResultSetMapping

        @SuppressWarnings("unchecked")
        List<RecordDTO> lista = q.getResultList()
                .stream().sorted(Comparator.comparing(RecordDTO::dataHoraLeitura))
                .toList();
        return lista;
    }

    public ParameterDTO parametros() {
        return new ParameterDTO(10, 20, LocalDate.parse("2025-01-01"));
    }
}
