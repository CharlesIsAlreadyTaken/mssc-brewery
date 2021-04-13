package guru.springframework.msscbrewery.web.controller.v2;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.services.v2.BeerServiceV2;
import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v2/beer")
@RequiredArgsConstructor
@RestController
public class BeerControllerV2 {
    private final BeerServiceV2 beerServiceV2;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable UUID beerId){
        return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody BeerDtoV2 beerDto) {
        BeerDtoV2 saveDto = beerServiceV2.saveNewBeer();

        HttpHeaders headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location", "/api/v2/beer/" +saveDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity handleUpdate(@PathVariable() UUID beerId, @RequestBody BeerDtoV2 beerDto){
        beerServiceV2.updateBeer(beerId, beerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId){
        beerServiceV2.deleteById(beerId);
    }
}
