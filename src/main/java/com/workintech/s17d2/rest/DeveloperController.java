package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.workintech.s17d2.model.Experience.JUNIOR;
import static com.workintech.s17d2.model.Experience.MID;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    public Map<Integer,Developer> developers;
    private Taxable taxable;


    @Autowired
    public DeveloperController(DeveloperTax taxable){
        this.taxable = taxable;
    }

    @PostConstruct
    public void init(){
        this.developers = new HashMap<>();
        developers.put(1,new Developer(1,"Batu",2500.0, JUNIOR));
    }

    @GetMapping
    public List<Developer> getAllDevelopers(){
        return new ArrayList<>(this.developers.values());
    }


    @GetMapping("/{id}")
    public Developer getDeveloperById(@PathVariable int id){
        return developers.get(id);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addDeveloper(@RequestBody Developer developer){
        double taxRate;
        double salary = developer.getSalary();
        if(developer.getExperience() == JUNIOR){
            taxRate = taxable.getSimpleTaxRate();
            developer.setSalary(salary - (salary * (taxRate / 100)));
        }else if(developer.getExperience() == MID){
            taxRate = taxable.getMiddleTaxRate();
            developer.setSalary(salary - (salary * (taxRate / 100)));
        }else{
            taxRate = taxable.getUpperTaxRate();
            developer.setSalary(salary - (salary * (taxRate / 100)));
        }
        this.developers.put(developer.getId(),developer);
    }


    @PutMapping("/{id}")
    public Developer update(@PathVariable int id,@RequestBody Developer newDeveloper){
        this.developers.replace(id, newDeveloper);
        return this.developers.get(id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        this.developers.remove(id);
    }


}