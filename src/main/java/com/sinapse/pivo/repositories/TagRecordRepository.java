package com.sinapse.pivo.repositories;

import com.sinapse.pivo.DTO.RecordDTO;
import com.sinapse.pivo.entities.TagRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TagRecordRepository extends JpaRepository<TagRecordEntity, Long> {

    @Query(value = """
        SELECT  name          AS nome_atributo_completo,
                SUBSTRING_INDEX(name,' ',1) AS nome_atributo_simplificado,
                value         AS valor_salvo,
                (value/100)   AS valor_convertido,
                dateOfRecord  AS data_hora_leitura,
                engineUnits   AS unidade_medida
        FROM tag_records
        WHERE dateOfRecord BETWEEN :inicio AND :fim
          /* filtro opcional por linha */
          /*${andLine}*/
        ORDER BY dateOfRecord DESC
        LIMIT :qtd
        """, nativeQuery = true)
    List<RecordDTO> fetchRecords(LocalDateTime inicio,
                                 LocalDateTime fim,
                                 @Param("qtd") int limite,
                                 @Param("andLine") String condicaoLinha);
}

