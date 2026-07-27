package com.mardenluiz.harpa.api.doc;

import com.mardenluiz.harpa.api.web.dto.AudioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;

@Tag(
        name = "Áudios",
        description = """
                Endpoints responsáveis pela consulta e gerenciamento dos áudios da Harpa Cristã.
                
                Esta API permite:
                - Buscar o áudio de um hino pelo número;
                - Limpar o cache Redis utilizado nas consultas de áudio.
                """
)
public interface AudioControllerOpenApi {

    @Operation(
            summary = "Buscar áudio pelo número do hino",
            description = """
                    Retorna os metadados do áudio correspondente ao número do hino informado.
                    
                    O número do hino deve estar compreendido entre **1** e **640**.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Áudio encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AudioDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Número do hino inválido.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Áudio não encontrado.",
                    content = @Content
            )
    })
    ResponseEntity<AudioDto> findByNumber(

            @Parameter(
                    name = "number",
                    description = "Número do hino da Harpa Cristã.",
                    required = true,
                    example = "15"
            )
            int number
    );

    @Operation(
            summary = "Limpar cache de áudios",
            description = """
                    Remove todas as entradas do cache Redis relacionadas aos áudios.
                    
                    Este endpoint deve ser utilizado após atualizações dos arquivos
                    de áudio para garantir que novas consultas retornem informações
                    atualizadas.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Cache removido com sucesso."
            )
    })
    ResponseEntity<Void> cleanCache();

}