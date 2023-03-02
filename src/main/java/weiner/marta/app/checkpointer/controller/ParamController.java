package weiner.marta.app.checkpointer.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import weiner.marta.app.checkpointer.dto.CheckPointDto;

@RestController
@RequestMapping("/s")
public class ParamController {

    @GetMapping("/params")
    public void getByParameters(@RequestParam("imie") String imie, @RequestParam("wiek") Integer wiek){
        System.out.println(imie);
        System.out.println(wiek);
    }

    @GetMapping("paths/{imie}/{wiek}")
    public void getByPathVariable(@PathVariable("imie") String imie, @PathVariable("wiek") Integer wiek){
        System.out.println(imie);
        System.out.println(wiek);
    }

    @PostMapping("/body")
    public void getByBody(@RequestBody CheckPointDto dto){
        System.out.println(dto);
    }

    @GetMapping("/headers")
    public void getByHeaders(@RequestHeader("imie") String imie, @RequestHeader("wiek") Integer wiek){
        System.out.println(imie);
        System.out.println(wiek);
    }

    @PostMapping("/all-in-one/{nazwisko}/{waga}")
    public void getAll(
            @RequestParam("imie") String imie, @RequestParam("wiek") Integer wiek,
            @PathVariable("nazwisko") String nazwisko, @PathVariable("waga") Integer waga,
            @RequestHeader("drugie") String drugie,
            @RequestBody CheckPointDto dto){
        System.out.println(dto);
        System.out.println(imie);
        System.out.println(nazwisko);
        System.out.println(wiek);
        System.out.println(waga);
        System.out.println(drugie);
    }
}
