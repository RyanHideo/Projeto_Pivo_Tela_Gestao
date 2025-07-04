package com.sinapse.pivo.repositories;

import com.sinapse.pivo.DTO.RecordDTO;
import com.sinapse.pivo.entities.TagHistorianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TagHistorianRepository extends JpaRepository<TagHistorianEntity, Long> {

    // lista "L 1", "L 2", ...
    @Query(value = """
        SELECT DISTINCT SUBSTRING_INDEX(name,' ',-1) AS linha
        FROM tagshistorian
        WHERE LOWER(name) LIKE 'potência l%' 
           OR name LIKE 'pressão l%' 
           OR name LIKE 'volume l%'
        ORDER BY linha
        """, nativeQuery = true)
    List<String> findDistinctLines();

    // atributos de uma linha em formato chave→unidade
    @Query(value = """
        SELECT p.engineUnits AS potencia,
               pr.engineUnits AS pressao,
               v.engineUnits AS volume
        FROM tagshistorian p
        JOIN tagshistorian pr
             ON SUBSTRING_INDEX(p.name,' ',-1)=SUBSTRING_INDEX(pr.name,' ',-1)
        JOIN tagshistorian v
             ON SUBSTRING_INDEX(p.name,' ',-1)=SUBSTRING_INDEX(v.name,' ',-1)
        WHERE LOWER(p.name) LIKE 'potência l%' 
          AND pr.name LIKE 'pressão l%' 
          AND v.name LIKE 'volume l%'
          AND LOWER(p.name) LIKE CONCAT('%', LOWER(:line), '%')
        """, nativeQuery = true)
    Map<String,String> findAttributesByLine(@Param("line") String line);
}

