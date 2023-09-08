package com.wantfood.aplication.api.controller;

import com.wantfood.aplication.api.assembler.CityDTOAssembler;
import com.wantfood.aplication.api.assembler.CityInputDisassembler;
import com.wantfood.aplication.api.model.CityDTO;
import com.wantfood.aplication.api.model.input.CityInputDTO;
import com.wantfood.aplication.api.openapi.controller.CityControllerOpenApi;
import com.wantfood.aplication.domain.exception.BusinessException;
import com.wantfood.aplication.domain.exception.StateNotFoundException;
import com.wantfood.aplication.domain.repository.CityRepository;
import com.wantfood.aplication.domain.service.CityRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/citys", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerOpenApi {

    private final CityRepository cityRepository;

    private final CityRegistrationService cityRegistrationService;

    private final CityDTOAssembler cityDTOAssembler;

    private final CityInputDisassembler cityInputDisassembler;

    @GetMapping
    public List<CityDTO> list() {
        var allCities = cityRepository.findAll();

        return cityDTOAssembler.toCollectionModel(allCities);
    }

    @GetMapping("/{cityId}")
    public CityDTO find(@PathVariable Long cityId) {
        var city = cityRegistrationService.fetchOrFail(cityId);

        return cityDTOAssembler.toModel(city);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO add(@RequestBody @Valid CityInputDTO cityInputDTO) {

        try {
            var city = cityInputDisassembler.toDomainObject(cityInputDTO);

            city = cityRegistrationService.add(city);

            return cityDTOAssembler.toModel(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cityId}")
    public CityDTO atualizar(@PathVariable Long cityId,
                             @RequestBody @Valid CityInputDTO cityInputDTO) {

        try {
            var currentCity = cityRegistrationService.fetchOrFail(cityId);

            cityInputDisassembler.copyToDomainObject(cityInputDTO, currentCity);

            currentCity = cityRegistrationService.add(currentCity);

            return cityDTOAssembler.toModel(currentCity);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long cityId) {
        cityRegistrationService.delete(cityId);
    }
}
/*
 * Não funciona pois o OAS_30 não tem descriçao nenhuma
 * @ApiParam(name = "body", value = "Representação de uma nova city"), alterando o body
 * */
