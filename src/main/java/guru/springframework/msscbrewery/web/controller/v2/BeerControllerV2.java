package guru.springframework.msscbrewery.web.controller.v2;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.services.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Validated
@Slf4j
@RequestMapping("/api/v2/beer")
@RequiredArgsConstructor
@RestController
public class BeerControllerV2 {
    private final BeerServiceV2 beerServiceV2;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> getBeer(@NotNull @PathVariable UUID beerId){
        return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@Valid @RequestBody BeerDtoV2 beerDto) {
        val saveDto = beerServiceV2.saveNewBeer();

        var headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location", "/api/v2/beer/" +saveDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity handleUpdate(@PathVariable() UUID beerId, @Valid @RequestBody BeerDtoV2 beerDto){
        beerServiceV2.updateBeer(beerId, beerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId){
        beerServiceV2.deleteById(beerId);
    }
}
