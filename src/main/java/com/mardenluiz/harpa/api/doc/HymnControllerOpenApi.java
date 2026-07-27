package com.mardenluiz.harpa.api.doc;

import com.mardenluiz.harpa.api.web.dto.HymnDto;
import com.mardenluiz.harpa.api.web.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(
        name = "Hinos",
        description = """
                Endpoints responsáveis pelo gerenciamento e consulta dos hinos da Harpa Cristã.
                
                Funcionalidades disponíveis:
                • Buscar um hino pelo número;
                • Buscar um hino pelo título;
                • Listar todos os hinos com paginação;
                • Limpar o cache Redis dos hinos.
                """
)
public interface HymnControllerOpenApi {

    @Operation(
            summary = "Buscar hino pelo número",
            description = """
                    Retorna todas as informações de um hino da Harpa Cristã.
                    
                    O número informado deve estar entre 1 e 640.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Hino encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HymnDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Número informado é inválido."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hino não encontrado."
            )
    })
    ResponseEntity<HymnDto> findHymnByNumber(

            @Parameter(
                    name = "number",
                    description = "Número do hino.",
                    required = true,
                    example = "15"
            )
            int number

    );



    @Operation(
            summary = "Listar todos os hinos",
            description = """
                    Retorna todos os hinos cadastrados de forma paginada.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista retornada com sucesso."
            )
    })
    ResponseEntity<PageResponse<HymnDto>> findAll(

            @ParameterObject
            Pageable pageable

    );



    @Operation(
            summary = "Buscar hino pelo título",
            description = """
                    Retorna um hino a partir do título informado.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Hino encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HymnDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Título inválido."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hino não encontrado."
            )
    })
    ResponseEntity<HymnDto> findByTitle(

            @Parameter(
                    name = "title",
                    description = "Título do hino.",
                    required = true,
                    example = "Chuvas de Graça"
            )
            @NotBlank(message = "O nome não pode ser nulo ou em branco")
            String title

    );



    @Operation(
            summary = "Limpar cache",
            description = """
                    Remove todas as entradas do cache Redis relacionadas aos hinos.
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